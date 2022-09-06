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
    static Map<Point,Boolean> visited = new HashMap<>();
    static Map<Point,Point> previous = new HashMap<>();
    static Queue<Integer> xQueue = new ArrayDeque<>();
    static Queue<Integer> yQueue = new ArrayDeque<>();

    public EarthCommand calculateCommandString(RoverPosition sourceRoverPosition, int xDestination, int yDestination, EarthCommand earthCommand){
        MapsLoader.fillOutObstacles(earthCommand.getObstacles());

        int xSource=sourceRoverPosition.getX(),ySource=sourceRoverPosition.getY();

        Direction sourceRoverDirection = sourceRoverPosition.getDirection();

        boolean reached_end=false;
        xQueue.add(xSource); yQueue.add(ySource);
        visited.put(new Point(xSource,ySource),true);

        while(xQueue.size() >0){
            int x = xQueue.remove();
            int y = yQueue.remove();
            if(x==xDestination && y == yDestination){
                reached_end=true;
                break;
            }
            exploreNeighbours(x,y);
        }
        if(reached_end)
            System.out.println("reached");
        else
            System.out.println("not");

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
            System.out.println(previousArr[i]);
        }


        CommandStringAndDirection commandStringAndDirection;
        String commandString ="";
        for (int i =1;i<previousArr.length;i++){
            if(previousArr[i].getX()>previousArr[i-1].getX()){
                commandStringAndDirection = sourceRoverDirection.reachCommand(Direction.EAST);
                sourceRoverDirection= commandStringAndDirection.getDirection();
                commandString+=(commandStringAndDirection.getCommandString());
                commandString+=("F");
            }
            else if(previousArr[i].getX()<previousArr[i-1].getX()){
                commandStringAndDirection = sourceRoverDirection.reachCommand(Direction.WEST);
                sourceRoverDirection= commandStringAndDirection.getDirection();
                commandString+=(commandStringAndDirection.getCommandString());
                commandString+=("F");
            }
            else if(previousArr[i].getY()>previousArr[i-1].getY())
            {
                commandStringAndDirection = sourceRoverDirection.reachCommand(Direction.NORTH);
                sourceRoverDirection= commandStringAndDirection.getDirection();
                commandString+=(commandStringAndDirection.getCommandString());
                commandString+=("F");
            }
            else if(previousArr[i].getY()<previousArr[i-1].getY())
            {
                commandStringAndDirection = sourceRoverDirection.reachCommand(Direction.SOUTH);
                sourceRoverDirection= commandStringAndDirection.getDirection();
                commandString+=(commandStringAndDirection.getCommandString());
                commandString+=("F");
            }
        }

        earthCommand.setCommandString(commandString);
        return earthCommand;
    }


    void exploreNeighbours(int x, int y){
        int dx[]={-1,1,0,0}, dy[]={0,0,1,-1};

        for (int i=0;i<4;i++){
            int xx = x+dx[i];
            int yy = y+dy[i];

            if(visited.containsKey(new Point(xx,yy)))
                continue;

            if(MapsLoader.getObstacles().containsKey(new Point(xx,yy)))
                continue;

            xQueue.add(xx);
            yQueue.add(yy);
            visited.put(new Point(xx,yy),true);
            previous.put(new Point(xx,yy),new Point(x,y));
        }


    }

}
