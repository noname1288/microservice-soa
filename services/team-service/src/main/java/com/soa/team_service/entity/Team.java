package com.soa.team_service.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true) // TeamMembership is the owning side
    private List<TeamMembership> memberships; // Các thành viên trong nhóm

    // Team.java
    public Long getLeaderId() {
        return memberships.stream()
                .filter(member -> "ADMIN".equalsIgnoreCase(member.getRole()))
                .map(TeamMembership::getUserId)
                .findFirst()
                .orElse(null);
    }

    public List<Long> getMemberIds() {
        Long leaderId = getLeaderId();
        return memberships.stream()
                .filter(member -> !member.getUserId().equals(leaderId))
                .map(TeamMembership::getUserId)
                .toList();
    }

}
