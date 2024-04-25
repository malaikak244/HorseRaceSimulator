import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.HashMap;
import java.util.Map;

public class RaceCustomisation {
    private static int totalRaces = 0;
    private static int coins = 100;
    private static JTextArea raceProgressTextArea;
    private static JPanel horseDetailsPanel;
    private static JPanel rightPanel;
    private static JLabel[] horseWinsLabels;
    private static JLabel totalRacesLabel;
    private static JButton[] betButtons;
    private static int[] horseWins;
    private static int numTracks;
    private static JLabel coinsLabel;
    private static Betting bettingSystem;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Race Customisation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Initialize coinsLabel
        coinsLabel = new JLabel("Coins: " + coins);
        frame.add(coinsLabel, BorderLayout.SOUTH);

        Color leftRightColor = Color.lightGray;
        Color centerColor = Color.gray;
        Color bottomColor = Color.cyan;

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(leftRightColor);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel topLeftLabel = new JLabel("Track Customisation", SwingConstants.CENTER);
        leftPanel.add(topLeftLabel);

        JPanel numOfTracksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel numOfTracksLabel = new JLabel("Number of tracks");
        JSlider numOfTracksSlider = new JSlider(JSlider.HORIZONTAL, 2, 5, 3);
        numOfTracksSlider.setMajorTickSpacing(1);
        numOfTracksSlider.setMinorTickSpacing(0);
        numOfTracksSlider.setPaintTicks(true);
        numOfTracksSlider.setPaintLabels(true);
        numOfTracksPanel.add(numOfTracksLabel);
        numOfTracksPanel.add(numOfTracksSlider);
        numOfTracksPanel.setToolTipText("Unable to change number of tracks after the first race");
        leftPanel.add(numOfTracksPanel);

        JPanel trackCustomizationPanel = new JPanel();
        trackCustomizationPanel.setLayout(new BoxLayout(trackCustomizationPanel, BoxLayout.Y_AXIS));
        leftPanel.add(trackCustomizationPanel);

        bettingSystem = new Betting(coins, coinsLabel, frame);

        numOfTracksSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                numTracks = numOfTracksSlider.getValue();
                updateHorseCustomization(numTracks);
                updateBetButtons(numTracks, coinsLabel, frame);
            }
        });

        JPanel trackColourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackColourLabel = new JLabel("Track Colour:");
        JTextField trackColourField = new JTextField(10);
        trackColourPanel.add(trackColourLabel);
        trackColourPanel.add(trackColourField);
        leftPanel.add(trackColourPanel);

        JPanel trackLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackLengthLabel = new JLabel("Track Length:");
        JSlider trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        trackLengthSlider.setMajorTickSpacing(10);
        trackLengthSlider.setMinorTickSpacing(5);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        trackLengthPanel.add(trackLengthLabel);
        trackLengthPanel.add(trackLengthSlider);
        leftPanel.add(trackLengthPanel);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1));
        centerPanel.setBackground(centerColor);

        JButton startRaceButton = new JButton("Start Race");
        startRaceButton.setPreferredSize(new Dimension(100, 30));
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalRaces++;
                totalRacesLabel.setText("Total races: " + totalRaces);
                numOfTracksSlider.setEnabled(false);
                Race race = new Race(trackLengthSlider.getValue());

                for (int i = 1; i <= numTracks; i++) {
                    JPanel horsePanel = (JPanel) horseDetailsPanel.getComponent(i - 1);
                    JTextField nameField = (JTextField) horsePanel.getComponent(1);
                    String horseName = nameField.getText();
                    race.addHorse(new Horse('H', horseName, 0.4));
                }

                String raceProgress = race.generateRaceProgress();

                raceProgressTextArea.setText(raceProgress);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        race.startRace(raceProgressTextArea);
                        int winnerIndex = race.getWinnerIndex();
                        if (winnerIndex != -1) {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    updateHorseWins(winnerIndex);
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        JPanel startRacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startRacePanel.add(startRaceButton);
        centerPanel.add(startRacePanel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel horseCustomisationLabel = new JLabel("Horse Customisation", SwingConstants.CENTER);
        topPanel.add(horseCustomisationLabel);
        centerPanel.add(topPanel);

        horseDetailsPanel = new JPanel(new GridLayout(1, 3));
        centerPanel.add(horseDetailsPanel);

        rightPanel = new JPanel();
        rightPanel.setBackground(leftRightColor);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel topRightLabel = new JLabel("Statistics", SwingConstants.CENTER);
        rightPanel.add(topRightLabel);
        System.out.println(numTracks);

        horseWinsLabels = new JLabel[numTracks];
        for (int i = 0; i < numTracks; i++) {
            horseWinsLabels[i] = new JLabel("Horse " + (i + 1) + " Wins: 0");
            rightPanel.add(horseWinsLabels[i]);
        }
        totalRacesLabel = new JLabel("Total Races: 0");
        rightPanel.add(totalRacesLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bottomColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel bettingLabel = new JLabel("Betting System", SwingConstants.CENTER);
        bottomPanel.add(bettingLabel);
        betButtons = new JButton[0];

        JButton viewOddsButton = new JButton("View Odds");
        bottomPanel.add(viewOddsButton);

        bottomPanel.add(coinsLabel);

        raceProgressTextArea = new JTextArea();
        raceProgressTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceProgressTextArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        bottomPanel.add(scrollPane);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createHorsePanel(String horseName) {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createTitledBorder(horseName));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);
        JLabel breedLabel = new JLabel("Breed:");
        JTextField breedField = new JTextField();
        panel.add(breedLabel);
        panel.add(breedField);
        JLabel coatColorLabel = new JLabel("Coat Color:");
        JPanel coatColorButtonsPanel = new JPanel();
        JButton brownButton = new JButton("Brown");
        JButton blackButton = new JButton("Black");
        JButton whiteButton = new JButton("White");
        coatColorButtonsPanel.add(brownButton);
        coatColorButtonsPanel.add(blackButton);
        coatColorButtonsPanel.add(whiteButton);
        panel.add(coatColorLabel);
        panel.add(coatColorButtonsPanel);
        return panel;
    }

    private static void updateHorseCustomization(int numTracks) {
        horseDetailsPanel.removeAll();
        horseWinsLabels = new JLabel[numTracks];
        for (int i = 0; i < numTracks; i++) {
            JPanel horsePanel = createHorsePanel("Horse " + (i + 1));
            JTextField nameField = (JTextField) horsePanel.getComponent(1);
            String horseName = nameField.getText().trim();
            if (!horseName.isEmpty()) {
                horseWinsLabels[i] = new JLabel(horseName + " Wins: 0");
            } else {
                horseWinsLabels[i] = new JLabel("Horse " + (i + 1) + " Wins: 0");
            }
            horseDetailsPanel.add(horsePanel);
        }
        updateRightPanel();
        horseDetailsPanel.revalidate();
        horseDetailsPanel.repaint();
    }

    private static void updateRightPanel() {
        rightPanel.removeAll();
        rightPanel.add(new JLabel("Statistics", SwingConstants.CENTER));
        for (JLabel label : horseWinsLabels) {
            rightPanel.add(label); 
        }
        rightPanel.add(totalRacesLabel);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private static void updateHorseWins(int horseIndex) {
        if (horseWins == null || horseIndex >= horseWins.length) {
            return;
        }
        horseWins[horseIndex]++;
        if (horseIndex < horseWinsLabels.length) {
            horseWinsLabels[horseIndex].setText("Horse " + (horseIndex + 1) + " Wins: " + horseWins[horseIndex]);
        }
    }

    private static void updateBetButtons(int numTracks, JLabel coinsLabel, JFrame frame) {
        if (horseWins == null || horseWins.length != numTracks) {
            horseWins = new int[numTracks];
            for (int i = 0; i < numTracks; i++) {
                horseWins[i] = 0;
            }
        }
    
        for (int i = 0; i < betButtons.length; i++) {
            if (betButtons[i] != null) {
                betButtons[i].setVisible(false);
            }
        }
    
        betButtons = new JButton[numTracks];
        for (int i = 0; i < numTracks; i++) {
            final int horseIndex = i+1;
            betButtons[i] = new JButton("Bet on Horse " + (i + 1));
            betButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    placeBet(horseIndex, coinsLabel);
                }
            });
            rightPanel.add(betButtons[i]);
        }
        rightPanel.revalidate();
        rightPanel.repaint();
    }

    private static void placeBet(int numTracks, JLabel coinsLabel) {
        bettingSystem.placeBet(numTracks);
    }
}
