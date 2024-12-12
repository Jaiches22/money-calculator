package software.ulpgc;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        CurrencyRepository repository = new FileCurrencyRepository(new File("currencies_data.tsv"));
        List<Currency> availableCurrencies = repository.fetchAll();

        System.out.println("Available Currencies:");
        for (Currency currency : availableCurrencies) {
            System.out.println(currency);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter base currency code: ");
        String baseCode = scanner.nextLine().toUpperCase();
        Currency baseCurrency = repository.findByCode(baseCode);

        System.out.print("Enter target currency code: ");
        String targetCode = scanner.nextLine().toUpperCase();
        Currency targetCurrency = repository.findByCode(targetCode);

        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        // Mocked conversion rate for demonstration
        Rate rate = new Rate(baseCurrency, targetCurrency, LocalDate.now(), 1.2);
        double convertedAmount = rate.convertAmount(amount);

        System.out.println("Converted Amount: " + convertedAmount + " " + targetCurrency.getCurrencySymbol());
    }
}

