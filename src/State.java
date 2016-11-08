import java.util.Stack;

public class State implements Cloneable{
	private static int n_rows;
	private static int n_cols;
	private static int [] cero;
	private int [][] puzzle;
    char action;
    int c;
	public State(int n_rows, int n_cols, int [] cero, int [][] puzzle){
		this.n_rows=n_rows;
		this.n_cols=n_cols;
		this.cero=cero;
		this.puzzle=puzzle;
		
	}
	public int[][] getPuzzle() {
		return puzzle;
	}
	public void setPuzzle(int[][] puzzle) {
		this.puzzle = puzzle;
	}
	public State() {
		
	}
	/*
	 * El puzzle, el array de numeros o de imagenes?
	 *METHODS:
	 *isValid isGoal succesor		
	 */

	public static boolean isValid(char c){
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
	
	public static Stack<Character> posiblemov(){
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
	
	public Stack<State> succesor() throws CloneNotSupportedException {
		Stack<State> s = new Stack<State>();
		Stack<Character> posmov=posiblemov();
		while(!posmov.isEmpty()){
			//pop del posible mov hacer movimiento y creamos un state  nuevo, push en s
		}
		
		return s;
	}
	private State move(char c, State s){
		switch(c){
			case 'u':
				s.puzzle[cero[0]][cero[1]] = s.puzzle[cero[0]-1][cero[1]];
				s.puzzle[cero[0]-1][cero[1]] = 0;
				action='u';
				break;
			case 'd':
				s.puzzle[cero[0]][cero[1]] = s.puzzle[cero[0]+1][cero[1]];
				s.puzzle[cero[0]+1][cero[1]] = 0;
				action='d';
				break;
			case 'l':
				s.puzzle[cero[0]][cero[1]] = s.puzzle[cero[0]][cero[1]-1];
				s.puzzle[cero[0]][cero[1]-1] = 0;
				action='l';
				break;
			case 'r':
				s.puzzle[cero[0]][cero[1]] = s.puzzle[cero[0]][cero[1]+1];
				s.puzzle[cero[0]][cero[1]+1] = 0;
				action='r';
				break;
		}
		return s;
	}
	public static boolean ispossUp(){
		boolean posible=false;
		if(cero[0]!=0){
			posible=true;
		}
		return posible;
	}
	public static boolean ispossDown(){
		boolean posible=false;
		if(cero[0]!=n_rows-1){
			posible=true;
		}
		return posible;
	}
	public static boolean ispossLeft(){
		boolean posible=false;
		if(cero[1]!=0){
			posible=true;
		}
		return posible;
	}
	public static boolean ispossRight(){
		boolean posible=false;
		if(cero[1]!=n_cols-1){
			posible=true;
		}
		return posible;
	}
	
	
}