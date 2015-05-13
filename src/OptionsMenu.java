import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class OptionsMenu extends BasicGameState {
	
	int option;
	int depth;
	String[] menuText;
	String[] resolutionOptions;
	Image selectArrow;

	public OptionsMenu() {
		
	}

	@Override
	public void init(GameContainer contianer, StateBasedGame arg1)
			throws SlickException {
		option = 0;
		depth = 0; 
		
		menuText = new String[]{"Resolution", "Difficulty", "Return"};
		resolutionOptions = new String[]{"800x600", "1024x600", "1600x900", "1680x1050", "1920x1080", "Fullscreen"};
		
		selectArrow = MainMenu.getSelectImage();
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input input = container.getInput();
		
		if(input.isKeyPressed(Input.KEY_ENTER)){
			switch(option){
			case 0:
				if(depth == 0){
					depth = 1;
				}
				else{
					String[] res = resolutionOptions[depth-1].split("x");
					if(res.length==2){
						StartGame.changeFullScreen(Integer.parseInt(res[0]),Integer.parseInt(res[1]), false);
					}
					else{
						StartGame.changeFullScreen(0,0,true);
					}
				}
				break;
			case 2:
				game.enterState(0);
				break;
			}
		}
		else if(input.isKeyPressed(Input.KEY_UP)){
			if(depth == 0 && option > 0){
				option--;
			}
			else if(depth > 0){
				if(option == 0 && depth > 1){
					depth--;
				}
				else if(option == 1){
					
				}
			}
		}
		else if(input.isKeyPressed(Input.KEY_DOWN)){
			if(depth == 0 && option < 2){
				option++;
			}
			else if(depth > 0){
				if(option == 0 && depth < resolutionOptions.length){
					depth++;
				}
				else if(option == 1){
					
				}
			}
		}
		else if(input.isKeyPressed(Input.KEY_LEFT) && depth > 0){
			depth = 0;
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		float xText = container.getWidth()/2-100;
		float yText = container.getHeight()/2-25*menuText.length;
		
		for(int i = 0; i < menuText.length; i++){
			g.drawString(menuText[i], xText, yText+(50*i));
		}
		if(depth > 0 && option == 0){
			yText = container.getHeight()/2-25*resolutionOptions.length;
			for(int i = 0; i < resolutionOptions.length; i++){
				g.drawString(resolutionOptions[i], xText+200, yText+(50*i));
			}
		}
		if(depth == 0){
			g.drawImage(selectArrow, xText-50,  yText-10+(50*option));
		}
		else{
			g.drawImage(selectArrow, xText+150, yText-10+(50*(depth-1)));
		}
	}


	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}

}
