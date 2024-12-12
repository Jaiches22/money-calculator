package software.ulpgc;

import java.time.LocalDate;

public class Rate {
    private final Currency baseCurrency;
    private final Currency targetCurrency;
    private final LocalDate exchangeDate;
    private final double conversionRate;

    public Rate(Currency baseCurrency, Currency targetCurrency, LocalDate exchangeDate, double conversionRate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeDate = exchangeDate;
        this.conversionRate = conversionRate;
    }

    public double convertAmount(double amount) {
        return amount * conversionRate;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "baseCurrency=" + baseCurrency +
                ", targetCurrency=" + targetCurrency +
                ", exchangeDate=" + exchangeDate +
                ", conversionRate=" + conversionRate +
                '}';
    }
}

