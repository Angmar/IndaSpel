
public class Order {
	
	private boolean active;
	private GameObject target;
	private float moveToX;
	private float moveToY;
	

	public Order() {
		active = false;
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setMoveTarget(float x, float y){
		active = true;
		moveToX = x;
		moveToY = y;
	}
	
	public float getMoveToX(){
		return moveToX;
	}
	
	public float getMoveToY(){
		return moveToY;
	}
	
	public void idle(){
		active = false;
	}
}
