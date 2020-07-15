package ru.uip.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.uip.model.MoneyTransfer;
import ru.uip.model.MoneyTransferResult;
import ru.uip.service.MoneyTransferService;

import javax.validation.Valid;

@RestController
@RequestMapping("/transfer")
public class MoneyTransferController {
    @Autowired
    private MoneyTransferService moneyTransferService;

    @PostMapping
    public ResponseEntity<MoneyTransferResult> transfer(@RequestBody @Valid MoneyTransfer moneyTransfer) {
        ResponseEntity<MoneyTransferResult> moneyTransferResult = moneyTransferService.transferMoney(moneyTransfer);
        return moneyTransferResult;
    }
}
