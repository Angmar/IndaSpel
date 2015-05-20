import java.util.ArrayList;

public interface Builder {

	public ArrayList<String> getBuildOptions();

	public int getProgress();

	public void queueSpawn(int opt);

	public ArrayList<Integer> getBuildQueue();

	public int[] getBuildTime();

	public int[] getBuildCosts();

}
