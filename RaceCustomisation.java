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

    // Main method
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
        JSlider numOfTracksSlider = new JSlider(JSlider.HORIZONTAL, 2, 5, 3); // Adjust min, max, and initial value as needed
        numOfTracksSlider.setMajorTickSpacing(1);
        numOfTracksSlider.setMinorTickSpacing(0);
        numOfTracksSlider.setPaintTicks(true);
        numOfTracksSlider.setPaintLabels(true);
        numOfTracksPanel.add(numOfTracksLabel);
        numOfTracksPanel.add(numOfTracksSlider);
        leftPanel.add(numOfTracksPanel);

        JPanel trackCustomizationPanel = new JPanel();
        trackCustomizationPanel.setLayout(new BoxLayout(trackCustomizationPanel, BoxLayout.Y_AXIS));
        leftPanel.add(trackCustomizationPanel);

        // Update track customizations based on slider value
        numOfTracksSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int numTracks = numOfTracksSlider.getValue();
                updateTrackCustomization(trackCustomizationPanel, numTracks);
            }
        });

        JPanel trackColourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackColourLabel = new JLabel("Track Colour:");
        JTextField trackColourField = new JTextField(10); // Adjust size as needed
        trackColourPanel.add(trackColourLabel);
        trackColourPanel.add(trackColourField);
        leftPanel.add(trackColourPanel);

        JPanel trackLengthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackLengthLabel = new JLabel("Track Length:");
        JSlider trackLengthSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // Adjust min, max, and initial value as needed
        trackLengthSlider.setMajorTickSpacing(10);
        trackLengthSlider.setMinorTickSpacing(5);
        trackLengthSlider.setPaintTicks(true);
        trackLengthSlider.setPaintLabels(true);
        trackLengthPanel.add(trackLengthLabel);
        trackLengthPanel.add(trackLengthSlider);
        leftPanel.add(trackLengthPanel);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column
        centerPanel.setBackground(centerColor);

        JButton startRaceButton = new JButton("Start Race");
        startRaceButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalRaces++;
                int numTracks = numOfTracksSlider.getValue();
                Race race = new Race(trackLengthSlider.getValue());

                // Add horses to the race based on the selected number of tracks
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
                        // Default to 2 tracks if the selected number is invalid
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

        JPanel horseDetailsPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns

        JPanel horse1Panel = createHorsePanel("Horse 1");
        JPanel horse2Panel = createHorsePanel("Horse 2");
        JPanel horse3Panel = createHorsePanel("Horse 3");

        horseDetailsPanel.add(horse1Panel);
        horseDetailsPanel.add(horse2Panel);
        horseDetailsPanel.add(horse3Panel);

        centerPanel.add(horseDetailsPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(leftRightColor);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JLabel topRightLabel = new JLabel("Statistics", SwingConstants.CENTER);
        rightPanel.add(topRightLabel);

        JLabel horse1WinsLabel = new JLabel("Horse 1 Wins: 0");
        JLabel horse2WinsLabel = new JLabel("Horse 2 Wins: 0");
        JLabel horse3WinsLabel = new JLabel("Horse 3 Wins: 0");
        JLabel totalRacesLabel = new JLabel("Total Races: 0");

        rightPanel.add(horse1WinsLabel);
        rightPanel.add(horse2WinsLabel);
        rightPanel.add(horse3WinsLabel);
        rightPanel.add(totalRacesLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bottomColor);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel bettingLabel = new JLabel("Betting System", SwingConstants.CENTER);
        bottomPanel.add(bettingLabel);

        JButton betButton1 = new JButton("Bet on Horse 1");
        JButton betButton2 = new JButton("Bet on Horse 2");
        JButton betButton3 = new JButton("Bet on Horse 3");
        JButton viewOddsButton = new JButton("View Odds");

        JLabel coinsLabel = new JLabel("Coins: " + coins);
        bottomPanel.add(betButton1);
        bottomPanel.add(betButton2);
        bottomPanel.add(betButton3);
        bottomPanel.add(viewOddsButton);
        bottomPanel.add(coinsLabel);

        betButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10;
                coinsLabel.setText("Coins: " + coins);
                System.out.println("Bet on Horse 1 placed");
            }
        });

        betButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10;
                coinsLabel.setText("Coins: " + coins);
                System.out.println("Bet on Horse 2 placed");
            }
        });

        betButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10;
                coinsLabel.setText("Coins: " + coins);
                System.out.println("Bet on Horse 3 placed");
            }
        });

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

    // Method to create a panel for a horse
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

    private static void updateTrackCustomization(JPanel trackCustomizationPanel, int numTracks) {
        trackCustomizationPanel.removeAll(); // Clear previous components
        for (int i = 1; i <= numTracks; i++) {
            JPanel trackPanel = new JPanel();
            trackPanel.setBorder(BorderFactory.createTitledBorder("Track " + i));
            trackPanel.add(new JLabel("Customization for Track " + i));
            trackCustomizationPanel.add(trackPanel);
        }
        trackCustomizationPanel.revalidate(); // Refresh panel layout
        trackCustomizationPanel.repaint(); // Repaint panel
    }
}