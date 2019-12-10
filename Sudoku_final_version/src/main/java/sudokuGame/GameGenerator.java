package sudokuGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameGenerator 
{
	public GamePuzzle generateRandomSudoku() 
	{
		//this method will generate a random new game
		GamePuzzle game = new GamePuzzle();
		GamePuzzle copy = new GamePuzzle(game);
		Random rand = new Random();
		
		//create the random given numbers for the game to start with
		List<String> valuesToUse =  new ArrayList<String>(Arrays.asList(copy.getValidNumbers()));
		for(int r = 0;r < copy.getRows();r++) 
		{
			int randomValue = rand.nextInt(valuesToUse.size());
			copy.makeMove(valuesToUse.get(randomValue), 0, r, true);
			valuesToUse.remove(randomValue);
		}
		
		//solve the puzzle
		recursiveSudokuSolver(copy, 0, 0);
		
		//determine how hard the game is, i.e. game gets easier as this value gets lower
		double hardness = 0.35;
		int valuesToKeep = (int)(hardness*(copy.getRows()*copy.getRows()));
		//only put some answers according to the hardness
		for(int i = 0;i < valuesToKeep;) 
		{
			int randomRow = rand.nextInt(game.getRows());
			int randomColumn = rand.nextInt(game.getCols());
			
			if(game.isSlotAvailable(randomColumn, randomRow)) 
			{
				game.makeMove(copy.getValue(randomColumn, randomRow), randomColumn, randomRow, false);
				i++;
			}
		}
		return game;
	}
	
	/**
	 * Solves the game
	 * Pre-cond: c = 0,r = 0
	 * Post-cond: solved puzzle
	 * @param r: the current row
	 * @param c: the current column
	 * @return valid move or not or done
	 * Responses: Erroneous data 
	 */
    private boolean recursiveSudokuSolver(GamePuzzle puzzle, int column,int row) 
    {
    	//If the move is not valid return false
		if(!puzzle.inRange(column, row)) 
		{
			return false;
		}
		
		//if the current space is empty
		if(puzzle.isSlotAvailable(column, row)) 
		{
			
			//loop to find the correct value for the space
			for(int i = 0;i < puzzle.getValidNumbers().length;i++) 
			{
				
				//if the current number works in the space
				if(!puzzle.numInRow(puzzle.getValidNumbers()[i], row) && !puzzle.numInCol(puzzle.getValidNumbers()[i], column) && !puzzle.numInBox(puzzle.getValidNumbers()[i], column, row)) {
					
					//make the move
					puzzle.makeMove(puzzle.getValidNumbers()[i], column, row, true);
					
					//if puzzle solved return true
					if(puzzle.boardFull()) {
						return true;
					}
					
					//go to next move
					if(row == puzzle.getRows() - 1) {
						if(recursiveSudokuSolver(puzzle, column + 1, 0)) return true;
					} else {
						if(recursiveSudokuSolver(puzzle, column, row + 1)) return true;
					}
				}
			}
		}
		
		//if the current space is not empty
		else {
			//got to the next move
			if(row == puzzle.getRows() - 1) {
				return recursiveSudokuSolver(puzzle, column + 1, 0);
			} else {
				return recursiveSudokuSolver(puzzle, column, row + 1);
			}
		}
		
		//undo move
		puzzle.clearSlot(column, row);
		
		//backtrack
		return false;
	}
}