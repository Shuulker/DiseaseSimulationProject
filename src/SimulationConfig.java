/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Encapsulates all configuration parameters for the simulation.
 */

// SimulationConfig is a simple data holder for simulation settings
public class SimulationConfig
{
    // -------------------------------
    // Public final fields
    // -------------------------------
    public final int populationSize;
    public final float infectionRate;
    public final int incubationPeriod;
    public final boolean vaccinationEnabled;
    public final float vaccinationEfficacy;
    public final int maxDays;
    public final int minRecoveryDays;
    public final int maxRecoveryDays;
    public final int minContagiousDays;
    public final int maxContagiousDays;

    /**
     * Constructor: creates a configuration for a simulation
     *
     * @param populationSize total number of people
     * @param infectionRate probability of infection per contact
     * @param incubationPeriod days before infection is active
     * @param vaccinationEnabled whether vaccination is applied
     * @param vaccinationEfficacy probability that vaccine works
     * @param maxDays maximum number of simulation days
     * @param minRecoveryDays minimum days until recovery
     * @param maxRecoveryDays maximum days until recovery
     * @param minContagiousDays minimum days post-recovery still contagious
     * @param maxContagiousDays maximum days post-recovery still contagious
     */
    public SimulationConfig(int populationSize, float infectionRate, int incubationPeriod,
                            boolean vaccinationEnabled, float vaccinationEfficacy, int maxDays,
                            int minRecoveryDays, int maxRecoveryDays,
                            int minContagiousDays, int maxContagiousDays)
    {
        this.populationSize = populationSize;
        this.infectionRate = infectionRate;
        this.incubationPeriod = incubationPeriod;
        this.vaccinationEnabled = vaccinationEnabled;
        this.vaccinationEfficacy = vaccinationEfficacy;
        this.maxDays = maxDays;
        this.minRecoveryDays = minRecoveryDays;
        this.maxRecoveryDays = maxRecoveryDays;
        this.minContagiousDays = minContagiousDays;
        this.maxContagiousDays = maxContagiousDays;
    }
}
