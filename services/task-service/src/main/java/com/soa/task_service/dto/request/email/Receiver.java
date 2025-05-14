package com.soa.task_service.dto.request.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Receiver {
    private Long id;
    private String nameAssignee;
    private String email;
}
