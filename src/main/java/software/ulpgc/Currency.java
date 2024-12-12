package software.ulpgc;

import java.util.Objects;

public class Currency {
    private final String isoCode;
    private final String fullName;
    private final String currencySymbol;

    public Currency(String isoCode, String fullName, String currencySymbol) {
        this.isoCode = isoCode;
        this.fullName = fullName;
        this.currencySymbol = currencySymbol;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(isoCode, currency.isoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isoCode);
    }

    @Override
    public String toString() {
        return "Currency{" +
                "isoCode='" + isoCode + '\'' +
                ", fullName='" + fullName + '\'' +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }
}
