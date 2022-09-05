package com.example.mars.rover;

import com.example.mars.rover.classes.EarthCommand;
import com.example.mars.rover.classes.MapsLoader;
import com.example.mars.rover.classes.RoverPosition;
import com.example.mars.rover.enums.Command;
import com.example.mars.rover.enums.RoverStatus;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MarsRoverService {

    public RoverPosition executeCommand(EarthCommand earthCommand, RoverPosition roverPosition){
        earthCommand.setCommandString(earthCommand.getCommandString().toUpperCase());

        if(matches(earthCommand.getCommandString(),"[^BFLR]"))
            throw new RuntimeException("Command strings can only have the letters: F,B,R and L!");

        MapsLoader.fillOutUpdates();
        MapsLoader.fillOutObstacles(earthCommand.getObstacles());

        for( int i =0;i<earthCommand.getCommandString().length();i++){

            int xBefore= roverPosition.getX();
            int yBefore= roverPosition.getY();

            Command.valueOf(String.valueOf(earthCommand.getCommandString().charAt(i))).apply(roverPosition);

            String obstacleKey = MapsLoader.generateKey(String.valueOf(roverPosition.getX()),String.valueOf(roverPosition.getY()));
            if(MapsLoader.getObstaclesMap().containsKey(obstacleKey)){

                roverPosition.setRoverStatus(RoverStatus.STOPPED);
                roverPosition.setX(xBefore);
                roverPosition.setY(yBefore);
                break;
            }
        }
        return roverPosition;
    }
    static boolean matches(String field,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        return matcher.find();
    }
}
