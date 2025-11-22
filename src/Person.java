public class Person {

    private int id;
    private HealthStatus healthStatus;
    private int daysInfected;
    private boolean vaccinated;
    private float immunityLevel;

    public Person(int id) {
        this.id = id;
        this.healthStatus = HealthStatus.SUSCEPTIBLE;
    }

    public void exposeTo(Disease d) {
        // TODO: attempt infection
    }

    public void updateHealth(Disease d) {
        // TODO: progress infection, recover, or die
    }

    public void receiveVaccine(Vaccination v) {
        // TODO: apply vaccine effects
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }
}
