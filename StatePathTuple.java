import java.util.*;

public class StatePathTuple {
	public final String[] state;
	public final ArrayList<String> path;

	public StatePathTuple(String[] state, ArrayList<String> path) {
		this.state = state;
		this.path = path;
	}
}