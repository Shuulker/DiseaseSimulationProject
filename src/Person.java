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
 * Represents a person in the simulation.
 * Tracks health state, vaccination status, and disease progression.
 */

// Person IS-A individual in the simulation
// Person HAS-A HealthStatus, vaccinated flag, infection and contagious counters
public class Person
{
    private final int id;                     // unique identifier
    private HealthStatus status;              // current health state
    private boolean vaccinated;               // whether person is vaccinated

    private int daysInfected;                 // counter of days infected
    private int daysContagious;               // counter of days contagious
    private int recoveryDuration;             // total days infected
    private int contagiousDuration;           // total days contagious

    /**
     * Constructs a Person with a unique ID
     * Initializes status to SUSCEPTIBLE and vaccination to false
     * 
     * @param id unique identifier for the person
     */
    public Person(int id)
    {
        this.id = id;
        this.status = HealthStatus.SUSCEPTIBLE;
        this.vaccinated = false;
        this.daysInfected = 0;
        this.daysContagious = 0;
        this.recoveryDuration = 0;
        this.contagiousDuration = 0;
    }

    /**
     * Returns the current health status
     * 
     * @return current HealthStatus
     */
    public HealthStatus getHealthStatus()
    {
        return status;
    }

    /**
     * Checks if the person has been vaccinated
     * 
     * @return true if vaccinated, false otherwise
     */
    public boolean isVaccinated()
    {
        return vaccinated;
    }

    /**
     * Vaccinates the person
     * Updates status to VACCINATED if previously SUSCEPTIBLE
     */
    public void vaccinate()
    {
        vaccinated = true;
        if (status == HealthStatus.SUSCEPTIBLE)
        {
            status = HealthStatus.VACCINATED;
        }
    }

    /**
     * Checks if the person is currently infected
     * 
     * @return true if INFECTED or CONTAGIOUS
     */
    public boolean isInfected()
    {
        return status == HealthStatus.INFECTED || status == HealthStatus.CONTAGIOUS;
    }

    /**
     * Sets the person's status to DEAD
     */
    public void setDead()
    {
        status = HealthStatus.DEAD;
    }

    /**
     * Infects a susceptible person with the given disease
     * Initializes recovery and contagious durations and resets counters
     * 
     * @param disease Disease instance to infect the person
     */
    public void infect(Disease disease)
    {
        if (status == HealthStatus.SUSCEPTIBLE)
        {
            status = HealthStatus.INFECTED;
            recoveryDuration = disease.randomRecoveryDays();
            contagiousDuration = disease.randomContagiousDays();
            daysInfected = 0;
            daysContagious = 0;
        }
    }

    /**
     * Checks if the person is currently contagious
     * 
     * @return true if status is CONTAGIOUS or INFECTED with at least 1 day of infection
     */
    public boolean isContagious()
    {
        return status == HealthStatus.INFECTED && daysInfected >= 1
               || status == HealthStatus.CONTAGIOUS;
    }

    /**
     * Progresses the person's infection by one day
     * Updates status from INFECTED → CONTAGIOUS → RECOVERED as appropriate
     * 
     * @param disease Disease instance being tracked (used for progression rules)
     */
    public void progressDay(Disease disease)
    {
        switch (status)
        {
            case INFECTED:
                daysInfected++;

                // Check if infection duration is complete → move to CONTAGIOUS
                if (daysInfected >= recoveryDuration)
                {
                    status = HealthStatus.CONTAGIOUS;
                    daysContagious = 0; // reset contagious counter
                }
                break;

            case CONTAGIOUS:
                daysContagious++;

                // Check if contagious duration is complete → move to RECOVERED
                if (daysContagious >= contagiousDuration)
                {
                    status = HealthStatus.RECOVERED;
                }
                break;

            default:
                break; // SUSCEPTIBLE, RECOVERED, VACCINATED, DEAD do nothing
        }
    }
}
