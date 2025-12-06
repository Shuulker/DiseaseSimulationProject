import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Orchestrates the simulation: infection spread, vaccination application,
 * population health updates, and statistics collection.
 */

// Controller has-a Population
// Controller has-a Disease
// Controller has-a Vaccination
// Controller has-a Statistics
// Controller has-a SimulationConfig
public class Controller
{
    private Population population;
    private Disease disease;
    private Vaccination vaccination;
    private Statistics statistics;
    private SimulationConfig config;  // stores last user-selected configuration

    private int currentDay;
    private int maxDays = 50;

    /**
     * Default constructor.
     * Initializes statistics object. Population and disease are set via reset or applyConfig.
     */
    public Controller()
    {
        statistics = new Statistics();
    }

    /**
     * Resets the simulation using simple parameters.
     * Seeds first infection and applies vaccination if enabled.
     *
     * @param populationSize number of people in population
     * @param infectionRate probability of disease spread
     * @param incubation incubation period for disease
     * @param vaccEnabled whether vaccination is applied
     * @param vaccEff effectiveness of vaccination
     */
    public void reset(int populationSize, float infectionRate, int incubation, boolean vaccEnabled, float vaccEff)
    {
        this.population = new Population(populationSize);
        this.disease = new Disease("ConfiguredVirus", infectionRate, incubation);
        this.statistics = new Statistics();
        this.currentDay = 0;

        // Seed first infection
        if (!population.getPeople().isEmpty())
        {
            population.getPeople().get(0).infect(this.disease);
        }

        // Apply vaccination if enabled
        if (vaccEnabled)
        {
            this.vaccination = new Vaccination("Vax", vaccEff, 1.0f);
            for (Person p : population.getPeople())
            {
                this.vaccination.applyTo(p);
            }
        }
        else
        {
            this.vaccination = null;
        }
    }

    // ---- Getters & Setters ----
    public Population getPopulation()
    {
        return population;
    }

    public Disease getDisease()
    {
        return disease;
    }

    public Statistics getStatistics()
    {
        return statistics;
    }

    public int getCurrentDay()
    {
        return currentDay;
    }

    public int getMaxDays()
    {
        return maxDays;
    }

    public void setMaxDays(int maxDays)
    {
        this.maxDays = Math.max(1, maxDays);
    }

    public SimulationConfig getConfig()
    {
        return config;
    }

    /**
     * Advances the simulation by one day.
     *
     * @return true if the simulation can continue, false if max days reached
     */
    public boolean step()
    {
        if (currentDay >= maxDays)
        {
            return false;
        }

        disease.spread(population);
        population.updateHealthAll(disease);
        statistics.recordDay(population);
        currentDay++;

        return true;
    }

    /**
     * Applies a detailed configuration to the simulation.
     * Sets up population, disease parameters, statistics, and vaccination.
     *
     * @param config user-defined simulation configuration
     */
    public void applyConfig(SimulationConfig config)
    {
        this.config = config;  // store user config
        this.maxDays = config.maxDays;

        this.population = new Population(config.populationSize);

        this.disease = new Disease("ConfiguredVirus", config.infectionRate, config.incubationPeriod);
        this.disease.setRecoveryDays(config.minRecoveryDays, config.maxRecoveryDays);
        this.disease.setPostRecoveryContagiousDays(config.minContagiousDays, config.maxContagiousDays);

        this.statistics = new Statistics();
        this.currentDay = 0;

        // Apply vaccination if enabled
        if (config.vaccinationEnabled)
        {
            this.vaccination = new Vaccination("Vax", config.vaccinationEfficacy, 1.0f);
            for (Person p : population.getPeople())
            {
                this.vaccination.applyTo(p);
            }
        }
        else
        {
            this.vaccination = null;
        }
    }

    /**
     * Starts the simulation. Resets current day to 0.
     */
    public void start()
    {
        currentDay = 0;
    }

    /**
     * Convenience method: resets the simulation and returns the population.
     *
     * @param populationSize number of people in population
     * @param infectionRate probability of disease spread
     * @param incubation incubation period for disease
     * @param vaccEnabled whether vaccination is applied
     * @param vaccEff effectiveness of vaccination
     * @return the newly created population
     */
    public Population resetAndGetPopulation(int populationSize, float infectionRate, int incubation, boolean vaccEnabled, float vaccEff)
    {
        reset(populationSize, infectionRate, incubation, vaccEnabled, vaccEff);
        return population;
    }
}
