package aircraft;

public class Aircraft {

    protected long id;
    protected String name;
    protected Coordinates coordinates;
    private static long idCounter = 0;

    protected Aircraft(String name, Coordinates coordinates) {
        this.id = nextid();
        this.name = name;
        this.coordinates = coordinates;
    }
    private long nextid() {
        ++idCounter;
        return idCounter;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }
}
