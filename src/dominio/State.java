package dominio;

import java.util.Stack;

public class State {
	private static int n_rows;
	private static int n_cols;
	private int icero;
	private int jcero;
	private final int [][] puzzle;
    char action;
	public State(int rows, int cols, int xcero, int ycero, int [][] puzzle){
		this.n_rows=rows;
		this.n_cols=cols;
		this.icero = xcero;
		this.jcero = ycero;
		this.puzzle=puzzle;
		
	}
	public State(int rows, int cols, int xcero, int ycero, int [][] puzzle,char action) {
		this.n_rows=rows;
		this.n_cols=cols;
		this.puzzle=puzzle;
		this.action=action;
		this.icero = xcero;
		this.jcero = ycero;
	}
	/*
	 * El puzzle, el array de numeros o de imagenes?
	 *METHODS:
	 *isValid isGoal succesor		
	 */

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
	public void generatesuccesor(Stack <State> succesors,char mov, int[][] p){
		move(mov,p);
		State statenew;
		switch(mov){
			case 'u':
				statenew=new State(n_rows,n_cols,this.icero++, this.jcero,p,mov);
				break;
			case 'd':
				statenew=new State(n_rows,n_cols,this.icero--, this.jcero,p,mov);
			break;
			case 'l':
				statenew=new State(n_rows,n_cols,this.icero, this.jcero++,p,mov);
			break;
			case 'r':
				statenew=new State(n_rows,n_cols,this.icero, this.jcero--,p,mov);
			break;
			default:
				statenew=new State(n_rows,n_cols,this.icero, this.jcero,p,mov);
			break;
		}
		succesors.push(statenew);
	}
	public Stack<State> succesor(){
		Stack<State> s = new Stack<State>();
		Stack<Character> posmov=posiblemov();
		while(!posmov.isEmpty()){
			//pop del posible mov hacer movimiento y creamos un state  nuevo, push en s
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
}