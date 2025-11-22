import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    public SimulationPanel() {
        setBackground(Color.BLACK); // looks nice for simulation visuals
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // TEMP: draw a test circle
        g.setColor(Color.GREEN);
        g.fillOval(100, 100, 20, 20);

        // Later, you'll loop over Population.getPeople()
        // and draw each person here.
    }
}
