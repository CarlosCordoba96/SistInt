import java.util.Stack;

public class State implements Cloneable{
	private static int n_rows;
	private static int n_cols;
	private  int [] cero=new int[2];
	private final int [][] puzzle;
    char action;
	public State(int n_rows, int n_cols, int [] cero, int [][] puzzle){
		this.n_rows=n_rows;
		this.n_cols=n_cols;
		whereiszero(puzzle,this.cero);
		this.puzzle=puzzle;
		
	}
	public State(int n_rows, int n_cols, int [] cero,int [][] puzzle,char action) {
		this.n_rows=n_rows;
		this.n_cols=n_cols;
		this.puzzle=puzzle;
		this.action=action;
		whereiszero(puzzle,this.cero);
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
	public void generatesuccesor(Stack <State> succesors,char mov){
		int [][] puzzlenew=copyarr(puzzle);
		move(mov,puzzlenew);
		State statenew=new State(n_rows,n_cols,cero,puzzlenew,mov);
		succesors.push(statenew);
	}
	public Stack<State> succesor() throws CloneNotSupportedException {
		Stack<State> s = new Stack<State>();
		Stack<Character> posmov=posiblemov();
		while(!posmov.isEmpty()){
			//pop del posible mov hacer movimiento y creamos un state  nuevo, push en s
			generatesuccesor(s,posmov.pop());
		}
		
		return s;
	}
	public int[][] getPuzzle() {
		return puzzle;
	}
	private void move(char c, int [][]arr){
		
		switch(c){
			case 'u':
				arr[cero[0]][cero[1]] = arr[cero[0]-1][cero[1]];
				arr[cero[0]-1][cero[1]] = 0;
				action='u';
				break;
			case 'd':
				arr[cero[0]][cero[1]] = arr[cero[0]+1][cero[1]];
				arr[cero[0]+1][cero[1]] = 0;
				action='d';
				break;
			case 'l':
				arr[cero[0]][cero[1]] = arr[cero[0]][cero[1]-1];
				arr[cero[0]][cero[1]-1] = 0;
				action='l';
				break;
			case 'r':
				arr[cero[0]][cero[1]] = arr[cero[0]][cero[1]+1];
				arr[cero[0]][cero[1]+1] = 0;
				action='r';
				break;
		}
		
	}
	public  boolean ispossUp(){
		boolean posible=false;
		if(cero[0]!=0){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossDown(){
		boolean posible=false;
		if(cero[0]!=n_rows-1){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossLeft(){
		boolean posible=false;
		if(cero[1]!=0){
			posible=true;
		}
		return posible;
	}
	public  boolean ispossRight(){
		boolean posible=false;
		if(cero[1]!=n_cols-1){
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
	public static void whereiszero(int [][] array, int[] cero){
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[i].length;j++){
				if(array[i][j]==0){
					cero[0]=i;
					cero[1]=j;
				}
			}
		}
	}
}