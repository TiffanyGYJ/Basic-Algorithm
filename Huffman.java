/*  
	class Node
		public  int frequency; // the frequency of this tree
    	public  char data;
    	public  Node left, right;
    
*/ 

	void decode(String s, Node root) {
        StringBuffer output = new StringBuffer();
        Node curr = root;
        
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == '0'){
                curr = curr.left;
                if(curr.data != '\u0000'){
                    output.append(curr.data);
                    curr = root;
                }
            }
            else{
                curr = curr.right;
                if(curr.data != '\u0000'){
                    output.append(curr.data);
                    //System.out.println("right child data");
                    curr = root;
                }
            }
        }

        System.out.println(output.toString());
       
    }