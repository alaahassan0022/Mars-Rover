package com.example.mars.rover.enums;

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

    }

    private Direction rotatedLeft;
    private Direction rotatedRight;
    public Direction getRotatedLeft() {
        return rotatedLeft;
    }
    public Direction getRotatedRight() {
        return rotatedRight;
    }

}
