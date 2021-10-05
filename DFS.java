
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS {
    private static final int[][] Moves = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Coordinate> solve(Maze maze) {
        List<Coordinate> path = new ArrayList<>();
        if (explore(maze, maze.getStart().getX(),maze.getStart().getY(),path))
            {return path;}
        return Collections.emptyList();
    }

    private boolean explore(Maze maze, int row, int col, List<Coordinate> path) {
        if (!maze.isValid(row, col) || maze.isWall(row, col) || maze.wasExplored(row, col))
            return false;

        path.add(new Coordinate(row, col));
        maze.setVisited(row, col, true);

        if (maze.isEnd(row, col))
            return true;

        for (int[] direction : Moves) 
            {Coordinate coordinate = getNext(row, col, direction[0], direction[1]);
            if (explore(maze, coordinate.getX(), coordinate.getY(), path))
                return true;}

        path.remove(path.size() - 1);
        return false;
    }

    private Coordinate getNext(int row, int col, int i, int j) {
        return new Coordinate(row + i, col + j);
    }
}
