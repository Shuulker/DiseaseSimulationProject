import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private SimulationPanel panel;

    public MainWindow() {
        setTitle("Disease Spread Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new SimulationPanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
