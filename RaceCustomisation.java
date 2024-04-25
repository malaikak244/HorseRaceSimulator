/**
 * The RaceCustomisation class includes all the code which builds the GUI and provides customisation options
 *
 * @Malaika Khan
 * @25/04/2024
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class RaceCustomisation {
    // Fields
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
    private static boolean raceInProgress = false;
    private static int horseIndex;
    private static boolean[] betPlaced;
    private static Betting bettingSystem;
    private static List<Race> races = new ArrayList<>();


    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                initializeGUI();
            }
        });
    }

    // Disables the fields for entering the names of the horse once the start race button is pressed (for consistency)
    private static void disableHorseNameFields() {
        Component[] components = horseDetailsPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel horsePanel = (JPanel) component;
                Component[] innerComponents = horsePanel.getComponents();
                for (Component innerComponent : innerComponents) {
                    if (innerComponent instanceof JTextField) {
                        JTextField nameField = (JTextField) innerComponent;
                        nameField.setEnabled(false);
                    }
                }
            }
        }
    }

    // Contains the code which builds the GUI
    private static void initializeGUI() {
        
        JFrame frame = new JFrame("Race Customisation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

       
        coinsLabel = new JLabel("Coins: " + coins);
        frame.add(coinsLabel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);


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



        JPanel trackLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackLengthLabel = new JLabel("Track Length:");
        JSlider trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 10, 100, 50);
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
                disableHorseNameFields();
                boolean allSymbolsChosen = true;
                Race race = new Race(trackLengthSlider.getValue());

                for (int i = 1; i <= numTracks; i++) {
                    JPanel horsePanel = (JPanel) horseDetailsPanel.getComponent(i - 1);
                    JRadioButton selectedButton = null;
            
                    for (Component component : horsePanel.getComponents()) {
                        if (component instanceof JRadioButton) {
                            JRadioButton radioButton = (JRadioButton) component;
                            if (radioButton.isSelected()) {
                                selectedButton = radioButton;
                                break;
                            }
                        }
                    }
            
                    if (selectedButton == null) {
                        allSymbolsChosen = false;
                        break;
                    }
                }
            
                if (!allSymbolsChosen) {
                    JOptionPane.showMessageDialog(null, "Please choose symbols for all horses before starting the race.");
                } else if (raceInProgress) {
                    JOptionPane.showMessageDialog(null, "A race is already in progress. Please wait for it to finish.");
                } else {
                    totalRaces++;
                    totalRacesLabel.setText("Total races: " + totalRaces);
                    numOfTracksSlider.setEnabled(false);
            
                    for (int i = 1; i <= numTracks; i++) {
                        JPanel horsePanel = (JPanel) horseDetailsPanel.getComponent(i - 1);
                        JTextField nameField = (JTextField) horsePanel.getComponent(1);
                        String horseName = nameField.getText().trim();
                        if (horseName.isEmpty()) {
                            horseName = "Horse " + i;
                        }
                        char horseCharacter = ' ';
                        JRadioButton selectedButton = null;
            
                        for (Component component : horsePanel.getComponents()) {
                            if (component instanceof JRadioButton) {
                                JRadioButton radioButton = (JRadioButton) component;
                                if (radioButton.isSelected()) {
                                    selectedButton = radioButton;
                                    break;
                                }
                            }
                        }
            
                        if (selectedButton != null) {
                            horseCharacter = selectedButton.getText().charAt(0);
                        }
            
                        race.addHorse(new Horse(horseCharacter, horseName, 0.4));
                    }
                    races.add(race);
            
                    String raceProgress = race.generateRaceProgress();
            
                    raceProgressTextArea.setText(raceProgress);
            
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            raceInProgress = true;
                            race.startRace(raceProgressTextArea);
                            int winnerIndex = race.getWinnerIndex();
                            if (winnerIndex != -1) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateHorseWins(winnerIndex);
                                        raceInProgress = false;
                                        updateBetButtons(numTracks, coinsLabel, frame);
            
                                        if (winnerIndex + 1 == horseIndex) {
                                            coins += 20;
                                            coinsLabel.setText("Coins: " + coins);
                                            JOptionPane.showMessageDialog(null, "Congratulations! You won 20 coins");
                                        }
                                        for (int i = 0; i < numTracks; i++) {
                                            betPlaced[i] = false;
                                        }
                                    }
                                });
                            
                            } else {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        raceInProgress = false;
                                        JOptionPane.showMessageDialog(null, "Race ended prematurely. Please start a new race.");
                                    }
                                });
                            }
            
                        }
                    }).start();
                }
            }
            
            
            
            
        });

        bettingSystem = new Betting(coins, coinsLabel, frame);

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
        viewOddsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!races.isEmpty()) {
                    bettingSystem.viewOdds(races);
                } else {
                    JOptionPane.showMessageDialog(frame, "There are no races to view odds for.", "No Races", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        bottomPanel.add(viewOddsButton);

        bottomPanel.add(coinsLabel);

        raceProgressTextArea = new JTextArea();
        raceProgressTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(raceProgressTextArea);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        bottomPanel.add(scrollPane);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Method which makes the panels for each horse
    private static JPanel createHorsePanel(String horseName) {
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.setBorder(BorderFactory.createTitledBorder(horseName));
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

       

        JLabel characterLabel = new JLabel("Character:");
        ButtonGroup characterButtonGroup = new ButtonGroup();
        JRadioButton charButton1 = new JRadioButton("£");
        JRadioButton charButton2 = new JRadioButton("$");
        JRadioButton charButton3 = new JRadioButton("%");
        JRadioButton charButton4 = new JRadioButton("&");
        JRadioButton charButton5 = new JRadioButton("@");

        characterButtonGroup.add(charButton1);
        characterButtonGroup.add(charButton2);
        characterButtonGroup.add(charButton3);
        characterButtonGroup.add(charButton4);
        characterButtonGroup.add(charButton5);

        panel.add(characterLabel);
        panel.add(charButton1);
        panel.add(new JLabel(""));
        panel.add(charButton2);
        panel.add(new JLabel(""));
        panel.add(charButton3);
        panel.add(new JLabel(""));
        panel.add(charButton4);
        panel.add(new JLabel(""));
        panel.add(charButton5);

        return panel;

    }

    // Updates the centre panel of the GUI when the customising options are tampered with
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

    // Updates the right panel of the GUI when the customising options are tampered with
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

    // Updates the wins recorded in the right panel
    private static void updateHorseWins(int horseIndex) {
        if (horseWins == null || horseIndex >= horseWins.length) {
            return;
        }
        horseWins[horseIndex]++;
        if (horseIndex < horseWinsLabels.length) {
            horseWinsLabels[horseIndex].setText("Horse " + (horseIndex + 1) + " Wins: " + horseWins[horseIndex]);
        }
    }

    // Updates the bet buttons depending on how many horses there are
    private static void updateBetButtons(int numTracks, JLabel coinsLabel, JFrame frame) {
        if (horseWins == null || horseWins.length != numTracks) {
            horseWins = new int[numTracks];
            betPlaced = new boolean[numTracks];
            for (int i = 0; i < numTracks; i++) {
                horseWins[i] = 0;
                betPlaced[i] = false;
            }
        }
    
        for (int i = 0; i < betButtons.length; i++) {
            if (betButtons[i] != null) {
                betButtons[i].setVisible(false);
            }
        }
    
        betButtons = new JButton[numTracks];
        for (int i = 0; i < numTracks; i++) {
            final int currentHorseIndex = i + 1;

            betButtons[i] = new JButton("Bet on Horse " + (i + 1));
    
            if (!raceInProgress) {
                betButtons[i].setEnabled(true);
            } else {
                betButtons[i].setEnabled(false);
            }
    
            betButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!betPlaced[currentHorseIndex - 1]) {
                        horseIndex = currentHorseIndex;
                        placeBet(currentHorseIndex, coinsLabel, frame);
                        betPlaced[currentHorseIndex - 1] = true; 
                    }
                }
            });
            rightPanel.add(betButtons[i]);
        }
        rightPanel.revalidate();
        rightPanel.repaint();
    }
    

    // Enables the user to place a bet and for 10 coins to be deducted from their balance (if they have enough)
    private static void placeBet(int horseIndex, JLabel coinsLabel, JFrame frame) {
        if (coins >= 10) {
            coins -= 10;
            coinsLabel.setText("Coins: " + coins);
            System.out.println("Bet on Horse " + horseIndex + " placed");
            disableBetButtons();
        } else if (coins < 10 && coins >= 0){
            JOptionPane.showMessageDialog(frame, "Not enough coins to place a bet.", "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    // Disables the bet buttons so that only one bet can be made per race
    private static void disableBetButtons() {
        for (JButton button : betButtons) {
            button.setEnabled(false);
        }
    }
}
