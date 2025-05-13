package com.soa.team_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.soa.team_service.dto.CreateTeamRequest;
import com.soa.team_service.dto.ListMemberResponse;
import com.soa.team_service.dto.ListTeamResponse;
import com.soa.team_service.dto.RequestGetListMember;
import com.soa.team_service.dto.TeamResponse;
import com.soa.team_service.entity.Team;
import com.soa.team_service.entity.TeamMembership;
import com.soa.team_service.repository.TeamRepository;
import com.soa.team_service.util.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamService {

    private final TeamRepository TeamRepository;
    private final TeamMembershipService teamMembershipService;
    private final WebClient webClient;
    
    // Tạo team
    public void createTeam(CreateTeamRequest request) {
        Team team = Team.builder()
                .name(request.getName())
                .build();
        TeamRepository.save(team);
        log.info("Team created with ID: {}", team.getId());

        teamMembershipService.createTeamMembership(request.getUserId(), team, Role.ADMIN);
    }
    // Xóa team
    public void deleteTeam(Long teamId) {
        TeamRepository.deleteById(teamId);
        log.info("Team deleted with ID: {}", teamId);
    }
    // Lấy chi tiết team theo id
    public TeamResponse getTeam(Long teamId) {
        Team team = TeamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        RequestGetListMember listIdUser = RequestGetListMember.builder()
                .listIdUser(team.getMemberIds())
                .build();
        ListMemberResponse listMemberResponse = webClient.post()
                .uri("http://127.0.0.1:8081/api/users/list-member")
                .bodyValue(listIdUser) 
                .retrieve()
                .bodyToMono(ListMemberResponse.class)
                .block();
        return TeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .leaderId(team.getLeaderId())
                .members(listMemberResponse.getMembers())
                .build();
    }
    // Lấy danh sách team theo userId
    public List<ListTeamResponse> getTeamByUserId(Long userId) {
        List<TeamMembership> memberships = teamMembershipService.findMembershipsByUserId(userId);
        return memberships.stream()
                .map(TeamMembership::getTeam)  
                .filter(team -> team != null)  
                .map(this::mapToListTeamResponse)  
                .collect(Collectors.toList());
    }
    // trả về danh sách team mà người dùng tham gia
    private ListTeamResponse mapToListTeamResponse(Team team) {
        return ListTeamResponse.builder()
                .id(team.getId())
                .name(team.getName())
                .build();
    }

    public Team getTeamById(Long teamId) {
        return TeamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }
}
