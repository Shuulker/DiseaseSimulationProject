import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
 * Represents the visual simulation grid.
 * Draws people colored by health status and allows interactive infection during setup.
 */

// SimulationPanel IS-A JPanel
// SimulationPanel HAS-A Population, Disease, interactiveSetup flag
public class SimulationPanel extends JPanel
{
    private Population population;         // simulation population
    private Disease disease;               // simulation disease
    private boolean interactiveSetup;      // whether users can click to infect people

    /**
     * Constructs a SimulationPanel with the given Population and Disease
     * 
     * @param population simulation population
     * @param disease simulation disease
     */
    public SimulationPanel(Population population, Disease disease)
    {
        this.population = population;
        this.disease = disease;
        this.interactiveSetup = false;

        setBackground(Color.WHITE);

        addMouseListener(new java.awt.event.MouseAdapter()
        {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e)
            {
                handleMouseClick(e);
            }
        });
    }

    /**
     * Updates the population and disease used by the panel and repaints
     * 
     * @param population simulation population
     * @param disease simulation disease
     */
    public void setPopulationAndDisease(Population population, Disease disease)
    {
        this.population = population;
        this.disease = disease;
        repaint();
    }

    /**
     * Sets whether the panel is in interactive setup mode
     * 
     * @param interactive true to enable interactive infection
     */
    public void setInteractiveSetup(boolean interactive)
    {
        this.interactiveSetup = interactive;
    }

    /**
     * Handles mouse clicks for infecting a person during interactive setup
     * 
     * @param e mouse event
     */
    private void handleMouseClick(java.awt.event.MouseEvent e)
    {
        if (!interactiveSetup || population == null || disease == null)
        {
            return;
        }

        int cols = population.getColumnCount();
        int rows = population.getRowCount();

        int col = e.getX() / Math.max(1, getCellWidth());
        int row = e.getY() / Math.max(1, getCellHeight());

        int index = row * cols + col;

        List<Person> people = population.getPeople();

        if (index >= 0 && index < people.size())
        {
            Person p = people.get(index);

            if (p.getHealthStatus() == HealthStatus.SUSCEPTIBLE)
            {
                p.infect(disease);
                repaint();
            }
        }
    }

    /**
     * Paints the simulation grid of people colored by health status
     * 
     * @param g Graphics object
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (population == null)
        {
            return;
        }

        List<Person> people = population.getPeople();
        int total = people.size();

        if (total == 0)
        {
            return;
        }

        int cols = population.getColumnCount();
        int rows = population.getRowCount();
        int cellW = getCellWidth();
        int cellH = getCellHeight();

        for (int i = 0; i < total; i++)
        {
            Person p = people.get(i);
            int col = i % cols;
            int row = i / cols;
            int x = col * cellW;
            int y = row * cellH;

            g.setColor(colorFor(p.getHealthStatus()));
            g.fillRect(x, y, cellW, cellH);
        }  
        g.setColor(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 14));
        int gridHeight = population.getRowCount() * getCellHeight();
        int textY = gridHeight + 20;   // space below the grid
        g.drawString("Click anywhere on the green to add infected", 10, textY);
    }

    /**
     * Computes the width of each grid cell
     * 
     * @return cell width in pixels
     */
    private int getCellWidth()
    {
        return population == null ? 1 : Math.max(1, getWidth() / population.getColumnCount());
    }

    /**
     * Computes the height of each grid cell
     * 
     * @return cell height in pixels
     */
    private int getCellHeight()
    {
        return population == null ? 1 : Math.max(1, getHeight() / population.getRowCount());
    }

    /**
     * Returns a color representing the given health status
     * 
     * @param s health status
     * @return color to use in rendering
     */
    private Color colorFor(HealthStatus s)
    {
        switch (s)
        {
            case SUSCEPTIBLE:
                return Color.GREEN;
            case INFECTED:
                return Color.RED;
            case CONTAGIOUS:
                return Color.ORANGE;
            case RECOVERED:
                return new Color(0, 100, 0); // DARK GREEN
            case VACCINATED:
                return new Color(0, 150, 255); // LIGHT BLUE
            case DEAD:
                return Color.DARK_GRAY;
            default:
                return Color.BLACK;
        }
    }
}

