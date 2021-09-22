package com.empik.githubadapter.repository;

import com.empik.githubadapter.entity.RequestCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestCountEntityRepository extends JpaRepository<RequestCountEntity, String> {
}
