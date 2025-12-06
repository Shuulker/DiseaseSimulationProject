import javax.swing.*;
import java.awt.*;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Setup UI allowing user to configure simulation parameters before starting.
 * - Recovery and contagious min/max fields on the same row
 * - Infection rate is a slider (0–1)
 * - Compact, aligned layout similar to original
 */

// SimulationSetupPanel has-a SimulationConfig (via listener)
public class SimulationSetupPanel extends JPanel
{
    private final JTextField populationField;
    private final JSlider infectionRateSlider;
    private final JTextField incubationField;
    private final JCheckBox enableVaccinationCheckbox;
    private final JTextField vaccinationEfficacyField;
    private final JTextField recoveryMinField;
    private final JTextField recoveryMaxField;
    private final JTextField contagiousMinField;
    private final JTextField contagiousMaxField;
    private final JTextField maxDaysField;
    private final JButton startButton;

    private final SetupListener listener;

    /**
     * Listener interface for notifying when the user has completed setup
     */
    public interface SetupListener
    {
        void onSetupComplete(SimulationConfig config);
    }

    /**
     * Constructor: builds the setup UI
     *
     * @param listener called when the user clicks Start
     */
    public SimulationSetupPanel(SetupListener listener)
    {
        this.listener = listener;
        setLayout(new GridLayout(10, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // -------------------------------
        // Population Size
        // -------------------------------
        populationField = new JTextField("300");
        add(new JLabel("Population Size:"));
        add(populationField);

        // -------------------------------
        // Infection Rate Slider
        // -------------------------------
        infectionRateSlider = new JSlider(0, 100, 20);
        infectionRateSlider.setMajorTickSpacing(20);
        infectionRateSlider.setMinorTickSpacing(5);
        infectionRateSlider.setPaintTicks(true);
        infectionRateSlider.setPaintLabels(true);
        add(new JLabel("Infection Rate (0%–100%):"));
        add(infectionRateSlider);

        // -------------------------------
        // Incubation Period
        // -------------------------------
        incubationField = new JTextField("2");
        add(new JLabel("Incubation Period (days):"));
        add(incubationField);

        // -------------------------------
        // Enable Vaccination
        // -------------------------------
        enableVaccinationCheckbox = new JCheckBox("Enable Vaccination?");
        add(enableVaccinationCheckbox);
        add(new JLabel()); // filler

        vaccinationEfficacyField = new JTextField("0.6");
        vaccinationEfficacyField.setEnabled(false);
        enableVaccinationCheckbox.addActionListener(e ->
        {
            vaccinationEfficacyField.setEnabled(enableVaccinationCheckbox.isSelected());
        });
        add(new JLabel("Vaccination Efficacy (0–1):"));
        add(vaccinationEfficacyField);

        // -------------------------------
        // Recovery Time (Min/Max)
        // -------------------------------
        recoveryMinField = new JTextField("3");
        recoveryMaxField = new JTextField("5");
        JPanel recoveryPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        recoveryPanel.add(new JLabel("Min:"), gbc);
        gbc.gridx = 1;
        recoveryMinField.setColumns(5);
        recoveryPanel.add(recoveryMinField, gbc);
        gbc.gridx = 2;
        recoveryPanel.add(new JLabel("Max:"), gbc);
        gbc.gridx = 3;
        recoveryMaxField.setColumns(5);
        recoveryPanel.add(recoveryMaxField, gbc);

        add(new JLabel("Recovery Time (days):"));
        add(recoveryPanel);

        // -------------------------------
        // Contagious Recovery (Min/Max)
        // -------------------------------
        contagiousMinField = new JTextField("2");
        contagiousMaxField = new JTextField("4");
        JPanel contagiousPanel = new JPanel(new GridBagLayout());

        gbc.gridx = 0; gbc.gridy = 0;
        contagiousPanel.add(new JLabel("Min:"), gbc);
        gbc.gridx = 1;
        contagiousMinField.setColumns(5);
        contagiousPanel.add(contagiousMinField, gbc);
        gbc.gridx = 2;
        contagiousPanel.add(new JLabel("Max:"), gbc);
        gbc.gridx = 3;
        contagiousMaxField.setColumns(5);
        contagiousPanel.add(contagiousMaxField, gbc);

        add(new JLabel("Contagious Recovery (days):"));
        add(contagiousPanel);

        // -------------------------------
        // Max Simulation Days
        // -------------------------------
        maxDaysField = new JTextField("50");
        add(new JLabel("Max Simulation Days:"));
        add(maxDaysField);

        // -------------------------------
        // Start Button
        // -------------------------------
        startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> sendData());
        add(new JLabel()); // empty placeholder
        add(startButton);
    }

    /**
     * Reads input fields, validates, creates SimulationConfig, and notifies listener
     */
    private void sendData()
    {
        try
        {
            int pop = Integer.parseInt(populationField.getText().trim());
            float infectRate = infectionRateSlider.getValue() / 100f;
            int incubation = Integer.parseInt(incubationField.getText().trim());
            boolean vacc = enableVaccinationCheckbox.isSelected();
            float vaccEff = vacc ? Float.parseFloat(vaccinationEfficacyField.getText().trim()) : 0f;
            int maxDays = Integer.parseInt(maxDaysField.getText().trim());

            int recoveryMin = Integer.parseInt(recoveryMinField.getText().trim());
            int recoveryMax = Integer.parseInt(recoveryMaxField.getText().trim());
            int contagiousMin = Integer.parseInt(contagiousMinField.getText().trim());
            int contagiousMax = Integer.parseInt(contagiousMaxField.getText().trim());

            // -------------------------------
            // Validation
            // -------------------------------
            if (pop <= 0)
            {
                JOptionPane.showMessageDialog(this, "Population size must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pop > 425000)
            {
                JOptionPane.showMessageDialog(this, "Population too large.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (infectRate < 0f || infectRate > 1f)
            {
                JOptionPane.showMessageDialog(this, "Infection rate must be between 0 and 1.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (incubation < 0)
            {
                JOptionPane.showMessageDialog(this, "Incubation period cannot be negative.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (vaccEff < 0f || vaccEff > 1f)
            {
                JOptionPane.showMessageDialog(this, "Vaccination efficacy must be between 0 and 1.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (maxDays <= 0)
            {
                JOptionPane.showMessageDialog(this, "Max simulation days must be positive.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (recoveryMin < 0 || recoveryMax < 0 || recoveryMin > recoveryMax)
            {
                JOptionPane.showMessageDialog(this, "Recovery time min/max must be non-negative and min ≤ max.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (contagiousMin < 0 || contagiousMax < 0 || contagiousMin > contagiousMax)
            {
                JOptionPane.showMessageDialog(this, "Contagious recovery min/max must be non-negative and min ≤ max.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // -------------------------------
            // Build config
            // -------------------------------
            SimulationConfig config = new SimulationConfig(
                    pop, infectRate, incubation,
                    vacc, vaccEff, maxDays,
                    recoveryMin, recoveryMax,
                    contagiousMin, contagiousMax
            );

            listener.onSetupComplete(config);

        } catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
