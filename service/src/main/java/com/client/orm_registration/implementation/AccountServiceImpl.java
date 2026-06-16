package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.AccountService;
import com.client.orm_registration.command.AccountRequest;
import com.client.orm_registration.mapper.AccountMapper;
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
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository,
                              ClientRepository clientRepository,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    @Transactional
    public AccountResponse addAccount(Long clientId, AccountRequest request) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        AccountId id = accountMapper.toId(request);

        if (accountRepository.existsById(id)) {

            Account existing = accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            accountMapper.updateEntity(existing, request);

            return accountMapper.toResponse(accountRepository.save(existing));
        }

        Account account = accountMapper.toEntity(request, client);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    @Transactional
    public AccountResponse updateAccount(Integer officeId, Integer clAccSl, AccountRequest request) {

        AccountId id = new AccountId();
        id.setOfficeId(officeId);
        id.setClAccSl(clAccSl);

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        accountMapper.updateEntity(account, request);

        return accountMapper.toResponse(accountRepository.save(account));
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
                .map(accountMapper::toResponse)
                .toList();
    }
}