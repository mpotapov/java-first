package weather;

import aircraft.Flyable;

import java.util.ArrayList;

public class Tower {

    private ArrayList<Flyable> observes = new ArrayList<Flyable>();

    public void register(Flyable flyable) {
        observes.add(flyable);
    }
    public void unregister(Flyable flyable) {
        if (observes.contains(flyable))
            observes.remove(flyable);
    }
    protected void ConditionsChanged() {
        if (observes.isEmpty())
            return;
        Flyable f = observes.get(0);

        for (int i = 0; i < this.observes.size(); i++) {
            if (i != 0 && observes.get(i - 1).equals(f))
                i--;
            if (i < observes.size() - 1)
                f = observes.get(i + 1);
            observes.get(i).updateConditions();
        }
    }
}
