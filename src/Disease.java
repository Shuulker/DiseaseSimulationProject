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
 * Represents a disease in the simulation with an infection rate and incubation period.
 * Provides functionality to attempt infection of a susceptible Person.
 */

// Disease has-a name
// Disease has-a infectionRate
// Disease has-a incubationPeriod
public class Disease
{

    private String name;
    private float infectionRate; // probability 0-1
    private int incubationPeriod;

    /**
     * Constructs a Disease with the specified name, infection rate, and incubation period.
     * 
     * @param name the name of the disease
     * @param infectionRate probability (0-1) that an infected person will infect a susceptible person
     * @param incubationPeriod number of days before infection symptoms appear or progress
     */
    public Disease(String name, float infectionRate, int incubationPeriod)
    {
        this.name = name;
        this.infectionRate = infectionRate;
        this.incubationPeriod = incubationPeriod;
    }

    /**
     * Returns the infection rate of the disease.
     * 
     * @return infection rate as a float between 0 and 1
     */
    public float getInfectionRate()
    {
        return infectionRate;
    }

    /**
     * Returns the incubation period of the disease.
     * 
     * @return incubation period in days
     */
    public int getIncubationPeriod()
    {
        return incubationPeriod;
    }

    /**
     * Attempts to infect a susceptible Person.
     * The infection occurs probabilistically based on the infection rate.
     * 
     * @param p the Person to attempt to infect
     */
    public void tryInfect(Person p)
    {
        if (p.getHealthStatus() == HealthStatus.SUSCEPTIBLE)
        {
            if (Math.random() < infectionRate)
            {
                p.infect();
            }
        }
    }
}
