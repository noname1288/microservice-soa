package com.soa.task_service.reposity.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.soa.task_service.dto.request.email.ListMemberResponse;
import com.soa.task_service.dto.request.email.RequestGetListMember;

@FeignClient(name = "user-service", url = "${app.services.user-service}")
public interface UserClient {
    @PostMapping(value = "/list-member", produces = MediaType.APPLICATION_JSON_VALUE)
    ListMemberResponse listMembers(@RequestBody RequestGetListMember requestGetListMember);
}
