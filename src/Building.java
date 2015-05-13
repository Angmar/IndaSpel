import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Building extends GameObject {

	public void setTarget(Vector2f newMovePoint){
		//Do nothing
	}

	public Building(float x, float y, int width, int height, 
			int maxHealth, int damage, float range, int faction, String image) throws SlickException {
		
		//x, y, width, height, maxHealth, damage, range, attackSpeed, faction, "portrait"
		super(x, y, width, height, maxHealth, damage, range, 0, faction, image);
	}
	
}
