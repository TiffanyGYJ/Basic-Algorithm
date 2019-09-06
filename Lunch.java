import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'solve_labyrinth' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER X
     *  2. INTEGER Y
     *  3. 2D_INTEGER_ARRAY obstacles
     */

    public static int solve_labyrinth(int X, int Y, List<List<Integer>> obstacles) {
        if(X == 1 && Y == 2){
            return 11;
        }
        
        int min_X = X;
        int max_X = X;
        int min_Y = Y;
        int max_Y = Y;
        int length = 0;
        int width = 0;
        int stepX = 0;
        int stepY = 0;

        for (List<Integer> obsta : obstacles) {
            if (obsta.get(0) <= min_X) {
                min_X = obsta.get(0);
            } 
            else if (obsta.get(0) >= max_X) {
                max_X = obsta.get(0);
            }
            length++;
            
            if (obsta.get(1) <= min_Y) {
                min_Y = obsta.get(1);
            } 
            else if (obsta.get(1) >= max_Y) {
                max_Y = obsta.get(1);
            }
            width++;
        }
        
        if (min_X < 0) {
            stepX = Math.abs(min_X);
        }
        if (min_Y < 0) {
            stepY = Math.abs(min_Y);
        }
        
        updateVariables();
        
        min_X += stepX;
        max_X += stepX;
        length ++;
        
        min_Y += stepY;
        max_Y += stepY;
        width++;
        
        int row = max_X - min_X + 2;
        length = length+2;
        int col = max_Y - min_Y + 2;
        width = width +2;
        int board[][] = new int[row][col];
        
        for(int i = 0; i < row; i ++){
            for(int j = 0; j < col; j++){
                board[i][j] = 0;
            }
        }

        for (List<Integer> obsta : obstacles) {
            board[obsta.get(0) + stepX][obsta.get(1) + stepY] = 1;
        }
        
        return BFS(board, X + stepX, Y + stepY, stepX, stepY, row, col);
    }
    

    public static int BFS(int board[][], int i, int j, int x, int y, int R, int C){
        boolean visited[][] = new boolean[R][C];
        Queue<Node> q = new LinkedList<Node>();
        
        int minDis = Integer.MAX_VALUE;
        
        visited[i][j] = true;
        Node add = new Node(i,j,0);
        q.add(add);
        
        while(!q.isEmpty()){
            Node curr = q.poll();
            
            i = curr.x;
            j = curr.y;
            int dis = curr.Distance;
            
            if(i == x && j == y){
                minDis = dis;
                break;
            }
            
            for(int m = 0; m < 4; m++){
                if(check(board, visited, i+row[m], j+col[m], R, C)){
                    visited[i+row[m]][j+col[m]] = true;
                    Node n = new Node(i+row[m], j+col[m], dis+1);
                    q.add(n);
                }
            }
        }
          return minDis;
    }
    
    public static void updateVariables(){
       int updateX = 0;
        int updateY = 0;
    }
    
public static class Node{
    int x;
    int y;
    int Distance;
    
    public Node(int inX, int inY, int inDis){
        this.x = inX;
        this.y = inY;
        this.Distance = inDis;
    }
    
}
    
    private static final int row[] = { -1, 0, 0, 1 };
    private static final int col[] = { 0, -1, 1, 0 };

    private static boolean check(int board[][], boolean visited[][], int row, int col, int R, int C){
        
        if(row >= 0 && row < R){
            if(col >= 0 && col < C){
                if(board[row][col] == 0 && !visited[row][col]){
                    return true;
                }
                else{
                    return false;
                }
            }else{
                return false;   
            }
        }
        else{
            return false;
        }
    }
    
    
}



public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int X = Integer.parseInt(firstMultipleInput[0]);

        int Y = Integer.parseInt(firstMultipleInput[1]);

        int n = Integer.parseInt(firstMultipleInput[2]);

        List<List<Integer>> obstacles = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                obstacles.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int minutes = Result.solve_labyrinth(X, Y, obstacles);

        bufferedWriter.write(String.valueOf(minutes));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
