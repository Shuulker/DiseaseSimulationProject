import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Displays the population grid, coloring each person according to their health status.
 * Supports interactive setup by allowing pre-infection of individuals via mouse clicks.
 */

// SimulationPanel has-a Population and Disease
public class SimulationPanel extends JPanel
{
    private Population population;
    private Disease disease;
    private boolean interactiveSetup = false; // allow clicking to pre-infect

    /**
     * Constructor: creates a SimulationPanel
     *
     * @param population the population to display
     * @param disease the disease to simulate
     */
    public SimulationPanel(Population population, Disease disease)
    {
        setBackground(Color.WHITE);
        this.population = population;
        this.disease = disease;

        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                handleMouseClick(e);
            }
        });
    }

    /**
     * Handles mouse click for interactive infection setup.
     *
     * @param e mouse event
     */
    private void handleMouseClick(MouseEvent e)
    {
        if (!interactiveSetup || population == null || disease == null) return;

        int col = e.getX() / getCellWidth();
        int row = e.getY() / getCellHeight();
        int index = row * population.getColumnCount() + col;

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
     * Update the population and disease references.
     *
     * @param population new population
     * @param disease new disease
     */
    public void setPopulationAndDisease(Population population, Disease disease)
    {
        this.population = population;
        this.disease = disease;
        repaint();
    }

    /**
     * Enable or disable interactive infection setup.
     *
     * @param interactive true to allow clicking to infect
     */
    public void setInteractiveSetup(boolean interactive)
    {
        this.interactiveSetup = interactive;
    }

    /**
     * Paint the population grid.
     *
     * @param g graphics context
     */
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (population == null) return;

        List<Person> people = population.getPeople();
        int total = people.size();
        if (total == 0) return;

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

            g.setColor(getColorForHealthStatus(p.getHealthStatus()));
            g.fillRect(x, y, cellW, cellH);
        }
    }

    /**
     * Returns a color corresponding to a health status.
     *
     * @param status health status
     * @return color to draw
     */
    private Color getColorForHealthStatus(HealthStatus status)
    {
        switch (status)
        {
            case SUSCEPTIBLE: return Color.GREEN;
            case INFECTED: return Color.RED;
            case RECOVERING: return Color.ORANGE;
            case RECOVERED: return new Color(0, 100, 0); // DARK GREEN
            case VACCINATED: return new Color(0, 150, 255); // LIGHT BLUE
            case DEAD: return Color.DARK_GRAY;
            default: return Color.BLACK;
        }
    }

    /**
     * Computes width of a single cell in the grid.
     *
     * @return cell width
     */
    private int getCellWidth()
    {
        return population == null ? 0 : getWidth() / population.getColumnCount();
    }

    /**
     * Computes height of a single cell in the grid.
     *
     * @return cell height
     */
    private int getCellHeight()
    {
        return population == null ? 0 : getHeight() / population.getRowCount();
    }
}
