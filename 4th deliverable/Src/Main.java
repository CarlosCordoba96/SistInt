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
		int maxDepth=Integer.MAX_VALUE;
		int incrDepth=1;
		Queue<Character> s=null;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Introduce the original image: ");
		String img1="pics/"+sc.next();
		
		System.out.println("Introduce the disordered image: ");
		String img2="pics/"+sc.next();
		
		System.out.println("Introduce the numer of cols: ");
		int cols=sc.nextInt();
		
		System.out.println("Introduce the number of rows: ");
		int rows=sc.nextInt();

		System.out.println("Which type of algorithm you want: BFS, DFS, DLS, IDS, UCS, A*");
		String strat=sc.next().toUpperCase();
		
		switch(strat.toUpperCase()){
		case "BFS":
			incrDepth = maxDepth;
			break;
		case "DFS":
			incrDepth = maxDepth;
			break;
		case "DLS":
			System.out.println("Introduce the Max Depth:");
			maxDepth=sc.nextInt();
			incrDepth = maxDepth;
			break;
		case "IDS":
			System.out.println("Introduce the Max Depth:");
			maxDepth=sc.nextInt();
			System.out.println("Introduce the incremental Depth:");
			incrDepth=sc.nextInt();
			break;
		case "UCS":
			incrDepth = maxDepth;
			break;
		case "A*":
			incrDepth = maxDepth;
		}

		ImgProcessor img= new ImgProcessor(rows,cols,img1,img2);

		int cero[]=img.getZero();
		
		int pos [][]=img.getPos();
		int org [][]=img.getOrg();
		StateSpace goal=new StateSpace(rows,cols,0,0,org,org);
		StateSpace initialstate= new StateSpace(rows,cols,cero[0],cero[1],pos,org);	
		Problem p = new Problem(goal,initialstate);
		 if(p.getInitialState().isGoal()){
			   System.out.println("The puzzle isn't scrambled");
			   System.exit(0);
			  }

		
		System.out.println("Calculating solution...");
		
		s=p.search(strat, maxDepth, incrDepth);
		
		if(s.isEmpty()){
			
			System.out.println("There is no solution");
		}else{
			savestatistics(p, s, rows, cols, strat,s);
			System.out.println("Solution generated:");
			System.out.println(s.toString());
			
		
		
		System.out.println("Showing solution...");
		try {
			img.showpath(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Program finished.");
		
		}
		System.exit(0);
	}

    

	static void savestatistics(Problem p, Queue<Character> l, int rows, int cols, String strat,Queue s){

		try{
			Writer file=null;
			if(strat.equals("A*")){
				file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("statisticsA.dat"), "utf-8"));
			}else{
				file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("statistics"+strat+".dat"), "utf-8"));
				
			}


			file.write(	"Problem consists in solving a " + rows + " times " + cols + " puzzle\n"+
					"\n"+
					"Statistics resulting from solving the problem:\n" +
					"Strategy used: " + strat.toUpperCase() + "\n" +
					"Number of movements: " + l.size() + "\n" +
					"Movements :"+ s.toString()+"\n"+
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
