/**
 * Write a description of class Horse here.
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

    }



    public void fall() {
        this.fallenHorse = true;
    }

    public double getConfidence() {
        return horseConfidence;
    }

    public int getDistanceTravelled() {
        return distance;
    }

    public String getName() {
        return horseName;
    }

    public char getSymbol() {
        return horseSymbol;
    }

    public void goBackToStart() {
        this.distance = 0;
    }

    public boolean hasFallen() {
        if (fallenHorse) {
            return true;
        } else {
            return false;
        }
    }

    public void moveForward() {
        distance++;
    }

    public void setConfidence(double newConfidence) {
        this.horseConfidence = newConfidence;
    }

    public void setSymbol(char newSymbol) {
        this.horseSymbol = newSymbol;
    }

    public void setDistanceTravelled(int distance) {
        this.distance = distance;
    }
}