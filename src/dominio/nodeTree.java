package dominio;

import java.util.Queue;

public class nodeTree {
	private nodeTree parent;
    StateSpace StateSpace;
	private int partialCost;
	private char action;
	private int depth;
	private float value;
	
	public nodeTree(nodeTree parent, StateSpace StateSpace, char action,String strat,int Mdepth) throws MdepthException{
		this.parent=parent;
		this.StateSpace=StateSpace;
		this.action=action;
		this.partialCost= parent.getPartialCost()+1;
		this.depth=parent.getDepth()+1;
		if (depth > Mdepth) {
			throw new MdepthException();
		}
		
		if(strat.equals("BFS")){
			this.value=this.depth;
		}else if(strat.equals("DFS") ||strat.equals("DLS")|| strat.equals("IPS") ){
			this.value=-this.depth;
		}else if(strat.equals("UCS")){
			this.value=this.partialCost;
		}
	}

	public nodeTree(StateSpace StateSpace){
		this.parent=null;
		this.StateSpace=StateSpace;
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
	public StateSpace getStateSpace() {
		return StateSpace;
	}
	public void setStateSpace(StateSpace StateSpace) {
		this.StateSpace = StateSpace;
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
		
		 while(parent!=null){
			   parent.getParent();
			   q.add(action);
			  }
		
	}
	
}