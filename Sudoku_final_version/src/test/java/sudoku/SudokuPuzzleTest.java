package sudokuTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import sudokuGame.GamePuzzle;

public class GamePuzzleTest {

	private GamePuzzle puzzle;
	
	@Before
	public void setUp() {
		String [][] board = new String [][] {
				{"0","0","8","3","4","2","9","0","0"},
				{"0","0","9","0","0","0","7","0","0"},
				{"4","0","0","0","0","0","0","0","3"},
				{"0","0","6","4","7","3","2","0","0"},
				{"0","3","0","0","0","0","0","1","0"},
				{"0","0","2","8","5","1","6","0","0"},
				{"7","0","0","0","0","0","0","0","8"},
				{"0","0","4","0","0","0","1","0","0"},
				{"0","0","3","6","9","7","5","0","0"}
		};
		puzzle = new GamePuzzle();
		puzzle.setBoard(board);
	}
	
	@Test
	public void testInRange() {
		Assert.assertTrue(puzzle.inRange(0,0));
		Assert.assertTrue(puzzle.inRange(8,8));
		Assert.assertFalse(puzzle.inRange(10,9));
	}
	
	@Test
	public void testIsValidMove() {
		Assert.assertTrue(puzzle.isValidMove("1", 0, 0));
		Assert.assertTrue(puzzle.isValidMove("4", 5, 1));
		Assert.assertFalse(puzzle.isValidMove("10", 3, 2));
	}
	

	@Test
	public void testNumInRow() {
		Assert.assertTrue(puzzle.numInRow("7", 0));
		Assert.assertTrue(puzzle.numInRow("3", 1));
		Assert.assertFalse(puzzle.numInRow("1", 8));
	}
	
	@Test
	public void testNumInCol() {
		Assert.assertTrue(puzzle.numInCol("4",0));
		Assert.assertTrue(puzzle.numInCol("2",5));
		Assert.assertFalse(puzzle.numInCol("1", 8));
	}
	
	@Test
	public void testNumInBox() {
		Assert.assertTrue(puzzle.numInBox("4",6, 1));
		Assert.assertFalse(puzzle.numInBox("2", 4, 4));
		Assert.assertTrue(puzzle.numInBox("8", 4, 4));
	}
	
	@Test
	public void testGetValue() {
		Assert.assertEquals(puzzle.getValue(3, 4), "7");
		Assert.assertEquals(puzzle.getValue(5, 5), "1");
		Assert.assertEquals(puzzle.getValue(0, 0), "0");
	}
	
	@Test
	public void testClearSlot() {
		Assert.assertEquals(puzzle.getValue(0, 3), "3");
		puzzle.clearSlot(0, 3);
		Assert.assertEquals(puzzle.getValue(0, 3), "");
	}
}
