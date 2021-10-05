
import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingUtilities;
import java.util.Arrays;

public class MazeDriver extends JFrame{
    public static int[][] printbfs, printdfs;
    public static boolean[][] bfsvisited, dfsvisited;
    public static double startbfs,endbfs,startdfs,enddfs;
    public static int checkfile = 1;
    public static int shift = 550, set = 50, length = 1050, height = 725, textbfsl = 180, textdfsl = 730, textheight = 635;

     public MazeDriver(){
        setTitle("Maze");
        setSize(length,height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Color color;
        int countbfs = 0,countdfs = 0;

        for (int row = 0; row<printbfs.length;row++){
            for (int col = 0; col<printbfs[0].length;col++){
                switch (printbfs[row][col]){
                    case 0 : {
                        if(bfsvisited[row][col])
                            {color = Color.ORANGE; countbfs++;}
                        else
                            color = Color.WHITE; break;}
                    case 1 : color = Color.BLACK; break;
                    case 2 : {color = Color.GREEN; countbfs++; break;}
                    case 3 : {color = Color.RED;  countbfs++; break;}
                    default : {color = Color.BLUE; countbfs++;}
                }
                g.setColor(color);
                g.fillRect(set*col,set*row,set,set);
                g.setColor(Color.BLACK);
                g.drawRect(set*col,set*row,set,set);
                g.setFont(new Font("default", Font.BOLD, 18));

            }
        }
                g.drawString("Breadth First Search", textbfsl, textheight);
                g.drawString((endbfs - startbfs)/1000000 + " milliseconds elapsed", textbfsl - 30, textheight + 25);
                g.drawString("Visited: " + countbfs, textbfsl, textheight + 50);
        for (int r = 0; r<printdfs.length;r++){
            for (int c = 0; c<printdfs[0].length;c++){
               switch (printdfs[r][c]){
                    case 0 : {
                        if(dfsvisited[r][c])
                            {color = Color.ORANGE; countdfs++;}
                        else
                            color = Color.WHITE; break;}
                    case 1 : color = Color.BLACK; break;
                    case 2 : {color = Color.GREEN; countdfs++; break;}
                    case 3 : {color = Color.RED; countdfs++; break;}
                    default : {color = Color.BLUE; countdfs++;}
                }

                

                g.setColor(color);
                g.fillRect(shift+(set*c),set*r,set,set);
                g.setColor(Color.BLACK);
                g.drawRect(shift+(set*c),set*r,set,set);

            }
        }
                g.drawString("Depth First Search", textdfsl, textheight);
                g.drawString((enddfs - startdfs)/1000000 + " milliseconds elapsed", textdfsl - 30, textheight + 25);
                g.drawString("Visited: " + countdfs, textdfsl, textheight + 50);

    }

    private static void searchalg(Maze maze) {
        List<Coordinate> path;
        startbfs = System.nanoTime();
        BFS bfs = new BFS();
        path = bfs.solve(maze);
        printbfs = maze.getPath(path);
        boolean[][] visited = maze.getVisited();
        bfsvisited = new boolean[visited.length][visited[0].length];
        for (int row = 0; row<visited.length;row++){
            for (int col = 0; col<visited[0].length;col++){
                bfsvisited[row][col] = visited[row][col]; }}
        endbfs = System.nanoTime();

        maze.clear();
        
        startdfs = System.nanoTime();
        DFS dfs = new DFS();
        path = dfs.solve(maze);
        printdfs = maze.getPath(path);
        dfsvisited = maze.getVisited();
        enddfs = System.nanoTime();
    }

    public static void main(String[] args) throws Exception {

        File file1 = new File(args[0]);
        String val = args[0];
        if(val.equals("maze2.txt") || val.equals("maze3.txt")){
            shift = 875; set = 30; length = 2050; height = 850;
            textbfsl = 345; textdfsl = 1225; textheight = 725;}
        Maze maze = new Maze(file1);
        searchalg(maze);
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                MazeDriver view = new MazeDriver();
                view.setVisible(true);
            }
        });
    }
}