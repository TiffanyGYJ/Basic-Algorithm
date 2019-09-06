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
     * Complete the 'race_distance' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY D
     *  2. INTEGER m
     */

  public static int race_distance(List<Integer> D, int m) {         
         int n = D.size();
         int[][] grid = new int[n][m+1]; 
         
         //populate the first row in the grid
         for(int j = 1; j < m; j++){
             grid[0][j] = 0;
         }
         grid[0][1] = D.get(0);
         
         ArrayList<Integer> diagonal;
         
         //i represent time period
         //j represent exhaustion level
         for(int i = 1; i < n; i++){
             for(int j = 0; j <= m; j++){
                     //if exhaustion level is not 0, follow the pattern
                     if(j != 0){
                         grid[i][j] = grid[i-1][j-1] + D.get(i);
                     }
                    //if exhaustion level is 0, find the max in the diagonal
                    //then compare it with the one right below it
                     else{
                         diagonal = new ArrayList<Integer>();
                         if(i < m){
                             for(int k = 1; k <= i; k++){
                                 diagonal.add(grid[i-k][k]);
                             }
                         }
                         else{
                             for(int k = 1; k <= m; k++){
                                 diagonal.add(grid[i-k][k]);
                             }
                         }
                         //the one below
                         diagonal.add(grid[i-1][0]);
                         
                         grid[i][j] = Collections.max(diagonal);
                     }
                     
                 }
             }
         
         
        return grid[n-1][0];
         }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> D = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        int distance = Result.race_distance(D, m);

        bufferedWriter.write(String.valueOf(distance));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}