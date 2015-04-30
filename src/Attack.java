
public class Attack implements Order {

	@Override
	public void act(GameObject actor, GameObject target) {
		target.takeDamage(actor.damage);
	}

}
