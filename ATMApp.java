import ui.ATMInterface;

public class ATMApp {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new ATMInterface().setVisible(true));
    }
}