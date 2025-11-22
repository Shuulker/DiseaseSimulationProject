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
 * Provides the user interface controls for the simulation, including buttons to start
 * and reset the simulation, and a label to show the current day.
 * Interacts with SimulationPanel to update the display and manages the simulation timer.
 */

// ControlPanel IS-A JPanel
// ControlPanel has-a SimulationPanel
// ControlPanel has-a Population
// ControlPanel has-a Disease
public class ControlPanel extends JPanel
{

    private JButton startButton, resetButton;
    private JLabel dayLabel;

    private SimulationPanel simulationPanel;
    private Population population;
    private Disease disease;

    private Timer timer;
    private int currentDay = 0;
    private final int maxDays = 50;

    /**
     * Constructs a ControlPanel associated with the given SimulationPanel.
     * Initializes UI components, simulation objects, and event handlers.
     * 
     * @param simulationPanel the panel responsible for rendering the simulation
     */
    public ControlPanel(SimulationPanel simulationPanel)
    {
        this.simulationPanel = simulationPanel;

        setLayout(new GridLayout(5, 1, 5, 5));

        startButton = new JButton("Start Simulation");
        resetButton = new JButton("Reset");
        dayLabel = new JLabel("Day: 0 / " + maxDays);

        startButton.addActionListener(this::startSimulation);
        resetButton.addActionListener(this::resetSimulation);

        add(startButton);
        add(resetButton);
        add(dayLabel);

        // initialize population & disease
        population = new Population(300); // 300 people
        disease = new Disease("TestVirus", 0.2f, 2); // 20% infection rate, 2-day incubation
        simulationPanel.setPopulation(population);
    }

    /**
     * Starts the simulation timer and updates the population daily.
     * Prevents multiple timers from running simultaneously.
     * Updates the SimulationPanel and day label on each step.
     * 
     * @param e the ActionEvent triggered by pressing the start button
     */
    private void startSimulation(ActionEvent e)
    {
        if (timer != null && timer.isRunning())
            return;

        startButton.setEnabled(false);
        currentDay = 0;

        timer = new Timer(300, evt ->
        {
            if (currentDay < maxDays)
            {
                population.step(disease);
                simulationPanel.repaint();
                currentDay++;
                dayLabel.setText("Day: " + currentDay + " / " + maxDays);
            }
            else
            {
                timer.stop();
                startButton.setEnabled(true);
            }
        });

        timer.start();
    }

    /**
     * Resets the simulation to its initial state.
     * Stops any running timer, re-initializes the population, and updates the display.
     * 
     * @param e the ActionEvent triggered by pressing the reset button
     */
    private void resetSimulation(ActionEvent e)
    {
        if (timer != null && timer.isRunning())
        {
            timer.stop();
        }

        population = new Population(300);
        simulationPanel.setPopulation(population);
        simulationPanel.repaint();
        currentDay = 0;
        dayLabel.setText("Day: 0 / " + maxDays);
        startButton.setEnabled(true);
    }
}
