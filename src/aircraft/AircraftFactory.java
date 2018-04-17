package aircraft;

public class AircraftFactory {

    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) {
        String low_type = type.toLowerCase();

        switch (low_type) {
            case "baloon":
                return (new Baloon(name, new Coordinates(longitude, latitude, height)));
            case "jetplane":
                return (new JetPlane(name, new Coordinates(longitude, latitude, height)));
            case "helicopter":
                return (new Helicopter(name, new Coordinates(longitude, latitude, height)));
            default: return null;
        }
    }
}
