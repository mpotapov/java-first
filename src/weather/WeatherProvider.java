package weather;
import aircraft.Coordinates;

public class WeatherProvider {

    private static WeatherProvider weatherProvider = new WeatherProvider();
    private static String[] wether = {"RAIN", "SUN", "FOG", "SNOW"};

    private WeatherProvider() {}

    public static WeatherProvider getProvider() {
        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        if (coordinates.getLaitude() > coordinates.getLongitude()) {
            if ((coordinates.getLaitude() - coordinates.getLongitude()) % 2 == 0)
                return (wether[0]);
            return (wether[1]);
        } else {
            if ((coordinates.getLaitude() - coordinates.getLongitude()) % 2 == 0)
                return (wether[2]);
            return (wether[3]);
        }
    }
}
