package ru.uip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.uip.model.JsonAccount;
import ru.uip.model.MoneyTransfer;
import ru.uip.model.MoneyTransferResult;

@Service
public class MoneyTransferService {
    @Autowired
    AccountProxyService accountProxyService;

    public MoneyTransferResult transferMoney(MoneyTransfer moneyTransfer) {
        JsonAccount from = accountProxyService.findById(moneyTransfer.getFromAccountNumber()).getBody();
        JsonAccount to = accountProxyService.findById(moneyTransfer.getToAccountNumber()).getBody();

        from.setAccountBalance(from.getAccountBalance() - moneyTransfer.getAmount());
        to.setAccountBalance(to.getAccountBalance() + moneyTransfer.getAmount());

        accountProxyService.updateAccount(from);
        accountProxyService.updateAccount(to);
        return new MoneyTransferResult(from, to);
    }
}
