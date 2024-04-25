/**
 * The horse class follows the criteria provided by the project summary. I have added bodies of code to the methods that were set out
 *
 * @Malaika Khan
 * @25/03/2024
 */
public class Horse {
    //Fields of class Horse
    String horseName;
    char horseSymbol;
    int distance;
    boolean fallenHorse;
    double horseConfidence;


    //Constructor of class Horse
    /**
     * Constructor for objects of class Horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence) {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
        this.fallenHorse = false;

    }



    // Lets the horse fall
    public void fall() {
        this.fallenHorse = true;
    }

    // Getter
    public double getConfidence() {
        return horseConfidence;
    }

    // Getter
    public int getDistanceTravelled() {
        return distance;
    }

    // Getter
    public String getName() {
        return horseName;
    }

    // Getter
    public char getSymbol() {
        return horseSymbol;
    }

    // Puts the horse at the start of the race
    public void goBackToStart() {
        this.distance = 0;
    }

    // Checks if horse has fallen
    public boolean hasFallen() {
        if (fallenHorse) {
            return true;
        } else {
            return false;
        }
    }

    // Moves the horse forward by 1
    public void moveForward() {
        distance++;
    }

    // Setter
    public void setConfidence(double newConfidence) {
        this.horseConfidence = newConfidence;
    }

    // Setter
    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }

    // Setter
    public void setDistanceTravelled(int distance) {
        this.distance = distance;
    }
}