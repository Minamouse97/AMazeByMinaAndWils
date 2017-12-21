package edu.wm.cs.cs301.amazebyminaandwils.falstad;

import android.util.Log;

import java.util.concurrent.TimeUnit;


import edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Direction;
import edu.wm.cs.cs301.amazebyminaandwils.falstad.Robot.Turn;
import edu.wm.cs.cs301.amazebyminaandwils.generation.CardinalDirection;
import edu.wm.cs.cs301.amazebyminaandwils.generation.Cells;

public class BasicRobot implements Robot {


    MazeController maze;
    Cells cell;
    protected CardinalDirection cdirection;
    protected int[] currentPosition;
    protected boolean hasStopped;
//    SimpleKeyListener skl;

    private static final String TAG = ":BasicRobotLOG:";


    int odometer = 0;
    float energyLevel;
    private UserInput UserInput;


    public BasicRobot(MazeController controller) {
        energyLevel = 3000;
        cdirection = CardinalDirection.East;
        hasStopped = false;
        this.maze = controller;
        currentPosition = maze.getCurrentPosition();
        cell = maze.getMazeConfiguration().getMazecells(); /* getMazecells is null*/
        System.out.println("robot with controller is being made!!!");
    }

    /**
     * Turn robot on the spot for amount of degrees.
     * If robot runs out of energy, it stops,
     * which can be checked by hasStopped() == true and by checking the battery level.
     * @param turn direction to turn and relative to current forward direction.
     */
    @Override
    public void rotate(Turn turn) {

        switch (turn) {
            case LEFT:
                // turns 90 degrees left

                //check battery
                if (energyLevel >= 3) {
                    cdirection = cdirection.rotateCounterClockwise();
                    //decrease battery
                    energyLevel = energyLevel - 3;
                    maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Left, 1);
                    Log.v(TAG, "BasicRobot turn(left) called");
                }
                else {
                    hasStopped = true;
                    maze.switchToLossScreen();
                }
                break;
            case RIGHT:
                // turns 90 degrees right

                //check battery
                if (energyLevel >= 3) {
                    cdirection = cdirection.rotateClockwise();
                    //decrease battery
                    energyLevel = energyLevel - 3;
                    maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Right, 1);
                    Log.v(TAG, "BasicRobot turn(right) called");
                }
                else {
                    hasStopped = true;
                    maze.switchToLossScreen();
                }
                break;
            case AROUND:
                //turns 180 degrees either which way

