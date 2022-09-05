package com.example.mars.rover.enums;

import com.example.mars.rover.classes.RoverPosition;

public enum CoordinateUpdate {
    INCREMENT_X{
        @Override
        public void move(RoverPosition roverPosition) {
            roverPosition.incrementX();
        }
    },
    INCREMENT_Y{
        @Override
        public void move(RoverPosition roverPosition) {
            roverPosition.incrementY();
        }
    },
    DECREMENT_X{
        @Override
        public void move(RoverPosition roverPosition) {
            roverPosition.decrementX();
        }
    },
    DECREMENT_Y{
        @Override
        public void move(RoverPosition roverPosition) {
            roverPosition.decrementY();
        }
    };

    public abstract void move(RoverPosition roverPosition);
}
