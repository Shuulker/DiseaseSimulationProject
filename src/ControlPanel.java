import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Manages simulation controls (start/reset), shows current day, and displays statistics
 * for the population (susceptible, infected, recovering, recovered, dead).
 */

// ControlPanel IS-A JPanel
// ControlPanel has-a Controller
// ControlPanel has-a SimulationPanel
public class ControlPanel extends JPanel
{
    // ---- UI Components ----
    private final JButton startButton;
    private final JButton resetButton;
    private final JLabel dayLabel;
    private final JLabel susceptibleLabel;
    private final JLabel infectedLabel;
    private final JLabel recoveringLabel;
    private final JLabel recoveredLabel;
    private final JLabel deadLabel;

    // ---- References ----
    private final SimulationPanel simulationPanel;
    private final Controller controller;
    private Timer timer;

    /**
     * Constructor for ControlPanel
     * Initializes all buttons, labels, layout, and links to simulation panel and controller.
     *
     * @param simulationPanel the panel displaying the simulation
     * @param controller the simulation controller
     */
    public ControlPanel(SimulationPanel simulationPanel, Controller controller)
    {
        this.simulationPanel = simulationPanel;
        this.controller = controller;

        setLayout(new GridLayout(9, 1, 5, 5));

        startButton = new JButton("Start Simulation");
        resetButton = new JButton("Reset");
        dayLabel = new JLabel("Day: 0 / " + controller.getMaxDays(), SwingConstants.CENTER);
        susceptibleLabel = new JLabel("Healthy: 0", SwingConstants.CENTER);
        infectedLabel = new JLabel("Infected: 0", SwingConstants.CENTER);
        recoveringLabel = new JLabel("Recovering: 0", SwingConstants.CENTER);
        recoveredLabel = new JLabel("Recovered: 0", SwingConstants.CENTER);
        deadLabel = new JLabel("Dead: 0", SwingConstants.CENTER);

        startButton.addActionListener(this::startSimulation);
        resetButton.addActionListener(this::resetSimulation);

        add(startButton);
        add(resetButton);
        add(dayLabel);
        add(susceptibleLabel);
        add(infectedLabel);
        add(recoveringLabel);
        add(recoveredLabel);
        add(deadLabel);

        updateStats(); // show initial statistics

        if (controller.getPopulation() != null)
        {
            simulationPanel.setPopulationAndDisease(controller.getPopulation(), controller.getDisease());
        }
    }

    /**
     * Starts the simulation when the start button is pressed.
     * Disables the button during simulation and updates the UI on each step.
     *
     * @param e the ActionEvent triggered by button click
     */
    private void startSimulation(ActionEvent e)
    {
        if (timer != null && timer.isRunning())
        {
            return;
        }

        startButton.setEnabled(false);
        controller.start();

        timer = new Timer(300, evt ->
        {
            boolean cont = controller.step();
            simulationPanel.setPopulationAndDisease(controller.getPopulation(), controller.getDisease());
            simulationPanel.repaint();

            dayLabel.setText("Day: " + controller.getCurrentDay() + " / " + controller.getMaxDays());
            updateStats();

            if (!cont)
            {
                timer.stop();
                startButton.setEnabled(true);
            }
        });

        timer.start();
    }

    /**
     * Resets the simulation to the initial state.
     * Re-applies configuration, re-enables interactive setup, and updates statistics.
     *
     * @param e the ActionEvent triggered by button click
     */
    private void resetSimulation(ActionEvent e)
    {
        if (timer != null && timer.isRunning())
        {
            timer.stop();
        }

        SimulationConfig config = controller.getConfig();
        if (config != null)
        {
            controller.applyConfig(config);  // create new population & disease
        }

        simulationPanel.setPopulationAndDisease(controller.getPopulation(), controller.getDisease());
        simulationPanel.setInteractiveSetup(true); // re-enable pre-infection clicking
        simulationPanel.repaint();

        dayLabel.setText("Day: " + controller.getCurrentDay() + " / " + controller.getMaxDays());
        updateStats();
        startButton.setEnabled(true);
    }

    /**
     * Updates the statistics labels based on the current population.
     */
    private void updateStats()
    {
        Population pop = controller.getPopulation();
        if (pop == null)
        {
            return;
        }

        int susceptible = 0;
        int infected = 0;
        int recovering = 0;
        int recovered = 0;
        int dead = 0;

        for (Person p : pop.getPeople())
        {
            switch (p.getHealthStatus())
            {
                case SUSCEPTIBLE -> susceptible++;
                case INFECTED -> infected++;
                case RECOVERING -> recovering++;
                case RECOVERED -> recovered++;
                case DEAD -> dead++;
            }
        }

        susceptibleLabel.setText("Healthy: " + susceptible);
        infectedLabel.setText("Infected: " + infected);
        recoveringLabel.setText("Recovering: " + recovering);
        recoveredLabel.setText("Recovered: " + recovered);
        deadLabel.setText("Dead: " + dead);
    }
}
