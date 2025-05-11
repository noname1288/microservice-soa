package com.soa.team_service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamResponse {
    private Long id;
    private String name;
    // leader
    private Long leaderId;
    // members
    private List<Long> memberIds;
}
