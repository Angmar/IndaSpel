import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MineralOre extends Building {

	public MineralOre(float x, float y) {
		super(x, y);
	}
	
	public int mine() {
		takeDamage(10);
		return 10;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, Input input, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Order getOrder() {
		Order order = new Order() {
			@Override
			public void setOrder(GameObject target) {
				if (target.getClass().getName().equals("Worker")) {
					Worker w = (Worker) target;
					w.setMinerals(mine());
				}
				
			}
		};
		return order;
	}

	@Override
	public void setOrder(GameObject target) {
		// TODO Auto-generated method stub
		
	}
	
}
