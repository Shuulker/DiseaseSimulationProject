import java.util.*;

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
 * Collects and stores daily statistics for the simulation, including numbers of infected,
 * recovered, and dead people. Provides methods to generate reports and export data to CSV.
 */

// Statistics has-a dailyInfected
// Statistics has-a dailyRecovered
// Statistics has-a dailyDeaths
public class Statistics
{

    private List<Integer> dailyInfected = new ArrayList<>();
    private List<Integer> dailyRecovered = new ArrayList<>();
    private List<Integer> dailyDeaths = new ArrayList<>();

    /**
     * Records the current day's statistics based on the Population.
     * 
     * @param population the Population to collect statistics from
     */
    public void recordDay(Population population)
    {
        // TODO: collect daily numbers from population
    }

    /**
     * Generates a report of collected statistics.
     */
    public void generateReport()
    {
        // TODO: print stats
    }

    /**
     * Exports collected statistics to a CSV file.
     * 
     * @param filename the path or name of the CSV file
     */
    public void exportToCSV(String filename)
    {
        // TODO: write CSV file
    }
}
