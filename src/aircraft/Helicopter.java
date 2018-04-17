package aircraft;
import weather.Simulation;
import weather.WeatherProvider;
import weather.WeatherTower;

public class Helicopter  extends Aircraft implements Flyable {

    private WeatherTower weatherTower;

    public void updateConditions() {
        String weather = WeatherProvider.getProvider().getCurrentWeather(this.coordinates);
        int longitude = this.coordinates.getLongitude();
        int latitude = this.coordinates.getLaitude();
        int height = this.coordinates.getHeight();

        weather = weather.toLowerCase();
        switch (weather) {
            case "sun":
                Simulation.toFile.add("Helicopter#" + this.name + "(" + this.id + "): Great weather!");
                if (height + 2 > 100)
                    this.coordinates = new Coordinates(longitude + 2, latitude, height);
                else
                    this.coordinates = new Coordinates(longitude + 2, latitude, height + 4);
                break;
            case "rain":
                Simulation.toFile.add("Helicopter#" + this.name + "(" + this.id + "): Lousy weather!");
                this.coordinates = new Coordinates(longitude + 5, latitude, height);
                break;
            case "fog":
                Simulation.toFile.add("Helicopter#" + this.name + "(" + this.id + "): We can break up!");
                this.coordinates = new Coordinates(longitude + 3, latitude, height);
                break;
            case "snow":
                Simulation.toFile.add("Helicopter#" + this.name + "(" + this.id + "): Let it snow! Let it snow!");
                this.coordinates = new Coordinates(longitude, latitude, height - 15);
                break;
        }
        if (this.coordinates.getHeight() <= 0) {
            Simulation.toFile.add("Helicopter#" + this.name + "(" + this.id + ") landing.");
            this.weatherTower.unregister(this);
            Simulation.toFile.add("Tower says: Helicopter#" + this.name + "(" + this.id + ") unregistered from weather tower.");
        }
    }

    public void registerTower(WeatherTower weatherTower) {
        weatherTower.register(this);
        this.weatherTower = weatherTower;
        Simulation.toFile.add("Tower says: Helicopter#" + this.name + "(" + this.id + ") registered to weather tower.");
    }

    Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }
}
