import java.util.List;
import java.io.Serializable;
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
 * Represents a disease in the simulation.
 * Tracks infection rate, mortality rate, recovery days, contagious days, and name.
 * Contains logic to spread the disease among a Population.
 */

// Disease HAS-A name, infection rate, mortality rate, recovery and contagious day ranges
// Disease IS-A model entity
public class Disease implements Serializable 
{

	private static final long serialVersionUID = 1L;
    private String name;                   // name of the disease
    private double infectionRate;          // chance per day to infect a susceptible person (0..1)
    private double mortalityRate;          // chance per day for infected to die (0..1)
    private int minRecoveryDays;           // minimum recovery duration
    private int maxRecoveryDays;           // maximum recovery duration
    private int minContagiousDays;         // minimum contagious duration
    private int maxContagiousDays;         // maximum contagious duration

    /**
     * Default constructor
     * Initializes disease with default values
     */
    public Disease()
    {
        this("Default", 0.2, 0.0, 3, 5, 2, 5); // default values set here
    }

    /**
     * Parameterized constructor
     * 
     * @param name name of the disease
     * @param infectionRate chance per day to infect a susceptible person
     * @param mortalityRate chance per day for infected to die
     * @param minRecovery minimum recovery duration in days
     * @param maxRecovery maximum recovery duration in days
     * @param minContagious minimum contagious duration in days
     * @param maxContagious maximum contagious duration in days
     */
    public Disease(String name, double infectionRate, double mortalityRate,
                   int minRecovery, int maxRecovery,
                   int minContagious, int maxContagious)
    {
        this.name = name;
        this.infectionRate = infectionRate;
        this.mortalityRate = mortalityRate;
        this.minRecoveryDays = minRecovery;
        this.maxRecoveryDays = maxRecovery;
        this.minContagiousDays = minContagious;
        this.maxContagiousDays = maxContagious;
    }
    
    public Disease copy()
    {
        return new Disease
        (
            this.name,
            this.infectionRate,
            this.mortalityRate,
            this.minRecoveryDays,
            this.maxRecoveryDays,
            this.minContagiousDays,
            this.maxContagiousDays
        );
    }


    /**
     * Retrieves the name of the disease
     * 
     * @return disease name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the disease
     * Ignores null or empty names
     * 
     * @param name new name of disease
     */
    public void setName(String name)
    {
        if (name != null && !name.isEmpty())
        {
            this.name = name;
        }
    }

    /**
     * Retrieves the infection rate
     * 
     * @return infection rate (0..1)
     */
    public double getInfectionRate()
    {
        return infectionRate;
    }

    /**
     * Sets the infection rate
     * 
     * @param rate new infection rate (0..1)
     */
    public void setInfectionRate(double rate)
    {
        this.infectionRate = rate;
    }

    /**
     * Retrieves the mortality rate
     * 
     * @return mortality rate (0..1)
     */
    public double getMortalityRate()
    {
        return mortalityRate;
    }

    /**
     * Sets the mortality rate
     * 
     * @param rate new mortality rate (0..1)
     */
    public void setMortalityRate(double rate)
    {
        this.mortalityRate = rate;
    }

    /**
     * Retrieves the recovery day range
     * 
     * @return array with min and max recovery days
     */
    public int[] getRecoveryDaysRange()
    {
        return new int[]{minRecoveryDays, maxRecoveryDays};
    }

    /**
     * Retrieves the contagious day range
     * 
     * @return array with min and max contagious days
     */
    public int[] getContagiousDaysRange()
    {
        return new int[]{minContagiousDays, maxContagiousDays};
    }

    /**
     * Sets the recovery day range
     * Ensures min <= max and both >= 0
     * 
     * @param min minimum recovery days
     * @param max maximum recovery days
     */
    public void setRecoveryDays(int min, int max)
    {
        minRecoveryDays = Math.max(0, Math.min(min, max));
        maxRecoveryDays = Math.max(min, max);
    }

    /**
     * Sets the contagious day range
     * Ensures min <= max and both >= 0
     * 
     * @param min minimum contagious days
     * @param max maximum contagious days
     */
    public void setContagiousDays(int min, int max)
    {
        minContagiousDays = Math.max(0, Math.min(min, max));
        maxContagiousDays = Math.max(min, max);
    }

    /**
     * Generates a random number of days in the range [min, max]
     * 
     * @param min minimum value
     * @param max maximum value
     * @return random integer in range
     */
    private int randomDays(int min, int max)
    {
        return (max <= min) ? min : min + (int)(Math.random() * (max - min + 1));
    }

    /**
     * Randomly generates recovery duration for a person
     * 
     * @return recovery days
     */
    public int randomRecoveryDays()
    {
        return randomDays(minRecoveryDays, maxRecoveryDays);
    }

    /**
     * Randomly generates contagious duration for a person
     * 
     * @return contagious days
     */
    public int randomContagiousDays()
    {
        return randomDays(minContagiousDays, maxContagiousDays);
    }

    /**
     * Spread disease among a population
     * Infects only susceptible people within neighborhood
     * 
     * @param population population to spread disease to
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

        // copy current states (so new infections don't immediately infect others)
        for (int i = 0; i < total; i++)
        {
            nextState[i] = people.get(i).getHealthStatus();
        }

        for (int i = 0; i < total; i++)
        {
            Person source = people.get(i);

            // Only contagious people can spread
            if (source.isContagious())
            {
                int row = i / cols;
                int col = i % cols;

                // neighborhood distance 2
                for (int dr = -2; dr <= 2; dr++)
                {
                    for (int dc = -2; dc <= 2; dc++)
                    {
                        if (dr == 0 && dc == 0)
                        {
                            continue;
                        }

                        attemptInfectNeighbor(people, nextState, row + dr, col + dc, cols, rows);
                    }
                }
            }
        }

        // apply new infections
        for (int i = 0; i < total; i++)
        {
            if (nextState[i] == HealthStatus.INFECTED)
            {
                people.get(i).infect(this);
            }
        }
    }

    /**
     * Attempts to infect a neighbor at the given row/column
     * 
     * @param people list of people in population
     * @param nextState array of next health states
     * @param row target row
     * @param col target column
     * @param cols total columns in grid
     * @param rows total rows in grid
     */
    private void attemptInfectNeighbor(List<Person> people, HealthStatus[] nextState, int row, int col, int cols, int rows)
    {
        if (row < 0 || row >= rows)
        {
            return;
        }

        if (col < 0 || col >= cols)
        {
            return;
        }

        int index = row * cols + col;

        if (index < 0 || index >= people.size())
        {
            return;
        }

        Person neighbor = people.get(index);

        // only attempt to infect SUSCEPTIBLE people
        if (neighbor.getHealthStatus() == HealthStatus.SUSCEPTIBLE)
        {
            if (Math.random() < infectionRate)
            {
                nextState[index] = HealthStatus.INFECTED;
            }
        }
    }
}
