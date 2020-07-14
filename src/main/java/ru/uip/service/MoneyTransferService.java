package ru.uip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.mongo.ReactiveStreamsMongoClientDependsOnBeanFactoryPostProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.tags.EscapeBodyTag;
import ru.uip.model.JsonAccount;
import ru.uip.model.MoneyTransfer;
import ru.uip.model.MoneyTransferResult;

@Service
public class MoneyTransferService {
    @Autowired
    AccountProxyService accountProxyService;

    public ResponseEntity<?> transferMoney(MoneyTransfer moneyTransfer) {
        JsonAccount from = null;
        JsonAccount to = null;
        try {
            from = accountProxyService.findById(moneyTransfer.getFromAccountNumber()).getBody();
            to = accountProxyService.findById(moneyTransfer.getToAccountNumber()).getBody();
        } catch (HttpClientErrorException e) {
            return new ResponseEntity("Error! Account wasn't found!", HttpStatus.NOT_FOUND);
        }

        if((from.getAccountBalance() - moneyTransfer.getAmount()) >= 0.0) {
            from.setAccountBalance(from.getAccountBalance() - moneyTransfer.getAmount());
            to.setAccountBalance(to.getAccountBalance() + moneyTransfer.getAmount());
        } else {
            return ResponseEntity.badRequest().body("Error! Insufficient funds!");
        }

        accountProxyService.updateAccount(from);
        accountProxyService.updateAccount(to);
        return ResponseEntity.ok(new MoneyTransferResult(from, to));
    }
}
