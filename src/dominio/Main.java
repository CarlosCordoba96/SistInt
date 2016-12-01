package dominio;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
		/*String img1="pics/Inicialalhambra10x5.png";*/
		
		//String img1=sc.next();
		
		System.out.println("Introduce the disordered image: ");
		String img2="pics/IntermedioAlhambra41.png";
		/*String img2="pics/IntermedioAlhambra10x5.png";*/
		
		//String img2=sc.next();
		
		System.out.println("Introduce the number of rows: ");
		int rows = 4;
		//int rows=sc.nextInt();
		System.out.println("Introduce the numer of cols: ");
		int cols = 4;
		//int cols=sc.nextInt();
		
		System.out.println("Which type of algorithm you want: BFS, DFS, DLS, IDS, UCS");
		String strat=sc.next();
		
		
		ImgProcessor img= new ImgProcessor(rows,cols,img1,img2);
		img.print();
		int cero[]=img.getZero();
		int pos [][]=img.getPos();
		int org [][]=img.getOrg();
		StateSpace goal=new StateSpace(rows,cols,0,0,org);
		StateSpace initialstate= new StateSpace(rows,cols,cero[0],cero[1],pos);
		
		Problem p = new Problem(goal,initialstate);
		int maxDepth=Integer.MAX_VALUE;
		int incrDepth;
		
		Queue<Character> s=null;
		
		/*printarray(pos);
		printarray(org);
		System.out.println(cero[0] + cero[1]);*/
		
		switch(strat){
		case "BFS":
			//p.printarray(p.getInitialState().getPuzzle());
			s=p.acSolve(strat, maxDepth);
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
		try {
			img.showpath(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		savestatistics(p, s, rows, cols, strat);
	}
	
	static void savestatistics(Problem p, Queue<Character> l, int rows, int cols, String strat){

		try{
			Writer file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("statistics.dat"), "utf-8"));
		
			file.write(	"Problem consists in solving a " + rows + " times " + cols + " puzzle\n"+
						"\n"+
						"Statistics resulting from solving the problem:\n" +
						"Strategy used: " + strat + "\n" +
						"Number of movements: " + l.size() + "\n" +
						"Visited nodes = " + p.getVisitednodes() + "\n" +
						"Created nodes = " + p.getCreatednodes() + "\n" +
						"Time invested = " + p.getTime() + "\n" +
						"\n"+
						"Created By Alvaro Angel-Moreno Pinilla, Carlos Córdoba Ruiz & Roberto Plaza Romero");
			file.close();
		
		}catch(IOException e){
			System.err.println("Error while creating or writting into the file");
		}
	}
	
	public static void printarray(int [][] a){
		for(int i = 0; i < a.length ; i ++){
			for(int j = 0 ; j < a[i].length ; j ++){
				System.out.print(a[i][j]+" ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
}
