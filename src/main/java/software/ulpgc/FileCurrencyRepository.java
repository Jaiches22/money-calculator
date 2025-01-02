package software.ulpgc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileCurrencyRepository implements CurrencyRepository {
    private final String resourcePath;

    public FileCurrencyRepository(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public List<Currency> fetchAll() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            return parse(bufferedReader);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Failed to load currency data", e);
        }
    }

    @Override
    public Currency findByCode(String isoCode) {
        return fetchAll().stream()
                .filter(currency -> currency.getIsoCode().equalsIgnoreCase(isoCode))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Currency with code " + isoCode + " not found"));
    }

    private List<Currency> parse(BufferedReader reader) throws IOException {
        List<Currency> currencies = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null) {
            currencies.add(parseLine(line));
        }

        return currencies;
    }

    private Currency parseLine(String line) {
        String[] data = line.split("\t");
        String isoCode = data[0];
        String fullName = data[1];
        String symbol = data.length > 2 ? data[2] : "";
        return new Currency(isoCode, fullName, symbol);
    }
}
