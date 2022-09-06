package com.example.mars.rover;

import com.example.mars.rover.classes.EarthCommand;
import com.example.mars.rover.classes.RoverPosition;
import com.example.mars.rover.enums.Direction;
import com.example.mars.rover.enums.RoverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mars-rover")
public class MarsRoverController {

    @Autowired
    MarsRoverService marsRoverService;

    @PostMapping("/x/{x}/y/{y}/direction/{direction}")
    public RoverPosition executeCommand(@RequestBody EarthCommand earthCommand, @PathVariable int x, @PathVariable int y, @PathVariable String direction){

        RoverPosition roverPosition = new RoverPosition(x,y, DirectionConverter.getDirection(direction),RoverStatus.MOVING);

        return marsRoverService.executeCommand(earthCommand,roverPosition);


    }
    @PostMapping("/source/x/{x}/y/{y}/direction/{direction}/destination/x/{xd}/y/{yd}")
    public EarthCommand getCommandString(@RequestBody EarthCommand earthCommand, @PathVariable int x, @PathVariable int y, @PathVariable String direction, @PathVariable int xd, @PathVariable int yd){

        RoverPosition roverPosition = new RoverPosition(x,y, DirectionConverter.getDirection(direction),RoverStatus.MOVING);

        return marsRoverService.calculateCommandString(roverPosition,xd,yd,earthCommand);
    }
}
class DirectionConverter {
    public static Direction getDirection(String direction){

        try{
        Direction direction1 =Direction.valueOf(direction.toUpperCase());
            return direction1;
        }
        catch(Exception e){
            throw new RuntimeException("No direction enum with the name \'"+direction+"\' exists!");
        }
    }
}