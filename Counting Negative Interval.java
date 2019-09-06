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
     * Complete the 'countIntervals' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts INTEGER_ARRAY li as parameter.
     */

    
    public static long countIntevals(List<Integer> li){
        
        List<Integer> sum = new ArrayList<Integer>();
        sum.add(0);
        
        int temp = 0;
        for(int i = 0; i < li.size(); i ++){
            temp += li.get(i);
            sum.add(temp);
        }
        
        long count = 0;
        for(int i = 0; i < sum.size()-1; i++){
            for(int j = i+1; j < sum.size(); j++){
                if(sum.get(i)>sum.get(j)){
                    count++;
                }
            }
        }
        
        return count;
    }   
  
}
/*    
    public static long countIntervals(List<Integer> li) {
    // Write your code here
        
        for(int in:li){
            System.out.println("input "+in);
        }
        
        //add an 0 in the front of the sum list
        List<Integer> sum = new ArrayList<Integer>();
        sum.add(0);
        
        int temp = 0;
        for(int i = 0; i < li.size(); i ++){
            temp += li.get(i);
            sum.add(temp);
        }
        
        long count = 0;
        
        for(int e:sum){
            System.out.println(e);
        }
        //call merge and pass in count
        modified_mergeSort(count, sum, 0, sum.size()-1);
        
        return count;
    }
    
    public static List<Integer> modified_mergeSort(long inversions, List<Integer> input_list, int left, int right) {
        if (input_list.size() == 1){
            return input_list;
        }
        
        int mid= input_list.size()/2;
            
        List<Integer> firstPart = new ArrayList<Integer>();
        List<Integer> secondPart = new ArrayList<Integer>();
        
        firstPart = input_list.subList(0,input_list.size()/2);
        secondPart = input_list.subList(input_list.size()/2, input_list.size());
        
        List<Integer> newList = new ArrayList<Integer>();
        
        firstPart = modified_mergeSort(inversions,firstPart, left, left+(mid-1));
        secondPart = modified_mergeSort(inversions,secondPart, left+mid, right);
        
        //CALL MERGE METHOD
        newList = merge(inversions,firstPart, left, left+(mid-1), secondPart,left+mid, right);

        return newList;
    }    
    
    public static List<Integer> merge(long inversions, List<Integer> firstHalf, int leftF, int rightF, List<Integer> secondHalf, int leftS, int rightS){
        
        List<Integer>outputList = new ArrayList<Integer>();
        List<Integer> mergedList = new ArrayList<Integer>();
        
        int newleft = Math.min(leftF, leftS);
        
        int i=0;
        int j=0;
        
    //merge the first two
        while ((i<=firstHalf.size()-1)&&(j<=secondHalf.size()-1)) {
            if (firstHalf.get(i).compareTo(secondHalf.get(j))<0) {        
                mergedList.add(firstHalf.get(i));
                int test = i+leftF-(mergedList.indexOf(firstHalf.get(i)))-newleft;
                if(test>0){
                    inversions += test; 
System.out.println("inversions "+inversions);
                }
                i++;
            }
            else {
                mergedList.add(secondHalf.get(j));
                int test = j+leftS-(mergedList.indexOf(secondHalf.get(i)))-newleft;
                if(test>0){
                    inversions += test;
System.out.println("inversions "+inversions);
                }
                j++;
            }
        }
     
        
    //copy the remaining elements in the array
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

*/
    
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> arr = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine().replaceAll("\\s+$", "");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        })
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(toList());

        long result = Result.countIntevals(arr);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
