package org.app.mesh.services;

import org.app.mesh.domains.User;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface UserService {

    Page<User> getUsersByName(int page, int size, String name);

    Page<User> getUsersByBirthdate(int page, int size, LocalDate birthdate);

    User getUserByPhone(String phone);

    User getUserByMail(String email);

    boolean moneyTransfer(long sourceId, long recipientId, float amount);
}
