package com.soa.gateway.service;

import com.soa.gateway.dto.request.IntrospectRequest;
import com.soa.gateway.dto.response.IntrospectResponse;
import com.soa.gateway.repository.UserServiceClientRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceClientService {
    UserServiceClientRepository repository;

    public Mono<IntrospectResponse> instrospect(String token){
        return repository.introspect(IntrospectRequest.builder()
                        .token(token)
                .build());
    }
}
