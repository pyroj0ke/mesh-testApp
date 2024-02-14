package org.app.mesh.repositories;

import org.app.mesh.domains.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    List<Account> getAccountByUserId(Long userId);
}