package com.soa.notification_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.soa.notification_service.dto.RequestSendEmail;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(RequestSendEmail request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(request.getEmail());  // Địa chỉ email người nhận
        mailMessage.setSubject("Chào " + request.getNameAssignee() 
                                +"!\n"+"Bạn có một công việc mới từ nhóm" + request.getNameTeam()
                            );
        mailMessage.setText(request.getTitleTask() 
                            +"\n"+ "Mô tả công việc: " + request.getDescriptionTask() 
                            + "\n" + "Ngày hết hạn: " + request.getDueDate()
                        );  // Nội dung email
        emailSender.send(mailMessage);  // Gửi email
    }
}
