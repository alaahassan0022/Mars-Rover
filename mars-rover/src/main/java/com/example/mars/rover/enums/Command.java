
package com.example.mars.rover.enums;

import com.example.mars.rover.classes.MapsLoader;
import com.example.mars.rover.classes.RoverPosition;

public enum Command {

    F{
        @Override
        public void apply(RoverPosition roverPosition) {
            String updateKey = MapsLoader.generateKey(roverPosition.getDirection().name(), this.name());
            MapsLoader.getUpdates().get(updateKey).move(roverPosition);//MapsLoader.getUpdates().get(updateKey) --> CoordinateUpdate
            //ex: roverPosition.getDirection() = NORTH, this = F --> CoordinateUpdate = INCREMENT_Y
            //INCREMENT_Y.move() is executed and the y-coordinate is incremented
        }
    },
    B{
        @Override
        public void apply(RoverPosition roverPosition) {
            String updateKey = MapsLoader.generateKey(roverPosition.getDirection().name(), this.name());
            MapsLoader.getUpdates().get(updateKey).move(roverPosition);
        }
    },
    R{
        @Override
        public void apply(RoverPosition roverPosition) {
            roverPosition.setDirection(roverPosition.getDirection().getRotatedRight());
            //sets the current direction to the direction that will be pointed at when it's rotated right
            //ex: roverPosition.getDirection() = NORTH -> NORTH.getRotatedRight() = EAST
        }
    },
    L{
        @Override
        public void apply(RoverPosition roverPosition) {
            roverPosition.setDirection(roverPosition.getDirection().getRotatedLeft());
        }
    };

    public abstract void apply(RoverPosition roverPosition);
}