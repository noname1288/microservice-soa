package com.soa.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSendEmail {
    private String nameAssignee;
    private String email;
    private String titleTask;
    private String descriptionTask;
    private String dueDate;
    private String nameTeam;
}
