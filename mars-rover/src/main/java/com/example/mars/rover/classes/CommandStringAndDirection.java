package com.example.mars.rover.classes;


import com.example.mars.rover.enums.Direction;

public class CommandStringAndDirection{
    String commandString;
    Direction direction;

    public CommandStringAndDirection(String commandString, Direction direction) {
        this.commandString = commandString;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getCommandString() {
        return commandString;
    }

    public void setCommandString(String commandString) {
        this.commandString = commandString;
    }
}