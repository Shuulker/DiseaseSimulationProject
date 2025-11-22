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
 * Represents a vaccination in the simulation.
 * Can be applied to a Person and reduce disease transmission in the population.
 */

// Vaccination has-a name
// Vaccination has-a efficacy
// Vaccination has-a coverageRate
public class Vaccination
{

    private String name;
    private float efficacy;
    private float coverageRate;

    /**
     * Constructs a Vaccination with the specified name.
     * 
     * @param name the name of the vaccination
     */
    public Vaccination(String name)
    {
        this.name = name;
    }

    /**
     * Applies the vaccination to a Person.
     * 
     * @param p the Person to vaccinate
     */
    public void applyTo(Person p)
    {
        // TODO
    }

    /**
     * Reduces disease transmission for the specified Disease.
     * 
     * @param d the Disease to affect
     */
    public void reduceTransmission(Disease d)
    {
        // TODO
    }
}
