package me.hongbin.generic;

public class NumberUtils {

    public static Long toNegative(Long amount) {
        if (amount > 0) {
            return amount * -1;
        }
        return amount;
    }
}
