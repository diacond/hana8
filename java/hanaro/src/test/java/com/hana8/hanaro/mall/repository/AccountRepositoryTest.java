package com.hana8.hanaro.mall.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        String unique = String.valueOf(System.nanoTime());
        User user = User.builder()
                .email("test" + unique + "@example.com")
                .password("password")
                .nickname("testUser" + unique)
                .role(Role.USER)
                .build();
        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("계좌번호로 계좌를 조회한다")
    void findByAccountNumber() {
        // given
        String accNo = "999-9999-9999"; // Unique enough
        Account account = Account.builder()
                .user(savedUser)
                .accountNumber(accNo)
                .balance(1000L)
                .build();
        accountRepository.save(account);

        // when
        Optional<Account> found = accountRepository.findByAccountNumber(accNo);

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getAccountNumber()).isEqualTo(accNo);
    }

    @Test
    @DisplayName("계좌번호 중복 여부를 확인한다")
    void existsByAccountNumber() {
        // given
        String accNo = "888-8888-8888";
        Account account = Account.builder()
                .user(savedUser)
                .accountNumber(accNo)
                .balance(1000L)
                .build();
        accountRepository.save(account);

        // when
        boolean exists = accountRepository.existsByAccountNumber(accNo);
        boolean notExists = accountRepository.existsByAccountNumber("000-0000-0000");

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("사용자 ID로 모든 계좌를 조회한다")
    void findAllByUserId() {
        // given
        String accNo1 = "777-7777-7777";
        String accNo2 = "666-6666-6666";
        Account account1 = Account.builder()
                .user(savedUser)
                .accountNumber(accNo1)
                .balance(1000L)
                .build();
        Account account2 = Account.builder()
                .user(savedUser)
                .accountNumber(accNo2)
                .balance(2000L)
                .build();
        accountRepository.saveAll(List.of(account1, account2));

        // when
        List<Account> accounts = accountRepository.findAllByUserId(savedUser.getId());

        // then
        assertThat(accounts).hasSize(2);
        assertThat(accounts).extracting("accountNumber")
                .containsExactlyInAnyOrder(accNo1, accNo2);
    }
}
