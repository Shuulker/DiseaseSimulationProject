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
 * Provides the UI panel for configuring the simulation.
 * Updates the underlying SimulationConfig model with user input.
 */

// SimulationSetupPanel IS-A JPanel
// SimulationSetupPanel HAS-A SimulationConfig, Disease, Population, Vaccination, SetupListener, and input UI components
public class SimulationSetupPanel extends JPanel
{
    private final JTextField populationField;
    private final JTextField diseaseNameField;
    private final JSlider infectionRateSlider;
    private final JSlider mortalityRateSlider;
    private final JCheckBox enableVaccinationCheckbox;
    private final JTextField vaccinationStartDayField;
    private final JTextField dailyVaccMinField;
    private final JTextField dailyVaccMaxField;
    private final JTextField recoveryMinField;
    private final JTextField recoveryMaxField;
    private final JTextField contagiousMinField;
    private final JTextField contagiousMaxField;
    private final JTextField maxDaysField;
    private final JButton startButton;
    private final Disease diseaseModel;

    private final SetupListener listener;
    private final SimulationConfig config;

    public interface SetupListener
    {
        void onSetupComplete(SimulationConfig config);
    }

    /**
     * Constructs the setup panel using an existing SimulationConfig
     * 
     * @param listener callback to notify when setup is complete
     * @param existingConfig the current simulation configuration
     */
    public SimulationSetupPanel(SetupListener listener, SimulationConfig existingConfig)
    {
        this.listener = listener;
        this.config = existingConfig;
        this.diseaseModel = config.getDisease();

        setLayout(new GridLayout(12, 2, 5, 5));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Population popModel = config.getPopulation();
        Disease diseaseModel = config.getDisease();
        Vaccination vacc = config.getVaccination();

        // Population
        populationField = new JTextField(String.valueOf(popModel.size()));
        add(new JLabel("Population Size:"));
        add(populationField);

        // Disease name
        diseaseNameField = new JTextField(diseaseModel.getName());
        add(new JLabel("Disease Name:"));
        add(diseaseNameField);

        // Infection rate slider
        infectionRateSlider = new JSlider(0, 100, (int)(diseaseModel.getInfectionRate() * 100));
        infectionRateSlider.setMajorTickSpacing(20);
        infectionRateSlider.setMinorTickSpacing(5);
        infectionRateSlider.setPaintTicks(true);
        infectionRateSlider.setPaintLabels(true);
        add(new JLabel("Infection Rate (0%–100%):"));
        add(infectionRateSlider);

        // Mortality rate slider
        mortalityRateSlider = new JSlider(0, 100, (int)(diseaseModel.getMortalityRate() * 100));
        mortalityRateSlider.setMajorTickSpacing(20);
        mortalityRateSlider.setMinorTickSpacing(5);
        mortalityRateSlider.setPaintTicks(true);
        mortalityRateSlider.setPaintLabels(true);
        add(new JLabel("Mortality Rate (0%–100%):"));
        add(mortalityRateSlider);

        // Recovery range fields
        int[] recoveryRange = diseaseModel.getRecoveryDaysRange();
        recoveryMinField = new JTextField(String.valueOf(recoveryRange[0]));
        recoveryMaxField = new JTextField(String.valueOf(recoveryRange[1]));
        JPanel recoveryPanel = new JPanel(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(0, 5, 0, 5);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
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

        // Contagious range fields
        int[] contagiousRange = diseaseModel.getContagiousDaysRange();
        contagiousMinField = new JTextField(String.valueOf(contagiousRange[0]));
        contagiousMaxField = new JTextField(String.valueOf(contagiousRange[1]));
        JPanel contagiousPanel = new JPanel(new java.awt.GridBagLayout());
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

        // Vaccination controls
        enableVaccinationCheckbox = new JCheckBox("Enable Vaccination?");
        enableVaccinationCheckbox.setSelected(config.vaccinationEnabled);
        add(enableVaccinationCheckbox);
        add(new JLabel()); // filler

        vaccinationStartDayField = new JTextField(String.valueOf(vacc.getStartDay()));
        dailyVaccMinField = new JTextField(String.valueOf((int)(vacc.getDailyMin() * 100)));
        dailyVaccMaxField = new JTextField(String.valueOf((int)(vacc.getDailyMax() * 100)));

        vaccinationStartDayField.setEnabled(config.vaccinationEnabled);
        dailyVaccMinField.setEnabled(config.vaccinationEnabled);
        dailyVaccMaxField.setEnabled(config.vaccinationEnabled);

        enableVaccinationCheckbox.addActionListener(e -> {
            boolean enabled = enableVaccinationCheckbox.isSelected();
            vaccinationStartDayField.setEnabled(enabled);
            dailyVaccMinField.setEnabled(enabled);
            dailyVaccMaxField.setEnabled(enabled);
        });

        JPanel vaccPanel = new JPanel(new java.awt.GridBagLayout());
        gbc.gridx = 0; gbc.gridy = 0;
        vaccPanel.add(new JLabel("Min:"), gbc);
        gbc.gridx = 1;
        dailyVaccMinField.setColumns(5);
        vaccPanel.add(dailyVaccMinField, gbc);
        gbc.gridx = 2;
        vaccPanel.add(new JLabel("Max:"), gbc);
        gbc.gridx = 3;
        dailyVaccMaxField.setColumns(5);
        vaccPanel.add(dailyVaccMaxField, gbc);

        add(new JLabel("Vaccination Start Day:"));
        add(vaccinationStartDayField);
        add(new JLabel("Daily Vaccination %:"));
        add(vaccPanel);

        // Max days
        maxDaysField = new JTextField(String.valueOf(config.maxDays));
        add(new JLabel("Max Simulation Days:"));
        add(maxDaysField);

        // Start button
        startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> sendData());
        add(new JLabel()); // filler
        add(startButton);
    }

