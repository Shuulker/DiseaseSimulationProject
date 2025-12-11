import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 * 
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 * 
 * Version/date: 11/21/2025
 * 
 * Responsibilities of class:
 * Controls the simulation using a SimulationConfig.
 * Advances the simulation day-by-day, applying disease spread, vaccination, and updating statistics.
 */

// Controller HAS-A SimulationConfig, Statistics
// Controller tracks the current day of the simulation
public class Controller
{
    private SimulationConfig config;    // simulation parameters and objects
    private Statistics statistics;      // collects daily simulation stats
    private int currentDay;             // current day in simulation

    /**
     * Constructor to create a Controller
     * Initializes statistics and sets current day to 0
     */
    public Controller()
    {
        this.statistics = new Statistics();
        this.currentDay = 0;
    }

    /**
     * Accepts a SimulationConfig (which contains Population, Disease, Vaccination objects).
     * Controller uses the objects inside config directly.
     * 
     * @param config simulation configuration to apply
     */
    public void applyConfig(SimulationConfig config)
    {
        this.config = config;
        this.statistics = new Statistics(); // reset statistics
        this.currentDay = 0;

        // recreate population to reset all people
        config.getPopulation().setSize(config.getPopulation().size());
    }

    /**
     * Returns the current simulation configuration
     * 
     * @return SimulationConfig used by this controller
     */
    public SimulationConfig getConfig()
    {
        return config;
    }

    /**
     * Returns the Population object from the configuration
     * 
     * @return Population being simulated
     */
    public Population getPopulation()
    {
        return config.getPopulation();
    }

    /**
     * Returns the Disease object from the configuration
     * 
     * @return Disease being simulated
     */
    public Disease getDisease()
    {
        return config.getDisease();
    }

    /**
     * Returns the Vaccination object from the configuration
     * 
     * @return Vaccination program being applied
     */
    public Vaccination getVaccination()
    {
        return config.getVaccination();
    }

    /**
     * Returns the Statistics object
     * 
     * @return Statistics collected so far
     */
    public Statistics getStatistics()
    {
        return statistics;
    }

    /**
     * Returns the current day of the simulation
     * 
     * @return current day (0-based)
     */
    public int getCurrentDay()
    {
        return currentDay;
    }

    /**
     * Returns the maximum number of simulation days
     * 
     * @return maxDays from SimulationConfig
     */
    public int getMaxDays()
    {
        return config == null ? 0 : config.maxDays;
    }

    /**
     * Sets the maximum number of simulation days
     * 
     * @param days maximum days to simulate (minimum 1)
     */
    public void setMaxDays(int days)
    {
        if (config != null)
        {
            config.maxDays = Math.max(1, days);
        }
    }

    /**
     * Single simulation step (one day).
     * Applies vaccination, spreads disease, updates people, and records statistics.
     * 
     * @return true if the simulation can continue, false if max days reached
     */
    public boolean step()
    {
        if (config == null || currentDay >= config.maxDays)
        {
            return false;
        }

        Vaccination vacc = config.getVaccination();

        // Vaccination
        if (config.vaccinationEnabled && vacc != null && currentDay >= vacc.getStartDay() && (vacc.getDailyMax() > 0 || vacc.getDailyMin() > 0))
        {
            distributeVaccinesDaily();
        }

        // Spread disease
        config.getDisease().spread(config.getPopulation());

        // Update people daily (mortality, recovery, contagious)
        updatePeopleDaily();

        // Record statistics for the current day
        statistics.recordDay(config.getPopulation());

        currentDay++;
        return currentDay < config.maxDays;
    }

    /**
     * Updates all people: apply mortality, progress infections, and recoveries.
     */
    private void updatePeopleDaily()
    {
        Population pop = config.getPopulation();
        Disease disease = config.getDisease();
        double mortality = disease.getMortalityRate(); // daily death probability

        for (Person p : pop.getPeople())
        {
            if (p.getHealthStatus() == HealthStatus.DEAD)
                continue;

            // Mortality for infected individuals
            if (p.isInfected() && Math.random() < mortality)
            {
                p.setDead();
                continue; // skip further daily progression
            }

            // Daily progression (infected → contagious → recovered)
            p.progressDay(disease);
        }
    }

    /**
     * Distributes vaccines to eligible people based on daily min/max percentages.
     */
    private void distributeVaccinesDaily()
    {
        Vaccination vacc = config.getVaccination();
        Population pop = config.getPopulation();

        if (currentDay < vacc.getStartDay())
            return;

        List<Person> people = pop.getPeople();

        // Filter eligible people: not dead and not vaccinated
        List<Person> eligible = people.stream()
                .filter(p -> p.getHealthStatus() != HealthStatus.DEAD && !p.isVaccinated())
                .collect(Collectors.toList());

        if (eligible.isEmpty())
            return;

        float dailyMin = vacc.getDailyMin();
        float dailyMax = vacc.getDailyMax();

        // pick random percent between min and max
        float dailyPercent = dailyMin + (float) (Math.random() * (dailyMax - dailyMin));

        // number to vaccinate based on eligible population
        int numToVaccinate = Math.round(eligible.size() * dailyPercent);

        // ensure at least 1 if eligible but percent rounds to 0
        if (numToVaccinate == 0 && !eligible.isEmpty())
        {
            numToVaccinate = 1;
        }

        // shuffle and vaccinate
        Collections.shuffle(eligible);

        for (int i = 0; i < numToVaccinate; i++)
        {
            vacc.applyTo(eligible.get(i));
        }
    }

    /**
     * Resets the simulation to day 0
     */
    public void start()
    {
        currentDay = 0;
    }
}
