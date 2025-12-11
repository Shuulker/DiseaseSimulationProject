import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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
 * Provides a control panel for the simulation.
 * Allows starting, pausing, resetting, and returning to setup.
 * Displays current day and statistics for all health states.
 */

// ControlPanel HAS-A SimulationPanel, Controller, Statistics
// ControlPanel IS-A JPanel (UI component)
public class ControlPanel extends JPanel
{
    public interface ControlPanelListener
    {
        /**
         * Callback for returning to the setup panel
         * 
         * @param config current simulation configuration
         */
        void onBackToSetup(SimulationConfig config);
    }

    private final JButton startButton;          // start simulation button
    private final JButton resetButton;          // reset simulation button
    private final JButton pauseButton;          // pause/resume button
    private final JButton backButton;           // return to setup button
    private final JLabel dayLabel;              // current day label
    private final JLabel susceptibleLabel;      // susceptible count label
    private final JLabel infectedLabel;         // infected count label
    private final JLabel recoveringLabel;       // recovering count label
    private final JLabel safeLabel;             // safe count label
    private final JLabel deadLabel;             // dead count label

    private final SimulationPanel simulationPanel;
    private final Controller controller;
    private final Statistics statistics;
    private final ControlPanelListener listener;

    private Timer timer;                        // timer for automatic simulation stepping
    private boolean paused;                     // simulation paused flag

    /**
     * Constructor for ControlPanel
     * Sets up all UI components and button actions
     * 
     * @param simulationPanel panel displaying the simulation grid
     * @param controller controller for simulation logic
     * @param listener callback listener for back-to-setup action
     */
    public ControlPanel(SimulationPanel simulationPanel, Controller controller, ControlPanelListener listener)
    {
        this.simulationPanel = simulationPanel;
        this.controller = controller;
        this.statistics = new Statistics();     // initialize stats
        this.listener = listener;

        setLayout(new GridLayout(10, 1, 5, 5));

        startButton = new JButton("Start Simulation");
        pauseButton = new JButton("Resume");   // initially paused
        resetButton = new JButton("Reset");
        backButton = new JButton("Back to Setup");

        dayLabel = new JLabel("Day: 0 / " + controller.getMaxDays(), SwingConstants.CENTER);
        susceptibleLabel = new JLabel("Susceptible: 0", SwingConstants.CENTER);
        infectedLabel = new JLabel("Infected: 0", SwingConstants.CENTER);
        recoveringLabel = new JLabel("Recovering: 0", SwingConstants.CENTER);
        safeLabel = new JLabel("Safe: 0", SwingConstants.CENTER);
        deadLabel = new JLabel("Dead: 0", SwingConstants.CENTER);

        startButton.addActionListener(this::startSimulation);
        resetButton.addActionListener(this::resetSimulation);
        pauseButton.addActionListener(this::togglePause);
        backButton.addActionListener(this::backToSetup);

        add(startButton);
        add(pauseButton);
        add(resetButton);
        add(dayLabel);
        add(susceptibleLabel);
        add(infectedLabel);
        add(recoveringLabel);
        add(safeLabel);
        add(deadLabel);
        add(backButton);

        paused = true; // simulation starts paused
        updateStats();
    }

    /**
     * Starts the simulation timer and begins stepping through days
     * 
     * @param e ActionEvent from start button
     */
    private void startSimulation(ActionEvent e)
    {
        if (timer == null)
        {
            controller.start();
            statistics.reset(); // clear previous stats

            timer = new Timer(300, evt ->
            {
                if (!paused)
                {
                    boolean cont = controller.step(); // perform one simulation step
                    simulationPanel.setPopulationAndDisease(controller.getPopulation(), controller.getDisease());
                    simulationPanel.repaint();

                    statistics.recordDay(controller.getPopulation()); // update stats
                    dayLabel.setText("Day: " + controller.getCurrentDay() + " / " + controller.getMaxDays());
                    updateStats();

                    if (!cont)
                    {
                        timer.stop();
                        startButton.setEnabled(true);
                        paused = true;
                        pauseButton.setText("Resume");
                    }
                }
            });

            timer.start();
        }

        paused = false;
        pauseButton.setText("Pause");
        startButton.setEnabled(false);
    }

    /**
     * Toggles the pause state of the simulation
     * 
     * @param e ActionEvent from pause button
     */
    private void togglePause(ActionEvent e)
    {
        paused = !paused;
        pauseButton.setText(paused ? "Resume" : "Pause");
    }

    /**
     * Resets the simulation to the initial configuration
     * 
     * @param e ActionEvent from reset button
     */
    private void resetSimulation(ActionEvent e)
    {
        if (timer != null)
        {
            timer.stop();
            timer = null;
        }

        SimulationConfig config = controller.getConfig();
        if (config != null)
        {
            controller.applyConfig(config);
        }

        statistics.reset(); // clear stats

        simulationPanel.setPopulationAndDisease(controller.getPopulation(), controller.getDisease());
        simulationPanel.setInteractiveSetup(true);
        simulationPanel.repaint();

        dayLabel.setText("Day: " + controller.getCurrentDay() + " / " + controller.getMaxDays());
        updateStats();

        startButton.setEnabled(true);
        paused = true;
        pauseButton.setText("Resume"); // always show resume after reset
    }

    /**
     * Returns to the setup panel
     * 
     * @param e ActionEvent from back button
     */
    private void backToSetup(ActionEvent e)
    {
        if (timer != null)
        {
            timer.stop();
            timer = null;
        }

        paused = true;
        pauseButton.setText("Resume");
        startButton.setEnabled(true);

        if (listener != null)
        {
            listener.onBackToSetup(controller.getConfig());
        }
    }

    /**
     * Updates the statistics labels to display current values
     */
    private void updateStats()
    {
        susceptibleLabel.setText("Susceptible: " + statistics.getLatestSusceptible());
        infectedLabel.setText("Infected: " + statistics.getLatestInfected());
        recoveringLabel.setText("Recovering: " + statistics.getLatestRecovering());
        safeLabel.setText("Safe: " + statistics.getLatestSafe());
        deadLabel.setText("Dead: " + statistics.getLatestDeaths());
    }
}
