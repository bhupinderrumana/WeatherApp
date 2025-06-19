import java.net.URL;
import java.util.Scanner;
import org.json.*;

public class WeatherService {

    private static final String API_KEY = "keLh8IAxlLrOAdeIuAPLDMTl9dWm396Y";

    // Get Location Key for City
    public static String getLocationKey(String city) {
        try {
            String urlStr = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + API_KEY + "&q=" + city;
            URL url = new URL(urlStr);
            Scanner sc = new Scanner(url.openStream());
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) {
                response.append(sc.nextLine());
            }
            sc.close();

            JSONArray arr = new JSONArray(response.toString());
            if (arr.length() > 0) {
                return arr.getJSONObject(0).getString("Key");
            } else {
                return null;
            }

        } catch (Exception e) {
            System.out.println("Error fetching location key: " + e.getMessage());
            return null;
        }
    }

    // Get current weather
    public static String getCurrentWeatherText(String locationKey) {
        StringBuilder sb = new StringBuilder();
        try {
            String urlStr = "http://dataservice.accuweather.com/currentconditions/v1/" + locationKey + "?apikey=" + API_KEY;
            Scanner sc = new Scanner(new URL(urlStr).openStream());
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) response.append(sc.nextLine());
            sc.close();

            JSONArray arr = new JSONArray(response.toString());
            JSONObject obj = arr.getJSONObject(0);

            sb.append("🌤️ Weather: ").append(obj.getString("WeatherText")).append("\n");
            sb.append("🌡️ Temperature: ").append(obj.getJSONObject("Temperature").getJSONObject("Metric").getDouble("Value")).append(" °C\n");

        } catch (Exception e) {
            sb.append("⚠️ Error getting current weather.\n");
        }
        return sb.toString();
    }

    // Get 3-day forecast
    public static String getForecastText(String locationKey) {
        StringBuilder sb = new StringBuilder();
        try {
            String urlStr = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/" + locationKey + "?apikey=" + API_KEY + "&metric=true";
            Scanner sc = new Scanner(new URL(urlStr).openStream());
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) response.append(sc.nextLine());
            sc.close();

            JSONObject root = new JSONObject(response.toString());
            JSONArray forecasts = root.getJSONArray("DailyForecasts");

            sb.append("📅 5-Day Forecast:\n");
            for (int i = 0; i < forecasts.length(); i++) {
                JSONObject day = forecasts.getJSONObject(i);
                String date = day.getString("Date").split("T")[0];
                double min = day.getJSONObject("Temperature").getJSONObject("Minimum").getDouble("Value");
                double max = day.getJSONObject("Temperature").getJSONObject("Maximum").getDouble("Value");
                String dayDesc = day.getJSONObject("Day").getString("IconPhrase");

                sb.append("• ").append(date).append(": ").append(min).append("°C - ").append(max).append("°C | ").append(dayDesc).append("\n");
            }

        } catch (Exception e) {
            sb.append("⚠️ Error getting forecast.\n");
        }
        return sb.toString();
    }

    // Get alerts (if any)
    public static String getAlertsText(String locationKey) {
        StringBuilder sb = new StringBuilder();
        try {
            String urlStr = "http://dataservice.accuweather.com/alerts/v1/" + locationKey + "?apikey=" + API_KEY;
            Scanner sc = new Scanner(new URL(urlStr).openStream());
            StringBuilder response = new StringBuilder();
            while (sc.hasNext()) response.append(sc.nextLine());
            sc.close();

            JSONArray alerts = new JSONArray(response.toString());
            if (alerts.length() == 0) {
                sb.append("🚨 No weather alerts.\n");
            } else {
                sb.append("🚨 Weather Alerts:\n");
                for (int i = 0; i < alerts.length(); i++) {
                    JSONObject alert = alerts.getJSONObject(i);
                    sb.append("• ").append(alert.getString("Description")).append("\n");
                }
            }

        } catch (Exception e) {
            sb.append("⚠️ Error getting alerts or none available.\n");
        }
        return sb.toString();
    }
}
