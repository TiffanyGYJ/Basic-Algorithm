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
     * Complete the 'catch_achilles' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER T
     *  2. INTEGER A
     */

    public static int catch_achilles(int T, int A) {
        HashMap<Integer, Node> graph = new HashMap<Integer, Node>();  
        Queue<Node> q = new LinkedList<Node>();
        
        Node root = new Node(T,null);
        root.depth = 0;
        graph.put(0, root);
        q.add(root);
        
        int key = 1;
        
        while(!q.isEmpty()){
            
            Node curr = q.poll();
            int currDis = curr.value;
            if(!q.contains(currDis*2)){
            Node twoL = new Node(currDis*2, curr);
            if(twoL.value == A){
                graph.put(-1, twoL);
                break;
            }
            else if(twoL.value < A+2){
                graph.put(key++, twoL);
                q.add(twoL);
            }
            }
            
            if(!q.contains(currDis+1)){
            Node plus = new Node(currDis+1, curr);
            if(plus.value == A){
                graph.put(-1, plus);
                break;
            }
            else{
                graph.put(key++, plus);
                q.add(plus);
            }
            }
            
            if(!q.contains(currDis-1)){
            Node minus = new Node(currDis-1, curr);
            if(minus.value == A){
                graph.put(-1, minus);
                break;
            }
            else if(minus.value > -2){
                graph.put(key++, minus);
                q.add(minus);
            }
            }

        }
        
        return graph.get(-1).depth;
    }

}

class Node {
    //String label;
    int depth;
    int value;
    Node parent;
    //ArrayList<Node> adjacencyList;
    
    public Node(int invalue, Node par){
        this.value = invalue;
        if(par!=null){
            this.depth = par.depth +1;    
        }
        this.parent = par;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int T = Integer.parseInt(firstMultipleInput[0]);

        int A = Integer.parseInt(firstMultipleInput[1]);

        int mins = Result.catch_achilles(T, A);

        bufferedWriter.write(String.valueOf(mins));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
