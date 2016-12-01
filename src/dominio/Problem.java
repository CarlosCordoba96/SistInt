package dominio;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Problem {

	private StateSpace goalState;
	private StateSpace initialState;
	private double time;
	private int visitednodes=0;
	
	public Problem(StateSpace goalstate, StateSpace initialState){
		this.goalState=goalstate;
		this.initialState=initialState;
	}
	
	public Queue<Character> acSolve(String strat, int maxdepth){
		double stime=System.currentTimeMillis();
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
			actualNode = removeFirstFrontier(qbuff);
			visitednodes++;
			if(actualNode.getStateSpace().isGoal(goalState)){
				sol = true;
			}else{
				sbuff = actualNode.getStateSpace().succesor();
				
				while(!sbuff.isEmpty()){
					ssbuff = sbuff.poll();
					try {
						newnode = new nodeTree(actualNode,ssbuff,ssbuff.action,strat,maxdepth);
						
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
			
			double endtime=System.currentTimeMillis();
			time=(endtime-stime)/1000;
			
			actualNode.getPath(rtrn);
			return rtrn;
		}
		return rtrn;
	}
	
public Queue<Character> search(String strat,int maxdepth, int incremdepth){
		
		int actualdepth = incremdepth;
		Queue<Character> q = new LinkedList<Character>();
		int n=0;
		while(q.isEmpty() && actualdepth <= maxdepth){//only 1 time all strategies except iterative
			q = acSolve(strat,actualdepth);
			System.out.println("Depth: " +actualdepth);
			actualdepth += incremdepth;//increment of the depth
			n++;
			
		}
		System.out.println("N times el bucle: "+n);
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