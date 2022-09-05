
package com.example.mars.rover.enums;

import com.example.mars.rover.classes.MapsLoader;
import com.example.mars.rover.classes.RoverPosition;

public enum Command {

    F{
        @Override
        public void apply(RoverPosition roverPosition) {
            MapsLoader.getUpdates().get(MapsLoader.generateKey(roverPosition.getDirection().name(),
                    this.name())).move(roverPosition);
        }
    },
    B{
        @Override
        public void apply(RoverPosition roverPosition) {
            MapsLoader.getUpdates().get(MapsLoader.generateKey(roverPosition.getDirection().name(),
                    this.name())).move(roverPosition);
        }
    },
    R{
        @Override
        public void apply(RoverPosition roverPosition) {
            roverPosition.setDirection(roverPosition.getDirection().getRotatedRight());
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