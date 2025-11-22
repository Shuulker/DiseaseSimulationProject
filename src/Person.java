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
 * Represents an individual in the simulation.
 * Tracks health status, days infected, and provides methods to infect and update health over time.
 */


// Person has-a id 
// Person has-a healthStatus
// Person has-a daysInfected
public class Person
{

    private int id;
    private HealthStatus healthStatus;
    private int daysInfected;

    /**
     * Constructs a Person with a unique ID.
     * Initializes healthStatus to SUSCEPTIBLE and daysInfected to 0.
     * 
     * @param id unique identifier for the Person
     */
    public Person(int id)
    {
        this.id = id;
        this.healthStatus = HealthStatus.SUSCEPTIBLE;
        this.daysInfected = 0;
    }

    /**
     * Returns the current health status of the Person.
     * 
     * @return the HealthStatus of this Person
     */
    public HealthStatus getHealthStatus()
    {
        return healthStatus;
    }

    /**
     * Infects the Person if they are currently susceptible.
     * Sets healthStatus to INFECTED and resets daysInfected.
     */
    public void infect()
    {
        if (healthStatus == HealthStatus.SUSCEPTIBLE)
        {
            healthStatus = HealthStatus.INFECTED;
            daysInfected = 0;
        }
    }

    /**
     * Updates the health of the Person for one day.
     * Increments daysInfected if infected and transitions to RECOVERED
     * after incubation period plus 3 days.
     * 
     * @param disease the Disease being simulated
     */
    public void updateHealth(Disease disease)
    {
        if (healthStatus == HealthStatus.INFECTED)
        {
            daysInfected++;
            if (daysInfected >= disease.getIncubationPeriod() + 3)
            {
                healthStatus = HealthStatus.RECOVERED;
            }
        }
    }
}
