package com.develop_mouse.gummy_dang.walkrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.develop_mouse.gummy_dang.walkrecord.domain.entity.Gummy;

public interface GummyRepository extends JpaRepository<Gummy, Long> {
}
