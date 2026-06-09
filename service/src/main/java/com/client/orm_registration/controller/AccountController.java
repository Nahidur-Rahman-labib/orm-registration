package com.client.orm_registration.controller;

import com.client.orm_registration.contract.AccountService;
import com.client.orm_registration.command.AccountRequest;
import com.client.orm_registration.query.AccountResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{id}/accounts")
    public ResponseEntity<AccountResponse> addAccount(@PathVariable Long id, @RequestBody AccountRequest request) {
        request.setClientId(id);
        return ResponseEntity.ok(accountService.addAccount(id, request));
    }

    @GetMapping("/{id}/accounts")
    public ResponseEntity<List<AccountResponse>> getAccounts(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountsByClientId(id));
    }

    @PutMapping("/{id}/accounts/{officeId}/{clAccSl}")
    public ResponseEntity<AccountResponse> updateAccount(@PathVariable Long id, @PathVariable Integer officeId,
                                                         @PathVariable Integer clAccSl, @RequestBody AccountRequest request) {
        request.setClientId(id);
        return ResponseEntity.ok(accountService.updateAccount(officeId, clAccSl, request));
    }

    @DeleteMapping("/{id}/accounts/{officeId}/{clAccSl}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id, @PathVariable Integer officeId,
                                              @PathVariable Integer clAccSl) {
        accountService.deleteAccount(officeId, clAccSl);
        return ResponseEntity.noContent().build();
    }
}