package dominio;

public class nodeTree {
	private nodeTree parent;
    State state;
	private int partialCost;
	private char action;
	private int value;
	private int depth;
	
	public nodeTree(nodeTree parent, State state, char action,int value){
		this.parent=parent;
		this.state=state;
		this.action=action;
		this.partialCost= parent.getPartialCost()+1;
		this.value =value;
		this.depth=parent.getDepth()+1;
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
	
}