import java.util.*;
import java.awt.Point;

public class Board {
	private static final char CHAR_BLANK = '-';
	private static final char CHAR_DOT = 'o';
	private static final char CHAR_WALL = 'x';
	private static final char CHAR_GOAL = 'g';
	
	private String[] board;
	private ArrayList<Point> dots;
	private HashSet<Point> blanks;
	private HashSet<Point> goals;
	private HashMap<String[], String[]> statesToPaths;
	
	public Board(String[] board) {
		this.board = board;
		dots = new ArrayList<>();
		blanks = new HashSet<>();
		goals = new HashSet<>();
		populateDotsAndWalls();
	}
	
	private void populateDotsAndWalls() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				switch (board[row].charAt(col)) {
					case CHAR_DOT:
						dots.add(new Point(row, col));
						break;
					case CHAR_BLANK:
						blanks.add(new Point(row, col));
						break;
				}
			}
		}
	}
	
	public void addGoal(Point p) {
		goals.add(p);
	}
	
	public int numDots() {
		return dots.size();
	}
	
	public int numGoals() {
		return goals.size();
	}
	
	/**
	* Assumes all goals have been added.
	**/
	public ArrayList<String> getOptimalSolution() {
		FrontierStack frontierStates = new FrontierStack();
		frontierStates.push(new StatePathTuple(board, new ArrayList<String>()));
		while (!frontierStates.empty()) {
			StatePathTuple statePathTuple = frontierStates.pop();
			String[] state = statePathTuple.state;
			ArrayList<String> path = statePathTuple.path;
			if (isGoalState(state)) {
				return path;
			}
			String[] leftState = moveLeft(state);
			String[] rightState = moveRight(state);
			String[] upState = moveUp(state);
			String[] downState = moveDown(state);
			if (!frontierStates.hasVisited(leftState)) {
				ArrayList<String> leftPath = (ArrayList<String>) path.clone();
				leftPath.add("Left");
				frontierStates.push(new StatePathTuple(leftState, leftPath));
			}
			if (!frontierStates.hasVisited(rightState)) {
				ArrayList<String> rightPath = (ArrayList<String>) path.clone();
				rightPath.add("Right");
				frontierStates.push(new StatePathTuple(rightState, rightPath));
			}
			if (!frontierStates.hasVisited(upState)) {
				ArrayList<String> upPath = (ArrayList<String>) path.clone();
				upPath.add("Up");
				frontierStates.push(new StatePathTuple(upState, upPath));
			}
			if (!frontierStates.hasVisited(downState)) {
				ArrayList<String> downPath = (ArrayList<String>) path.clone();
				downPath.add("Down");
				frontierStates.push(new StatePathTuple(downState, downPath));
			}
		}
		// At this point, we have run out of options, so there is no solution.
		ArrayList<String> result = new ArrayList<>();
		result.add("No solution found.");
		return result;
	}
	
	private boolean isGoalState(String[] board) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board.length; col++) {
				if (board[row].charAt(col) == CHAR_DOT && !goals.contains(new Point(row, col))) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static String[] moveLeft(String[] board) {
		String[] result = new String[board.length];
		for (int row = 0; row < board.length; row++) {
			StringBuilder newRow = new StringBuilder(board[row]);
			for (int col = 0; col < board.length - 1; col++) {
				char current = newRow.charAt(col);
				char rightOfCurrent = newRow.charAt(col + 1);
				if (current == CHAR_BLANK && rightOfCurrent == CHAR_DOT) {
					// Move the dot to the empty space to its left.
					newRow.setCharAt(col, CHAR_DOT);
					newRow.setCharAt(col + 1, CHAR_BLANK);
				}
			}
			result[row] = newRow.toString();
		}
		return result;
	}
	
	private static String[] moveRight(String[] board) {
		String[] result = new String[board.length];
		for (int row = 0; row < board.length; row++) {
			StringBuilder newRow = new StringBuilder(board[row]);
			for (int col = board.length - 1; col > 0; col--) {
				char current = newRow.charAt(col);
				char leftOfCurrent = newRow.charAt(col - 1);
				if (current == CHAR_BLANK && leftOfCurrent == CHAR_DOT) {
					// Move the dot to the empty space to its right.
					newRow.setCharAt(col, CHAR_DOT);
					newRow.setCharAt(col - 1, CHAR_BLANK);
				}
			}
			result[row] = newRow.toString();
		}
		return result;
	}
	
	private static String[] moveUp(String[] board) {
		String[] result = new String[board.length];
		for (int row = 0; row < board.length; row++) {
			result[row] = "";
		}
		for (int col = 0; col < board.length; col++) {
			StringBuilder newCol = new StringBuilder();
			for (int row = 0; row < board.length; row++) {
				newCol.append(board[row].charAt(col));
			}
			for (int row = 0; row < board.length - 1; row++) {
				char current = newCol.charAt(row);
				char underCurrent = newCol.charAt(row + 1);
				if (current == CHAR_BLANK && underCurrent == CHAR_DOT) {
					// Move the dot to the empty space above it.
					newCol.setCharAt(row, CHAR_DOT);
					newCol.setCharAt(row + 1, CHAR_BLANK);
				}
			}
			for (int row = 0; row < board.length; row++) {
				result[row] += newCol.charAt(row);
			}
		}
		return result;
	}
	
	private static String[] moveDown(String[] board) {
		String[] result = new String[board.length];
		for (int row = 0; row < board.length; row++) {
			result[row] = "";
		}
		for (int col = 0; col < board.length; col++) {
			StringBuilder newCol = new StringBuilder();
			for (int row = 0; row < board.length; row++) {
				newCol.append(board[row].charAt(col));
			}
			for (int row = board.length - 1; row > 0; row--) {
				char current = newCol.charAt(row);
				char aboveCurrent = newCol.charAt(row - 1);
				if (current == CHAR_BLANK && aboveCurrent == CHAR_DOT) {
					// Move the dot to the empty space below it.
					newCol.setCharAt(row, CHAR_DOT);
					newCol.setCharAt(row - 1, CHAR_BLANK);
				}
			}
			for (int row = 0; row < board.length; row++) {
				result[row] += newCol.charAt(row);
			}
		}
		return result;
	}
	
	private static void printStringArray(String[] array) {
		System.out.println();
		for (String row : array) {
			System.out.println(row);
		}
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String row : board) {
			result.append(row + '\n');
		}
		// Trim the last \n.
		return result.substring(0, result.length() - 1);
	}
}