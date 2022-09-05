package com.example.mars.rover;

import com.example.mars.rover.classes.EarthCommand;
import com.example.mars.rover.classes.RoverPosition;
import com.example.mars.rover.enums.Direction;
import com.example.mars.rover.enums.RoverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MarsRoverServiceTest {

    RoverPosition roverPosition;
    EarthCommand earthCommand;
    @Autowired
    MarsRoverService marsRoverService;

    @BeforeEach
    void instantiateObjects(){
        roverPosition = new RoverPosition(4,2, Direction.EAST, RoverStatus.MOVING);
    }

    @Test
    void shouldReturnRoverPosition() {
        earthCommand= new EarthCommand("FLFFFRFLB",new int [][]{});

        RoverPosition roverPosition1 = new RoverPosition(6,4,Direction.NORTH,RoverStatus.MOVING);

        RoverPosition roverPosition2 = marsRoverService.executeCommand(earthCommand,roverPosition);

        assertThat(roverPosition2).usingRecursiveComparison().isEqualTo(roverPosition1);

    }
    @Test
    void shouldNotReturnRoverPosition() {
        earthCommand= new EarthCommand("FLFFFRFL",new int [][]{});

        RoverPosition roverPosition1 = new RoverPosition(6,4,Direction.NORTH,RoverStatus.MOVING);

        RoverPosition roverPosition2 = marsRoverService.executeCommand(earthCommand,roverPosition);

        assertThat(roverPosition2).usingRecursiveComparison().isNotEqualTo(roverPosition1);

    }
    @Test
    void shouldReportBeforeObstacle() {

        earthCommand= new EarthCommand("FLFFFRFLB",new int [][]{{5,5}});

        RoverPosition roverPosition1 = new RoverPosition(5,4,Direction.NORTH,RoverStatus.STOPPED);

        RoverPosition roverPosition2 = marsRoverService.executeCommand(earthCommand,roverPosition);

        assertThat(roverPosition2).usingRecursiveComparison().isEqualTo(roverPosition1);

    }
    @Test
    void shouldThrowException() {
        earthCommand= new EarthCommand("FLFFFRFALB",new int [][]{});

        assertThrows(RuntimeException.class, () -> marsRoverService.executeCommand(earthCommand,roverPosition));
    }
    @Test
    void shouldNotThrowException() {
        earthCommand= new EarthCommand("FLFFFRFLB",new int [][]{});

        assertDoesNotThrow( () -> marsRoverService.executeCommand(earthCommand,roverPosition));
    }


}