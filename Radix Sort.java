import java.io.*;
import java.math.*;
//import java.security.*;
import java.text.*;
import java.util.*;
//import java.util.concurrent.*;
//import java.util.function.*;
//import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'radix_sort' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY input_list
     *  2. INTEGER_ARRAY d_permutation
     */

    
    /*  
     * Radix Binary Sort
     * 1. Get the largest number in the list and convert it to binary to see the max digit
     * 2. Calculate how many digits as a group we are looking at
     * 3. Convert all to binary string arraylist
     * 4. Do counting sort on the string, compare substring convert to int
     */
    
     /*
     * Counting Sort
     * 1. Determine the array length needed by knowing the digit group, either 0-2 or 0-11
     * 2. Count number of occurance in the original array
     * 3. Modify array
     * 4. Match original array to the count array
     */
    
    public static String padLeftZeros(String str, int n) {
        return String.format("%1$" + n + "s", str).replace(' ', '0');
        }
    
    public static List<Integer> radix_sort(List<Integer> input_list, List<Integer> d_permutation) {
        //Get the largest number in the list and calculate the bits used
        int max = Collections.max(input_list);
        String maxB = Integer.toBinaryString(max);
        int bits = maxB.length();
        int d_pSize = d_permutation.size();
        
        double bd = bits;
        double dSd = d_pSize;
        //group is the number of bits we are looking at together
        double groupd = Math.ceil(bd / dSd);
        int group = (int)Math.round(groupd);
    
        String[] inputBinary = new String[input_list.size()];

        //Convert Decimal to Binary with Padding
        int padnum = group*d_pSize;
        for(int i = 0 ; i < input_list.size(); i++){
            String str = padLeftZeros(Integer.toBinaryString(input_list.get(i)),padnum);
            inputBinary[i] = str;
            }
    
        //Counting Sort
    
        //j is the part that is been sorted
        for(int j = 0; j < d_permutation.size(); j ++){ 
                    
            //for comparison, convert back to decimal and compare
            //count array max is 2^n - 1, but in order to have the number, set the length to number+1
            int[] count = new int[(int) Math.pow(2.0, group)];
            Arrays.fill(count,0);
            int[] input = new int[input_list.size()];
            String[] output = new String[input_list.size()];
        
            int read = d_permutation.get(j);
        //count occurrence and populate new matching array
            for(int i = 0; i < inputBinary.length; i ++){
                //get the substring, convert them to binary then to decimal in order to store and sort
                int store;
                
                if(read ==0){
                    int subStartIndex = inputBinary[i].length()-group;
                    store = Integer.parseInt(inputBinary[i].substring(subStartIndex),2);
                }
                else if(read == d_permutation.size()-1){
                    store = Integer.parseInt(inputBinary[i].substring(0, group),2);
                }
                else{
                    int subStartindex = inputBinary[i].length()-((read+1)*group);
                    store = Integer.parseInt(inputBinary[i].substring(subStartindex, subStartindex+group),2);
                }

                //create new input array with input[i] contains the value of inputlist(i) that is sorted at the moment
                input[i] = store;            
                count[store]++;
                }
        
            //change count so that count contain actual position of the output
            for(int i = 1; i < count.length; i++){
                count[i] += count[i - 1];
                }            
                     
            //match count with the output
            for(int i = input.length-1; i >= 0; i-- ){
                //we need to get the original number
                output[ count[input[i]] -1] = inputBinary[i];
                count[input[i]]--;
                }
        
            //copy the sorted array to original
            inputBinary = output.clone();
    }
    
    //finished sorting, need to convert back to decimal arraylist and return
    ArrayList<Integer> outputDecimal = new ArrayList<Integer>();
    for(int i = 0 ; i < inputBinary.length; i++){
        outputDecimal.add(Integer.parseInt(inputBinary[i], 2));
    }
    
    return outputDecimal;
    
    }
}
                                            
        
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int d = Integer.parseInt(bufferedReader.readLine().trim());

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> d_permutation = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> unsorted_list = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> sorted_list = Result.radix_sort(unsorted_list, d_permutation);

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
