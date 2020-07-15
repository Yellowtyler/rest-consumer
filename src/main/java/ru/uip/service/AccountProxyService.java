package ru.uip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.uip.model.JsonAccount;

@Service
public class AccountProxyService {
    private RestTemplate template;

    private String baseUrl;

    @Autowired
    public AccountProxyService(RestTemplate template, @Value("${account.url}") String baseUrl) {
        this.template = template;
        this.baseUrl = baseUrl;
    }

    public ResponseEntity<JsonAccount> findById(String accountNumber) {
        try {
            ResponseEntity<JsonAccount> jsonAccount = template.getForEntity(baseUrl+ "/account/" + accountNumber, JsonAccount.class);
            return jsonAccount;
        } catch (HttpClientErrorException.NotFound exp) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<JsonAccount> updateAccount(JsonAccount jsonAccount) {
        HttpEntity<JsonAccount> jsonAccountHttpEntity = new HttpEntity<>(jsonAccount);
        try {
            return template.postForEntity(baseUrl + "/account", jsonAccountHttpEntity, JsonAccount.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
