package dominio;

import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BS {
	public static void main(String[] args) throws IOException{
		init();

	}
	public static void init() throws IOException {
		int rows =4;
		int cols = 4;	  
		String img1="pics/AlhambraInicialPuzzle4x4.png";
		String img2="pics/IntermedioAlhambra41.png";
		ImgProcessor img= new ImgProcessor(rows,cols,img1,img2);
		int cero[]=img.getZero();
		int pos [][]=img.getPos();
		int org[][]=img.getOrg();
		boolean solution;
		State goal=new State(rows,cols,0,0,org);
		State initialstate= new State(rows,cols,cero[0],cero[1],pos);
		nodeTree firstnode = new nodeTree(initialstate);
		PriorityQueue<nodeTree> frontier = createFrontier();
		insertFrontier(firstnode,frontier);
		solution=false;
		while(solution==false && !frontierIsEmpty(frontier)){
			
		}
		
	}
	public static PriorityQueue<nodeTree> createFrontier(){
		return new PriorityQueue<nodeTree>(new Comparator<nodeTree>() {
			public int compare(nodeTree e1, nodeTree e2) {
				if(e1.getValue() > e2.getValue())
					return 1;
				
				else if(e1.getValue() < e2.getValue())
					return -1;
				
				else
					return 0;
			}

		});
	}

	public static void insertFrontier(nodeTree t, Queue<nodeTree> frontier){
		frontier.add(t);
		}

	public static nodeTree removeFirstFrontier(Queue<nodeTree> frontier){
		return frontier.poll();
		}

	public static boolean frontierIsEmpty(Queue<nodeTree> frontier){
		return frontier.isEmpty();
		}
}
