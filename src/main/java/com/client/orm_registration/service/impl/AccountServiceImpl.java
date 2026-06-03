package com.client.orm_registration.service.impl;

import com.client.orm_registration.dto.AccountRequest;
import com.client.orm_registration.dto.AccountResponse;
import com.client.orm_registration.entity.Account;
import com.client.orm_registration.entity.AccountId;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.repository.AccountRepository;
import com.client.orm_registration.repository.ClientRepository;
import com.client.orm_registration.service.AccountService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public AccountServiceImpl(
            AccountRepository accountRepository,
            ClientRepository clientRepository
    ) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public AccountResponse addAccount(AccountRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + request.getClientId()));

        Integer clAccSl = request.getClAccSl();

        if (clAccSl == null) {
            clAccSl = getNextAccountSerial();
        }

        AccountId accountId = new AccountId();
        accountId.setOfficeId(request.getOfficeId());
        accountId.setClAccSl(clAccSl);

        if (accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account already exists");
        }

        Account account = new Account();
        account.setId(accountId);
        account.setClient(client);
        account.setAccountTitle(request.getAccountTitle());
        account.setAccountOpenDt(request.getAccountOpenDt());
        account.setEffectiveDt(request.getEffectiveDt());
        account.setExpiryDt(request.getExpiryDt());
        account.setLimitAmt(request.getLimitAmt());
        account.setEntityId(request.getEntityId() != null ? request.getEntityId() : "C");
        account.setApproveFlag(0);
        account.setRecordUserId("SYSTEM");
        account.setRecordDt(new Date());

        Account savedAccount = accountRepository.save(account);

        return mapToResponse(savedAccount);
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Integer officeId, Integer clAccSl, AccountRequest request) {
        AccountId accountId = new AccountId();
        accountId.setOfficeId(officeId);
        accountId.setClAccSl(clAccSl);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setAccountTitle(request.getAccountTitle());
        account.setAccountOpenDt(request.getAccountOpenDt());
        account.setEffectiveDt(request.getEffectiveDt());
        account.setExpiryDt(request.getExpiryDt());
        account.setLimitAmt(request.getLimitAmt());
        account.setEntityId(request.getEntityId() != null ? request.getEntityId() : account.getEntityId());
        account.setRecordDt(new Date());

        Account updatedAccount = accountRepository.save(account);

        return mapToResponse(updatedAccount);
    }

    @Override
    @Transactional
    public void deleteAccount(Integer officeId, Integer clAccSl) {
        AccountId accountId = new AccountId();
        accountId.setOfficeId(officeId);
        accountId.setClAccSl(clAccSl);

        if (!accountRepository.existsById(accountId)) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.deleteById(accountId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientClientId(clientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Integer getNextAccountSerial() {
        Number nextVal = (Number) entityManager
                .createNativeQuery("SELECT SEQ_CL_ACC_SL.NEXTVAL FROM DUAL")
                .getSingleResult();

        return nextVal.intValue();
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();

        response.setOfficeId(account.getId().getOfficeId());
        response.setClAccSl(account.getId().getClAccSl());
        response.setAccountNo(account.getAccountNo());

        if (account.getClient() != null) {
            response.setClientId(account.getClient().getClientId());
        }

        response.setAccountTitle(account.getAccountTitle());
        response.setAccountOpenDt(account.getAccountOpenDt());
        response.setEffectiveDt(account.getEffectiveDt());
        response.setExpiryDt(account.getExpiryDt());
        response.setLimitAmt(account.getLimitAmt());
        response.setEntityId(account.getEntityId());

        return response;
    }
}