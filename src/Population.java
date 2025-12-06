import java.util.ArrayList;
import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Manages a collection of Person objects, provides grid layout information,
 * and handles updating health progression for all individuals.
 */

// Population has-a list of Person objects
public class Population
{
    private final List<Person> people;

    /**
     * Constructor: creates a population of the given size
     *
     * @param size number of Person objects in the population
     */
    public Population(int size)
    {
        people = new ArrayList<>(Math.max(0, size));
        for (int i = 0; i < size; i++)
        {
            people.add(new Person(i));
        }
    }

    // -------------------------------
    // Getters / Utility
    // -------------------------------

    /**
     * Retrieves the list of people in the population
     *
     * @return list of Person objects
     */
    public List<Person> getPeople()
    {
        return people;
    }

    /**
     * Returns number of columns for grid layout based on sqrt(N)
     *
     * @return number of columns
     */
    public int getColumnCount()
    {
        return (int) Math.ceil(Math.sqrt(people.size()));
    }

    /**
     * Returns number of rows for grid layout based on column count
     *
     * @return number of rows
     */
    public int getRowCount()
    {
        int cols = getColumnCount();
        return (int) Math.ceil((double) people.size() / cols);
    }

    /**
     * Returns number of people in the population
     *
     * @return population size
     */
    public int size()
    {
        return people.size();
    }

    // -------------------------------
    // Health Update Logic
    // -------------------------------

    /**
     * Updates the health of all individuals in the population
     *
     * @param disease the disease affecting the population
     */
    public void updateHealthAll(Disease disease)
    {
        for (Person p : people)
        {
            p.updateHealth(disease);
        }
    }
}
