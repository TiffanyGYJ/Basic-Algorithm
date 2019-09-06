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
     * Complete the 'lay_cable' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER nodes
     *  2. 2D_INTEGER_ARRAY edges
     */

    public static int lay_cable(int nodes, List<List<Integer>> edges) {
        
        Graph g = new Graph(nodes, edges);
        
        int res = g.Kruskal();
        
        return res;
    }
}
    
class Graph{
    
    int N; // cities
    int M; // edges
    Edge[] edge;
    
    public int Kruskal(){
        int minC = 0;
        
        //Make-Sets for each Node
        subset[] subsetArr = new subset[N+1];
        for(int i = 0; i < subsetArr.length; i++){
            subsetArr[i] = new subset(i, 0);
        }
        
        //sort edge list by ascending order
        Arrays.sort(edge);

        int count = 0;
        int traceN = 0;
        
        while(traceN < N-1){
//            System.out.println("count "+count);
//            System.out.println("array length "+edge.length);
            Edge next = edge[count];
            count++;
            
            int u = find(subsetArr, next.start);
            int v = find(subsetArr, next.finish);
            
            if(u != v){
                traceN++;
                minC += next.cost;
                Union(subsetArr, u, v);
            }
            
        }
        
        return minC;
        
    }
    
    
    public Graph(int n, List<List<Integer>> input){
        N = n;
        M = input.size();
        
        edge = new Edge[M];
        
        for(int i = 0; i < M; i++){
            List<Integer> temp = input.get(i);
            edge[i] = new Edge(temp.get(0), temp.get(1), temp.get(2));
        }
    }
    
    //Recursively find the parent of an element
    public int find(subset[] subsetArr, int element){
        if(subsetArr[element].parent != element){
            subsetArr[element].parent = find(subsetArr, subsetArr[element].parent);
        }
//        System.out.println("element "+element);
//        System.out.println("find parent "+subsetArr[element].parent);
        return subsetArr[element].parent;
    }
    
    public void Union(subset[] subsetArr, int x, int y){
//        System.out.println("union ");
        int xRep = find(subsetArr, x);
        int yRep = find(subsetArr, y);
        
        //Attach smaller priority to larger priority
        if(subsetArr[xRep].priority > subsetArr[yRep].priority){
            subsetArr[yRep].parent = xRep;
        }
        else if(subsetArr[xRep].priority < subsetArr[yRep].priority){
            subsetArr[xRep].parent = yRep;
        }
        //If same rank, randomly pick one
        else{
            subsetArr[xRep].parent = yRep;
            subsetArr[yRep].priority++;
        }
        
    }
    
    
    class subset{
        int parent;
        int priority;
        
        public subset(){
            
        }
        
        public subset(int p, int pr){
            this.parent = p;
            this.priority = pr;
        }
    }
    
    class Edge implements Comparable<Edge>{
        int start;
        int finish;
        int cost;
        
        public Edge(int st, int fi, int co){
            this.start = st;
            this.finish = fi;
            this.cost = co;
        }
        
        public Edge(){
            
        }
        
/*        public void printEdge(){
            System.out.println(this.cost+","+ this.start+","+this.finish);
        }
*/        
        public int compareTo(Edge a){
            if(this.cost > a.cost){
                return 1;
            }
            else if(this.cost < a.cost){
                return -1;
            }
            else{
                return 0; 
            }
        }
    
    }
    
    
}
   


public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> edges = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                edges.add(
                    Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        int cost = Result.lay_cable(n, edges);

        bufferedWriter.write(String.valueOf(cost));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