                //check battery
                if (energyLevel >= 6) {
                    cdirection = cdirection.oppositeDirection();

                    //decrease battery
                    energyLevel = energyLevel - 3;

                    maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Right, 1);
                    maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Right, 1);
                    Log.v(TAG, "BasicRobot turn(around) called");
                }
                else {
                    hasStopped = true;
                    maze.switchToLossScreen();
                }
                break;
        }
    }

    /**
     * Moves robot forward a given number of steps. A step matches a single cell.
     * If the robot runs out of energy somewhere on its way, it stops,
     * which can be checked by hasStopped() == true and by checking the battery level.
     * If the robot hits an obstacle like a wall, it depends on the mode of operation
     * what happens. If an algorithm drives the robot, it remains at the position in front
     * of the obstacle and also hasStopped() == true as this is not supposed to happen.
     * This is also helpful to recognize if the robot implementation and the actual maze
     * do not share a consistent view on where walls are and where not.
     * If a user manually operates the robot, this behavior is inconvenient for a user,
     * such that in case of a manual operation the robot remains at the position in front
     * of the obstacle but hasStopped() == false and the game can continue.
     * @param distance is the number of cells to move in the robot's current forward direction
     * @param manual is true if robot is operated manually by user, false otherwise
     * @precondition distance >= 0
     */
    @Override
    public void move(int distance, boolean manual) {

        while (distance > 0) {
            if (energyLevel >= 5) {
                if (manual == true) {
                    distance = 1;
                }

                if (maze.checkMove(1)) {
                    maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Up, 1);
                    Log.v(TAG, "BasicRobot move(up) called");
                    //set current position in Maze

                    cdirection = getCurrentDirection();
                    //decrease battery level by 5
                    energyLevel = energyLevel - 5;
                    //increase odometer by 1
                    odometer++;

                }
            }
            else {
                hasStopped = true;
                maze.switchToLossScreen();
            }
            distance--;
        }
    }


    public void moveBack(int distance, boolean manual) {
        if (energyLevel >= 5) {
            if (manual == true) {
                distance = 1;
            }
            if (maze.checkMove(-1)) {
                maze.keyDown(edu.wm.cs.cs301.amazebyminaandwils.falstad.MazeController.UserInput.Down, 1);
                Log.v(TAG, "BasicRobot move(down) called");
                //set current position in Maze

                cdirection = getCurrentDirection();
                //decrease battery level by 5
                energyLevel = energyLevel - 5;
                //increase odometer by 1
                odometer++;
            }
            else {
                hasStopped = true;
                maze.switchToLossScreen();
            }
        }
    }

    /**
     * Provides the current position as (x,y) coordinates for the maze cell as an array of length 2 with [x,y].
     * @postcondition 0 <= x < width, 0 <= y < height of the maze.
     * @throws Exception if position is outside of the maze
     */
    @Override
    public int[] getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Provides the robot with a reference to the maze it is currently in.
     * The robot memorizes the maze such that this method is most likely called only once
     * and for initialization purposes. The maze serves as the main source of information
     * for the robot about the current position, the presence of walls, the reaching of an exit.
     * @param maze is the current maze
     * @precondition maze != null, maze refers to a fully operational, configured maze configuration
     */
    @Override
    public void setMaze(MazeController maze) {
        currentPosition = maze.getCurrentPosition();

        // gets the cells which describe where walls are in the maze
        cell = maze.mazeConfig.getMazecells();

        // gets the direction from CardinalDirection
        int[] cd = new int[2];
        cd = cdirection.getDirection();

        // checks the coordinates of the maze direction and sets the robot direction to match that one
        if (cd[0] == 0 && cd[1] == -1) {
            cdirection = CardinalDirection.North;
        }
        if (cd[0] == 1 && cd[1] == 0) {
            cdirection = CardinalDirection.East;
        }
        if (cd[0] == 0 && cd[1] == 1) {
            cdirection = CardinalDirection.South;
        }
        else {
            cdirection = CardinalDirection.West;
        }

    }

    private Cells getCells() {
        if (null == cell) {
            cell = maze.getMazeConfiguration().getMazecells();
        }
        return cell;
    }

    /**
     * Tells if current position (x,y) is right at the exit but still inside the maze.
     * Used to recognize termination of a search.
     * @return true if robot is at the exit, false otherwise
     */
    @Override
    public boolean isAtExit() {
        cell = getCells();
        return cell.isExitPosition(maze.getCurrentPosition()[0], maze.getCurrentPosition()[1]);
    }



    /**
     * Tells if a sensor can identify the exit in given direction relative to
     * the robot's current forward direction from the current position.
     * @return true if the exit of the maze is visible in a straight line of sight
     * @throws UnsupportedOperationException if robot has no sensor in this direction
     */
    @Override
    public boolean canSeeExit(Direction cdirection) throws UnsupportedOperationException {
        if (hasDistanceSensor(cdirection) == true) {
            if (distanceToObstacle(cdirection) == Integer.MAX_VALUE) {// This only returns true at the goal per the the method in distance
                return true;
            }

            else {
                return false;
            }
        }

        else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Tells if current position is inside a room.
     * @return true if robot is inside a room, false otherwise
     * @throws UnsupportedOperationException if not supported by robot
     */
    @Override
    public boolean isInsideRoom() throws UnsupportedOperationException {
        if (cell.isInRoom(getCurrentPosition()[0], getCurrentPosition()[1]) == true) {
            return true;
        }
        else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Tells if the robot has a room sensor.
     */
    @Override
    public boolean hasRoomSensor() {
        return true;
    }

    /**
     * Provides the current cardinal direction.
     *
     * @return cardinal direction is robot's current direction in absolute terms
     */
    @Override
    public CardinalDirection getCurrentDirection() {
        // gets direction from MazeController
        return maze.getCurrentDirection();
    }

    /**
     * Returns the current battery level.
     * The robot has a given battery level (energy level) that it draws energy from during operations.
     * The particular energy consumption is device dependent such that a call for distance2Obstacle may use less energy than a move forward operation.
     * If battery level <= 0 then robot stops to function and hasStopped() is true.
     * @return current battery level, level is > 0 if operational.
     */
    @Override
    public float getBatteryLevel() {
        return energyLevel;
    }

    /**
     * Sets the current battery level.
     * The robot has a given battery level (energy level) that it draws energy from during operations.
     * The particular energy consumption is device dependent such that a call for distance2Obstacle may use less energy than a move forward operation.
     * If battery level <= 0 then robot stops to function and hasStopped() is true.
     * @param level is the current battery level
     * @precondition level >= 0
     */
    @Override
    public void setBatteryLevel(float level) {
        energyLevel = level;
    }

    /**
     * Gets the distance traveled by the robot.
     * The robot has an odometer that calculates the distance the robot has moved.
     * Whenever the robot moves forward, the distance that it moves is added to the odometer counter.
     * The odometer reading gives the path length if its setting is 0 at the start of the game.
     * The counter can be reset to 0 with resetOdomoter().
     * @return the distance traveled measured in single-cell steps forward
     */
    @Override
    public int getOdometerReading() {
        return odometer;
    }

    /**
     * Resets the odomoter counter to zero.
     * The robot has an odometer that calculates the distance the robot has moved.
     * Whenever the robot moves forward, the distance that it moves is added to the odometer counter.
     * The odometer reading gives the path length if its setting is 0 at the start of the game.
     */
    @Override
    public void resetOdometer() {
        odometer = 0;
    }

    /**
     * Gives the energy consumption for a full 360 degree rotation.
     * Scaling by other degrees approximates the corresponding consumption.
     * @return energy for a full rotation
     */
    @Override
    public float getEnergyForFullRotation() {
        return 12;

    }

    /**
     * Gives the energy consumption for moving forward for a distance of 1 step.
     * For simplicity, we assume that this equals the energy necessary
     * to move 1 step backwards and that scaling by a larger number of moves is
     * approximately the corresponding multiple.
     * @return energy for a single step forward
     */
    @Override
    public float getEnergyForStepForward() {
        return 5;

    }

    /**
     * Tells if the robot has stopped for reasons like lack of energy, hitting an obstacle, etc.
     * @return true if the robot has stopped, false otherwise
     */
    @Override
    public boolean hasStopped() {
        return hasStopped;
    }

    /**
     * Tells the distance to an obstacle (a wall or border)
     * in a direction as given and relative to the robot's current forward direction.
     * Distance is measured in the number of cells towards that obstacle,
     * e.g. 0 if current cell has a wall in this direction,
     * 1 if it is one step forward before directly facing a wall,
     * Integer.MaxValue if one looks through the exit into eternity.
     * @return number of steps towards obstacle if obstacle is visible
     * in a straight line of sight, Integer.MAX_VALUE otherwise
     * @throws UnsupportedOperationException if not supported by robot
     */
    @Override
    public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
        if (hasDistanceSensor(direction)) {
            try {
                TimeUnit.MILLISECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // sensor cost = 1
            setBatteryLevel(energyLevel - 1);

            //get direction for checking walls in that direction
            CardinalDirection cd;
            if (direction == Direction.LEFT) {
                cd = cdirection.rotateCounterClockwise();
            }

            else if (direction == Direction.RIGHT) {
                cd = cdirection.rotateClockwise();
            }

            else if (direction == Direction.BACKWARD) {
                cd = cdirection.oppositeDirection();
            }

            else {
                cd = cdirection;
            }

            // set a counter to see how many steps you can go before running into an obstacle
            int count = 0;
            int currX = maze.getCurrentPosition()[0]; // current x coordinate
            int currY = maze.getCurrentPosition()[1]; // current y coordinate

            while (true) {

                if (currX < 0 || currX >= cell.width || currY < 0 || currY >= cell.height) { // checks if out of bounds of maze
                    return Integer.MAX_VALUE;
                }

                else {
                    switch (cd) { // each one of the cases checks to see if there is a wall in the given direction and then increments the counter of the while loop while decrementing/incrementing the corresponding coordinate value
                        case North:
                            if (cell.hasWall(currX, currY, CardinalDirection.North)) {
                                return count;
                            }
                            currY--;
                            break;
                        case South:
                            if (cell.hasWall(currX, currY, CardinalDirection.South)) {
                                return count;
                            }
                            currY++;
                            break;
                        case East:
                            if (cell.hasWall(currX, currY, CardinalDirection.East)) {
                                return count;
                            }
                            currX++;
                            break;
                        case West:

                            if (cell.hasWall(currX, currY, CardinalDirection.West)) {
                                return count;
                            }
                            currX--;
                            break;

                    }
                    count++;
                }
            }
        }
        else {
            throw new UnsupportedOperationException();
        }
    }


    /**
     * Tells if the robot has a distance sensor for the given direction.
     * Since this interface is generic and may be implemented with robots
     * that are more or less equipped. The purpose is to allow for a flexible
     * robot driver to adapt its driving strategy according the features it
     * finds supported by a robot.
     */
    @Override
    public boolean hasDistanceSensor(Direction direction) {
        return true;
    }

}
