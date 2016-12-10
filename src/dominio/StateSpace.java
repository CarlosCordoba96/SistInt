package dominio;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class StateSpace {
	private static int n_rows;
	private static int n_cols;
	private int icero;
	private int jcero;
	private final int [][] puzzle;
	char action;
	public StateSpace(int rows, int cols, int xcero, int ycero, int [][] puzzle){
		this.n_rows=rows;
		this.n_cols=cols;
		this.icero = xcero;
		this.jcero = ycero;
		this.puzzle=puzzle;

	}
	public StateSpace(int rows, int cols, int xcero, int ycero, int [][] puzzle,char action) {
		this.n_rows=rows;
		this.n_cols=cols;
		this.puzzle=puzzle;
		this.action=action;
		this.icero = xcero;
		this.jcero = ycero;
	}


	public  boolean isValid(char c){
		boolean sol=false;
		switch(c){
		case 'u':
			if(ispossUp()){
				sol=true;
			}
			break;
		case 'd':
			if(ispossDown()){
				sol=true;
			}
			break;
		case 'l':
			if(ispossLeft()){
				sol=true;
			}
			break;
		case 'r':
			if(ispossRight()){
				sol=true;
			}
			break;
		}
		return sol;
	}

	public  Stack<Character> posiblemov(){
		Stack<Character> movements = new Stack<Character>();
		if(isValid('u')){
			movements.push('u');
		}
		if(isValid('d')){
			movements.push('d');
		}
		if(isValid('l')){
			movements.push('l');
		}
		if(isValid('r')){
			movements.push('r');
		}
		return movements;
	}

	public void generatesuccesor(Queue<StateSpace> succesors,char mov, int[][] p){
		move(mov,p);
		StateSpace StateSpacenew = null;
		switch(mov){
		case 'u':
			StateSpacenew=new StateSpace(n_rows,n_cols,this.icero++, this.jcero,p,mov);
			break;
		case 'd':
			StateSpacenew=new StateSpace(n_rows,n_cols,this.icero--, this.jcero,p,mov);
			break;
		case 'l':
			StateSpacenew=new StateSpace(n_rows,n_cols,this.icero, this.jcero++,p,mov);
			break;
		case 'r':
			StateSpacenew=new StateSpace(n_rows,n_cols,this.icero, this.jcero--,p,mov);
			break;
		default:
			StateSpacenew=new StateSpace(n_rows,n_cols,this.icero, this.jcero,p,mov);
			break;
		}
		succesors.add(StateSpacenew);
	}
	public Queue<StateSpace> succesor(){
		Queue<StateSpace> s = new LinkedList<StateSpace>();
		Stack<Character> posmov=posiblemov();
		while(!posmov.isEmpty()){
			generatesuccesor(s,posmov.pop(),copyarr(this.puzzle));
		}

		return s;
	}
	public int[][] getPuzzle() {
		return puzzle;
	}
	private void move(char c, int [][]arr){

		switch(c){
		case 'u':
			arr[icero][jcero] = arr[icero-1][jcero];
			arr[icero-1][jcero] = 0;
			this.icero--;
			break;
		case 'd':
			arr[icero][jcero] = arr[icero+1][jcero];
			arr[icero+1][jcero] = 0;
			this.icero++;
			break;
		case 'l':
			arr[icero][jcero] = arr[icero][jcero-1];
			arr[icero][jcero-1] = 0;
			this.jcero--;
			break;
		case 'r':
			arr[icero][jcero] = arr[icero][jcero+1];
			arr[icero][jcero+1] = 0;
			this.jcero++;
			break;
		}

	}
	public  boolean ispossUp(){
		boolean posible=false;
		if(icero!=0){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossDown(){
		boolean posible=false;
		if(icero!=n_rows-1){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossLeft(){
		boolean posible=false;
		if(jcero!=0){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossRight(){
		boolean posible=false;
		if(jcero !=n_cols-1){
			posible=true;
		}
		return posible;
	}
	public static void printarray(int[][] array){

		for(int i = 0; i<array.length;i++){
			for(int j = 0; j<array[0].length;j++){

				System.out.print(array[i][j]+" ");
			}
			System.out.print("\n");
		}
	}
	public static int [] [] copyarr(int [][]array){
		int [] [] a = new int[n_rows][n_cols];
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[i].length;j++){
				a[i][j]=array[i][j];
			}
		}
		return a;
	}
	public boolean isGoal( StateSpace sol) {
		boolean goal = false;

		if (Arrays.deepEquals(getPuzzle(), sol.getPuzzle()))
			goal = true;

		return goal;
	}
	public int heuristic(){
		int h=0;
		for(int i=0;i<puzzle.length;i++){
			for(int j=0;j<puzzle[i].length;j++){
				if(puzzle[i][j]!= (i*10)+j){
					h++;
				}
			}
		}
		return h;
	}
}