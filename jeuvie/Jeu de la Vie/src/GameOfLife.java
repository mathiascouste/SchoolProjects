/**
 * Created by Hakim on 07/04/14.
 */
public class GameOfLife {

    public static void main(String argv[]) {
        Grid grid = new Grid(5, 5);

        for(Cell c : grid.getCell(0, 1).getNeighbours()) {
            System.out.println(c);
        }

        grid.start();
    }
}
