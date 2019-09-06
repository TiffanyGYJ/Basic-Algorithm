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
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER nodes
     *  2. 2D_INTEGER_ARRAY edges
     */

    public static List<Integer> lay_cable(int nodes, List<List<Integer>> edges) {
        
        int N = nodes;
        int E = edges.size();
        List<Node> nList = new ArrayList<Node>();
        
        for(int i = 0; i < N; i++){
            Node add = new Node(i+1);
            nList.add(add);
        }

        for(int i = 0; i < E; i++){
            int sta = edges.get(i).get(0);
            Node st = nList.get(sta-1);
            int fin = edges.get(i).get(1);
            Node fi = nList.get(fin-1);
            int wei = edges.get(i).get(2);
            
           st.addNeighbor(new Edge(st, fi, wei));
            fi.addNeighbor(new Edge(fi, st, wei));
        }
                          
        Dijkstra(nList.get(0));
        
        List<Integer> result = new ArrayList<Integer>();
        for(int i = 1; i < nList.size(); i++){
            result.add(nList.get(i).distance);
        }
        
        return result;                
}
        
    public static void Dijkstra(Node source){
        
        source.distance = 0;
        PriorityQueue<Node> pQ = new PriorityQueue<Node>();
        pQ.add(source);
        source.visited = true;
        
        while(!pQ.isEmpty()){
            Node cur = pQ.poll();
            
            for(Edge e:cur.adjList){
                Node f = e.finish;
                if(!f.visited){
                    int dis = cur.distance + e.weight;
                    if(dis < f.distance){
                        //update f
                        pQ.remove(f);
                        f.distance = dis;
                        pQ.add(f);
                    }
                }
                
            }
            
            cur.visited = true;
        }
        
    }
    
}
                        

class Node implements Comparable<Node>{
    
    public int name;
    public List<Edge> adjList;
    public boolean visited;
    public int distance;
    
    public Node(int inname){
        this.name = inname;
        this.distance = Integer.MAX_VALUE;
        this.adjList = new ArrayList<Edge>();
    }

    public void addNeighbor(Edge e){
        this.adjList.add(e);
    }
    
    public boolean isVisited(){
        return this.visited;
    }
    
    @Override
    public int compareTo(Node in) {
        return Integer.compare(this.distance, in.distance);
    }
    
    
}


class Edge{
    
    public int weight;
    public Node start;
    public Node finish; 
    
    public Edge(Node s, Node f, int w){
        this.start = s;
        this.finish = f;
        this.weight = w; 
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

        List<Integer> costs = Result.lay_cable(n, edges);

        bufferedWriter.write(
            costs.stream()
                .map(Object::toString)
                .collect(joining(" "))
            + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
