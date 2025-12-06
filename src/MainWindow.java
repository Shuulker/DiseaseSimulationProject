import javax.swing.*;
import java.awt.*;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Main application window. Shows setup screen first, builds controller and
 * main simulation UI after user configures settings.
 */

// MainWindow IS-A JFrame
// MainWindow has-a Controller, SimulationPanel, and ControlPanel
public class MainWindow extends JFrame
{
    private Controller controller;
    private SimulationPanel simulationPanel;
    private ControlPanel controlPanel;

    /**
     * Constructor: initializes the main window and displays the setup screen.
     */
    public MainWindow()
    {
        setTitle("Disease Spread Simulation");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        showSetupScreen();

        setVisible(true);
    }

    /**
     * Displays the initial setup screen where the user configures simulation parameters.
     */
    private void showSetupScreen()
    {
        SimulationSetupPanel setupPanel = new SimulationSetupPanel(config -> 
        {
            // 1️ Create controller and apply configuration
            controller = new Controller();
            controller.applyConfig(config);

            // 2️ Create SimulationPanel with both population & disease
            simulationPanel = new SimulationPanel(controller.getPopulation(), controller.getDisease());

            // Enable interactive setup BEFORE starting simulation
            simulationPanel.setInteractiveSetup(true);

            // 3️ Create ControlPanel (start/reset buttons)
            controlPanel = new ControlPanel(simulationPanel, controller);

            // 4️ Replace setup screen with simulation UI
            getContentPane().removeAll();
            add(simulationPanel, BorderLayout.CENTER);
            add(controlPanel, BorderLayout.EAST);
            revalidate();
            repaint();
        });

        getContentPane().removeAll();
        add(setupPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Main entry point for the simulation application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
