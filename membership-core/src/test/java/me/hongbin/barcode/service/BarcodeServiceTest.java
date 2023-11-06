package me.hongbin.barcode.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import me.hongbin.generic.type.LockSupport;
import me.hongbin.user.domain.User;
import me.hongbin.user.domain.UserRepository;

class BarcodeServiceTest {

    private BarcodeService barcodeService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new InMemoryUserRepository();
        barcodeService = new BarcodeService(new InMemoryBarcodeRepository(), () -> 1111111111L,
            userRepository,
            new LockSupport() {
                public void getLock(String key) {

                }

                public void release(String key) {

                }
            }
        );
    }

    @DisplayName("바코드를 생성할 수 있다.")
    @Test
    void create() {
        userRepository.save(new User("김XX"));
        var barcode = barcodeService.create(1L);

        Assertions.assertThat(barcode).isNotNull();
        Assertions.assertThat(barcode.barcode()).hasSize(10);
    }

    @DisplayName("이전에 생성한 바코드가 있다면, 동일한 바코드를 돌려받는다.")
    @Test
    void create2() {
        userRepository.save(new User("김XX"));
        var previous = barcodeService.create(1L);
        var next = barcodeService.create(1L);

        Assertions.assertThat(previous).isEqualTo(next);
    }

    public static class InMemoryUserRepository implements UserRepository {

        private final Map<Long, User> values = new HashMap<>();
        private Long id = 1L;

        @Override
        public Optional<User> findById(Long userId) {
            return Optional.ofNullable(values.get(userId));
        }

        public User save(User user) {
            values.put(id++, user);
            return user;
        }
    }
}