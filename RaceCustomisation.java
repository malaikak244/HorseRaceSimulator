import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RaceCustomisation {
    private static int totalRaces = 0; // Track total num of races
    private static int coins = 100; // Initial num of coins
    private static JTextArea raceProgressTextArea; // TextArea displays race progress
    private static boolean raceStarted = false;
    private static JPanel horseDetailsPanel;
    private static JPanel rightPanel;
    private static JLabel[] horseWinsLabels;
    private static JLabel totalRacesLabel;
    private static JButton[] betButtons; // Array to store bet buttons

    public static void main(String[] args) {
        JFrame frame = new JFrame("Race Customisation");
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

        numOfTracksSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int numTracks = numOfTracksSlider.getValue();
                updateHorseCustomization(numTracks);
                updateBetButtons(numTracks);
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
                raceStarted = true;
                numOfTracksSlider.setEnabled(false);
                int numTracks = numOfTracksSlider.getValue();
                Race race = new Race(trackLengthSlider.getValue());

                switch (numTracks) {
                    case 2:
                        race.addHorse(new Horse('H', "Horse 1", 0.4));
                        race.addHorse(new Horse('H', "Horse 2", 0.4));
                        break;
                    case 3:
                        race.addHorse(new Horse('H', "Horse 1", 0.4));
                        race.addHorse(new Horse('H', "Horse 2", 0.4));
                        race.addHorse(new Horse('H', "Horse 3", 0.4));
                        break;
                    case 4:
                        race.addHorse(new Horse('H', "Horse 1", 0.4));
                        race.addHorse(new Horse('H', "Horse 2", 0.4));
                        race.addHorse(new Horse('H', "Horse 3", 0.4));
                        race.addHorse(new Horse('H', "Horse 4", 0.4));
                        break;
                    case 5:
                        race.addHorse(new Horse('H', "Horse 1", 0.4));
                        race.addHorse(new Horse('H', "Horse 2", 0.4));
                        race.addHorse(new Horse('H', "Horse 3", 0.4));
                        race.addHorse(new Horse('H', "Horse 4", 0.4));
                        race.addHorse(new Horse('H', "Horse 5", 0.4));
                        break;
                    default:
                        race.addHorse(new Horse('H', "Horse 1", 0.4));
                        race.addHorse(new Horse('H', "Horse 2", 0.4));
                        break;
                }

                String raceProgress = race.generateRaceProgress();

                raceProgressTextArea.setText(raceProgress);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        race.startRace(raceProgressTextArea);
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
        JPanel horse1Panel = createHorsePanel("Horse 1");
        JPanel horse2Panel = createHorsePanel("Horse 2");
        JPanel horse3Panel = createHorsePanel("Horse 3");
        horseDetailsPanel.add(horse1Panel);
        horseDetailsPanel.add(horse2Panel);
        horseDetailsPanel.add(horse3Panel);
        centerPanel.add(horseDetailsPanel);

        rightPanel = new JPanel();
        rightPanel.setBackground(leftRightColor);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        JLabel topRightLabel = new JLabel("Statistics", SwingConstants.CENTER);
        rightPanel.add(topRightLabel);
        horseWinsLabels = new JLabel[3];
        for (int i = 0; i < horseWinsLabels.length; i++) {
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
        betButtons = new JButton[3]; // Initialize bet buttons array with maximum size
        for (int i = 0; i < betButtons.length; i++) {
            betButtons[i] = new JButton("Bet on Horse " + (i + 1));
            int index = i; // Store current index for ActionListener
            betButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (coins >= 10) {
                        coins -= 10;
                        coinsLabel.setText("Coins: " + coins);
                        System.out.println("Bet on Horse " + (index + 1) + " placed");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Not enough coins to place a bet.", "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            bottomPanel.add(betButtons[i]);
        }
        JButton viewOddsButton = new JButton("View Odds");
        bottomPanel.add(viewOddsButton);
        JLabel coinsLabel = new JLabel("Coins: " + coins);
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
        for (int i = 1; i <= numTracks; i++) {
            JPanel horsePanel = createHorsePanel("Horse " + i);
            horseDetailsPanel.add(horsePanel);
        }
        horseDetailsPanel.revalidate();
        horseDetailsPanel.repaint();
        updateBetButtons(numTracks); // Update bet buttons based on the number of tracks
    }

    private static void updateBetButtons(int numTracks) {
        // Remove all existing bet buttons
        for (int i = 0; i < betButtons.length; i++) {
            if (betButtons[i] != null) {
                betButtons[i].setVisible(false);
                rightPanel.remove(betButtons[i]);
            }
        }
        // Create new bet buttons based on the number of tracks
        betButtons = new JButton[numTracks];
        for (int i = 0; i < numTracks; i++) {
            betButtons[i] = new JButton("Bet on Horse " + (i + 1));
            int index = i;
            betButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (coins >= 10) {
                        coins -= 10;
                        coinsLabel.setText("Coins: " + coins);
                        System.out.println("Bet on Horse " + (index + 1) + " placed");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Not enough coins to place a bet.", "Insufficient Coins", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });
            rightPanel.add(betButtons[i]);
        }
        rightPanel.revalidate();
        rightPanel.repaint();
    }
}
