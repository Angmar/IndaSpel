import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MineralOre extends Building {

	public MineralOre(float x, float y) throws SlickException {
		super(x, y);
		width = 40;
		height = 50;
		createPortrait("mineral ore.png");
	}

	@Override
	public void update(GameContainer container, Input input, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		renderPortrait(g);
	}

	
}
