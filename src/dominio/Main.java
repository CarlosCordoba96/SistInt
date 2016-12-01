package dominio;

import java.io.IOException;
import java.util.Queue;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		init();

	}
	public static void init() throws IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Introduce the original image: ");
		String img1="pics/AlhambraInicialPuzzle4x4.png";
		//String img1=sc.next();
		
		System.out.println("Introduce the disordered image: ");
		String img2="pics/IntermedioAlhambra41.png";
		//String img2=sc.next();
		
		System.out.println("Introduce the number of rows: ");
		int rows =4;
		//int rows=sc.nextInt();
		System.out.println("Introduce the numer of cols: ");
		int cols = 4;	  
		//int cols=sc.nextInt();
		
		System.out.println("Which type of algorithm you want: BFS, DFS, DLS, IDS, UCS");
		String strat=sc.next();
		
		
		ImgProcessor img= new ImgProcessor(rows,cols,img1,img2);
		int cero[]=img.getZero();
		int pos [][]=img.getPos();
		int org[][]=img.getOrg();
		StateSpace goal=new StateSpace(rows,cols,0,0,org);
		StateSpace initialstate= new StateSpace(rows,cols,cero[0],cero[1],pos);
		
		Problem p = new Problem(goal,initialstate);
		int maxDepth=Integer.MAX_VALUE;
		int incrDepth;
		
		Queue<Character> s=null;
		
		switch(strat){
		case "BFS":
			s=p.acSolve(strat, maxDepth);
			//p.search(strat, maxdepth, initialdepth)
			break;
		case "DFS":
			s=p.acSolve(strat, maxDepth);
			
			break;
		case "DLS":
			System.out.println("Introduce the Max Depth:");
			maxDepth=sc.nextInt();
			s=p.acSolve(strat, maxDepth);
			break;
		case "IDS":
			System.out.println("Introduce the Max Depth:");
			maxDepth=sc.nextInt();
			System.out.println("Introduce the incremental Depth:");
			incrDepth=sc.nextInt();
			s=p.search(strat, maxDepth, incrDepth);
			break;
		case "UCS":
			s=p.acSolve(strat, maxDepth);
			break;
		}
		
		System.out.println(s.toString());
		
		
		
		
	}

}
