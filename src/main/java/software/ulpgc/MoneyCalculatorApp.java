package software.ulpgc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MoneyCalculatorApp {

    private final CurrencyRepository repository;

    public MoneyCalculatorApp() {
        repository = new FileCurrencyRepository("currencies_data.tsv");
    }

    public void start() {
        List<Currency> currencies = repository.fetchAll();

        JFrame frame = new JFrame("Money Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel baseCurrencyLabel = new JLabel("Base Currency:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(baseCurrencyLabel, gbc);

        JComboBox<String> baseCurrencyCombo = new JComboBox<>();
        currencies.forEach(currency -> baseCurrencyCombo.addItem(currency.getIsoCode()));
        gbc.gridx = 1;
        frame.add(baseCurrencyCombo, gbc);

        JLabel targetCurrencyLabel = new JLabel("Target Currency:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(targetCurrencyLabel, gbc);

        JComboBox<String> targetCurrencyCombo = new JComboBox<>();
        currencies.forEach(currency -> targetCurrencyCombo.addItem(currency.getIsoCode()));
        gbc.gridx = 1;
        frame.add(targetCurrencyCombo, gbc);

        JLabel amountLabel = new JLabel("Amount:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(amountLabel, gbc);

        JTextField amountField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(amountField, gbc);

        JButton convertButton = new JButton("Convert");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(convertButton, gbc);

        JLabel resultLabel = new JLabel();
        gbc.gridy = 4;
        frame.add(resultLabel, gbc);

        convertButton.addActionListener((ActionEvent e) -> {
            try {
                String baseCode = (String) baseCurrencyCombo.getSelectedItem();
                String targetCode = (String) targetCurrencyCombo.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());

                if (baseCode == null || targetCode == null) {
                    resultLabel.setText("Please select both currencies.");
                    return;
                }

                Currency baseCurrency = repository.findByCode(baseCode);
                Currency targetCurrency = repository.findByCode(targetCode);

                Rate rate = new Rate(baseCurrency, targetCurrency, null, 1.2);
                double convertedAmount = rate.convertAmount(amount);

                resultLabel.setText(String.format("Converted Amount: %.2f %s", convertedAmount, targetCurrency.getCurrencySymbol()));
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoneyCalculatorApp().start());
    }
}
