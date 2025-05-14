package com.soa.gateway.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.soa.gateway.dto.ApiResponse;
import com.soa.gateway.service.UserServiceClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    UserServiceClientService userServiceClientService;
    ObjectMapper objectMapper;

    List<String> excludedUrls = List.of( // list url không bị chặn
            "/api/user/login",
            "/api/user/register",
            "/api/user/validate"
    );



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Enter authentication filter......");

        //Kiểm tra path của request trước khi xác thực
        String path = exchange.getRequest().getPath().value();

        // ✅ Nếu là đường dẫn được loại trừ, bỏ qua xác thực
        if (excludedUrls.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        //get token from authorization header
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader)) {
            return unAuthenticated(exchange.getResponse());
        }

        String token = authHeader.getFirst().replace("Bearer ", "");
        log.info("token: " + token);

        //verify token
        //delegate user-service


        return userServiceClientService.instrospect(token)
                .flatMap(introspect -> {
                    if (introspect.isSuccess()) {
                        // tiếp tục
                        return chain.filter(exchange);
                    } else {
                        return unAuthenticated(exchange.getResponse());
                    }
                }).onErrorResume(throwable -> unAuthenticated(exchange.getResponse())); // neu 5xx CŨNG BỊ CHẶN
    }

    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unAuthenticated(ServerHttpResponse response){
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(4444)
                .message("Unauthorized")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(body.getBytes()))
        );
    }

}

