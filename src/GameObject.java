import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

public abstract class GameObject {

	protected float x;
	protected float y;
	protected int height;
	protected int width;
	protected int maxHealth;
	protected int currentHealth;
	protected int damage;
	protected float range;
	protected float spotRange;
	protected int attackSpeed;
	protected int attackProgress;
	protected boolean alive;
	protected Image portrait;
	protected Rectangle posRect;
	protected Color attackLaser;
	protected int faction; // 0 = Resource, 1 = Friendly, 2 = Enemy

	private Image movePointFlag;

	// To be changed
	protected boolean selected;
	protected Vector2f movePoint;
	protected GameObject target;
	protected GameObject attacker;

	public GameObject(float x, float y, int width, int height, int maxHealth,
			int damage, float range, int attackSpeed, int faction, String image)
			throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
		this.damage = damage;
		this.range = range;
		this.spotRange = range * 3;
		this.attackSpeed = attackSpeed;
		this.attackProgress = 0;
		this.faction = faction;
		this.attackLaser = Color.red;

		if (faction == 1) {
			movePointFlag = new Image("movepoint.png");
			movePointFlag = movePointFlag.getScaledCopy(20, 20);
		}

		this.alive = true;

		this.portrait = new Image(image);
		this.portrait = portrait.getScaledCopy(width, height);

