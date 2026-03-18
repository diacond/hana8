package com.hana8.hanaro.mall.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("이메일로 사용자를 조회한다")
    void findByEmail() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password")
                .nickname("testUser")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByEmail("test@test.com");

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNickname()).isEqualTo("testUser");
    }

    @Test
    @DisplayName("이메일 중복 여부를 확인한다")
    void existsByEmail() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password")
                .nickname("testUser")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByEmail("test@test.com");
        boolean notExists = userRepository.existsByEmail("other@test.com");

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    @Test
    @DisplayName("닉네임 중복 여부를 확인한다")
    void existsByNickname() {
        // given
        User user = User.builder()
                .email("test@test.com")
                .password("password")
                .nickname("testUser")
                .role(Role.USER)
                .build();
        userRepository.save(user);

        // when
        boolean exists = userRepository.existsByNickname("testUser");
        boolean notExists = userRepository.existsByNickname("otherUser");

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}
