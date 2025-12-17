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
 * Represents the configuration of a simulation, including Population, Disease, and Vaccination.
 * Stores maxDays and whether vaccination is enabled.
 */

// SimulationConfig IS-A configuration holder for the simulation
// SimulationConfig HAS-A Population, Disease, Vaccination, maxDays, vaccinationEnabled
public class SimulationConfig
{
    private Population population;       // population for the simulation
    private Disease disease;             // disease for the simulation
    private Vaccination vaccination;     // vaccination configuration

    public int maxDays;                  // maximum number of simulation days
    public boolean vaccinationEnabled;   // whether vaccination is enabled

    /**
     * Constructs a SimulationConfig with default model objects
     * Sets default values for maxDays and vaccinationEnabled
     */
    public SimulationConfig()
    {
        this.population = new Population();
        this.disease = new Disease();
        this.vaccination = new Vaccination();

        vaccinationEnabled = false;
        this.maxDays = 100;
    }

    /**
     * Retrieves the Population object
     * 
     * @return the simulation population
     */
    public Population getPopulation()
    {
        return population;
    }

    /**
     * Retrieves the Disease object
     * 
     * @return the simulation disease
     */
    public Disease getDisease()
    {
        return disease;
    }
    
    public void setDisease(Disease disease)
    {
        if (disease != null)
        {
            this.disease = disease;
        }
    }


    /**
     * Retrieves the Vaccination object
     * 
     * @return the simulation vaccination configuration
     */
    public Vaccination getVaccination()
    {
        return vaccination;
    }
}
