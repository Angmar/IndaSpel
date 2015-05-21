import java.util.ArrayList;

public interface Builder {

	/**
	 * Return the names of the build options
	 * @return
	 */
	public ArrayList<String> getBuildOptions();

	/**
	 * Return the build progress
	 * @return
	 */
	public int getProgress();

	/**
	 * Add the opt parameter to the build queue
	 * @param opt
	 */
	public void queueSpawn(int opt);

	/**
	 * Return the build queue
	 * @return
	 */
	public ArrayList<Integer> getBuildQueue();

	/**
	 * Return the build times for the different build options
	 * @return
	 */
	public int[] getBuildTime();

	/**
	 * Return the build costs for the different build options
	 * @return
	 */
	public int[] getBuildCosts();

}
