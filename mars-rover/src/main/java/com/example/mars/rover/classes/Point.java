package com.example.mars.rover.classes;

import java.util.Objects;

public class Point {
    private final int x;
    private final int y;
    private int hashCode;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.hashCode = Objects.hash(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object point) {
        if (this == point)
            return true;
        if (point == null || getClass() != point.getClass())
            return false;
        Point thatPoint = (Point) point;
        return x == thatPoint.x && y == thatPoint.y;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
