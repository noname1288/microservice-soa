package com.soa.task_service.dto.request.email;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListMemberResponse {
    String success;
    List<Receiver> members;
}
