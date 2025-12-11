import java.util.ArrayList;
import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Version/date: 12/11/2025
 *
 * Responsibilities of class:
 * Tracks daily statistics of the population during the simulation.
 * Records counts of susceptible, infected, recovering, safe, and dead individuals.
 * Can reset data for a new simulation.
 */

// Statistics IS-A plain Java object
// Statistics HAS-A lists of daily counts for each health category
public class Statistics
{
    private final List<Integer> dailySusceptible;
    private final List<Integer> dailyInfected;
    private final List<Integer> dailyRecovering;
    private final List<Integer> dailySafe;
    private final List<Integer> dailyDeaths;

    /**
     * Constructs an empty Statistics object with initialized lists.
     */
    public Statistics()
    {
        dailySusceptible = new ArrayList<>();
        dailyInfected = new ArrayList<>();
        dailyRecovering = new ArrayList<>();
        dailySafe = new ArrayList<>();
        dailyDeaths = new ArrayList<>();
    }

    /**
     * Record the counts for a single day from the current population.
     *
     * @param population the Population to record statistics from
     */
    public void recordDay(Population population)
    {
        int susceptible = 0;
        int infected = 0;
        int recovering = 0;
        int safe = 0;
        int dead = 0;

        for (Person p : population.getPeople())
        {
            switch (p.getHealthStatus())
            {
                case SUSCEPTIBLE -> susceptible++;
                case INFECTED -> infected++;
                case CONTAGIOUS -> recovering++;
                case RECOVERED, VACCINATED -> safe++;
                case DEAD -> dead++;
            }
        }

        dailySusceptible.add(susceptible);
        dailyInfected.add(infected);
        dailyRecovering.add(recovering);
        dailySafe.add(safe);
        dailyDeaths.add(dead);
    }

    // -------------------------
    // Latest-day getters
    // -------------------------
    public int getLatestSusceptible()
    {
        return dailySusceptible.isEmpty() ? 0 : dailySusceptible.get(dailySusceptible.size() - 1);
    }

    public int getLatestInfected()
    {
        return dailyInfected.isEmpty() ? 0 : dailyInfected.get(dailyInfected.size() - 1);
    }

    public int getLatestRecovering()
    {
        return dailyRecovering.isEmpty() ? 0 : dailyRecovering.get(dailyRecovering.size() - 1);
    }

    public int getLatestSafe()
    {
        return dailySafe.isEmpty() ? 0 : dailySafe.get(dailySafe.size() - 1);
    }

    public int getLatestDeaths()
    {
        return dailyDeaths.isEmpty() ? 0 : dailyDeaths.get(dailyDeaths.size() - 1);
    }

    // -------------------------
    // Full-history getters
    // -------------------------
    public List<Integer> getDailySusceptible()
    {
        return dailySusceptible;
    }

    public List<Integer> getDailyInfected()
    {
        return dailyInfected;
    }

    public List<Integer> getDailyRecovering()
    {
        return dailyRecovering;
    }

    public List<Integer> getDailySafe()
    {
        return dailySafe;
    }

    public List<Integer> getDailyDeaths()
    {
        return dailyDeaths;
    }

    /**
     * Clear all statistics (for resetting the simulation).
     */
    public void reset()
    {
        dailySusceptible.clear();
        dailyInfected.clear();
        dailyRecovering.clear();
        dailySafe.clear();
        dailyDeaths.clear();
    }
}
