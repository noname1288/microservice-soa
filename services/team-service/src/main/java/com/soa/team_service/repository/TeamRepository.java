package com.soa.team_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soa.team_service.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
