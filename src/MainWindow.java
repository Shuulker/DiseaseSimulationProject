import javax.swing.*;
import java.awt.*;

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
 * Represents the main application window for the disease spread simulation.
 * Sets up the layout, initializes SimulationPanel and ControlPanel, and displays the GUI.
 */

// MainWindow IS-A JFrame
// MainWindow has-a SimulationPanel
// MainWindow has-a ControlPanel
public class MainWindow extends JFrame
{

    /**
     * Constructs the main application window.
     * Initializes the simulation panel, control panel, layout, and displays the GUI.
     */
    public MainWindow()
    {
        setTitle("Disease Spread Simulation");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        SimulationPanel simulationPanel = new SimulationPanel(new Population(100));
        ControlPanel controlPanel = new ControlPanel(simulationPanel);

        add(simulationPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);

        setVisible(true);
    }

    /**
     * Main method launches the application using the Swing event dispatch thread.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
