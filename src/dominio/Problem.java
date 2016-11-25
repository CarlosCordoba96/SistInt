package dominio;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Problem {

	private StateSpace goalState;
	private StateSpace initialState;
	
	public Problem(StateSpace goalstate, StateSpace initialState){
		this.goalState=goalstate;
		this.initialState=initialState;
	}
	private Queue<Character> acSolve(StateSpace s, String strat, int maxdepth){
		Queue<Character> q = new PriorityQueue<Character>();
		Queue<nodeTree> qbuff = new PriorityQueue<nodeTree>(new Comparator<nodeTree>() {
			public int compare(nodeTree e1, nodeTree e2) {
				if(e1.getValue() > e2.getValue())
					return 1;
				
				else if(e1.getValue() < e2.getValue())
					return -1;
				
				else
					return 0;
			}
		});
		
		nodeTree initialNode = new nodeTree(s);
		Stack<StateSpace> sbuff;
		StateSpace ssbuff;
		nodeTree actualNode = null;
		qbuff.add(initialNode);
		
		boolean sol = false;
		
		while(!sol && !qbuff.isEmpty()){
			actualNode = qbuff.poll();
			if(actualNode.getStateSpace().isGoal(goalState)){
				sol = true;
			}else{
				sbuff = actualNode.getStateSpace().succesor();
				while(!sbuff.isEmpty()){
					ssbuff = sbuff.pop();
					qbuff.add(new nodeTree(actualNode,ssbuff,ssbuff.action,strat,maxdepth));
				}
			}
		}
		if(sol){
			actualNode.getPath(q);
			return q;
		}
		return null;
	}
	
public Queue<Character> search(StateSpace s, String strat,int maxdepth, int initialdepth){
		
		int actualdepth = initialdepth;
		Queue<Character> q = new PriorityQueue<Character>();
		while(q.isEmpty() && actualdepth <= maxdepth){
			q = acSolve(s,strat,actualdepth);
			actualdepth += initialdepth;
		}
		return null;
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