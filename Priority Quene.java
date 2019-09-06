import java.io.*;
import java.util.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Solution{

	public static ArrayList<Student> createPriorityQueue() {
		ArrayList<Student> heapList = new ArrayList<Student>();
        Student zero = new Student();
        heapList.add(zero);
		return heapList;
	}

	public static void addStudent(ArrayList<Student> priority_queue, String string, double gpa) {
		Student inStudent = new Student(string, gpa);
        if(priority_queue.size()== 1){
            priority_queue.add(inStudent);
        }
        else{
            Student negative = new Student(-500.76);
            priority_queue.add(negative);
            Heap_increase_key(priority_queue, priority_queue.size()-1, inStudent);
        }  
	}

	public static String invite(ArrayList<Student> priority_queue) {
		if(priority_queue.size() == 0){
            return "empty";
        }
        else{
            Student highest = priority_queue.get(1);
            int last = priority_queue.size()-1;
            priority_queue.set(1, priority_queue.get(last));
            priority_queue.remove(last);
                               
            max_heapify(priority_queue, 1);
            return highest.id;
        }
	}

    public static void Heap_increase_key(ArrayList<Student> studentL, int i, Student inStudent){
        if(inStudent.gpa < studentL.get(i).gpa){
            System.out.println("input gpa less than 0");
        }
        else{
            studentL.set(i, inStudent);
            while((i > 1) && (studentL.get(i/2).gpa < studentL.get(i).gpa)){
                Collections.swap(studentL, i/2, i);
                i = i/2;
            }
            
        }
    }

    public static void max_heapify(ArrayList<Student> studentL, int i){
        int N = studentL.size()-1;
        int left = 2*i;
        int right = 2*i +1;
        int largest;
        
        if((left <= N) && (studentL.get(left).gpa > studentL.get(i).gpa)){
            largest = left;
        }
        else{
            largest = i;
        }
        
        if((right <= N) && (studentL.get(right).gpa > studentL.get(largest).gpa)){
            largest = right;
        }
        
        if(largest != i){
            Collections.swap(studentL, i, largest);
            max_heapify(studentL, largest);
        }
    }
   
    
   public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> inputs = IntStream.range(0, n).mapToObj(i -> {
            try {
                return bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(x -> x != null).map(Object::toString).collect(toList());

        ArrayList<Student> priority_queue = createPriorityQueue();

        int invites = 0;
        for (String line : inputs) {
            String[] parts = line.split(" ");
            if (parts[0].equals("ADD_STUDENT")) {
                addStudent(priority_queue, parts[1], Double.parseDouble(parts[2]));
            } else {
                invite(priority_queue);
                invites += 1;
            }
        }
        bufferedWriter.write(IntStream.range(0, n - 2 * invites).mapToObj(i -> invite(priority_queue))
                .map(Object::toString).collect(joining("\n")) + "\n");

        bufferedReader.close();
        bufferedWriter.close();
    }  
}

class Student {

    String id;
    double gpa;
    
    public Student(){} 
    
    public Student(double negative){
        this.gpa = negative;
    }
    
    public Student(String inid, double ingpa){
        this.id = inid;
        this.gpa = ingpa;
    }
}
     