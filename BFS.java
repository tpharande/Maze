
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BFS {
    private static final int[][] Moves = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Coordinate> solve(Maze maze) {
        LinkedList<Coordinate> next = new LinkedList<>();
        Coordinate start = maze.getStart();
        next.add(start);

        while (!next.isEmpty()) {
            Coordinate c = next.remove();

            if (!maze.isValid(c.getX(), c.getY()) || maze.wasExplored(c.getX(), c.getY()))
                continue;

            if (maze.isWall(c.getX(), c.getY())) 
                {maze.setVisited(c.getX(), c.getY(), true);
                continue;}

            if (maze.isEnd(c.getX(), c.getY())) 
                return backtrack(c);

            for (int[] direction : Moves) 
                { 
                Coordinate coordinate = new Coordinate(c.getX() + direction[0], c.getY() + direction[1], c);
                next.add(coordinate);
                maze.setVisited(c.getX(), c.getY(), true);}
        }
        return Collections.emptyList();
    }

    private List<Coordinate> backtrack(Coordinate c) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate iter = c;

        while (iter != null) {
            path.add(iter);
            iter = iter.parent;}

        return path;
    }
}