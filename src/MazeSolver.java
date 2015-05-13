import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Assumption: Every maze is well formed and solvable

public class MazeSolver {

    private String input;
    private char[][] maze;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int mazeHeight;
    private int mazeWidth;

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java -jar JuniferMaze.jar path/to/input.txt");
            System.exit(1);
        }
        MazeSolver mazeSolver = new MazeSolver(args[0]);
        mazeSolver.solveMaze();
    }

    public MazeSolver(String filePath) throws IOException {
        input = (new String(Files.readAllBytes(Paths.get(filePath))));
        parseMazeParameters();
        stripMazeInput();
        parseMazeInput();
    }

    public void solveMaze() {
        breadthFirstSearch();
        printMaze();
    }

    private void stripMazeInput() {
        input = input.replaceAll(" ", "");
        for (int i = 0; i < 3; i++) {
            input = input.substring(input.indexOf('\n') + 1);
        }
        input = input.replaceAll("\r", "");
        input = input.replaceAll("\n", "");
    }

    private void parseMazeParameters() {
        String[] lines = input.split("\r\n");
        String[] firstLine = lines[0].split(" ");
        mazeHeight = Integer.parseInt(firstLine[0]);
        mazeWidth = Integer.parseInt(firstLine[1]);
        maze = new char[mazeWidth][mazeHeight];
        String[] secondLine = lines[1].split(" ");
        startY = Integer.parseInt(secondLine[1]);
        startX = Integer.parseInt(secondLine[0]);
        String[] thirdLine = lines[2].split(" ");
        endY = Integer.parseInt(thirdLine[0]);
        endX = Integer.parseInt(thirdLine[1]);
    }

    private void parseMazeInput() {
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                maze[x][y] = charAtPos(x, y) == '1' ? '#' : ' ';
            }
        }
    }

    private char charAtPos(int x, int y) {
        return input.charAt(y * mazeWidth + x);
    }

    private boolean isStartPos(int x, int y) {
        return (x == startX && y == startY);
    }

    private boolean isEndPos(int x, int y) {
        return (x == endX && y == endY);
    }

    private void printMaze() {
        for (int y = 0; y < mazeHeight; y++) {
            for (int x = 0; x < mazeWidth; x++) {
                if (isStartPos(x, y)) {
                    System.out.print('S');
                }
                else if (isEndPos(x ,y)) {
                    System.out.print('E');
                }
                else {
                    System.out.print(maze[x][y]);
                }
                if (x == mazeWidth - 1) {
                    System.out.println();
                }
            }
        }
    }

    // http://en.wikipedia.org/wiki/Breadth-first_search
    private void breadthFirstSearch() {
        HashSet<Tuple> discovered = new HashSet<Tuple>();
        Hashtable<Tuple, Tuple> nodeDiscovered = new Hashtable<Tuple, Tuple>();
        Queue<Tuple> queue = new LinkedList<Tuple>();
        Tuple start = new Tuple(startX, startY);
        Tuple end = new Tuple(endX, endY);
        queue.add(start);
        discovered.add(start);
        while (!queue.isEmpty() && !discovered.contains(end)) {
            Tuple next = queue.poll();
            for (Tuple neighbour : pointNeighbours(next.x, next.y)) {
                if (!discovered.contains(neighbour)) {
                    queue.add(neighbour);
                    discovered.add(neighbour);
                    nodeDiscovered.put(neighbour, next);
                }
            }
        }
        try {
            Tuple next = nodeDiscovered.get(end);
            while (true) {
                maze[next.x][next.y] = 'X';
                if (nodeDiscovered.contains(next) && nodeDiscovered.get(next) != start) {
                    next = nodeDiscovered.get(next);
                } else {
                    break;
                }
            }
        }
        catch (Exception e) {
            System.err.println("No Path Found - Maze Impossible");
        }
    }

    private ArrayList<Tuple> pointNeighbours(int x, int y) {
        ArrayList<Tuple> neighbours = new ArrayList<Tuple>();
        if (y != 0 && maze[x][y - 1] != '#') {
            neighbours.add(new Tuple(x, y - 1));
        }
        if (x != 0 && maze[x - 1][y] != '#') {
            neighbours.add(new Tuple(x - 1, y));
        }
        if (y != mazeHeight - 1 && maze[x][y + 1] != '#') {
            neighbours.add(new Tuple(x, y + 1));
        }
        if (x != mazeWidth - 1 && maze[x + 1][y] != '#') {
            neighbours.add(new Tuple(x + 1, y));
        }
        return neighbours;
    }



}
