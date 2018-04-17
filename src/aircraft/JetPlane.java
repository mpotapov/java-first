package aircraft;

import weather.Simulation;
import weather.WeatherProvider;
import weather.WeatherTower;

public class JetPlane extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    public void updateConditions() {
        String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);
        int longitude = this.coordinates.getLongitude();
        int latitude = this.coordinates.getLaitude();
        int height = this.coordinates.getHeight();

        weather = weather.toLowerCase();
        switch (weather) {
            case "sun":
                Simulation.toFile.add("JetPlane#" + this.name + "(" + this.id + "): We fly to the sun!");
                if (height + 2 > 100)
                    this.coordinates = new Coordinates(longitude + 10, latitude, height);
                else
                    this.coordinates = new Coordinates(longitude + 10, latitude, height + 2);
                break;
            case "rain":
                Simulation.toFile.add("JetPlane#" + this.name + "(" + this.id + "): It's raining men!");
                this.coordinates = new Coordinates(longitude + 5, latitude, height);
                break;
            case "fog":
                Simulation.toFile.add("JetPlane#" + this.name + "(" + this.id + "): It's not a fog, we are in da clouds!");
                this.coordinates = new Coordinates(longitude + 1, latitude, height);
                break;
            case "snow":
                Simulation.toFile.add("JetPlane#" + this.name + "(" + this.id + "): Marry Christmas");
                this.coordinates = new Coordinates(longitude, latitude, height - 7);
                break;
        }
        if (this.coordinates.getHeight() <= 0) {
            Simulation.toFile.add("JetPlane#" + this.name + "(" + this.id + ") landing.");
            this.weatherTower.unregister(this);
            Simulation.toFile.add("Tower says: JetPlane#" + this.name + "(" + this.id + ") unregistered from weather tower.");
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        weatherTower.register(this);
        this.weatherTower = weatherTower;
        Simulation.toFile.add("Tower says: JetPlane#" + this.name + "(" + this.id + ") registered to weather tower.");
    }

    JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }
}
