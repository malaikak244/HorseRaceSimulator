import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Betting {
    private int coins;
    private JLabel coinsLabel;
    private JFrame frame;

    public Betting(int initialCoins, JLabel coinsLabel, JFrame frame) {
        this.coins = initialCoins;
        this.coinsLabel = coinsLabel;
        this.frame = frame;
    }

    public void viewOdds() {
        JOptionPane.showMessageDialog(frame, "Displaying Odds", "View Odds", JOptionPane.INFORMATION_MESSAGE);
    }

    public void placeBet(int horseNumber) {
        if (coins >= 10) {
            coins -= 10;
            coinsLabel.setText("Coins: " + coins);
            System.out.println("Bet on Horse " + horseNumber + " placed");
        } else {
            JOptionPane.showMessageDialog(frame, "Not enough coins to place a bet.", "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
        }
    }

    public int getNumTracks() {
        return numTracks;
    }
}

 