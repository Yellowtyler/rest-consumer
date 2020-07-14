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
        try {
            JsonAccount from = accountProxyService.findById(moneyTransfer.getFromAccountNumber()).getBody();
            JsonAccount to = accountProxyService.findById(moneyTransfer.getToAccountNumber()).getBody();
            return ResponseEntity.ok(new MoneyTransferResult(from, to));
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>("Error! Account wasn't found!", HttpStatus.NOT_FOUND);
        }
    }
}
