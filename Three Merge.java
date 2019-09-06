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
import java.util.ArrayList;
import java.util.List;

class Result {

    /*
     * Complete the 'three_merge' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY input_list as parameter.
     */

    
    public static List<Integer> three_merge(List<Integer> input_list) {
        if (input_list.size() <= 1){
            return input_list;
        }
        
        List<Integer> firstPart = new ArrayList<Integer>();
        List<Integer> secondPart = new ArrayList<Integer>();
        List<Integer> thirdPart = new ArrayList<Integer>();
        
        firstPart = input_list.subList(0,input_list.size()/3);
        secondPart = input_list.subList(input_list.size()/3, input_list.size()*2/3);
        thirdPart = input_list.subList(input_list.size()*2/3, input_list.size());
/*        
        for (int element: input_list) {
            if (input_list.indexOf(element)<(input_list.size()/3)) {
                firstPart.add(element);
            }
            else if (input_list.indexOf(element)>=(input_list.size()*2/3)){
                thirdPart.add(element);
            }
            else {
                secondPart.add(element);
            }
        }
        */
        
        List<Integer> newList = new ArrayList<Integer>();
        List<Integer> tempList = new ArrayList<Integer>();
        
        firstPart = three_merge(firstPart);
        secondPart = three_merge(secondPart);
        thirdPart = three_merge(thirdPart);
        
        //CALL MERGE METHOD
        tempList = merge(firstPart,secondPart);
        newList = merge(tempList, thirdPart);
      
        return newList;
    }    
    
    public static List<Integer> merge(List<Integer> firstHalf, List<Integer> secondHalf){
        
        List<Integer>outputList = new ArrayList<Integer>();
        List<Integer> mergedList = new ArrayList<Integer>();
        
        int i=0;
        int j=0;
        
    //merge the first two
        while ((i<=firstHalf.size()-1)&&(j<=secondHalf.size()-1)) {
            if (firstHalf.get(i).compareTo(secondHalf.get(j))<0) {        
                mergedList.add(firstHalf.get(i));
                i++;
            }
            else {
                mergedList.add(secondHalf.get(j));
                j++;
            }
        }
        
        
        if (i==firstHalf.size()) {
            List<Integer> temp = new ArrayList<Integer>(secondHalf.subList(j,secondHalf.size()));
            mergedList.addAll(temp);
        }
        else {
            List<Integer> temp = new ArrayList<Integer>(firstHalf.subList(i,firstHalf.size()));
            mergedList.addAll(temp);
        }

      return mergedList;
    } 
}


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> unsorted_list = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> sorted_list = Result.three_merge(unsorted_list);

        bufferedWriter.write(
            sorted_list.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
