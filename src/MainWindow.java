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
 * Represents the main window of the simulation application.
 * Handles switching between setup and simulation screens.
 * Manages Controller, SimulationPanel, ControlPanel, and SimulationConfig objects.
 */

// MainWindow IS-A JFrame
// MainWindow HAS-A Controller, SimulationPanel, ControlPanel, SimulationConfig
public class MainWindow extends JFrame
{
    private Controller controller;                 // simulation controller
    private SimulationPanel simulationPanel;       // panel that renders the population and disease
    private ControlPanel controlPanel;             // panel with simulation controls
    private SimulationConfig currentConfig;        // current simulation configuration
    private final PresetManager presetManager;

    /**
     * Default constructor
     * Sets up window size, title, layout, and shows setup screen
     */
    public MainWindow()
    {
        setTitle("Disease Spread Simulation");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        presetManager = new PresetManager(); 

        showSetupScreen();

        setVisible(true);
    }

    /**
     * Displays the simulation setup screen
     * Allows user to configure simulation parameters
     */
    private void showSetupScreen()
    {
        // if currentConfig is null (first time), create a default
        if (currentConfig == null)
        {
            currentConfig = new SimulationConfig();
        }

        // create the setup panel with callback for when setup is complete
        SimulationSetupPanel setupPanel = new SimulationSetupPanel(config ->
        {
            // save the config so it can be reused when returning
            currentConfig = config;

            controller = new Controller();
            controller.applyConfig(config);

            simulationPanel = new SimulationPanel(controller.getPopulation(), controller.getDisease());
            simulationPanel.setInteractiveSetup(true);

            // create the control panel with callback to return to setup
            controlPanel = new ControlPanel(simulationPanel, controller, cpConfig ->
            {
                currentConfig = cpConfig;
                showSetupScreen();
            });

            getContentPane().removeAll();
            add(simulationPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.EAST);
            revalidate();
            repaint();
        }, currentConfig, presetManager); // pass the existing config

        getContentPane().removeAll();
        add(setupPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Application entry point
     * Launches the GUI on the Event Dispatch Thread
     * 
     * @param args command line arguments (unused)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
