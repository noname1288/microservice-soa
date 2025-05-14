package com.soa.notification_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.soa.notification_service.dto.Receiver;
import com.soa.notification_service.dto.RequestSendEmail;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(RequestSendEmail request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        List<Receiver> receivers = request.getReceivers();
        for (Receiver re : receivers) {
            mailMessage.setTo(re.getEmail());
            mailMessage.setSubject("Thông báo công việc mới!");
            mailMessage.setText(getEmailContent(re, request.getTitleTask(), request.getDescriptionTask(), request.getDueDate()));    
            emailSender.send(mailMessage);
        }
    }

    private String getEmailContent(Receiver re, String titleTask, String descriptionTask, String dueDate) {
        return "Chào " + re.getNameAssignee() + ",\n\n" +
                "Bạn có một công việc mới:\n\n" +
                "Tiêu đề công việc: " + titleTask + "\n" +
                "Mô tả công việc: " + descriptionTask + "\n" +
                "Ngày hết hạn: " + dueDate + "\n\n" +
                "Trân trọng,\n" +
                "Đội ngũ phát triển";
    }
}
