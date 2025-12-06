/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Responsibilities of class:
 * Represents a vaccination. Can vaccinate individuals (coverage) and
 * compute adjusted infection chance per person.
 */

// Vaccination has-a name
// Vaccination has-a efficacy (reduces infection probability)
// Vaccination has-a coverageRate (percent of population vaccinated)
public class Vaccination
{
    private final String name;
    private final float efficacy;      // 0..1
    private final float coverageRate;  // 0..1

    /**
     * Constructor ensures efficacy and coverageRate are clamped to [0..1]
     */
    public Vaccination(String name, float efficacy, float coverageRate)
    {
        this.name = name;
        this.efficacy = Math.max(0f, Math.min(1f, efficacy));
        this.coverageRate = Math.max(0f, Math.min(1f, coverageRate));
    }

    /**
     * Apply vaccination to a person based on coverage probability.
     */
    public void applyTo(Person p)
    {
        if (!p.isVaccinated() && Math.random() < coverageRate)
        {
            p.vaccinate(efficacy);
        }
    }

    /**
     * Returns the adjusted infection probability for a person given a base rate.
     * Vaccinated people have their probability reduced by efficacy.
     */
    public double adjustInfectionChance(Person p, double baseRate)
    {
        if (p.isVaccinated())
        {
            return baseRate * (1.0 - p.getVaccineEfficacy());
        }
        return baseRate;
    }

    // -------------------------------
    // Getters
    // -------------------------------
    public String getName()
    {
        return name;
    }

    public float getEfficacy()
    {
        return efficacy;
    }

    public float getCoverageRate()
    {
        return coverageRate;
    }
}
