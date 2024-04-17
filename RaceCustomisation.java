import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RaceCustomisation {
    private static int totalRaces = 0; // Track total number of races
    private static int coins = 100; // Initial number of coins

    public static void main(String[] args) {
        // Create a frame
        JFrame frame = new JFrame("Race Customisation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Set colors
        Color leftRightColor = Color.lightGray;
        Color centerColor = Color.gray;
        Color bottomColor = Color.cyan;

        // Create a panel for the left section
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(leftRightColor);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Add components to the left panel
        JLabel topLeftLabel = new JLabel("Track Customisation", SwingConstants.CENTER);
        leftPanel.add(topLeftLabel);

        // Add components for number of tracks
        JPanel numOfTracksPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Left-align components
        JLabel numOfTracksLabel = new JLabel("Number of Tracks:");
        JTextField numOfTracksField = new JTextField(5); // Adjust size as needed
        numOfTracksPanel.add(numOfTracksLabel);
        numOfTracksPanel.add(numOfTracksField);
        leftPanel.add(numOfTracksPanel);

        // Add components for track colour
        JPanel trackColourPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel trackColourLabel = new JLabel("Track Colour:");
        JTextField trackColourField = new JTextField(10); // Adjust size as needed
        trackColourPanel.add(trackColourLabel);
        trackColourPanel.add(trackColourField);
        leftPanel.add(trackColourPanel);

        // Add components for track length
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

        // Create a panel for the center section
        JPanel centerPanel = new JPanel(new GridLayout(3, 1)); // 3 rows, 1 column
        centerPanel.setBackground(centerColor);

        // Add a "Start Race" button
        JButton startRaceButton = new JButton("Start Race");
        startRaceButton.setPreferredSize(new Dimension(100, 30)); // Set preferred size
        startRaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                totalRaces++; // Increment total number of races
                // Implement race start logic here
                System.out.println("Race Started");
            }
        });
        // Create a panel for the button and center-align it
        JPanel startRacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startRacePanel.add(startRaceButton);
        centerPanel.add(startRacePanel);

        // Create a top panel for horse customisation
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center-align components
        JLabel horseCustomisationLabel = new JLabel("Horse Customisation", SwingConstants.CENTER);
        topPanel.add(horseCustomisationLabel);
        centerPanel.add(topPanel);

        // Create a panel for horse details
        JPanel horseDetailsPanel = new JPanel(new GridLayout(1, 3)); // 1 row, 3 columns

        // Create panels for each horse
        JPanel horse1Panel = createHorsePanel("Horse 1");
        JPanel horse2Panel = createHorsePanel("Horse 2");
        JPanel horse3Panel = createHorsePanel("Horse 3");

        // Add horse panels to the horse details panel
        horseDetailsPanel.add(horse1Panel);
        horseDetailsPanel.add(horse2Panel);
        horseDetailsPanel.add(horse3Panel);

        centerPanel.add(horseDetailsPanel);

        // Create a panel for the right section
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(leftRightColor);
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        // Add components to the right panel
        JLabel topRightLabel = new JLabel("Statistics", SwingConstants.CENTER);
        rightPanel.add(topRightLabel);

        // Add statistics components
        JLabel horse1WinsLabel = new JLabel("Horse 1 Wins: 0");
        JLabel horse2WinsLabel = new JLabel("Horse 2 Wins: 0");
        JLabel horse3WinsLabel = new JLabel("Horse 3 Wins: 0");
        JLabel totalRacesLabel = new JLabel("Total Races: 0");

        rightPanel.add(horse1WinsLabel);
        rightPanel.add(horse2WinsLabel);
        rightPanel.add(horse3WinsLabel);
        rightPanel.add(totalRacesLabel);

        // Create a panel for the bottom section (betting system)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(bottomColor); // Set bottom panel background color
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center-align components

        JLabel bettingLabel = new JLabel("Betting System", SwingConstants.CENTER);
        bottomPanel.add(bettingLabel);

        // Add betting components
        JButton betButton1 = new JButton("Bet on Horse 1");
        JButton betButton2 = new JButton("Bet on Horse 2");
        JButton betButton3 = new JButton("Bet on Horse 3");
        JButton viewOddsButton = new JButton("View Odds");

        JLabel coinsLabel = new JLabel("Coins: " + coins); // Label to display coins
        bottomPanel.add(betButton1);
        bottomPanel.add(betButton2);
        bottomPanel.add(betButton3);
        bottomPanel.add(viewOddsButton);
        bottomPanel.add(coinsLabel);

        // Add action listeners to betting buttons
        betButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10; // Decrease coins by 10 when betting
                coinsLabel.setText("Coins: " + coins); // Update coins label
                // Implement betting logic for horse 1
                System.out.println("Bet on Horse 1 placed");
            }
        });

        betButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10; // Decrease coins by 10 when betting
                coinsLabel.setText("Coins: " + coins); // Update coins label
                // Implement betting logic for horse 2
                System.out.println("Bet on Horse 2 placed");
            }
        });

        betButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                coins -= 10; // Decrease coins by 10 when betting
                coinsLabel.setText("Coins: " + coins); // Update coins label
                // Implement betting logic for horse 3
                System.out.println("Bet on Horse 3 placed");
            }
        });

        // Add panels to the frame
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(rightPanel, BorderLayout.EAST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Set frame visibility
        frame.setVisible(true);
    }

    // Method to create a panel for a horse
    private static JPanel createHorsePanel(String horseName) {
        JPanel panel = new JPanel(new GridLayout(6, 2)); // 6 rows, 2 columns
        panel.setBorder(BorderFactory.createTitledBorder(horseName)); // Set horse name as border title

        // Add components for horse name
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        panel.add(nameLabel);
        panel.add(nameField);

        // Add components for horse breed
        JLabel breedLabel = new JLabel("Breed:");
        JTextField breedField = new JTextField();
        panel.add(breedLabel);
        panel.add(breedField);

        // Add components for horse coat color
        JLabel coatColorLabel = new JLabel("Coat Color:");
        JPanel coatColorButtonsPanel = new JPanel(); // Panel to hold coat color buttons
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
}

