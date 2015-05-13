import org.junit.Test;

import static org.junit.Assert.*;

public class MazeSolverTest {

    @Test
    public void inputTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/input.txt");
        mazeSolver.solveMaze();
    }

    @Test
    public void largeInputTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/large_input.txt");
        mazeSolver.solveMaze();
    }

    @Test
    public void mediumInputTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/medium_input.txt");
        mazeSolver.solveMaze();
    }

    @Test
    public void smallTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/small.txt");
        mazeSolver.solveMaze();
    }

    @Test
    public void sparseMediumTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/sparse_medium.txt");
        mazeSolver.solveMaze();
    }

    @Test
    public void impossibleMazeTest() throws Exception {
        MazeSolver mazeSolver = new MazeSolver("input/impossible.txt");
        mazeSolver.solveMaze();
    }

}