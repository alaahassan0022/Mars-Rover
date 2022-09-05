package com.example.mars.rover.classes;


import com.example.mars.rover.enums.Direction;
import com.example.mars.rover.enums.RoverStatus;

public class RoverPosition {
    private int x;
    private int y;
    private Direction direction;
    private RoverStatus roverStatus;

    @Override
    public String toString() {
        return "RoverPosition{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                ", roverStatus=" + roverStatus +
                '}';
    }

    public RoverStatus getRoverStatus() {
        return roverStatus;
    }

    public void setRoverStatus(RoverStatus roverStatus) {
        this.roverStatus = roverStatus;
    }

    public void incrementX() {
        x++;
    }
    public void decrementX(){
        x--;
    }
    public void incrementY(){
        y++;
    }
    public void decrementY(){
        y--;
    }

    public RoverPosition(int x, int y, Direction direction, RoverStatus roverStatus) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.roverStatus = roverStatus;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
