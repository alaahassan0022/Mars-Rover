package com.example.mars.rover.classes;

import com.example.mars.rover.enums.Command;
import com.example.mars.rover.enums.CoordinateUpdate;
import com.example.mars.rover.enums.Direction;

import java.util.HashMap;
import java.util.Map;

public class MapsLoader{
    private static Map<String, CoordinateUpdate> updates ;
    public static Map<String, CoordinateUpdate> getUpdates() {
        return updates;
    }
    public static void fillOutUpdates(){
        updates = new HashMap<>();

        updates.put(generateKey(Direction.NORTH.name(), Command.F.name()),CoordinateUpdate.INCREMENT_Y);
        updates.put(generateKey(Direction.NORTH.name(),Command.B.name()),CoordinateUpdate.DECREMENT_Y);

        updates.put(generateKey(Direction.SOUTH.name(),Command.F.name()),CoordinateUpdate.DECREMENT_Y);
        updates.put(generateKey(Direction.SOUTH.name(),Command.B.name()),CoordinateUpdate.INCREMENT_Y);

        updates.put(generateKey(Direction.EAST.name(),Command.F.name()),CoordinateUpdate.INCREMENT_X);
        updates.put(generateKey(Direction.EAST.name(),Command.B.name()),CoordinateUpdate.DECREMENT_X);

        updates.put(generateKey(Direction.WEST.name(),Command.F.name()),CoordinateUpdate.DECREMENT_X);
        updates.put(generateKey(Direction.WEST.name(),Command.B.name()),CoordinateUpdate.INCREMENT_X);

    }
    public static String generateKey(String string1, String string2){

        return string1+"_"+string2;
    }
    private static Map<Point,Boolean> obstacles ;

    public static Map<Point, Boolean> getObstacles() {
        return obstacles;
    }

    public static void fillOutObstacles(int[][] obstaclesArr){
        obstacles=new HashMap<>();

        for(int i=0;i<obstaclesArr.length;i++)
            obstacles.put(new Point((obstaclesArr[i][0]),(obstaclesArr[i][1])),true);
    }

}
