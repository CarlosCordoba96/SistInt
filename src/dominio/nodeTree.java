package dominio;

import java.util.Queue;

public class nodeTree {
	private nodeTree parent;
    State state;
	private int partialCost;
	private char action;
	private float value;
	private int depth;
	
	public nodeTree(nodeTree parent, State state, char action,String strat,int Mdepth){
		this.parent=parent;
		this.state=state;
		this.action=action;
		this.partialCost= parent.getPartialCost()+1;
		this.depth=parent.getDepth()+1;
		
		if(strat.equals("BFS")){
			this.value=this.depth;
		}else if(strat.equals("DFS") ||strat.equals("DLS")|| strat.equals("IPS") ){
			this.value=-this.depth;
		}else if(strat.equals("UCS")){
			this.value=this.partialCost;
		}
	}
	public nodeTree(State state){
		this.parent=null;
		this.state=state;
		this.partialCost=0;
		this.action=' ';	
		this.depth=0;
		this.value =0;
	}

	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public nodeTree getParent() {
		return parent;
	}
	public void setParent(nodeTree parent) {
		this.parent = parent;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public int getPartialCost() {
		return (int) partialCost;
	}
	public void setPartialCost(int partialCost) {
		this.partialCost = partialCost;
	}
	public char getAction() {
		return action;
	}
	public void setAction(char action) {
		this.action = action;
	}
	public float getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void getPath(Queue<Character> q){
		
		if(parent!=null){
			parent.getPath(q);
			q.add(action);
		}else{
			q.add(action);
		}
		
	}
	
}