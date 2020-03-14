package com.estee.na.repository;

import com.estee.na.domain.BoilerplateTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoilerplateTestRepository extends JpaRepository<BoilerplateTest, Long> {
}
