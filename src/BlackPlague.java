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
 * Represents the Black Plague disease preset with predefined infection, mortality, recovery, and contagious rates.
 */
public class BlackPlague extends Disease
{
    private static final long serialVersionUID = 1L;

    /**
     * Constructs the Black Plague disease preset with preset values.
     * Infection rate: 60%
     * Mortality rate: 30%
     * Recovery days: 5–10
     * Contagious days: 2–5
     */
    public BlackPlague()
    {
        super(
            "Black Plague",
            0.6,    // infection rate
            0.3,    // mortality rate
            5, 10,  // recovery days (min, max)
            2, 5    // contagious days (min, max)
        );
    }
}