		this.posRect = new Rectangle(x - width / 2, y - height / 2, Math.max(
				width, height), Math.max(width, height));
	}

	public abstract void update(GameContainer container, int delta)
			throws SlickException;

	public abstract void render(GameContainer container, Graphics g)
			throws SlickException;

	/*
	 * Renders the image of the GameObject. And a green select box if the object
	 * is selected, along with where they move-point (if it has any). Then also
	 * renders the attack laser if it is attacking.
	 */
	protected void renderPortrait(GameContainer c, Graphics g) {
		if (selected) {
			g.setColor(Color.green);
			g.setLineWidth(2);
			int lineDist = Math.max(height, width);

			g.drawLine(x - lineDist / 2, y - lineDist / 2, x - lineDist / 2, y
					+ lineDist / 2);
			g.drawLine(x + lineDist / 2, y - lineDist / 2, x + lineDist / 2, y
					+ lineDist / 2);

			g.drawLine(x + lineDist / 2, y - lineDist / 2, x + lineDist / 3, y
					- lineDist / 2);
			g.drawLine(x + lineDist / 2, y + lineDist / 2, x + lineDist / 3, y
					+ lineDist / 2);

			g.drawLine(x - lineDist / 2, y - lineDist / 2, x - lineDist / 3, y
					- lineDist / 2);
			g.drawLine(x - lineDist / 2, y + lineDist / 2, x - lineDist / 3, y
					+ lineDist / 2);
			g.setColor(Color.red);

			g.drawLine(x - lineDist / 2, y - lineDist / 2 - 10, (x - lineDist
					/ 2 + (lineDist * ((float) currentHealth / maxHealth))), y
					- lineDist / 2 - 10);

			g.setColor(Color.white);
			// g.setLineWidth(1);

			if (selected && hasTarget() && faction == 1) {
				if (movePoint != null && target == null) {
					g.drawImage(movePointFlag,
							movePoint.getX() - movePointFlag.getWidth() / 2,
							movePoint.getY() - movePointFlag.getHeight() / 2);
				} else {
					// Find a good way to indicate target
				}
			}
		}
		if (attackProgress < attackSpeed / 2 && attackProgress > 0
				&& target != null && targetInRange()) {
			turnToTarget(target.getX() - x, target.getY() - y);

			g.setLineWidth(3);
			g.setColor(attackLaser);
			g.drawLine(x, y, target.getX(), target.getY());
			g.setColor(Color.white);
			// g.setLineWidth(1);
		}
		g.setLineWidth(1);
		g.drawImage(portrait, x - width / 2, y - height / 2);
	}

	// Used for testing purposes
	private void drawSelectBox(Graphics g, float xBox, float yBox, int lineDist) {

	}

	/**
	 * Renders the GameObject on the minimap in the interface.
	 */
	public void drawOnMap(GameContainer c, Graphics g) {
		if (faction == 1) {
			g.setColor(Color.green);
		} else if (faction == 2) {
			g.setColor(Color.red);
		}
		float mapX = (c.getHeight() / 4) * x / MainGame.FIELDSIZE;
		float mapY = (c.getHeight() / 4) * y / MainGame.FIELDSIZE + 3
				* c.getHeight() / 4;

		if (mapX > 0 && mapX < c.getHeight() / 4
				&& mapY > c.getHeight() * 3 / 4 && mapY < c.getHeight()) {
			g.fill(new Rectangle(mapX, mapY, 2, 2));
		}
		g.setColor(Color.white);
	}

	/*
	 * Rotates the image of the GameObject so that it faces the a certain x and
	 * y-coordinates.
	 * 
	 * @param xDistance Distance from this object to the point in the x-axis
	 * (targetX - this.x)
	 * 
	 * @param yDistance Distance from this object to the point in the y-axis
	 * (targetY - this.y)
	 */
	protected void turnToTarget(float xDistance, float yDistance) {
		portrait.setRotation((float) (90 + Math.toDegrees(Math.atan2(yDistance,
				xDistance))));
	}

	/**
	 * Set a new target move-point for the character and discards any old
	 * target.
	 * 
	 * @param newMovePoint
	 */
	public void setTarget(Vector2f newMovePoint) {
		movePoint = newMovePoint;
		target = null;
	}

	/**
	 * Set a new target for the character and discards any old move-point.
	 * 
	 * @param newTarget
	 */
	public void setTarget(GameObject newTarget) {
		target = newTarget;
		movePoint = null;
	}

	/**
	 * Clear the target and move-point
	 */
	public void clearTarget() {
		movePoint = null;
		target = null;
	}

	// Quick check if the target is within range
	protected boolean targetInRange() {
		if (getDistance(target.getX(), target.getY()) <= range) {
			return true;
		}
		return false;
	}

	// Checks if the possible target is within spot range.
	protected boolean targetInSpotRange(GameObject possibleTarget) {
		if (possibleTarget == null) {
			return false;
		}
		return getDistance(possibleTarget.getX(), possibleTarget.getY()) <= spotRange;
	}

	// Sets the attacker of the GameObject
	protected void setAttacker(GameObject attacker) {
		this.attacker = attacker;
	}

	/**
	 * Gets the distance between this GameObject and the x and y-coordinates
	 * given.
	 * 
	 * @param targetX
	 * @param targetY
	 * @return
	 */
	public float getDistance(float targetX, float targetY) {
		return (float) Math.sqrt(Math.pow(targetX - x, 2)
				+ Math.pow(targetY - y, 2));
	}

	/**
	 * Checks whether this GameObject has a target or move-point
	 * 
	 * @return
	 */
	public boolean hasTarget() {
		if (target != null || movePoint != null) {
			return true;
		}
		return false;
	}

	// Quick check the distance to the target
	protected float targetDistance() {
		return (float) Math.sqrt(Math.pow(target.getX() - x, 2)
				+ Math.pow(target.getY() - y, 2));
	}

	// Checks if two GameObjects are the same faction.
	protected boolean isSameFaction(int targetFaction) {
		return targetFaction == faction;
	}

	// Check if the GameObject and its target are of the same faction
	protected boolean isSameFaction() {
		return faction == target.getFaction();
	}

	/**
	 * @return the current target
	 */
	public GameObject getTarget() {
		return target;
	}

	/**
	 * @return the current move-point
	 */
	public Vector2f getMovePoint() {
		return movePoint;
	}

	/**
	 * @return the current x-coordinate
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the current y-coordinate
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return the height of the GameObject
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the width of the GameObject
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the maximum possible health of the GameObject
	 */
	public int getMaxHealth() {
		return maxHealth;
	}

	/**
	 * @return the current health
	 */
	public int getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * @return true if alive, else, false
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * @return the rectangle that is around the GameObject
	 */
	public Rectangle getRect() {
		return posRect;
	}

	/**
	 * Reduces the current health by the amount of damage taken in. If the
	 * current health drop to or below zero, the alive status will be set to
	 * false.
	 * 
	 * @param damage
	 *            to be taken.
	 */
	public void takeDamage(int damage) {
		currentHealth -= damage;
		if (currentHealth <= 0) {
			alive = false;
		}
	}

	/**
	 * @return true if the GameObject is attacking, else false
	 */
	public boolean isAttacking() {
		return attackProgress > 0;
	}

	/**
	 * Makes the GameObject identify itself as selected by the player.
	 */
	public void select() {
		selected = true;
	}

	/**
	 * Makes the GameObject identify itself as not being selected by the player.
	 */
	public void deselect() {
		selected = false;
	}

	/**
	 * @return the number of which the faction belongs to
	 */
	public int getFaction() {
		return faction;
	}

}
