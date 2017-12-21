package edu.wm.cs.cs301.amazebyminaandwils.generation;



import java.util.ArrayList;
import java.util.List;

public class MazeBuilderEller extends MazeBuilder implements Runnable {

    /**
     *  constructor
     */
    public MazeBuilderEller() {
        super();
        System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
    }

    public MazeBuilderEller(boolean det) {
        super(det);
        System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
    }

    @Override
    protected int generateRooms() {
        return 0;
    }


    @Override
    protected void generatePathways() {

        /**
         * Initialize each cell of the first row to exist in their own set
         */
        int[][] multi = new int[height][width];
        int i = 1;
        for (int y = 0; y<height; y++) {
            for (int x = 0; x<width; x++) {
                if (multi[y][x] == 0){
                    multi[y][x] = i;
                    i++;
                }
            }


            /**
             * Randomly join adjacent cells, but only if they are not in the same set.
             * When joining adjacent cells, merge the cells of both sets into a single set, indicating that all cells in both sets are now connected
             * (there is a path that connects any two cells in the set).
             */

            if (y != height - 1) {
                for (int x = 1; x<width; x++) {
                    if ( multi[y][x] != multi[y][x-1] ) {
                        int h = random.nextIntWithinInterval(0, 2);
                        if (h == 0) {
                            CardinalDirection cd = CardinalDirection.West;
                            Wall wall = new Wall(x, y, cd);
                            cells.deleteWall(wall);
                            multi[y][x-1] = multi[y][x];
                        }
                    }
                }
            }
            else {
                for (int x = 1; x<width; x++) {
                    if ( multi[y][x] != multi[y][x-1] ) {
                        CardinalDirection cd = CardinalDirection.West;
                        Wall wall = new Wall(x, y, cd);
                        cells.deleteWall(wall);
                        multi[y][x-1] = multi[y][x];
                    }
                }
            }


            /**
             * For each set, randomly create vertical connections downward to the next row.
             * Each remaining set must have at least one vertical connection.
             * The cells in the next row thus connected must share the set of the cell above them.
             */

            if ( y != height - 1) {
                ArrayList<Integer> numList = new ArrayList<Integer>();

                for ( int x = 0; x<width; x++ ) {
                    //int u = multi[y][x];
                    if (!numList.contains(multi[y][x])) {
                        numList.add(multi[y][x]);
                        CardinalDirection cds = CardinalDirection.South;
                        Wall wall = new Wall(x, y, cds);
                        cells.deleteWall(wall);
                        multi[y+1][x] = multi[y][x];
                    }
                    else {
                        int w = random.nextIntWithinInterval(0, 2);
                        if ( w == 0 ) {
                            CardinalDirection cds = CardinalDirection.South;
                            Wall wall = new Wall(x, y, cds);
                            cells.deleteWall(wall);
                            multi[y+1][x] = multi[y][x];
                        }
                    }
                }
            }

        }

    }
}



