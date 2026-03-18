package com.hana8.hanaro.mall.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.Transaction;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import com.hana8.hanaro.mall.enums.TransactionType;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private Account toAccount;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .email("test@example.com")
                .password("password")
                .nickname("testUser")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        toAccount = Account.builder()
                .user(user)
                .accountNumber("123-4567-8901")
                .balance(1000L)
                .build();
        accountRepository.save(toAccount);
    }

    @Test
    @DisplayName("특정 계좌로의 입금 내역을 조회한다")
    void findInboundTransactions() {
        // given
        Transaction deposit = Transaction.builder()
                .toAccount(toAccount)
                .amount(500L)
                .type(TransactionType.DEPOSIT)
                .description("입금")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction transfer = Transaction.builder()
                .toAccount(toAccount)
                .amount(300L)
                .type(TransactionType.TRANSFER)
                .description("이체입금")
                .createdAt(LocalDateTime.now())
                .build();

        Transaction withdrawal = Transaction.builder()
                .fromAccount(toAccount)
                .amount(200L)
                .type(TransactionType.WITHDRAW)
                .description("출금")
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.saveAll(List.of(deposit, transfer, withdrawal));

        // when
        List<Transaction> inbound = transactionRepository.findInboundTransactions(toAccount.getId());

        // then
        assertThat(inbound).hasSize(2);
        assertThat(inbound).extracting("type")
                .containsExactlyInAnyOrder(TransactionType.DEPOSIT, TransactionType.TRANSFER);
    }
}
