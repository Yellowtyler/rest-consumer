package ru.uip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.uip.model.JsonAccount;
import ru.uip.model.MoneyTransfer;
import ru.uip.model.MoneyTransferResult;

@Service
public class MoneyTransferService {
    @Autowired
    AccountProxyService accountProxyService;

    public ResponseEntity<MoneyTransferResult> transferMoney(MoneyTransfer moneyTransfer) {
        ResponseEntity<JsonAccount> from = accountProxyService.findById(moneyTransfer.getFromAccountNumber());
        ResponseEntity<JsonAccount> to = accountProxyService.findById(moneyTransfer.getToAccountNumber());

        if(from.getStatusCode() == HttpStatus.OK && to.getStatusCode() == HttpStatus.OK) {
            from.getBody().setAccountBalance(from.getBody().getAccountBalance() - moneyTransfer.getAmount());
            to.getBody().setAccountBalance(to.getBody().getAccountBalance() + moneyTransfer.getAmount());
            if(from.getBody().getAccountBalance() - moneyTransfer.getAmount() < 0) {
                return ResponseEntity.badRequest().build();
            }
            ResponseEntity<JsonAccount> fromUpdate = accountProxyService.updateAccount(from.getBody());
            ResponseEntity<JsonAccount> toUpdate = accountProxyService.updateAccount(to.getBody());

            if(fromUpdate.getStatusCode() == HttpStatus.OK && toUpdate.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok(new MoneyTransferResult(from.getBody(), to.getBody()));
            }
            return ResponseEntity.badRequest().build();
        }
        else if (from.getStatusCode() == HttpStatus.NOT_FOUND ||
                to.getStatusCode() == HttpStatus.NOT_FOUND ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build();
        }
    }
}
