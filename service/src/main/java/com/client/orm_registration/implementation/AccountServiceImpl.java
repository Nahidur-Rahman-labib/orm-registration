package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.AccountService;
import com.client.orm_registration.command.AccountRequest;
import com.client.orm_registration.query.AccountResponse;
import com.client.orm_registration.entity.Account;
import com.client.orm_registration.entity.AccountId;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.repository.AccountRepository;
import com.client.orm_registration.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                              ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public AccountResponse addAccount(Long clientId, AccountRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        AccountId id = new AccountId();
        id.setOfficeId(request.getOfficeId());
        id.setClAccSl(request.getClAccSl());

        if (accountRepository.existsById(id)) {
            Account existing = accountRepository.findById(id).get();
            // update existing instead of throwing
            existing.setAccountTitle(request.getAccountTitle());
            existing.setLimitAmt(request.getLimitAmt());
            accountRepository.save(existing);
            return mapToResponse(existing);
        }

        Account account = new Account();
        account.setId(id);
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
        account.setAccountType(request.getAccountType());

        Account saved = accountRepository.save(account);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Integer officeId, Integer clAccSl, AccountRequest request) {
        AccountId id = new AccountId();
        id.setOfficeId(officeId);
        id.setClAccSl(clAccSl);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Update fields safely
        if (request.getAccountTitle() != null) account.setAccountTitle(request.getAccountTitle());
        if (request.getAccountOpenDt() != null) account.setAccountOpenDt(request.getAccountOpenDt());
        if (request.getEffectiveDt() != null) account.setEffectiveDt(request.getEffectiveDt());
        if (request.getExpiryDt() != null) account.setExpiryDt(request.getExpiryDt());
        if (request.getLimitAmt() != null) account.setLimitAmt(request.getLimitAmt());
        if (request.getEntityId() != null && !request.getEntityId().isEmpty()) account.setEntityId(request.getEntityId());
        if (request.getAccountType() != null && !request.getAccountType().isEmpty()) account.setAccountType(request.getAccountType());

        account.setRecordDt(new Date()); // update record timestamp

        Account updated = accountRepository.save(account);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAccount(Integer officeId, Integer clAccSl) {
        AccountId id = new AccountId();
        id.setOfficeId(officeId);
        id.setClAccSl(clAccSl);

        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Account not found");
        }

        accountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByClientId(Long clientId) {
        return accountRepository.findByClientClientId(clientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AccountResponse mapToResponse(Account account) {
        AccountResponse response = new AccountResponse();
        response.setOfficeId(account.getId().getOfficeId());
        response.setClAccSl(account.getId().getClAccSl());
        response.setAccountNo(account.getAccountNo());
        response.setClientId(account.getClient() != null ? account.getClient().getClientId() : null);
        response.setAccountTitle(account.getAccountTitle());
        response.setAccountOpenDt(account.getAccountOpenDt());
        response.setEffectiveDt(account.getEffectiveDt());
        response.setExpiryDt(account.getExpiryDt());
        response.setLimitAmt(account.getLimitAmt());
        response.setEntityId(account.getEntityId());
        response.setAccountType(account.getAccountType());
        return response;
    }
}