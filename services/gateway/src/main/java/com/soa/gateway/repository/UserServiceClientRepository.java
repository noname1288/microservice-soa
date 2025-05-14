package com.soa.gateway.repository;

import com.soa.gateway.dto.request.IntrospectRequest;
import com.soa.gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;


public interface UserServiceClientRepository {
    @PostExchange(url = "/validate", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<IntrospectResponse> introspect (@RequestBody IntrospectRequest request);
}
