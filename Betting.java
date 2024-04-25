/**
 * The Betting class provides a window for users to see the racing history when clicking on the "view odds" button
 *
 * @Malaika Khan
 * @25/04/2024
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Betting {
    // Fields
    private int coins;
    private JLabel coinsLabel;
    private JFrame frame;
    private List<Race> raceList;

    // Constructor
    public Betting(int initialCoins, JLabel coinsLabel, JFrame frame) {
        this.coins = initialCoins;
        this.coinsLabel = coinsLabel;
        this.frame = frame;
        this.raceList = new ArrayList<>();
    }


    public void viewOdds() {
        JOptionPane.showMessageDialog(frame, "Displaying Odds", "View Odds", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method to add a race to the list
    public void addRace(Race race) {
        raceList.add(race);
    }

    // Method to view odds using the list of races
    public void viewOdds(List<Race> races) {
        JFrame oddsFrame = new JFrame("Race Odds");
        oddsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        oddsFrame.setSize(400, 300);
        oddsFrame.setLocationRelativeTo(null);

        JTextArea oddsTextArea = new JTextArea();
        oddsTextArea.setEditable(false);

        for (Race race : races) {
            oddsTextArea.append("Race Length: " + race.getLength() + "\n");
            oddsTextArea.append("Winner: " + race.getWinner() + "\n\n");
        }

        JScrollPane scrollPane = new JScrollPane(oddsTextArea);
        oddsFrame.add(scrollPane);

        oddsFrame.setVisible(true);
    }

    // Getter method to access the list of races
    public List<Race> getRaceList() {
        return raceList;
    }
}
