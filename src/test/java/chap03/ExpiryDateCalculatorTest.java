package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부하면_만료일_한달뒤() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 8, 1))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2021, 9, 1)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 9, 10))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2021,10, 10)
        );

    }

    @Test
    void 납부일과_만료일_한달뒤_날짜_같지_않음() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 8, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2021, 9, 30)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2021, 2, 28)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 1, 31))
                        .payAmount(10_000)
                        .build(),
                LocalDate.of(2020, 2, 29)
        );
    }

    @Test
    void 첫_납부일과_만료일_한달뒤_날짜_같지_않을때_만원_납부() {
        PayData payData = PayData.builder()
                .firstBillingDate(LocalDate.of(2020, 1, 31))
                .billingDate(LocalDate.of(2020, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData, LocalDate.of(2020, 3, 31));

        PayData payData2 = PayData.builder()
                .firstBillingDate(LocalDate.of(2021, 1, 30))
                .billingDate(LocalDate.of(2021, 2, 28))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData2, LocalDate.of(2021, 3, 30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2021, 5, 31))
                .billingDate(LocalDate.of(2021, 6, 30))
                .payAmount(10_000)
                .build();

        assertExpiryDate(payData3, LocalDate.of(2021, 7, 31));
    }

    @Test
    void 이만원_이상_납부하면_만료일_비례해서_계산() {
        PayData payData = PayData.builder()
                .billingDate(LocalDate.of(2021, 5, 31))
                .payAmount(20_000)
                .build();
        assertExpiryDate(payData, LocalDate.of(2021, 7, 31));

        PayData payData2 = PayData.builder()
                .billingDate(LocalDate.of(2021, 1, 30))
                .payAmount(30_000)
                .build();
        assertExpiryDate(payData2, LocalDate.of(2021, 4, 30));

        PayData payData3 = PayData.builder()
                .firstBillingDate(LocalDate.of(2021, 1, 31))
                .billingDate(LocalDate.of(2021, 2, 28))
                .payAmount(20_000)
                .build();
        assertExpiryDate(payData3, LocalDate.of(2021, 4, 30));
    }

    @Test
    void 십만원_납부하면_1년_제공() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 1, 1))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2022, 1, 1)
        );
    }

    @Test
    void 십만원_이상_납부하면_만료일_비례해서_추가_제공() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 1, 1))
                        .payAmount(130_000)
                        .build(),
                LocalDate.of(2022, 4, 1)
        );

        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2021, 1, 1))
                        .payAmount(220_000)
                        .build(),
                LocalDate.of(2023, 3, 1)
        );
    }

    @Test
    void 윤달에_십만원_납부() {
        assertExpiryDate(
                PayData.builder()
                        .billingDate(LocalDate.of(2020, 2, 29))
                        .payAmount(100_000)
                        .build(),
                LocalDate.of(2021, 2, 28)
        );
    }

    private void assertExpiryDate(PayData payData, LocalDate expiryDate) {
        ExpiryDateCalculator calculator = new ExpiryDateCalculator();
        LocalDate actualExypiryDate = calculator.calculateExpiryDate(payData);

        assertEquals(expiryDate, actualExypiryDate);
    }
}
