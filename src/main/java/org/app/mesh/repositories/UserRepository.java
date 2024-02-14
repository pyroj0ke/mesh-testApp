package org.app.mesh.repositories;

import org.app.mesh.domains.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findByNameContainingIgnoreCaseOrderByName(Pageable pageable, String name);

    Page<User> findByBirthdateAfterOrderByBirthdate(Pageable pageable, LocalDate birthdate);
}
