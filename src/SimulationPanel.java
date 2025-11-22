import javax.swing.*;
import java.awt.*;
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
 * Responsible for rendering the simulation visually.
 * Draws each Person as a colored circle based on their HealthStatus.
 * Updates dynamically as the simulation progresses.
 */

// SimulationPanel IS-A JPanel
// SimulationPanel has-a Population
public class SimulationPanel extends JPanel
{

    private Population population;

    /**
     * Constructs a SimulationPanel associated with a given Population.
     * Sets the background color and stores the Population reference.
     * 
     * @param population the Population to render
     */
    public SimulationPanel(Population population)
    {
        setBackground(Color.WHITE);
        this.population = population;
    }
    
    /**
     * Updates the Population displayed by this panel.
     * 
     * @param population the new Population to display
     */
    public void setPopulation(Population population)
    {
        this.population = population;
    }

    /**
     * Paints the simulation.
     * Each Person is drawn as a colored circle according to their health status.
     * 
     * @param g the Graphics context for drawing
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        List<Person> people = population.getPeople();
        int cols = 20;
        int spacing = 25;

        for (int i = 0; i < people.size(); i++)
        {
            Person p = people.get(i);
            int x = (i % cols) * spacing + 20;
            int y = (i / cols) * spacing + 20;

            switch (p.getHealthStatus())
            {
                case SUSCEPTIBLE:
                    g.setColor(Color.GREEN);
                    break;
                case INFECTED:
                    g.setColor(Color.RED);
                    break;
                case RECOVERED:
                    g.setColor(Color.BLUE);
                    break;
                case DEAD:
                    g.setColor(Color.GRAY);
                    break;
            }

            g.fillOval(x, y, 15, 15);
        }
    }
}
