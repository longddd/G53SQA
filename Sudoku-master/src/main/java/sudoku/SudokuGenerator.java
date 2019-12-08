package sudokuGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SudokuGenerator 
{
	public SudokuPuzzle generateRandomSudoku() 
	{
		SudokuPuzzle puzzle = new SudokuPuzzle();
		SudokuPuzzle copy = new SudokuPuzzle(puzzle);
		
		Random randomGenerator = new Random();
		
		List<String> notUsedValidValues =  new ArrayList<String>(Arrays.asList(copy.getValidNumbers()));
		for(int r = 0;r < copy.getRows();r++) 
		{
			int randomValue = randomGenerator.nextInt(notUsedValidValues.size());
			copy.makeMove(notUsedValidValues.get(randomValue), 0, r, true);
			notUsedValidValues.remove(randomValue);
		}
		
		recursiveSudokuSolver(copy, 0, 0);
		
		int numberOfValuesToKeep = (int)(0.33333*(copy.getRows()*copy.getRows()));
		
		for(int i = 0;i < numberOfValuesToKeep;) 
		{
			int randomRow = randomGenerator.nextInt(puzzle.getRows());
			int randomColumn = randomGenerator.nextInt(puzzle.getCols());
			
			if(puzzle.isSlotAvailable(randomColumn, randomRow)) 
			{
				puzzle.makeMove(copy.getValue(randomColumn, randomRow), randomColumn, randomRow, false);
				i++;
			}
		}
		return puzzle;
	}
	
	/**
	 * Solves the sudoku puzzle
	 * Pre-cond: c = 0,r = 0
	 * Post-cond: solved puzzle
	 * @param r: the current row
	 * @param c: the current column
	 * @return valid move or not or done
	 * Responses: Erroneous data 
	 */
    private boolean recursiveSudokuSolver(SudokuPuzzle puzzle, int c,int r) 
    {
    	//If the move is not valid return false
		if(!puzzle.inRange(c,r)) 
		{
			return false;
		}
		
		//if the current space is empty
		if(puzzle.isSlotAvailable(c, r)) 
		{
			
			//loop to find the correct value for the space
			for(int i = 0;i < puzzle.getValidNumbers().length;i++) 
			{
				
				//if the current number works in the space
				if(!puzzle.numInRow(puzzle.getValidNumbers()[i], r) && !puzzle.numInCol(puzzle.getValidNumbers()[i], c) && !puzzle.numInBox(puzzle.getValidNumbers()[i], c, r)) {
					
					//make the move
					puzzle.makeMove(puzzle.getValidNumbers()[i], c, r, true);
					
					//if puzzle solved return true
					if(puzzle.boardFull()) {
						return true;
					}
					
					//go to next move
					if(r == puzzle.getRows() - 1) {
						if(recursiveSudokuSolver(puzzle, c + 1, 0)) return true;
					} else {
						if(recursiveSudokuSolver(puzzle, c, r + 1)) return true;
					}
				}
			}
		}
		
		//if the current space is not empty
		else {
			//got to the next move
			if(r == puzzle.getRows() - 1) {
				return recursiveSudokuSolver(puzzle, c + 1, 0);
			} else {
				return recursiveSudokuSolver(puzzle, c, r + 1);
			}
		}
		
		//undo move
		puzzle.clearSlot(c, r);
		
		//backtrack
		return false;
	}
}