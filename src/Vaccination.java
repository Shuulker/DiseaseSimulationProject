/**
 * Lead Author(s):
 * @author Joseph Roberts
 *
 * Version/date: 12/11/2025
 *
 * Responsibilities of class:
 * Represents a vaccination campaign for the population.
 * Tracks daily vaccination percentages, start day, and can apply vaccination to individuals.
 */

// Vaccination IS-A plain Java object
// Vaccination HAS-A daily min/max fraction and start day
public class Vaccination
{
    private float dailyMin;    // 0..1 fraction of population per day
    private float dailyMax;    // 0..1 fraction
    private int startDay;      // day vaccination begins

    /**
     * Default constructor.
     * Initializes dailyMin=0.01, dailyMax=0.03, startDay=25
     */
    public Vaccination()
    {
        this(0.01f, 0.03f, 25); // default values set here
    }

    /**
     * Constructor with specified values.
     *
     * @param dailyMin minimum daily fraction of population to vaccinate (0..1)
     * @param dailyMax maximum daily fraction of population to vaccinate (0..1)
     * @param startDay day to start vaccination campaign
     */
    public Vaccination(float dailyMin, float dailyMax, int startDay)
    {
        this.dailyMin = dailyMin;
        this.dailyMax = dailyMax;
        this.startDay = startDay;
    }

    /**
     * Apply vaccination to a person.
     * Vaccinates any non-DEAD, non-vaccinated person.
     *
     * @param p Person to vaccinate
     */
    public void applyTo(Person p)
    {
        if (p == null || p.getHealthStatus() == HealthStatus.DEAD || p.isVaccinated())
        {
            return;
        }

        p.vaccinate();
    }

    // -------------------------
    // Getters and setters
    // -------------------------

    /**
     * Gets the minimum daily vaccination fraction.
     *
     * @return dailyMin fraction (0..1)
     */
    public float getDailyMin()
    {
        return dailyMin;
    }

    /**
     * Sets the minimum daily vaccination fraction.
     *
     * @param dailyMin fraction (0..1)
     */
    public void setDailyMin(float dailyMin)
    {
        this.dailyMin = dailyMin;
    }

    /**
     * Gets the maximum daily vaccination fraction.
     *
     * @return dailyMax fraction (0..1)
     */
    public float getDailyMax()
    {
        return dailyMax;
    }

    /**
     * Sets the maximum daily vaccination fraction.
     *
     * @param dailyMax fraction (0..1)
     */
    public void setDailyMax(float dailyMax)
    {
        this.dailyMax = dailyMax;
    }

    /**
     * Gets the day vaccination starts.
     *
     * @return startDay
     */
    public int getStartDay()
    {
        return startDay;
    }

    /**
     * Sets the day vaccination starts.
     *
     * @param startDay day to begin vaccination
     */
    public void setStartDay(int startDay)
    {
        this.startDay = startDay;
    }
}
