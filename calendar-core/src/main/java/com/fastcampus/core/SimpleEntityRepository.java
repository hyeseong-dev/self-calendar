package com.fastcampus.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, Long> {
}
