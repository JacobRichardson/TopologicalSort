/* 
 * A3 P2 Topological Sort 
 *  
 * Jacob Richardson 
 * Assignment 3 Part 1 
 * Assigned Date: March 28, 2019 
 * Due Date: April 4, 2019 
 * Submission Date: April 4, 2019 
 */ 

package A3P2;
 
//Imports 
import java.io.*;
import java.util.*;

public class TopoSortRichardson 
{
	//Class variables.
	private static Scanner sc;
	private static int numVertices;
	private static LinkedList<Integer> adjLists[];
	private static int  inDegree[];
	private static PrintWriter writer;
	
	/**
	 * @description This method reads the data form a file, creates an adjaceny list from the data, and constructs
	 * an in degree array.
	 * @param dataFile
	 * @throws FileNotFoundException 
	 */
	public static void createDiGraph(File dataFile) throws FileNotFoundException 
	{
		
		//Create a new scanner on the file.
		sc = new Scanner(dataFile);

		//Variable for if the first line has been read or not.
		Boolean readFirstLine = false;
		
		//Whiel there is a next line.
		while(sc.hasNextLine())
		{
			
			//If we have not read the first line.
			if(!readFirstLine)
			{
				
				//Grab the number of nodes.
				numVertices = sc.nextInt();
				
				//Create the list to be the size of the number of vertices plus one.
				adjLists = new LinkedList[numVertices + 1];
				
				//Create the in degree array to be the size of the number of vertices plus one.
				inDegree = new int [numVertices + 1];
				
				//For i less than num vertices.
				for(int i=1; i< numVertices + 1; i++) 
				{
					//Create a new linked list in the array.
					adjLists[i] = new LinkedList<Integer>();
				}
				
				//Set read first line to be true.
				readFirstLine = true;
			}
			else 
			{
		
				//Store the next two ints.
				int i = sc.nextInt();
				int j = sc.nextInt();
				
				//Increment the indegree of the j'th node by 1.
				inDegree[j]++;
				
				//Check to see that the edge is not already in the list.
				if(!adjLists[i].contains(j)) 
				{
					//Add the edge to the list.
					adjLists[i].add(j);
				}
			}
			
			//If there is another line.
			if(sc.hasNextLine()) 
			{
				//Move to the next line.
				sc.nextLine();
			}

		}
	}
	
	/**
	 * @description This 
	 * @param dataFile
	 */
	public static void sort(File dataFile) 
	{
		
		//Create a stack.
		Stack<Integer> s = new Stack<Integer>(); 
		
		//For u less than the length of inDegree.
		for(int u=1; u<inDegree.length; u++) 
		{
			//If the degree of u is zero.
			if(inDegree[u] == 0)
			{
				//Push u into the stack.
				s.push(u);
			}
		}
		
		//Set i to 1.
		int i = 1;
		
		//Temporary variable for the nodes.
		int u;
		
		//While the stack is not emtpy.
		while(!s.isEmpty())
		{
			//u equals the element ontop of the stack.
			u = s.pop();
			
			//For each vertex that is adjacent to u.
			for(int v  : adjLists[i])
			{
				//Decrement the in degree of the vertex by 1.
				inDegree[v]--;
				
				//If the in degree of the vertex is 0.
				if(inDegree[v] == 0)
				{
					//push the vertex onto the stack.
					s.push(v);
				}
			}
			
			//Increment i.
			i++;
			
			//Write the node to the output file.
			writer.print(u + " ");
			
		}
		
		//Check to see if i is greater than the number of vertices.
		if(i > numVertices)
		{
			//Write
			writer.println();
		}
		else
		{
			//Print the graph is cyclic.
			writer.println("The graph is cyclic.");
		}
		
		//Print a new line for formatting purposes.
		writer.println("\n");
		
	}
	
	/**
	 * @description This is the main method of the class. 
	 * @param args[] Command line arguments.
	 */
	public static void main (String args[]) throws FileNotFoundException
	{
		//If there is atleast one argument passed in.
		if(args.length < 1) 
		{
			//Print error message.
			System.out.println("One argument must be provided");
			
			//Exit with a non-zero exist sttatus.
			System.exit(1);
		}
		
		//Create the writer and outputfile.
		 writer = new PrintWriter(new FileOutputStream("output.txt", true));
		
		 //Allowing for multiple files to be passed into the program.
		 for(int i=0; i<args.length; i++) 
		 {
			//Write header.
			 writer.println("Topological sort result for input file: " + args[i]);
			 
			//Call createDiGraph.
			createDiGraph(new File(args[i]));
					
			//Call sort.
			sort(new File(args[i]));
		 }
		
		//Close the writer;
		writer.close();
	}
}
