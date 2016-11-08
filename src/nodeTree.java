import java.util.Random;

public class nodeTree {
	private nodeTree parent;
    State state;
	private float partialCost;
	private char action;
	private float value;
	private Random rndGenerator = new Random();
	
	public nodeTree(nodeTree parent, State state, char action){
		this.parent=parent;
		this.state=state;
		this.action=action;
		this.partialCost=rndGenerator.nextInt(900)+100;
		this.value = this.getTotalCost();
	}
	public nodeTree(State state){
		this.parent=null;
		this.state=state;
		this.partialCost=0;

	}
	public int getTotalCost(){
		 if((this.parent!=null)){
			   return (int) (this.partialCost+this.parent.getTotalCost());
			  }else{
			   return (int) value;
			  }
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
	public void setValue(float value) {
		this.value = value;
	}
	
}