package com.xuan.xuanapigateway;

import com.example.xuanapiclientsdk.utils.SignUtils;
import com.xuan.xuancommon.model.entity.InterfaceInfo;
import com.xuan.xuancommon.model.entity.User;
import com.xuan.xuancommon.service.InnerInterfaceInfoService;
import com.xuan.xuancommon.service.InnerUserInterfaceInfoService;
import com.xuan.xuancommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component// 哇趣别忘了这个
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserServicel;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @DubboReference
    private InnerInterfaceInfoService innerinterfaceInfoService;

    public static final List<String> IP_WHITE_LIST = new ArrayList<>();

    static {
        IP_WHITE_LIST.add("127.0.0.1");
        IP_WHITE_LIST.add("localhost");
    }


    private static final String INTERFACE_HOST = "http://localhost:8123";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();

        String path = INTERFACE_HOST + request.getPath().value();

        System.out.println("请求id：" + request.getId());
        System.out.println("请求路径：" + path);
        System.out.println("请求方法：" + request.getMethodValue());
        System.out.println("请求参数：" + request.getQueryParams());
        System.out.println("请求头：" + request.getHeaders());
        System.out.println("请求来源地址" + request.getRemoteAddress().toString());

        // 2. 访问控制：黑白名单
        String sourceAddress = request.getLocalAddress().getHostString();
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            // 403 禁止访问
            return handleNoAuth(response);
        }

        // 3. 用户鉴权：aksk
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        // 不能直接获取secretKey，这样很容易被截胡到
        // String secretKey = httpServletRequest.getHeader("secretKey");
        String body = headers.getFirst("body");
        String nonce = headers.getFirst("nonce");
        String sign = headers.getFirst("sign");
        String timestamp = headers.getFirst("timestamp");

        // 从数据库中查找accesskey对应的用户
        // if (!accessKey.equals("123")) {
        //     throw new RuntimeException("无权限");
        // }
        User invokeUser = null;
        try {
            invokeUser = innerUserServicel.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.info("getInvokeUser Error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }

        // todo:检测随机数（）正常来说应该是放进redis或者数据库中，这里简单处理
        if (Integer.parseInt(nonce) > 10000) {
            throw new RuntimeException("随机数错误");
        }

        // 检验时间戳与当前时间差距不能超过5分钟
        if (Long.parseLong(timestamp) - System.currentTimeMillis() / 1000 > 5 * 60) {
            throw new RuntimeException("请求超时");
        }

        // 这个时候再检验sign，从数据库中查出secretKey，并且和现在的进行比较
        // String serverSign = SignUtils.genSign(body, "123");
        // if (!sign.equals(serverSign)) {
        //     throw new RuntimeException("无权限");
        // }
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtils.genSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return handleNoAuth(response);
        }

        // 4. todo 请求接口是否存在
        InterfaceInfo interfaceInfo = null;
        String method = request.getMethodValue();

        try {
            interfaceInfo = innerinterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.info("getInterfaceInfo Error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }

        //5. 请求转发，调用模拟接口
        log.info("响应：" + response.getStatusCode());

        // 6. 响应日志
        return handleResponse(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }


    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceId, long userId) {
        try {
            // 获取原始响应对象
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 获取数据缓冲工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 获取响应的状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            // 判断状态码是否为200 OK(按道理来说,现在没有调用,是拿不到响应码的,对这个保持怀疑 沉思.jpg)
            if (statusCode == HttpStatus.OK) {
                // 创建一个装饰后的响应对象(开始穿装备，增强能力)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {

                    // 重写writeWith方法，用于处理响应体的数据
                    // 这段方法就是只要当我们的模拟接口调用完成之后,等它返回结果，
                    // 就会调用writeWith方法,我们就能根据响应结果做一些自己的处理
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 判断响应体是否是Flux类型
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 返回一个处理后的响应体
                            // (这里就理解为它在拼接字符串,它把缓冲区的数据取出来，一点一点拼接好)
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // todo：7. 成功调用次数+1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceId, userId);
                                } catch (Exception e) {
                                    log.info("invokeCount Error", e);
                                }
                                // 读取响应体的内容并转换为字节数组
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info("响应日志：" + data);
                                // 将处理后的内容重新包装成DataBuffer并返回
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 对于200 OK的请求,将装饰后的响应对象传递给下一个过滤器链,并继续处理(设置repsonse对象为装饰过的)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 对于非200 OK的请求，直接返回，进行降级处理
            return chain.filter(exchange);
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    private static Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private static Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -1;
    }
}