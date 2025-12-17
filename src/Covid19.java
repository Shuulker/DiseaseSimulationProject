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
 * Represents the COVID-19 disease preset with predefined infection, mortality, recovery, and contagious rates.
 */
public class Covid19 extends Disease
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the COVID-19 disease preset with preset values.
     * Infection rate: 35%
     * Mortality rate: 1%
     * Recovery days: 7–14
     * Contagious days: 3–10
     */
    public Covid19()
    {
        super(
            "COVID-19",
            0.35,   // infection rate
            0.01,   // mortality rate
            7, 14,  // recovery days (min, max)
            3, 10   // contagious days (min, max)
        );
    }
}
