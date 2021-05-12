package main;

public class Landmark{
    public double x, y;
    public String landmarkName;

    public Landmark(double x, double y, String landmarkName) {
        this.x = x;
        this.y = y;
        this.landmarkName = landmarkName;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getLandmarkName() {
        return landmarkName;
    }

    public void setLandmarkName(String landmarkName) {
        this.landmarkName = landmarkName;
    }

    @Override
    public String toString() {
        return x + ", " + y + ", " + landmarkName;
    }
}