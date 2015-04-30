
public class Mine implements Order {

	@Override
	public void act(GameObject actor, GameObject target) {
		MineralOre ore = (MineralOre) target;
		Worker worker = (Worker) actor;
		worker.mine(ore);
	}

}
