import java.util.*;

public class Population {

    private List<Person> people = new ArrayList<>();
    private int totalInfected;
    private int totalRecovered;

    public Population() {
        // TODO: initialize people
    }

    public void spreadInfection(Disease d) {
        // TODO: loop over people and attempt infection
    }

    public void updateAll(Disease d) {
        // TODO: update health of all people
    }

    public Statistics getStatistics() {
        // TODO: compute and return stats snapshot
        return null;
    }

    public List<Person> getPeople() {
        return people;
    }
}
