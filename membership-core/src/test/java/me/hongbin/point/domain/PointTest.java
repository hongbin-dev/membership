package me.hongbin.point.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import me.hongbin.generic.exception.BusinessException;
import me.hongbin.partner.domain.Category;

class PointTest {

    @DisplayName("포인트를 차감할 수 있다")
    @Test
    void subtract() {
        var point = new Point(5000L, Category.A, 1L);

        point.subtract(1000L);

        assertThat(point.getAmount()).isEqualTo(4000L);
    }

    @DisplayName("포인트 차감시 양수가 아니면 예외가 발생한다.")
    @ValueSource(longs = {-1L, 0})
    @ParameterizedTest
    void subtract2(Long value) {
        var point = new Point(5000L, Category.A, 1L);

        assertThatThrownBy(() -> point.subtract(value))
            .isInstanceOf(BusinessException.class)
            .hasMessageEndingWith("사용할 포인트를 1원이상 입력해주세요");
    }

    @DisplayName("포인트 차감시 기존 포인트보다 많을 시 예외가 발생한다")
    @Test
    void subtrac3() {
        var point = new Point(5000L, Category.A, 1L);

        assertThatThrownBy(() -> point.subtract(5001L))
            .isInstanceOf(BusinessException.class)
            .hasMessage("포인트 사용량을 초과했습니다. total=5000, request=5001");
    }

    @DisplayName("포인트를 추가할 수 있다")
    @Test
    void plus() {
        var point = new Point(5000L, Category.A, 1L);

        point.plus(1000L);

        assertThat(point.getAmount()).isEqualTo(6000L);
    }

    @DisplayName("포인트 추가시 양수가 아닌경우 예외가 발생한다.")
    @ValueSource(longs = {-1L, 0})
    @ParameterizedTest
    void plus2(Long value) {
        var point = new Point(5000L, Category.A, 1L);

        assertThatThrownBy(() -> point.plus(value))
            .isInstanceOf(BusinessException.class)
            .hasMessageEndingWith("적립할 포인트를 1원이상 입력해주세요");
    }
}