package chap04;

import org.junit.jupiter.api.*;

@DisplayName("@DisplayName 테스트")
public class LifeCycleTest {
    public LifeCycleTest() {
        System.out.println("new LifeCycleTest");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll");
    }

    @BeforeEach
    void SetUp() {
        System.out.println("BeforeEach");
    }

    @DisplayName("Test1")
    @Test
    void test1() {
        System.out.println("Test1");
    }

    @Disabled
    @Test
    void test2() {
        System.out.println("Test2");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("AfterAll");
    }
}
