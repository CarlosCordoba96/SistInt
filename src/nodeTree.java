
public class nodeTree {
	private nodeTree parent;
	private State state;
	private int partialCost;
	private char action;
	public nodeTree(nodeTree parent, State state, int partialCost,char action){
		this.parent=parent;
		this.state=state;
		this.partialCost=partialCost;
		this.action=action;

	}
	public nodeTree(){
		

	}
}
