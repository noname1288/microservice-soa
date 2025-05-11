package com.soa.team_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.soa.team_service.entity.Team;
import com.soa.team_service.entity.TeamMembership;
import com.soa.team_service.repository.TeamMembershipRepository;
import com.soa.team_service.util.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeamMembershipService {

    private final TeamMembershipRepository teamMembershipRepository;

    public void createTeamMembership(Long userId, Team team, Role role) {
        TeamMembership teamMembership = TeamMembership.builder()
                .userId(userId)
                .team(team)
                .role(role.toString())
                .build();

        teamMembershipRepository.save(teamMembership);
        log.info("Team membership created with ID: {}", teamMembership.getId());
    }

    public boolean isLeader(Long userId, Long teamId) {
        TeamMembership teamMembership = teamMembershipRepository.findByUserIdAndTeamId(userId, teamId);
        return teamMembership != null && Role.ADMIN.toString().equals(teamMembership.getRole());
    }

    public List<TeamMembership> findMembershipsByUserId(Long userId) {
        return teamMembershipRepository.findByUserId(userId);
    }

    public void removeMember(Long userId, Long teamId) {
        teamMembershipRepository.deleteByUserIdAndTeamId(userId, teamId);
        log.info("Team membership removed for user ID: {} in team ID: {}", userId, teamId);
    }

}
