package com.soa.team_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.soa.team_service.dto.CreateTeamRequest;
import com.soa.team_service.dto.ListTeamResponse;
import com.soa.team_service.dto.TeamResponse;
import com.soa.team_service.entity.Team;
import com.soa.team_service.service.TeamMembershipService;
import com.soa.team_service.service.TeamService;
import com.soa.team_service.util.Role;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping()
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMembershipService teamMembershipService;

    //tạo team
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void CreateTeam(@RequestBody CreateTeamRequest request) {
        teamService.createTeam(request);
    }

    //lấy danh sách team theo userId
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ListTeamResponse> getTeamByUserId(@RequestParam("userId") Long userId) {
        return teamService.getTeamByUserId(userId);
    }

    //lấy team theo id
    @GetMapping("/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public TeamResponse getTeam(@PathVariable("teamId") Long teamId) {
        return teamService.getTeam(teamId);
    }

    //xóa team
    @DeleteMapping("/{teamId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTeam(@PathVariable("teamId") Long teamId) {
        teamService.deleteTeam(teamId);
    }

    //thêm thành viên
    @PostMapping("/add-member")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMember(@RequestParam("userId") Long userId, @RequestParam("teamId") Long teamId) {
        Team team = teamService.getTeamById(teamId);
        teamMembershipService.createTeamMembership(userId, team, Role.MEMBER);
    }

    //xóa thành viên
    @DeleteMapping("/remove-member")
    @ResponseStatus(HttpStatus.OK)
    public void removeMember(@RequestParam("userId") Long userId, @RequestParam("teamId") Long teamId) {
        teamMembershipService.removeMember(userId, teamId);
    }
    
    //kiểm tra leader
    @GetMapping("/is-leader")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> isLeader(@RequestParam("userId") Long userId, @RequestParam("teamId") Long teamId) {
        boolean isLeader = teamMembershipService.isLeader(userId, teamId);
        return Map.of("isLeader", isLeader);
    }
    

}
