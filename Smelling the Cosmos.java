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
     * Complete the 'smellCosmos' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER_ARRAY b
     */
      
    public static List<Integer> smellCosmos(List<Integer> a, List<Integer> b) {

        int length = a.size();  
        
        //Base Case
        if((a.size()==1) || (b.size()==1)){
            //Multiply two numbers
            List<Integer> result = new ArrayList<>(Arrays.asList((a.get(0))*(b.get(0))));
            return result;
        }
        
        List<Integer> ahigh = new ArrayList<Integer>();
        List<Integer> alow = new ArrayList<Integer>();
        List<Integer> bhigh = new ArrayList<Integer>();
        List<Integer> blow = new ArrayList<Integer>();
        
        //Split
        ahigh = a.subList(0,a.size()/2);
        alow = a.subList(a.size()/2, a.size());
        bhigh = b.subList(0,b.size()/2);
        blow = b.subList(b.size()/2, b.size());
        
        
        List<Integer> asum = new ArrayList<Integer>();
        List<Integer> bsum = new ArrayList<Integer>();
        
        for(int i=0; i < ahigh.size(); i++){      
            asum.add(ahigh.get(i)+alow.get(i));
            bsum.add(bhigh.get(i)+blow.get(i));
        }
        
        List<Integer> c0 = new ArrayList<Integer>();
        List<Integer> c1 = new ArrayList<Integer>();
        List<Integer> c01 = new ArrayList<Integer>();
        
        //Recursion
        c0 = smellCosmos(ahigh, bhigh);
        c1 = smellCosmos(alow, blow);
        c01= smellCosmos(asum, bsum);
        
        List<Integer> subC = new ArrayList<Integer>();
            
        for(int i=0; i <length-1; i++){
            int sub;
            sub = c01.get(i) - c1.get(i) - c0.get(i); 
            subC.add(sub);
        }
        
        //Overlay three arrays
        
        for(int g: c0){
            System.out.print("c0 "+g+" ");
        }
        for(int k: subC){
            System.out.print("subC "+k+" ");
        }
        for(int e: c1){
            System.out.print("c1 "+e+" ");
        }
        
        
        List<Integer> C = new ArrayList<Integer>();  
        
        C.addAll(c0);  
        int count=0;
        for(int i= subC.size()/2+1; i< c0.size(); i++){
            C.set(i, C.get(i)+subC.get(count));  
            count++;
        }
        
        for(int i = subC.size()/2; i < subC.size(); i++){
            C.add(subC.get(i));
        }
        
        int count2=0;
        for(int i = c0.size()+1; i<c1.size()+c0.size()+1; i++){
            if(i<(c0.size()+1+(subC.size()/2))){
              C.set(i, C.get(i)+c1.get(count2));  
                count2++;
            }
            else{
                C.add(c1.get(count2));
                count2++;
            }
          
        }
     
        System.out.println();
        return C;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<Integer> a = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> b = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
            .map(Integer::parseInt)
            .collect(toList());

        List<Integer> result = Result.smellCosmos(a, b);

        bufferedWriter.write(
            result.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
