import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherApp {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Weather App");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Load background image
        ImageIcon bgIcon = new ImageIcon("resources/download.jpg");
        JLabel background = new JLabel(bgIcon);
        background.setLayout(new BorderLayout());
        frame.setContentPane(background);

        // Top panel with title
        JLabel title = new JLabel("Weather Forecast", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.BLACK);
        background.add(title, BorderLayout.NORTH);

        // Center panel for input and output
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JTextField cityField = new JTextField(20);
        cityField.setMaximumSize(new Dimension(200, 30));
        cityField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton getWeatherBtn = new JButton("Get Weather");
        getWeatherBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JTextArea outputArea = new JTextArea(12, 40);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setOpaque(false);
        outputArea.setForeground(Color.DARK_GRAY);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(cityField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(getWeatherBtn);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(new JScrollPane(outputArea));

        background.add(centerPanel, BorderLayout.CENTER);

        // Button action
        getWeatherBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText().trim();
                if (city.isEmpty()) {
                    outputArea.setText("❗ Please enter a city name.");
                    return;
                }

                String locationKey = WeatherService.getLocationKey(city);
                if (locationKey == null) {
                    outputArea.setText("❌ Could not find location key for: " + city);
                    return;
                }

                StringBuilder sb = new StringBuilder();
                sb.append("📍 City: ").append(city).append("\n");
                sb.append("📌 Location Key: ").append(locationKey).append("\n\n");

                sb.append(WeatherService.getCurrentWeatherText(locationKey)).append("\n");
                sb.append(WeatherService.getForecastText(locationKey)).append("\n");
                sb.append(WeatherService.getAlertsText(locationKey)).append("\n");

                outputArea.setText(sb.toString());
            }
        });

        frame.setVisible(true);
    }
}
