package com.example.mars.rover.classes;

import org.springframework.stereotype.Component;

import java.util.Arrays;

public class EarthCommand {
    private String commandString;
    private int [][] obstacles;

    public EarthCommand(String commandString, int[][] obstacles) {
        this.commandString = commandString;
        this.obstacles = obstacles;
    }

    @Override
    public String toString() {
        return "EarthCommand{" +
                "commandString='" + commandString + '\'' +
                ", obstacles=" + Arrays.toString(obstacles) +
                '}';
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
    }

    public int[][] getObstacles() {
        return obstacles;
    }

    public void setObstacles(int[][] obstacles) {
        this.obstacles = obstacles;
    }
}