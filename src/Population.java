import java.util.ArrayList;
import java.util.List;

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
 * Represents the population of people in the simulation.
 * Manages the list of Person objects and handles daily infection spread and health updates.
 */

// Population has-a people
public class Population
{

    private List<Person> people = new ArrayList<>();

    /**
     * Constructs a Population with the specified size.
     * Initializes the list of Person objects and infects one person initially.
     * 
     * @param size the number of people in the population
     */
    public Population(int size)
    {
        for (int i = 0; i < size; i++)
        {
            people.add(new Person(i));
        }

        // infect one person initially
        if (!people.isEmpty())
        {
            people.get(0).infect();
        }
    }

    /**
     * Returns the list of people in the population.
     * 
     * @return list of Person objects
     */
    public List<Person> getPeople()
    {
        return people;
    }

    /**
     * Advances the simulation by one day.
     * Each infected person attempts to infect 1-3 random others,
     * then all people update their health status.
     * 
     * @param disease the Disease being simulated
     */
    public void step(Disease disease)
    {
        // try to infect some random people
        for (Person p : people)
        {
            if (p.getHealthStatus() == HealthStatus.INFECTED)
            {
                int contacts = 1 + (int)(Math.random() * 3);
                for (int i = 0; i < contacts; i++)
                {
                    Person target = people.get((int)(Math.random() * people.size()));
                    disease.tryInfect(target);
                }
            }
        }

        // update everyone’s health (progress infection -> recovery)
        for (Person p : people)
        {
            p.updateHealth(disease);
        }
    }
}
