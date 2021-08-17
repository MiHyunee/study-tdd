package chap02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test  //테스트 메소드 인식
    void plus() {
        int result = Calculator.plus(1, 2);
        assertEquals(3, result);  //검증
        assertEquals(5, Calculator.plus(4, 1));
    }
}
