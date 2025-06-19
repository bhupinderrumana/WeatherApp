import org.json.JSONObject;
import java.net.URL;
import java.util.Scanner;

public class LocationService {
    public static String getUserCity() {
        try {
            URL url = new URL("http://ip-api.com/json");
            Scanner sc = new Scanner(url.openStream());
            StringBuilder json = new StringBuilder();
            while (sc.hasNext()) json.append(sc.nextLine());
            sc.close();

            JSONObject obj = new JSONObject(json.toString());
            return obj.getString("city");
        } catch (Exception e) {
            e.printStackTrace();
            return "Unknown";
        }
    }
}
