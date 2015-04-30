import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Building extends GameObject {

	public Building(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
}
