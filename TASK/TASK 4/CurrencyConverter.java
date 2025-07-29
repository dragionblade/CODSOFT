import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyConverter extends JFrame {

    private JComboBox<String> baseCurrencyBox;
    private JComboBox<String> targetCurrencyBox;
    private JTextField amountField;
    private JLabel resultLabel;

    // IMPORTANT: Replace "YOUR_API_KEY" with your actual key from https://www.exchangerate-api.com/
    private final String apiKey = "6d3f3d7f8bab0559caac7f2b";
    private final String apiUrl = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/";

    public CurrencyConverter() {
        // Frame setup
        setTitle("Currency Converter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 10, 10));
        setLocationRelativeTo(null); // Center the window

        // Currency options
        String[] currencies = {"USD", "EUR", "GBP", "JPY", "CAD", "AUD", "INR", "CNY"};

        // UI Components
        add(new JLabel("Base Currency:"));
        baseCurrencyBox = new JComboBox<>(currencies);
        add(baseCurrencyBox);

        add(new JLabel("Target Currency:"));
        targetCurrencyBox = new JComboBox<>(currencies);
        add(targetCurrencyBox);

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        JButton convertButton = new JButton("Convert");
        add(convertButton);

        resultLabel = new JLabel("Result: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(resultLabel);

        // Action Listener for the convert button
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });
    }

    private void convertCurrency() {
        try {
            String baseCurrency = (String) baseCurrencyBox.getSelectedItem();
            String targetCurrency = (String) targetCurrencyBox.getSelectedItem();
            double amount = Double.parseDouble(amountField.getText());

            // Fetching the exchange rate
            URL url = new URL(apiUrl + baseCurrency);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsing JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Check for an error from the API
            if ("error".equals(jsonResponse.optString("result"))) {
                String errorType = jsonResponse.optString("error-type", "An unknown API error occurred.");
                JOptionPane.showMessageDialog(this, "API Error: " + errorType, "API Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JSONObject conversionRates = jsonResponse.getJSONObject("conversion_rates");
            double rate = conversionRates.getDouble(targetCurrency);

            // Calculating and displaying the result
            double convertedAmount = amount * rate;
            resultLabel.setText(String.format("Result: %.2f %s", convertedAmount, targetCurrency));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error fetching conversion rates. Check API key and connection.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CurrencyConverter().setVisible(true);
            }
        });
    }
}
