
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Maze{
    private static final int ROAD = 0, WALL = 1, START = 2, EXIT = 3, PATH = 4;

    private int[][] maze;
    private boolean[][] visited;
    private Coordinate start, end;

    public Maze(File maze) throws FileNotFoundException {
        String fileText = "";
        try (Scanner input = new Scanner(maze)) {
            while (input.hasNextLine()) {
                fileText += input.nextLine() + "\n";
            }
        }
        initializeMaze(fileText);
    }

    private void initializeMaze(String text) {
        if (text == null || (text = text.trim()).length() == 0) {
            throw new IllegalArgumentException("empty lines");
        }

        String[] lines = text.split("[\r]?\n");
        maze = new int[lines.length][lines[0].length()];
        visited = new boolean[lines.length][lines[0].length()];

        for (int row = 0; row < maze.length; row++) {
            if (lines[row].length() != maze[0].length) {
                throw new IllegalArgumentException("line " + (row + 1) + " wrong length (was " + lines[row].length() + " but should be " + maze[0].length + ")");
            }

            for (int col = 0; col < maze[0].length; col++) {
                if (lines[row].charAt(col) == '1')
                    maze[row][col] = WALL;
                else if (lines[row].charAt(col) == '2') {
                    maze[row][col] = START;
                    start = new Coordinate(row, col);
                } else if (lines[row].charAt(col) == '3') {
                    maze[row][col] = EXIT;
                    end = new Coordinate(row, col);
                } else
                    maze[row][col] = ROAD;
            }
        }
    }

    public Coordinate getStart() 
        {return start;}

    public boolean isEnd(int x, int y) 
        {return x == end.getX() && y == end.getY();}

    public boolean isStart(int x, int y) 
        {return x == start.getX() && y == start.getY();}

    public boolean wasExplored(int row, int col) 
        {return visited[row][col];}

    public boolean isWall(int row, int col) 
        {return maze[row][col] == WALL;}

    public void setVisited(int row, int col, boolean value) 
        {visited[row][col] = value;}

    public boolean isValid(int row, int col) 
        {if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length)
            return false;
        return true;}

    public boolean[][] getVisited() {
        //System.out.println("Visited " + Arrays.deepToString(visited));
        return visited;
    }

    public int[][] getPath(List<Coordinate> path) {
        int[][] tempMaze = Arrays.stream(maze)
            .map(int[]::clone)
            .toArray(int[][]::new);
        for (Coordinate coordinate : path) {
            if (isStart(coordinate.getX(), coordinate.getY()) || isEnd(coordinate.getX(), coordinate.getY()))
                continue;
            tempMaze[coordinate.getX()][coordinate.getY()] = PATH;
        }

        return tempMaze;
    }

    public void clear() {
        for (int i = 0; i < visited.length; i++)
            Arrays.fill(visited[i], false);
    }
}