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
 * Acts as the central controller for the disease spread simulation.
 * Manages the simulation loop, steps through days, and coordinates Population, Disease, Vaccination, and Statistics.
 */

// NOTE: Very soon make sure to have this class handle most of the functionality that ControlPanel does
//       as that was the original intention but I wanted to create rough functionality as soon as possible.

// Controller has-a Population
// Controller has-a Disease
// Controller has-a Vaccination
// Controller has-a Statistics
public class Controller
{

    private Population population;
    private Disease disease;
    private Vaccination vaccination;
    private Statistics stats;

    private int currentDay;
    private int maxDays;

    /**
     * Constructs a Controller with the specified maximum number of days for the simulation.
     * 
     * @param maxDays the total number of days the simulation will run
     */
    public Controller(int maxDays)
    {
        this.maxDays = maxDays;
    }

    /**
     * Initializes the simulation.
     * Creates the Population, Disease, Vaccination, and Statistics objects.
     */
    public void initializeSimulation()
    {
        // TODO: create population, disease, vaccination, stats
    }

    /**
     * Runs the simulation for the configured number of days.
     * Calls step() for each day in the simulation.
     */
    public void run()
    {
        for (currentDay = 0; currentDay < maxDays; currentDay++)
        {
            step();
        }
    }

    /**
     * Advances the simulation by one day.
     * Updates the population, disease spread, vaccination effects, and statistics.
     */
    public void step()
    {
        // TODO: advance simulation one step
    }

    /**
     * Generates a report of the simulation.
     * Could include printing to console or exporting to CSV.
     */
    public void report()
    {
        // TODO: output summary or export CSV
    }
}
