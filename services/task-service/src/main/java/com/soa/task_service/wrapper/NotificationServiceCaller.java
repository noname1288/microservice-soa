package com.soa.task_service.wrapper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.soa.task_service.dto.request.email.RequestSendEmail;
import com.soa.task_service.reposity.httpclient.NotificationClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@EnableAsync
public class NotificationServiceCaller {

    private final NotificationClient notificationClient;

    @Async
    public void sendEmailAsync(RequestSendEmail request) {
        try {
            notificationClient.sendEmail(request); // vẫn gọi sync, nhưng chạy trong thread riêng
        } catch (Exception e) {
            // log lỗi hoặc gửi lại sau
            System.err.println("Gửi email lỗi: " + e.getMessage());
        }
    }
}

