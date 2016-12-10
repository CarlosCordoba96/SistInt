package dominio;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Problem {

	private StateSpace goalState;
	private StateSpace initialState;
	private double time;
	private int visitednodes = 0;
	private int creatednodes = 0;
	
	public StateSpace getInitialState() {
		return initialState;
	}

	public void setInitialState(StateSpace initialState) {
		this.initialState = initialState;
	}



	public Problem(StateSpace goalstate, StateSpace initialState){
		this.goalState=goalstate;
		this.initialState=initialState;
	}

	public Queue<Character> acSolve(String strat, int maxdepth) throws MaxMemoryException{
		
		Queue<Character> rtrn = new LinkedList<Character>();
		Queue<nodeTree> qbuff = createFrontier();

		nodeTree initialNode = new nodeTree(this.initialState);
		Queue<StateSpace> sbuff;
		StateSpace ssbuff;
		nodeTree actualNode = null;
		boolean sol = false;
		nodeTree newnode =null;

		insertFrontier(initialNode,qbuff);
		
		while(!sol && !frontierIsEmpty(qbuff)){	
			Runtime runtime = Runtime.getRuntime();
			long memory = runtime.totalMemory() - runtime.freeMemory();
			if(bytesToMegabytes(memory)>=1500){//Limit the memory consumption to 1,5GB
				throw new MaxMemoryException();
			}
				

			actualNode = removeFirstFrontier(qbuff);
			visitednodes++;
			if(actualNode.getStateSpace().isGoal()){
				sol = true;
			}else{
				sbuff = actualNode.getStateSpace().succesor();
				while(!sbuff.isEmpty()){
					ssbuff = sbuff.poll();
					try {
						newnode = new nodeTree(actualNode,ssbuff,ssbuff.action,strat,maxdepth);
						this.creatednodes++;
					} catch (MdepthException e) {
						newnode=null;
						
						continue;
					}
					if(newnode !=null){
						insertFrontier(newnode,qbuff);
					}
				}
			}
		}
		if(sol){

			
			

			actualNode.getPath(rtrn);
			return rtrn;
		}
		return rtrn;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getVisitednodes() {
		return visitednodes;
	}

	public void setVisitednodes(int visitednodes) {
		this.visitednodes = visitednodes;
	}

	public int getCreatednodes() {
		return creatednodes;
	}

	public void setCreatednodes(int creatednodes) {
		this.creatednodes = creatednodes;
	}

	 public Queue<Character> search(String strat,int maxdepth, int incremdepth){

		  int actualdepth = incremdepth;
		  Queue<Character> q = new LinkedList<Character>();
		  double stime=System.currentTimeMillis();
		  while(q.isEmpty() && actualdepth <= maxdepth){//only 1 time all strategies except iterative
			  try{
				  q = acSolve(strat,actualdepth);
			  }catch (Exception e) {
				System.out.println("Maximum memory achieved: 1,5 GB");
				break;
			}
			  
		   
		   actualdepth += incremdepth;//increment of the depth
		  }
		  this.time=(System.currentTimeMillis()-stime)/1000;
		  return q;
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
	public static long bytesToMegabytes(long bytes) {
		long MEGABYTE = 1024L * 1024L;
            return bytes / MEGABYTE;
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