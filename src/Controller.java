public class Controller {

    private Population population;
    private Disease disease;
    private Vaccination vaccination;
    private Statistics stats;

    private int currentDay;
    private int maxDays;

    public Controller(int maxDays) {
        this.maxDays = maxDays;
    }

    public void initializeSimulation() {
        // TODO: create population, disease, vaccination, stats
    }

    public void run() {
        for (currentDay = 0; currentDay < maxDays; currentDay++) {
            step();
        }
    }

    public void step() {
        // TODO: advance simulation one step
    }

    public void report() {
        // TODO: output summary or export CSV
    }
}
