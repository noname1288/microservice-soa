package com.soa.team_service.repository.HttpClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.soa.team_service.dto.ListMemberResponse;
import com.soa.team_service.dto.RequestGetListMember;


@FeignClient(name = "user-service", url = "${app.services.users}")
public interface UserClient {
    @PostMapping(value = "/list-member", produces = MediaType.APPLICATION_JSON_VALUE)
    ListMemberResponse listMembers(@RequestBody RequestGetListMember requestGetListMember);
}
