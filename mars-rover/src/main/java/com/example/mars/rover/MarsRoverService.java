package com.example.mars.rover;

import com.example.mars.rover.classes.*;
import com.example.mars.rover.enums.Command;
import com.example.mars.rover.enums.Direction;
import com.example.mars.rover.enums.RoverStatus;
import org.springframework.stereotype.Service;

import java.util.*;
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

        for(int i=0;i<earthCommand.getCommandString().length();i++){

            int xBefore= roverPosition.getX();
            int yBefore= roverPosition.getY();

            String currentCommand = ""+earthCommand.getCommandString().charAt(i)+"";

            Command.valueOf(currentCommand).apply(roverPosition);// ex: Command.valueOf("F").apply -> Command.F.apply -> moves forward


            if(MapsLoader.getObstacles().containsKey(new Point(roverPosition.getX(),roverPosition.getY()))){ // This upcoming point is an obstacle
                roverPosition.setRoverStatus(RoverStatus.STOPPED); // set rover status to STOPPED
                roverPosition.setX(xBefore); // set x to the older value right before the obstacle
                roverPosition.setY(yBefore); // set y to the older value right before the obstacle
                break; // end the iterations to report
            }
        }
        return roverPosition;
    }
    static boolean matches(String field, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(field);
        return matcher.find();
    }
    static Map<Point,Boolean> visited;
    static Map<Point,Point> previous;
    static Queue<Integer> xQueue;
    static Queue<Integer> yQueue;

    public EarthCommand calculateCommandString(RoverPosition sourceRoverPosition, int xDestination, int yDestination, EarthCommand earthCommand){
        visited = new HashMap<>();
        previous = new HashMap<>();
        xQueue = new ArrayDeque<>();
        yQueue = new ArrayDeque<>();


        MapsLoader.fillOutObstacles(earthCommand.getObstacles());

        int xSource=sourceRoverPosition.getX(),ySource=sourceRoverPosition.getY();

        Direction sourceRoverDirection = sourceRoverPosition.getDirection();

        xQueue.add(xSource); yQueue.add(ySource);
        visited.put(new Point(xSource,ySource),true);

        while(xQueue.size()>0){
            int xCurrent = xQueue.remove();
            int yCurrent = yQueue.remove();
            if(xCurrent==xDestination && yCurrent == yDestination){
                break;
            }
            exploreNeighbours(xCurrent,yCurrent);
        }

        Point destinationPoint = new Point(xDestination,yDestination);
        List<Point> pointList= new ArrayList<>();
        while(true){
            if(destinationPoint==null)
                break;
            pointList.add(destinationPoint);
            destinationPoint = previous.get(destinationPoint);
        }

        Point previousArr[]= new Point[pointList.size()];
        for(int i=0;i<pointList.size();i++){
            previousArr[i]=pointList.get(pointList.size()-1-i);
        }


//        CommandStringAndDirection commandStringAndDirection=null;
        String commandString ="";

        for (int i=1;i<previousArr.length;i++){
            if(previousArr[i].getX()>previousArr[i-1].getX()){
                setCurrentCommand(Direction.EAST
                                ,sourceRoverDirection
                                ,commandString);
            }
            else if(previousArr[i].getX()<previousArr[i-1].getX()){
                setCurrentCommand(Direction.WEST
                        ,sourceRoverDirection
                        ,commandString);
            }
            else if(previousArr[i].getY()>previousArr[i-1].getY())
            {
                setCurrentCommand(Direction.NORTH
                        ,sourceRoverDirection
                        ,commandString);
            }
            else if(previousArr[i].getY()<previousArr[i-1].getY())
            {
                setCurrentCommand(Direction.SOUTH
                        ,sourceRoverDirection
                        ,commandString);
            }
        }
        earthCommand.setCommandString(commandString);
        return earthCommand;
    }

    void setCurrentCommand( Direction direction, Direction sourceRoverDirection, String commandString ){
        CommandStringAndDirection commandStringAndDirection = sourceRoverDirection.reachCommand(direction);
        sourceRoverDirection= commandStringAndDirection.getDirection();
        commandString+=(commandStringAndDirection.getCommandString());
        commandString+=("F");
    }

    void exploreNeighbours(int xCurrent, int yCurrent){
        int xMovements[]={-1,1,0,0}, yMovements[]={0,0,1,-1};

        for (int i=0;i<4;i++){
            int xNeighbour = xCurrent+xMovements[i];
            int yNeighbour = yCurrent+yMovements[i];

            if(visited.containsKey(new Point(xNeighbour,yNeighbour)))
                continue;

            if(MapsLoader.getObstacles().containsKey(new Point(xNeighbour,yNeighbour)))
                continue;

            xQueue.add(xNeighbour);
            yQueue.add(yNeighbour);
            visited.put(new Point(xNeighbour,yNeighbour),true);
            previous.put(new Point(xNeighbour,yNeighbour),new Point(xCurrent,yCurrent));
        }


    }

}
