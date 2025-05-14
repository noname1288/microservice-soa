package com.soa.task_service.reposity.httpclient;

import com.soa.task_service.dto.response.CheckingLeaderReponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "team-service", url = "${app.services.team-service}")
public interface TeamClient {
    @GetMapping("/is-leader")
    CheckingLeaderReponse isLeader(
            @RequestParam("userId") Long userId,
            @RequestParam("teamId") Long teamId
    );

}
