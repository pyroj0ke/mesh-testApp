package org.app.mesh.services;

import lombok.extern.slf4j.Slf4j;
import org.app.mesh.domains.Account;
import org.app.mesh.repositories.AccountRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService{

    private final AccountRepository accountRepository;

    private final BigDecimal balanceRaiseLimit = new BigDecimal(2.07);
    private final BigDecimal coefficient = new BigDecimal(1.1);

    private List<Account> initAccounts = new ArrayList<>();
    private List<BigDecimal> accountsToReach= new ArrayList<>();

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
        initAccount();
    }

    private void initAccount(){
        initAccounts = getAccounts();
        accountsToReach = initAccounts.stream()
                .map(account -> account.getBalance().multiply(balanceRaiseLimit))
                .collect(Collectors.toList());
        initAccounts.forEach(account->
                log.info("client ID - {}, initial  balance - {},  started at - {}", account.getUser().getId(), account.getBalance().setScale(2, RoundingMode.HALF_UP), formatEventDate()));
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Scheduled(fixedDelay = 30000, initialDelay = 30000)
    @Async
    @Override
    public void incrementBalance() {
        List<Account> accounts = getAccounts();

        accounts.forEach(account-> {
            BigDecimal newBalance = account.getBalance().multiply(coefficient);
            if (!(newBalance.compareTo(accountsToReach.get(accounts.indexOf(account))) > 0)){
                account.setBalance(newBalance);
                log.info("user ID - {}, current  balance - {},  changed at - {}", account.getUser().getId(), account.getBalance().setScale(2, RoundingMode.HALF_UP), formatEventDate());
            }
        });
    }

    private  String formatEventDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
        return formatter.format(LocalDateTime.now());
    }
}
