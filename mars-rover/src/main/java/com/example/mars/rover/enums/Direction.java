package com.example.mars.rover.enums;

import com.example.mars.rover.classes.CommandStringAndDirection;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    static {
        (((NORTH.rotatedRight=EAST)
                .rotatedRight=SOUTH)
                .rotatedRight=WEST)
                .rotatedRight=NORTH;

        (((NORTH.rotatedLeft=WEST)
                .rotatedLeft=SOUTH)
                .rotatedLeft=EAST)
                .rotatedLeft=NORTH;

        (NORTH.opposite=SOUTH).opposite=NORTH;
        (EAST.opposite=WEST).opposite=EAST;

    }

    private Direction rotatedLeft;
    private Direction rotatedRight;

    public Direction getOpposite() {
        return opposite;
    }

    private Direction opposite;
    public Direction getRotatedLeft() {
        return rotatedLeft;
    }
    public Direction getRotatedRight() {
        return rotatedRight;
    }

    public  CommandStringAndDirection reachCommand(Direction direction){

        if(this==direction){
            return new CommandStringAndDirection("",this);
        }
        else if(this.rotatedRight==direction){
            return new CommandStringAndDirection("R",this.rotatedRight);
        }
        else if(this.rotatedLeft==direction) {
            return new CommandStringAndDirection("L",this.rotatedLeft);
        }
        else{
            return new CommandStringAndDirection("RR",this.opposite);
        }
    }

}
