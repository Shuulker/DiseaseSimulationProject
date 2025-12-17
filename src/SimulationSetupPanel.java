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
    private final PresetManager presetManager;
    private JButton savePresetButton;
    private JButton deletePresetButton;
    private JComboBox<Disease> diseasePresetDropdown;


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
    public SimulationSetupPanel(SetupListener listener, SimulationConfig existingConfig, PresetManager presetManager)
    {
        this.listener = listener;
        this.config = existingConfig;
        this.diseaseModel = config.getDisease();
        this.presetManager = presetManager;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        int row = 0;

        // Population
        populationField = new JTextField(String.valueOf(config.getPopulation().size()));
        addLabelAndComponent("Population Size:", populationField, gbc, row++);

        // Disease name
        diseaseNameField = new JTextField(diseaseModel.getName());
        addLabelAndComponent("Disease Name:", diseaseNameField, gbc, row++);

        // Infection rate
        infectionRateSlider = new JSlider(0, 100, (int)(diseaseModel.getInfectionRate()*100));
        infectionRateSlider.setMajorTickSpacing(20);
        infectionRateSlider.setMinorTickSpacing(5);
        infectionRateSlider.setPaintTicks(true);
        infectionRateSlider.setPaintLabels(true);
        addLabelAndComponent("Infection Rate (0%-100%):", infectionRateSlider, gbc, row++);

        // Mortality rate
        mortalityRateSlider = new JSlider(0,100,(int)(diseaseModel.getMortalityRate()*100));
        mortalityRateSlider.setMajorTickSpacing(20);
        mortalityRateSlider.setMinorTickSpacing(5);
        mortalityRateSlider.setPaintTicks(true);
        mortalityRateSlider.setPaintLabels(true);
        addLabelAndComponent("Mortality Rate (0%-100%):", mortalityRateSlider, gbc, row++);

        // Recovery range
        int[] recoveryRange = diseaseModel.getRecoveryDaysRange();
        recoveryMinField = new JTextField(String.valueOf(recoveryRange[0]));
        recoveryMaxField = new JTextField(String.valueOf(recoveryRange[1]));
        JPanel recoveryPanel = new JPanel(new GridLayout(1,4,5,0));
        recoveryPanel.add(new JLabel("Min:"));
        recoveryPanel.add(recoveryMinField);
        recoveryPanel.add(new JLabel("Max:"));
        recoveryPanel.add(recoveryMaxField);
        addLabelAndComponent("Recovery Time (days):", recoveryPanel, gbc, row++);

        // Contagious range
        int[] contagiousRange = diseaseModel.getContagiousDaysRange();
        contagiousMinField = new JTextField(String.valueOf(contagiousRange[0]));
        contagiousMaxField = new JTextField(String.valueOf(contagiousRange[1]));
        JPanel contagiousPanel = new JPanel(new GridLayout(1,4,5,0));
        contagiousPanel.add(new JLabel("Min:"));
        contagiousPanel.add(contagiousMinField);
        contagiousPanel.add(new JLabel("Max:"));
        contagiousPanel.add(contagiousMaxField);
        addLabelAndComponent("Contagious Days:", contagiousPanel, gbc, row++);

        // Enable vaccination
        enableVaccinationCheckbox = new JCheckBox("Enable Vaccination?", config.vaccinationEnabled);
        gbc.gridx = 0; 
        gbc.gridy = row++; 
        gbc.gridwidth = 2; 
        gbc.weighty = 0.0;
        add(enableVaccinationCheckbox, gbc);
        gbc.gridwidth = 1;

        // Vaccination fields
        vaccinationStartDayField = new JTextField(String.valueOf(config.getVaccination().getStartDay()));
        dailyVaccMinField = new JTextField(String.valueOf((int)(config.getVaccination().getDailyMin()*100)));
        dailyVaccMaxField = new JTextField(String.valueOf((int)(config.getVaccination().getDailyMax()*100)));
        JPanel vaccPanel = new JPanel(new GridLayout(1,4,5,0));
        vaccPanel.add(new JLabel("Min:"));
        vaccPanel.add(dailyVaccMinField);
        vaccPanel.add(new JLabel("Max:"));
        vaccPanel.add(dailyVaccMaxField);
        addLabelAndComponent("Daily Vaccination %:", vaccPanel, gbc, row++);
        addLabelAndComponent("Vaccination Start Day:", vaccinationStartDayField, gbc, row++);

        boolean vaccEnabled = enableVaccinationCheckbox.isSelected();
        vaccinationStartDayField.setEnabled(vaccEnabled);
        dailyVaccMinField.setEnabled(vaccEnabled);
        dailyVaccMaxField.setEnabled(vaccEnabled);

        // Max simulation days
        maxDaysField = new JTextField(String.valueOf(config.maxDays));
        addLabelAndComponent("Max Simulation Days:", maxDaysField, gbc, row++);

        // Preset dropdown
        diseasePresetDropdown = new JComboBox<>();
        refreshPresetDropdown();
        gbc.gridx = 0; 
        gbc.gridy = row++; 
        gbc.gridwidth = 2;
        add(diseasePresetDropdown, gbc);
        gbc.gridwidth = 1;

        // Save Preset button
        savePresetButton = new JButton("Save Preset");
        gbc.gridx = 0; 
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        add(savePresetButton, gbc);
        gbc.gridwidth = 1;

        // Delete Preset button
        deletePresetButton = new JButton("Delete Preset");
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        add(deletePresetButton, gbc);
        gbc.gridwidth = 1;

        // Spacer
        gbc.weighty = 1.0;
        gbc.gridx = 0; 
        gbc.gridy = row++; 
        gbc.gridwidth = 2;
        add(Box.createVerticalGlue(), gbc);
        gbc.weighty = 0; 
        gbc.gridwidth = 1;

        // Start simulation button
        startButton = new JButton("Start Simulation");
        gbc.gridx = 0; 
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        add(startButton, gbc);

        // --- Listeners ---
        enableVaccinationCheckbox.addActionListener(e -> {
            boolean enabled = enableVaccinationCheckbox.isSelected();
            vaccinationStartDayField.setEnabled(enabled);
            dailyVaccMinField.setEnabled(enabled);
            dailyVaccMaxField.setEnabled(enabled);
        });

        diseasePresetDropdown.addActionListener(e -> loadSelectedPreset());

        savePresetButton.addActionListener(e -> savePreset());
        deletePresetButton.addActionListener(e -> deletePreset());
        startButton.addActionListener(e -> sendData());
    }

    /**
     * Adds a label and component to the panel in two columns.
     * 
     * @param labelText text for the label
     * @param comp component to add
     * @param gbc GridBagConstraints to use
     * @param row row index in layout
     */
    private void addLabelAndComponent(String labelText, Component comp, GridBagConstraints gbc, int row)
    {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weighty = 0.1;
        add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        add(comp, gbc);
    }

    /**
     * Refreshes the disease preset dropdown list.
     * Sets the current disease as the selected item.
     */
    private void refreshPresetDropdown()
    {
        diseasePresetDropdown.removeAllItems();

        Disease currentDisease = config.getDisease();

        for (Disease d : presetManager.getPresets()) 
        {
            diseasePresetDropdown.addItem(d);

            if (currentDisease != null && d.getName().equals(currentDisease.getName())) 
            {
                diseasePresetDropdown.setSelectedItem(d);
            }
        }

        diseasePresetDropdown.setRenderer((list, value, index, isSelected, cellHasFocus) -> 
        {
            JLabel label = new JLabel(value == null ? "" : value.getName());
            if (isSelected) 
            {
                label.setOpaque(true);
                label.setBackground(list.getSelectionBackground());
            }
            return label;
        });
    }

    /**
     * Loads the selected preset into the input fields.
     */
    private void loadSelectedPreset()
    {
        Disease selected = (Disease) diseasePresetDropdown.getSelectedItem();
        if (selected != null) 
        {
            diseaseNameField.setText(selected.getName());
            infectionRateSlider.setValue((int)(selected.getInfectionRate()*100));
            mortalityRateSlider.setValue((int)(selected.getMortalityRate()*100));
            int[] r = selected.getRecoveryDaysRange();
            recoveryMinField.setText(String.valueOf(r[0]));
            recoveryMaxField.setText(String.valueOf(r[1]));
            int[] c = selected.getContagiousDaysRange();
            contagiousMinField.setText(String.valueOf(c[0]));
            contagiousMaxField.setText(String.valueOf(c[1]));
        }
    }

    /**
     * Saves the current disease configuration as a new preset.
     * Validates input fields and prevents duplicate preset names.
     * Shows error messages if input is invalid or duplicate.
     */
    private void savePreset()
    {
        String name = diseaseNameField.getText().trim();

        if (name.isEmpty())
        {
            JOptionPane.showMessageDialog(
                this,
                "Disease name cannot be empty.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Prevent duplicate preset names (case-insensitive)
        for (Disease d : presetManager.getPresets())
        {
            if (d.getName().equalsIgnoreCase(name))
            {
                JOptionPane.showMessageDialog(
                    this,
                    "A preset with that name already exists.",
                    "Duplicate Name",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        }

        try
        {
            float infectRate = infectionRateSlider.getValue() / 100f;
            float mortality = mortalityRateSlider.getValue() / 100f;
            int recoveryMin = Integer.parseInt(recoveryMinField.getText().trim());
            int recoveryMax = Integer.parseInt(recoveryMaxField.getText().trim());
            int contagiousMin = Integer.parseInt(contagiousMinField.getText().trim());
            int contagiousMax = Integer.parseInt(contagiousMaxField.getText().trim());

            Disease newPreset = new Disease(name, infectRate, mortality, recoveryMin, recoveryMax, contagiousMin, contagiousMax);
            presetManager.addPreset(newPreset);
            refreshPresetDropdown();

            JOptionPane.showMessageDialog(
                this,
                "Preset saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(
                this,
                "Invalid numeric input.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Deletes the currently selected disease preset.
     * Protects default presets ("Default", "Covid-19", "Black Plague") from deletion.
     * Confirms with the user before removing a preset.
     */
    private void deletePreset()
    {
        Disease selected = (Disease) diseasePresetDropdown.getSelectedItem();

        if (selected == null)
        {
            JOptionPane.showMessageDialog(
                this,
                "No preset selected.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String name = selected.getName();

        // Protect critical presets from deletion
        if ("Default".equalsIgnoreCase(name) ||
            "Covid-19".equalsIgnoreCase(name) ||
            "Black Plague".equalsIgnoreCase(name))
        {
            JOptionPane.showMessageDialog(
                this,
                "Cannot delete the \"" + name + "\" preset.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete preset \"" + name + "\"?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION)
        {
            presetManager.removePreset(selected);
            refreshPresetDropdown();

            // Optional: select Default preset after deletion
            for (int i = 0; i < diseasePresetDropdown.getItemCount(); i++)
            {
                Disease d = diseasePresetDropdown.getItemAt(i);
                if ("Default".equalsIgnoreCase(d.getName()))
                {
                    diseasePresetDropdown.setSelectedIndex(i);
                    break;
                }
            }

            JOptionPane.showMessageDialog(
                this,
                "Preset deleted.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
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
        config.vaccinationEnabled = vaccEnabled;
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
        Disease selectedPreset = (Disease) diseasePresetDropdown.getSelectedItem();
        if (selectedPreset != null)
        {
            Disease diseaseCopy = selectedPreset.copy(); // clone
            diseaseCopy.setName(diseaseNameField.getText().trim());
            diseaseCopy.setInfectionRate(infectRate);
            diseaseCopy.setMortalityRate(mortalityRate);
            diseaseCopy.setRecoveryDays(recoveryMin, recoveryMax);
            diseaseCopy.setContagiousDays(contagiousMin, contagiousMax);
            config.setDisease(diseaseCopy);
        }

        popModel.setSize(pop);
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
