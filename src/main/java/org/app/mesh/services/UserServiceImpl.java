package org.app.mesh.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.mesh.domains.Account;
import org.app.mesh.domains.Mail;
import org.app.mesh.domains.Phone;
import org.app.mesh.domains.User;
import org.app.mesh.repositories.AccountRepository;
import org.app.mesh.repositories.MailRepository;
import org.app.mesh.repositories.PhoneRepository;
import org.app.mesh.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final MailRepository mailRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public Page<User> getUsersByName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByNameContainingIgnoreCaseOrderByName(pageable, name);
    }

    @Override
    public Page<User> getUsersByBirthdate(int page, int size, LocalDate birthdate) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findByBirthdateAfterOrderByBirthdate(pageable, birthdate);
    }

    @Cacheable(value = "userByPhoneCache", key = "#phone")
    @Override
    public User getUserByPhone(String phone) {
        Phone userPhone = phoneRepository.getFirstByPhone(phone).orElseThrow(() -> new RuntimeException("Phone " + phone + " not found"));
        return userPhone.getUser();
    }

    @Override
    public User getUserByMail(String email) {
        Mail userMail = mailRepository.getFirstByEmail(email).orElseThrow(() -> new RuntimeException("Email " + email + " not found"));
        return userMail.getUser();
    }

    @Override
    public boolean moneyTransfer(long sourceId, long recipientId, float amount) {
        Account sender ;
        Account recipient;

        if(Float.isInfinite(amount))
            throw new RuntimeException("Infinite balance");

        if(Float.isNaN(amount))
            throw new RuntimeException("NaN balance");

        BigDecimal amountAsBigDecimal = new BigDecimal(amount);

        List<Account> senderAccounts = accountRepository.getAccountByUserId(sourceId);
        List<Account> recipientAccounts = accountRepository.getAccountByUserId(recipientId);

        if (senderAccounts.isEmpty()) {
            throw new RuntimeException("Sender account not found");
        } else {
            sender = senderAccounts.get(0);
        }

        if (recipientAccounts.isEmpty()) {
            throw new RuntimeException("Recipient account not found");
        } else {
            recipient = recipientAccounts.get(0);
        }

        if (sender.getBalance().compareTo(amountAsBigDecimal) >= 0 ) {
            sender.getAccountLock().writeLock().lock();
            recipient.getAccountLock().writeLock().lock();
            sender.setBalance(sender.getBalance().subtract(amountAsBigDecimal));
            sender.getAccountLock().writeLock().unlock();
            recipient.setBalance(recipient.getBalance().add(amountAsBigDecimal));
            recipient.getAccountLock().writeLock().unlock();
            return true;
        } else {
            throw new RuntimeException("Not enough money");
        }
    }
}
