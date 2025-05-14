package com.soa.task_service.dto.request.email;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestSendEmail {
    private List<Receiver> Receivers;
    private String titleTask;
    private String descriptionTask;
    private String dueDate;
}
