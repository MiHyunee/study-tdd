package chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {
    public LocalDate calculateExpiryDate(PayData payData) {
        int addedMonths = calcAddedMonths(payData.getPayAmount());

        if(payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonths);
        }
        else {
            return payData.getBillingDate().plusMonths(addedMonths);
        }
    }

    private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addedMonths) {
        LocalDate candidateDate = payData.getBillingDate().plusMonths(addedMonths);
        final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();

        if(!isSameDayOfMonth(payData.getFirstBillingDate(), candidateDate)) {
            final int lengthOfCandidMonth = candidateDate.lengthOfMonth();
            if(lengthOfCandidMonth < dayOfFirstBilling) {
                return candidateDate.withDayOfMonth(lengthOfCandidMonth);
            }
            return candidateDate.withDayOfMonth(dayOfFirstBilling);
        }
        return candidateDate;
    }

    private int calcAddedMonths(int payAmount) {
        int serviceMonth = (payAmount/100_000)*2;
        int month = payAmount/10_000;
        return serviceMonth+month;
    }

    private boolean isSameDayOfMonth(LocalDate date1, LocalDate date2) {
        return date1.getDayOfMonth()==date2.getDayOfMonth();
    }
}
