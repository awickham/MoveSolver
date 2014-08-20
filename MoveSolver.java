import java.util.*;
import java.awt.Point;

public class MoveSolver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter the game board one row at a time.");
		String firstRow = input.nextLine();
		int size = firstRow.length();
		String[] rows = new String[size];
		rows[0] = firstRow;
		for (int i = 1; i < size; i++) {
			rows[i] = input.nextLine();
		}
		Board board = new Board(rows);
		int numDots = board.numDots();
		System.out.println("Please enter the " + numDots + " goal locations, one at a time.");
		while (board.numDots() > board.numGoals()) {
			String[] coordinates = input.nextLine().split(",?\\s+");
			int row = Integer.valueOf(coordinates[0]);
			int col = Integer.valueOf(coordinates[1]);
			board.addGoal(new Point(row, col));
		}
		System.out.println("The optimal solution for this board is:");
		ArrayList<String> optimalSolution = board.getOptimalSolution();
		for (String s : optimalSolution) {
			System.out.println(s);
		}
	}
	
	public static void test() {
		HashSet<List<String>> testSet = new HashSet<>();
		String[] test1 = new String[1];
		String[] test2 = new String[1];
		test1[0] = "hey";
		test2[0] = "hey";
		testSet.add(Arrays.asList(test1));
		System.out.println(testSet.contains(Arrays.asList(test1)));
		System.out.println(testSet.contains(Arrays.asList(test2)));
		System.exit(0);
	}

}