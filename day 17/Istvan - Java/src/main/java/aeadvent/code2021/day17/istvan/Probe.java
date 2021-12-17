package aeadvent.code2021.day17.istvan;

import java.util.Optional;

public class Probe {
    private Point position;
    private Velocity velocity;

    public Probe(Point startPosition, Velocity launchVelocity) {
        this.position = startPosition;
        this.velocity = launchVelocity;
    }

    public Point getPosition() {
        return position;
    }

    public void move() {
        int horizontalSpeed = velocity.horizontalSpeed();
        int verticalSpeed = velocity.verticalSpeed();
        position = new Point(position.x()+horizontalSpeed, position.y()+verticalSpeed);

        if (horizontalSpeed != 0)
            horizontalSpeed = horizontalSpeed <0? horizontalSpeed+1:horizontalSpeed-1;
        verticalSpeed--;
        velocity = new Velocity(horizontalSpeed, verticalSpeed);
    }

    public int getHorizontalSpeed() {
        return velocity.horizontalSpeed();
    }

    public int getVerticalSpeed() {
        return velocity.verticalSpeed();
    }

    public boolean isTargetAreaMissed(Area targetArea) {
        return (position.y() < targetArea.y1() && position.y() < targetArea.y2() && velocity.verticalSpeed() < 0)
                || (position.x() < targetArea.x1() && position.x() < targetArea.x2() && velocity.horizontalSpeed() <= 0)
                || (position.x() > targetArea.x1() && position.x() > targetArea.x2() && velocity.horizontalSpeed() >= 0);
    }


    public boolean isTargetAreaReached(Area targetArea) {
        return targetArea.isInArea(position);
    }


    public Optional<Point> getHighestPointWhenReachingTarget(Area targetArea) {
        Point highest = position;
        while (!isTargetAreaMissed(targetArea) && !isTargetAreaReached(targetArea)) {
            move();
            if (position.y() > highest.y())
                highest = position;
        }
        return isTargetAreaReached(targetArea)? Optional.of(highest):Optional.empty();
    }
}
