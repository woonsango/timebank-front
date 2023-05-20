package com.capstone.timepay.domain.agent;

import com.capstone.timepay.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgentRepository  extends JpaRepository<Agent,Long>, JpaSpecificationExecutor<Agent> {
    Agent findByCreatedUserAndAssignedUser(User createdUser, User assignedUser);
}
