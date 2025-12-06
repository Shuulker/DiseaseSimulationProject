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
 * Represents the possible health states of a Person in the simulation.
 * Used to track infection, recovery, and mortality status.
 */

// HealthStatus IS-A enumeration of possible person health states
public enum HealthStatus
{
    SUSCEPTIBLE, // person is healthy but can be infected
    INFECTED,    // person is currently infected
    RECOVERING,  // person who is recovered but still contagious
    RECOVERED,   // person has recovered and not contagious
    VACCINATED,   // person who is vaccinated
    DEAD         // person has died from infection
}
