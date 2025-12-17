import java.io.*;
import java.util.*;

/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * References:
 * Morelli, R., & Walde, R. (2016). Java, Java, Java: Object-Oriented Problem Solving.
 * Retrieved from https://open.umn.edu/opentextbooks/textbooks/java-java-java-object-oriented-problem-solving
 *
 * Version/date: 12/17/2025
 *
 * Responsibilities of class:
 * Manages disease presets including saving, loading, and maintaining default presets.
 * Provides methods to add, remove, and retrieve presets while persisting them to disk.
 */
public class PresetManager
{
    private static final String FILE_NAME = "presets.dat";
    private final List<Disease> presets = new ArrayList<>();

    /**
     * Constructs a PresetManager and loads existing presets from disk.
     * Ensures that default presets exist if none are loaded.
     */
    public PresetManager()
    {
        loadPresets();
        ensureDefaultPresets();
    }

    /**
     * Returns an unmodifiable list of all disease presets.
     *
     * @return unmodifiable List of Disease presets
     */
    public List<Disease> getPresets()
    {
        return Collections.unmodifiableList(presets);
    }

    /**
     * Adds a new disease preset and saves it to disk.
     *
     * @param disease the Disease object to add
     */
    public void addPreset(Disease disease)
    {
        presets.add(disease);
        savePresets();
    }

    /**
     * Removes a disease preset and saves the updated list to disk.
     *
     * @param preset the Disease object to remove
     */
    public void removePreset(Disease preset)
    {
        presets.remove(preset);
        savePresets();
    }

    // ---------------- Persistence ----------------

    /**
     * Saves all presets to a file using object serialization.
     */
    private void savePresets()
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME)))
        {
            out.writeObject(presets);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Loads presets from disk, replacing the current list.
     * If loading fails, presets are cleared and the file is deleted.
     */
    @SuppressWarnings("unchecked")
    private void loadPresets()
    {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            presets.clear();
            presets.addAll((List<Disease>) in.readObject());
        }
        catch (Exception e)
        {
            System.err.println("Failed to load presets, resetting defaults.");
            presets.clear();
            file.delete();
        }
    }

    // ---------------- Defaults ----------------

    /**
     * Ensures that default presets exist in the list.
     * Adds generic Disease, Covid19, and BlackPlague if the list is empty.
     */
    private void ensureDefaultPresets()
    {
        if (presets.isEmpty())
        {
            presets.add(new Disease());
            presets.add(new Covid19());
            presets.add(new BlackPlague());
            savePresets();
        }
    }
}
