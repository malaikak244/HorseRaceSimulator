import java.util.concurrent.TimeUnit;
import java.lang.Math;
import javax.swing.JTextArea;
import java.util.ArrayList;
import java.util.List;


public class Race {
    private int raceLength;
    private List<Horse> horses;

    public Race(int distance) {
        raceLength = distance;
        horses = new ArrayList<>();
    }


    public void addHorse(Horse theHorse) {
        horses.add(theHorse);
    }

    public void startRace(JTextArea raceProgressTextArea) {
        boolean finished = false;

        for (Horse horse : horses) {
            if (horse != null) {
                horse.goBackToStart();
            }
        }

        while (!finished) {
            for (Horse horse : horses) {
                if (horse != null) {
                    moveHorse(horse);
                }
            }

            printRace();

            int fallenCount = 0;
            for (Horse horse : horses) {
                if (horse != null && horse.hasFallen()) {
                    fallenCount++;
                }
            }

            if (anyHorseWon() || fallenCount >= horses.size() - 1) {
                finished = true;
            }

            if (fallenCount >= horses.size() - 1) {
                System.out.println("Race ended prematurely because " + fallenCount + " horses have fallen.");
                finished = true;
                break;
            }

            String raceProgress = generateRaceProgress();
            raceProgressTextArea.setText(raceProgress);

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        winningHorse();
    }

    private void moveHorse(Horse theHorse) {
        if (!theHorse.hasFallen()) {
            adjustConfidence(theHorse);

            double moveProbability = 0.5 + (theHorse.getConfidence() * 0.4);
            if (Math.random() < moveProbability) {
                theHorse.moveForward();
            }

            double fallProbability;
            if (theHorse.getConfidence() < 0.5) {
                fallProbability = 0.005 * (1 - theHorse.getConfidence()); 
            } else {
                fallProbability = 0.05 + (0.1 * (theHorse.getConfidence() - 0.5));
            }

            if (Math.random() < fallProbability) {
                theHorse.fall();
            }
        }
    }

    public void adjustConfidence(Horse theHorse) {
        if (theHorse.hasFallen()) {
            double newConfidence = Math.floor(theHorse.getConfidence() * 0.9 * 100) / 100;
            theHorse.setConfidence(Math.max(0.2, newConfidence));
        } else {
            double newConfidence;
            if (theHorse.getConfidence() < 0.5) {
                newConfidence = Math.min(1.0, Math.floor(theHorse.getConfidence() * 1.02 * 100) / 100);
            } else {
                newConfidence = Math.min(1.0, Math.floor(theHorse.getConfidence() * 1.01 * 100) / 100);
            }
            
            if (Math.random() < 0.05) {
                newConfidence = Math.max(0.2, Math.floor(newConfidence * 0.9 * 100) / 100);
            }
            theHorse.setConfidence(newConfidence);
        }
    }

    private boolean anyHorseWon() {
        for (Horse horse : horses) {
            if (horse != null && raceWonBy(horse)) {
                return true;
            }
        }
        return false;
    }

    private boolean raceWonBy(Horse theHorse) {
        return theHorse.getDistanceTravelled() == raceLength;
    }

    public String generateRaceProgress() {
        StringBuilder raceProgress = new StringBuilder();
        raceProgress.append("Race Progress:\n");
    
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            if (horse != null) {
                raceProgress.append("Lane ").append(i + 1).append(": ").append(generateLaneProgress(horse)).append("\n");
            }
        }
    
        return raceProgress.toString();
    }
    
    private String generateLaneProgress(Horse horse) {
        StringBuilder laneProgress = new StringBuilder();

        for (int i = 0; i < horse.getDistanceTravelled(); i++) {
            laneProgress.append(" ");
        }

        if (horse.hasFallen()) {
            laneProgress.append("X");
        } else {
            laneProgress.append(horse.getSymbol());
        }

        for (int i = horse.getDistanceTravelled() + 1; i <= raceLength; i++) {
            laneProgress.append(" ");
        }

        laneProgress.append(" ").append(horse.getName()).append(": (Confidence: ").append(horse.getConfidence()).append(")");

        return laneProgress.toString();
    }

    private void printRace() {
        System.out.print('\u000C');
        multiplePrint('=', raceLength + 3);
        System.out.println();

        for (Horse horse : horses) {
            if (horse != null) {
                printLane(horse);
                System.out.println();
            }
        }

        multiplePrint('=', raceLength + 3);
        System.out.println();
    }

    private void printLane(Horse theHorse) {
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        System.out.print('|');
        
        multiplePrint(' ', spacesBefore);
        
        if (theHorse.hasFallen()) {
            System.out.print('\u2322');
        } else {
            System.out.print(theHorse.getSymbol());
        }
        
        multiplePrint(' ', spacesAfter);
        
        System.out.print('|');

        
        System.out.print(theHorse.getName() + ": (Confidence: " + theHorse.getConfidence() + ")");
    }

    private void multiplePrint(char aChar, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(aChar);
        }
    }

    private void winningHorse() {
        for (Horse horse : horses) {
            if (horse != null && raceWonBy(horse)) {
                System.out.println("And the winner is " + horse.getName());
            }
        }
    }

    public int getWinnerIndex() {
        for (int i = 0; i < horses.size(); i++) {
            Horse horse = horses.get(i);
            if (horse != null && raceWonBy(horse)) {
                return i;
            }
        }
        return -1;
    }
    

    
    
}
