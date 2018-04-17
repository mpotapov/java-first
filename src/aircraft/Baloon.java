package aircraft;

import weather.Simulation;
import weather.WeatherProvider;
import weather.WeatherTower;

public class Baloon extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    public void updateConditions() {
        String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);
        int longitude = this.coordinates.getLongitude();
        int latitude = this.coordinates.getLaitude();
        int height = this.coordinates.getHeight();

        weather = weather.toLowerCase();
        switch (weather) {
            case "sun":
                Simulation.toFile.add("Baloon#" + this.name + "(" + this.id + "): Oh, it is great weather outside!");
                if (height + 2 > 100)
                    this.coordinates = new Coordinates(longitude + 10, latitude, height);
                else
                    this.coordinates = new Coordinates(longitude + 10, latitude, height + 2);
                break;
            case "rain":
                Simulation.toFile.add("Baloon#" + this.name + "(" + this.id + "): Shit, I don't like rain!");
                this.coordinates = new Coordinates(longitude, latitude, height - 5);
                break;
            case "fog":
                Simulation.toFile.add("Baloon#" + this.name + "(" + this.id + "): We can't see anything!");
                this.coordinates = new Coordinates(longitude, latitude, height - 3);
                break;
            case "snow":
                Simulation.toFile.add("Baloon#" + this.name + "(" + this.id + "): Wait, what? Is this a cocaine?!");
                this.coordinates = new Coordinates(longitude, latitude, height - 12);
                break;
        }
        if (this.coordinates.getHeight() <= 0) {
            Simulation.toFile.add("Baloon#" + this.name + "(" + this.id + ") landing.");
            this.weatherTower.unregister(this);
            Simulation.toFile.add("Tower says: Baloon#" + this.name + "(" + this.id + ") unregistered from weather tower.");
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        weatherTower.register(this);
        this.weatherTower = weatherTower;
        Simulation.toFile.add("Tower says: Baloon#" + this.name + "(" + this.id + ") registered to weather tower.");
    }

    Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
    }
}
