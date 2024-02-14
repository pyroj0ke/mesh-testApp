package org.app.mesh.services;

import org.app.mesh.domains.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAccounts();

    void incrementBalance();
}
