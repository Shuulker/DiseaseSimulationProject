import java.util.ArrayList;
import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Collects and stores daily statistics for the simulation.
 */

// Statistics has-a dailyInfected, dailyRecovered, dailyDeaths
public class Statistics
{
    private final List<Integer> dailyInfected = new ArrayList<>();
    private final List<Integer> dailyRecovered = new ArrayList<>();
    private final List<Integer> dailyDeaths = new ArrayList<>();

    /**
     * Record today's counts from the population
     *
     * @param population the current population to evaluate
     */
    public void recordDay(Population population)
    {
        int infected = 0;
        int recovered = 0;
        int dead = 0;

        for (Person p : population.getPeople())
        {
            switch (p.getHealthStatus())
            {
                case INFECTED -> infected++;
                case RECOVERED -> recovered++;
                case DEAD -> dead++;
                default -> {}
            }
        }

        dailyInfected.add(infected);
        dailyRecovered.add(recovered);
        dailyDeaths.add(dead);
    }

    // -------------------------------
    // Getters
    // -------------------------------

    public List<Integer> getDailyInfected()
    {
        return dailyInfected;
    }

    public List<Integer> getDailyRecovered()
    {
        return dailyRecovered;
    }

    public List<Integer> getDailyDeaths()
    {
        return dailyDeaths;
    }
}
