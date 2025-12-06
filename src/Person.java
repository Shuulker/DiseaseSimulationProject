/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Represents an individual in the simulation. Tracks health status, infection
 * timing, and vaccination state.
 */

// Person IS-A individual in the simulation
// Person has-a HealthStatus, infection timing, vaccination status
public class Person
{
    private int id;
    private HealthStatus healthStatus;
    private int daysInfected;

    private boolean vaccinated;
    private float vaccineEfficacy;

    // Dynamic recovery timing
    private int assignedRecoveryDays;
    private int recoveryCountdown;

    /**
     * Constructor: creates a new person with default SUSCEPTIBLE status
     *
     * @param id unique identifier for the person
     */
    public Person(int id)
    {
        this.id = id;
        this.healthStatus = HealthStatus.SUSCEPTIBLE;
        this.daysInfected = 0;
        this.vaccinated = false;
        this.vaccineEfficacy = 0f;
        this.assignedRecoveryDays = 0;
        this.recoveryCountdown = 0;
    }

    // -------------------------------
    // Getters / Setters
    // -------------------------------

    public int getId()
    {
        return id;
    }

    public HealthStatus getHealthStatus()
    {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus status)
    {
        this.healthStatus = status;
    }

    public boolean isVaccinated()
    {
        return vaccinated;
    }

    public float getVaccineEfficacy()
    {
        return vaccineEfficacy;
    }

    // -------------------------------
    // Infection & Health Logic
    // -------------------------------

    /**
     * Infects the person if they are currently SUSCEPTIBLE.
     * RECOVERED or VACCINATED people are immune.
     *
     * @param disease the disease instance to infect with
     */
    public void infect(Disease disease)
    {
        if (healthStatus == HealthStatus.SUSCEPTIBLE)
        {
            healthStatus = HealthStatus.INFECTED;
            daysInfected = 0;
            assignedRecoveryDays = disease.randomRecoveryDays(); // random recovery period
        }
    }

    /**
     * Updates health status per day:
     * INFECTED -> after assignedRecoveryDays -> RECOVERING
     * RECOVERING -> after recoveryCountdown -> RECOVERED
     *
     * @param disease the disease for recovery timing
     */
    public void updateHealth(Disease disease)
    {
        switch (healthStatus)
        {
            case INFECTED ->
            {
                daysInfected++;
                if (daysInfected >= assignedRecoveryDays)
                {
                    healthStatus = HealthStatus.RECOVERING;
                    recoveryCountdown = disease.randomContagiousDays(); // random contagious period
                }
            }
            case RECOVERING ->
            {
                recoveryCountdown--;
                if (recoveryCountdown <= 0)
                {
                    healthStatus = HealthStatus.RECOVERED;
                }
            }
            default -> {}
        }
    }

    /**
     * Checks if the person can spread disease.
     *
     * @return true if INFECTED or RECOVERING
     */
    public boolean isContagious()
    {
        return healthStatus == HealthStatus.INFECTED || healthStatus == HealthStatus.RECOVERING;
    }

    // -------------------------------
    // Vaccination Logic
    // -------------------------------

    /**
     * Applies vaccination to the person, marking as VACCINATED if SUSCEPTIBLE
     *
     * @param efficacy value from 0.0 to 1.0
     */
    public void vaccinate(float efficacy)
    {
        this.vaccinated = true;
        this.vaccineEfficacy = Math.max(0f, Math.min(1f, efficacy));

        // Optional: immediately mark as VACCINATED visually
        if (healthStatus == HealthStatus.SUSCEPTIBLE)
        {
            healthStatus = HealthStatus.VACCINATED;
        }
    }
}
