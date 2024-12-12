package software.ulpgc;


import java.util.List;

public interface CurrencyRepository {
    List<Currency> fetchAll();
    Currency findByCode(String isoCode);
}