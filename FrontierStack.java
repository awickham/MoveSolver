import java.util.*;

public class FrontierStack {
	private Queue<StatePathTuple> frontier;
	private HashSet<List<String>> visited;
	
	public FrontierStack() {
		frontier = new LinkedList<>();
		visited = new HashSet<>();
	}
	
	public void push(StatePathTuple statePathTuple) {
		frontier.offer(statePathTuple);
		visited.add(Arrays.asList(statePathTuple.state));
	}
	
	public StatePathTuple pop() {
		return frontier.poll();
	}
	
	public boolean empty() {
		return frontier.peek() == null;
	}
	
	public boolean hasVisited(String[] state) {
		return visited.contains(Arrays.asList(state));
	}
}