    /**
     * Reads all UI values, validates, updates the SimulationConfig, and notifies listener
     */
    private void sendData()
    {
        int pop = 0;
        try
        {
            pop = Integer.parseInt(populationField.getText().trim());
            if (pop <= 0)
            {
                throw new IllegalArgumentException("Population must be positive.");
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String diseaseName = diseaseNameField.getText().trim();
        if (diseaseName.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Disease name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        diseaseModel.setName(diseaseName);

        float infectRate = 0f;
        try
        {
            infectRate = infectionRateSlider.getValue() / 100f;
            if (infectRate < 0f || infectRate > 1f)
            {
                throw new IllegalArgumentException("Infection rate must be 0..100%.");
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float mortalityRate = 0f;
        try
        {
            mortalityRate = mortalityRateSlider.getValue() / 100f;
            if (mortalityRate < 0f || mortalityRate > 1f)
            {
                throw new IllegalArgumentException("Mortality rate must be 0..100%.");
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int maxDays = 0;
        try
        {
            maxDays = Integer.parseInt(maxDaysField.getText().trim());
            if (maxDays <= 0)
            {
                throw new IllegalArgumentException("Max days must be positive.");
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean vaccEnabled = enableVaccinationCheckbox.isSelected();
        int vaccStart = 0;
        float dailyVaccMin = 0f;
        float dailyVaccMax = 0f;

        if (vaccEnabled)
        {
            try
            {
                vaccStart = Integer.parseInt(vaccinationStartDayField.getText().trim());
                if (vaccStart < 0)
                {
                    throw new IllegalArgumentException("Vaccination start day must be >= 0.");
                }

                dailyVaccMin = Float.parseFloat(dailyVaccMinField.getText().trim());
                dailyVaccMax = Float.parseFloat(dailyVaccMaxField.getText().trim());
                if (dailyVaccMin < 0f || dailyVaccMax < 0f || dailyVaccMin > dailyVaccMax)
                {
                    throw new IllegalArgumentException("Daily vaccination percent invalid.");
                }
            }
            catch (IllegalArgumentException ex)
            {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        int recoveryMin = 0, recoveryMax = 0, contagiousMin = 0, contagiousMax = 0;
        try
        {
            recoveryMin = Integer.parseInt(recoveryMinField.getText().trim());
            recoveryMax = Integer.parseInt(recoveryMaxField.getText().trim());
            if (recoveryMin < 0 || recoveryMax < 0 || recoveryMin > recoveryMax)
            {
                throw new IllegalArgumentException("Recovery range invalid.");
            }

            contagiousMin = Integer.parseInt(contagiousMinField.getText().trim());
            contagiousMax = Integer.parseInt(contagiousMaxField.getText().trim());
            if (contagiousMin < 0 || contagiousMax < 0 || contagiousMin > contagiousMax)
            {
                throw new IllegalArgumentException("Contagious range invalid.");
            }
        }
        catch (IllegalArgumentException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // apply validated values to model objects
        Population popModel = config.getPopulation();
        Disease diseaseModel = config.getDisease();
        popModel.setSize(pop);
        diseaseModel.setInfectionRate(infectRate);
        diseaseModel.setMortalityRate(mortalityRate);
        diseaseModel.setRecoveryDays(recoveryMin, recoveryMax);
        diseaseModel.setContagiousDays(contagiousMin, contagiousMax);
        config.maxDays = maxDays;

        if (vaccEnabled)
        {
            config.getVaccination().setDailyMin(dailyVaccMin / 100f);
            config.getVaccination().setDailyMax(dailyVaccMax / 100f);
            config.getVaccination().setStartDay(vaccStart);
        }

        listener.onSetupComplete(config);
    }
}
