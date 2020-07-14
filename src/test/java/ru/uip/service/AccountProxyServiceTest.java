package ru.uip.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.uip.model.EnumAccountStatus;
import ru.uip.model.JsonAccount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL, ids = "ru.uip:producer:+:stubs:9090")
class AccountProxyServiceTest {
    @Autowired
    private AccountProxyService accountProxyService;

    @Test
    public void testShouldGetAccountWithId1() {
        final JsonAccount expectedAccount = new JsonAccount(
                "1",
                "MikeAccount",
                1000,
                EnumAccountStatus.ACTIVE
        );
        JsonAccount resultAccount = accountProxyService.findById("1").getBody();
        assertThat(expectedAccount, equalTo(resultAccount));
    }

    @Test
    public void testShouldGetAccountWithId2() {
        final JsonAccount expectedAccount = new JsonAccount(
                "2",
                "AlexAccount",
                200,
                EnumAccountStatus.INACTIVE
        );
        JsonAccount resultAccount = accountProxyService.findById("2").getBody();
        assertThat(expectedAccount, equalTo(resultAccount));
    }

//    @Test
//    public void testShouldNotFindAccountWithId6() {
//        ResponseEntity<JsonAccount> resultAccount = accountProxyService.findById("6");
//        assertThat(HttpStatus.NOT_FOUND, equalTo(resultAccount.getStatusCode()));
//    }

    @Test
    public void testUpdateAccountWithId1() {
        final JsonAccount mikeAccount = new JsonAccount(
                "1",
                "MikeAccount",
                800,
                EnumAccountStatus.ACTIVE
        );

        ResponseEntity<JsonAccount> actualEntity = accountProxyService.updateAccount(mikeAccount);
        assertThat(HttpStatus.OK, equalTo(actualEntity.getStatusCode()));
    }

    @Test
    public void testUpdateAccountWithId2() {
        final JsonAccount alexAccount = new JsonAccount(
                "2",
                "AlexAccount",
                200,
                EnumAccountStatus.INACTIVE
        );

        ResponseEntity<JsonAccount> actualEntity = accountProxyService.updateAccount(alexAccount);
        assertThat(HttpStatus.OK, equalTo(actualEntity.getStatusCode()));
    }

    @Test
    public void testCreateAccountWithId3() {
        final JsonAccount newAccount = new JsonAccount(
                "3",
                "test",
                2001,
                EnumAccountStatus.ACTIVE
        );

        ResponseEntity<JsonAccount> actualEntity = accountProxyService.updateAccount(newAccount);
        assertThat(HttpStatus.OK, equalTo(actualEntity.getStatusCode()));
    }

}