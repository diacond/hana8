package com.hana8.hanaro.mall.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.History;
import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.enums.ProductType;
import com.hana8.hanaro.mall.enums.Role;
import com.hana8.hanaro.mall.enums.Status;
import com.hana8.hanaro.mall.enums.Term;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("닉네임으로 사용자의 가입 내역을 정확히 조회한다")
    void findAllByUser_Nickname() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("testUser")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        Product product = Product.builder()
                .name("하나 적금")
                .type(ProductType.SAVINGS)
                .depositAmount(10000L)
                .term(Term.MONTHLY)
                .durationMonths(12)
                .maturityYield(new BigDecimal("3.5"))
                .cancellationYield(new BigDecimal("1.0"))
                .onsale(OnSale.YES)
                .build();
        productRepository.save(product);

        Account account = Account.builder()
                .user(user)
                .accountNumber("123-456-78901")
                .balance(10000L)
                .build();
        accountRepository.save(account);

        History history = History.builder()
                .user(user)
                .product(product)
                .account(account)
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        historyRepository.save(history);

        // when
        List<History> results = historyRepository.findAllByUser_Nickname("testUser");

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getUser().getNickname()).isEqualTo("testUser");
        assertThat(results.get(0).getProduct().getName()).isEqualTo("하나 적금");
    }

    @Test
    @DisplayName("닉네임이 일치하지 않으면 빈 리스트를 반환한다")
    void findAllByUser_Nickname_Empty() {
        // when
        List<History> results = historyRepository.findAllByUser_Nickname("nonExistent");

        // then
        assertThat(results).isEmpty();
    }
}
