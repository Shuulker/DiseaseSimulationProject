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
 * Represents a collection of Person objects in the simulation.
 * Manages population size and provides layout information for visualization.
 */

// Population IS-A collection of Person objects
// Population HAS-A list of Person and size
public class Population
{
    private List<Person> people;     // list of persons in the simulation
    private int size;                 // total number of people

    /**
     * Constructs a default Population of 10,000 people
     */
    public Population()
    {
        this(10000); // default value set here
    }

    /**
     * Constructs a Population of a specified size
     * Initializes all Person objects
     * 
     * @param size the number of people in the population
     */
    public Population(int size)
    {
        this.size = Math.max(0, size);
        initializePeople();
    }

    /**
     * Initializes the list of Person objects based on the current size
     */
    private void initializePeople()
    {
        people = new ArrayList<>(Math.max(0, size));
        for (int i = 0; i < size; i++)
        {
            people.add(new Person(i));
        }
    }

    /**
     * Retrieves the list of Person objects
     * 
     * @return list of persons in the population
     */
    public List<Person> getPeople()
    {
        return people;
    }

    /**
     * Returns the number of people in the population
     * 
     * @return current population size
     */
    public int size()
    {
        return people.size();
    }

    /**
     * Calculates the number of columns for grid layout
     * Used for visualizing population in a square-ish grid
     * 
     * @return number of columns
     */
    public int getColumnCount()
    {
        return (int) Math.ceil(Math.sqrt(people.size()));
    }

    /**
     * Calculates the number of rows for grid layout
     * Used for visualizing population in a square-ish grid
     * 
     * @return number of rows
     */
    public int getRowCount()
    {
        int cols = getColumnCount();
        return (int) Math.ceil((double) people.size() / cols);
    }

    /**
     * Sets a new population size and re-initializes all Person objects
     * 
     * @param newSize new size of the population
     */
    public void setSize(int newSize)
    {
        this.size = Math.max(0, newSize);
        initializePeople();
    }
}
