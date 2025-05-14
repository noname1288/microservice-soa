package com.soa.task_service.reposity.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.soa.task_service.dto.request.email.RequestSendEmail;


@FeignClient(name = "notification-service", url = "${app.services.notification-service}")
public interface NotificationClient {
    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    void sendEmail(@RequestBody RequestSendEmail request);
} 
