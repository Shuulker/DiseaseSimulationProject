import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Represents a disease with an infection rate, incubation period, 
 * optional mortality rate, and configurable recovery and post-recovery contagious periods.
 */

// Disease has-a name, infection rate, incubation period, mortality rate
// Disease has-a configurable recovery and contagious periods
public class Disease
{
    private String name;
    private double infectionRate;        // 0..1 probability of infection
    private int incubationPeriod;        // days
    private double mortalityRate;        // optional, 0..1
    private int minRecoveryDays;         // minimum days until recovery
    private int maxRecoveryDays;         // maximum days until recovery
    private int minContagiousDays;       // minimum days post-recovery still contagious
    private int maxContagiousDays;       // maximum days post-recovery still contagious

    /**
     * Constructor to create a Disease.
     *
     * @param name name of the disease
     * @param infectionRate probability of spreading the disease (0..1)
     * @param incubationPeriod number of days before symptoms appear
     */
    public Disease(String name, double infectionRate, int incubationPeriod)
    {
        this.name = name;
        this.infectionRate = infectionRate;
        this.incubationPeriod = incubationPeriod;
        this.mortalityRate = 0.0;
        this.minRecoveryDays = 3;       // default
        this.maxRecoveryDays = 3;       // default
        this.minContagiousDays = 0;     // default
        this.maxContagiousDays = 0;     // default
    }

    // -------------------------------
    // Getters / Setters
    // -------------------------------

    public String getName()
    {
        return name;
    }

    public double getInfectionRate()
    {
        return infectionRate;
    }

    public void setInfectionRate(double rate)
    {
        this.infectionRate = Math.max(0.0, Math.min(1.0, rate));
    }

    public int getIncubationPeriod()
    {
        return incubationPeriod;
    }

    public void setIncubationPeriod(int days)
    {
        this.incubationPeriod = Math.max(0, days);
    }

    public double getMortalityRate()
    {
        return mortalityRate;
    }

    public void setMortalityRate(double m)
    {
        this.mortalityRate = Math.max(0.0, Math.min(1.0, m));
    }

    public int getMinRecoveryDays()
    {
        return minRecoveryDays;
    }

    public int getMaxRecoveryDays()
    {
        return maxRecoveryDays;
    }

    public void setRecoveryDays(int minDays, int maxDays)
    {
        this.minRecoveryDays = Math.max(0, Math.min(minDays, maxDays));
        this.maxRecoveryDays = Math.max(minDays, maxDays);
    }

    public int getMinContagiousDays()
    {
        return minContagiousDays;
    }

    public int getMaxContagiousDays()
    {
        return maxContagiousDays;
    }

    public void setPostRecoveryContagiousDays(int minDays, int maxDays)
    {
        this.minContagiousDays = Math.max(0, Math.min(minDays, maxDays));
        this.maxContagiousDays = Math.max(minDays, maxDays);
    }

    /**
     * Returns a random recovery duration within the configured range.
     *
     * @return number of recovery days
     */
    public int randomRecoveryDays()
    {
        return minRecoveryDays + (int)(Math.random() * (maxRecoveryDays - minRecoveryDays + 1));
    }

    /**
     * Returns a random post-recovery contagious duration within the configured range.
     *
     * @return number of contagious days
     */
    public int randomContagiousDays()
    {
        return minContagiousDays + (int)(Math.random() * (maxContagiousDays - minContagiousDays + 1));
    }

    // -------------------------------
    // Infection Logic
    // -------------------------------

    /**
     * Spreads the disease through the population based on infection rate and neighboring positions.
     *
     * @param population population to spread disease through
     */
    public void spread(Population population)
    {
        List<Person> people = population.getPeople();
        int total = people.size();

        if (total == 0)
        {
            return;
        }

        int cols = population.getColumnCount();
        int rows = population.getRowCount();
        HealthStatus[] nextState = new HealthStatus[total];

        for (int i = 0; i < total; i++)
        {
            nextState[i] = people.get(i).getHealthStatus();
        }

        for (int i = 0; i < total; i++)
        {
            Person p = people.get(i);

            if (p.getHealthStatus() == HealthStatus.INFECTED)
            {
                int row = i / cols;
                int col = i % cols;

                // Check neighbors up to distance 2
                for (int dr = -2; dr <= 2; dr++)
                {
                    for (int dc = -2; dc <= 2; dc++)
                    {
                        if (dr == 0 && dc == 0) continue; // skip self
                        infectNeighbor(people, nextState, row + dr, col + dc, cols, rows);
                    }
                }
            }
        }

        // Apply infections
        for (int i = 0; i < total; i++)
        {
            if (nextState[i] == HealthStatus.INFECTED)
            {
                people.get(i).infect(this);
            }
        }
    }

    /**
     * Attempts to infect a neighboring person if within bounds and susceptible.
     *
     * @param people list of people
     * @param nextState array of next health states
     * @param row row of neighbor
     * @param col column of neighbor
     * @param cols total columns in population grid
     * @param rows total rows in population grid
     */
    private void infectNeighbor(List<Person> people, HealthStatus[] nextState, int row, int col, int cols, int rows)
    {
        if (row < 0 || row >= rows) return;
        if (col < 0 || col >= cols) return;

        int index = row * cols + col;

        if (index >= people.size()) return;

        Person neighbor = people.get(index);

        if (neighbor.getHealthStatus() == HealthStatus.SUSCEPTIBLE)
        {
            if (Math.random() < infectionRate)
            {
                nextState[index] = HealthStatus.INFECTED;
            }
        }
    }
}
