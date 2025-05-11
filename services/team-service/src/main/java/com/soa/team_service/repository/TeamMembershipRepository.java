package com.soa.team_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soa.team_service.entity.TeamMembership;

public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {

    TeamMembership findByUserIdAndTeamId(Long userId, Long teamId);
    void deleteByUserIdAndTeamId(Long userId, Long teamId);
    List<TeamMembership> findByUserId(Long userId);

}
