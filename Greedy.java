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
     * Complete the 'schedule_race' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts 2D_INTEGER_ARRAY races as parameter.
     */

   public static List<Integer> schedule_race(List<List<Integer>> races) {
         //keep tracks of the end time
        ArrayList<Integer> tracks = new ArrayList<Integer>();
        int cars = races.size();
        ArrayList<Integer> output = new ArrayList<Integer>();
        
        //initialize the track with the first car finish time
        tracks.add(races.get(0).get(1));
        output.add(0);
        
        for(int i = 1; i < cars; i++){
            //check if all tracks are full
            if(checkAdd(races,i,tracks)){
                int index = tracks.size();
                tracks.add(races.get(i).get(1));
                output.add(index);
            }
            else{
                //there are empty tracks
                int minIndex = tracks.indexOf(Collections.min(tracks));    
                tracks.set(minIndex, races.get(i).get(1));
                output.add(minIndex);
            }
        }
        
        return output;
         
        }

    private static boolean checkAdd(List<List<Integer>> races, int i, ArrayList<Integer> tracks) {
        // TODO Auto-generated method stub
        boolean full = true;
        int j = 0;
        while(full && j < tracks.size()){
            if(tracks.get(j) > races.get(i).get(0)){
                full = true;
            }
            else{
                full = false;
            }
            j++;
        }
        return full;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> races = new ArrayList<>();

        IntStream.range(0, n).forEach(i -> {
            try {
                races.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        List<Integer> schedule = Result.schedule_race(races);

        bufferedWriter.write(
            schedule.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
