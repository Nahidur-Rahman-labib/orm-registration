package com.client.orm_registration.controller;

import com.client.orm_registration.dto.AccountRequest;
import com.client.orm_registration.dto.AccountResponse;
import com.client.orm_registration.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/{clientId}/accounts")
    public ResponseEntity<AccountResponse> addAccount(
            @PathVariable Long clientId,
            @RequestBody AccountRequest request
    ) {
        request.setClientId(clientId);
        AccountResponse response = accountService.addAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}/accounts")
    public ResponseEntity<List<AccountResponse>> getAccountsByClientId(@PathVariable Long clientId) {
        List<AccountResponse> response = accountService.getAccountsByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clientId}/accounts/{officeId}/{clAccSl}")
    public ResponseEntity<AccountResponse> updateAccount(
            @PathVariable Long clientId,
            @PathVariable Integer officeId,
            @PathVariable Integer clAccSl,
            @RequestBody AccountRequest request
    ) {
        request.setClientId(clientId);
        AccountResponse response = accountService.updateAccount(officeId, clAccSl, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clientId}/accounts/{officeId}/{clAccSl}")
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long clientId,
            @PathVariable Integer officeId,
            @PathVariable Integer clAccSl
    ) {
        accountService.deleteAccount(officeId, clAccSl);
        return ResponseEntity.noContent().build();
    }
}