package aircraft;

public class Coordinates {

    private int longitude;
    private int laitude;
    private int height;

    Coordinates(int longitude, int laitude, int height) {
        this.longitude = longitude;
        this.laitude = laitude;
        this.height = height;
    }

    public int getLongitude() {
        return longitude;
    }

    public int getLaitude() {
        return laitude;
    }

    public int getHeight() {
        return height;
    }
}
