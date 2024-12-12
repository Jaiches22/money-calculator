package software.ulpgc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCurrencyRepository implements CurrencyRepository {
    private final File sourceFile;

    public FileCurrencyRepository(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    @Override
    public List<Currency> fetchAll() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sourceFile))) {
            return parse(bufferedReader);
        } catch (IOException e) {
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
