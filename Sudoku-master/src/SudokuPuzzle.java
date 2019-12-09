package sudokuGame;

public class SudokuPuzzle 
{

	protected String [][] board;
	protected boolean [][] mutableSlots;
	private final int ROWS;
	private final int COLS;
	private final int SECTORWIDTH;
	private final int SECTORHEIGHT;
	private final String [] VALIDNUMBERS;
	
	public SudokuPuzzle()
	{
		//create a 9 by 9 sudoku puzzle
		this.ROWS = 9;
		this.COLS = 9;
		this.SECTORWIDTH = 3;
		this.SECTORHEIGHT = 3;
		this.VALIDNUMBERS = new String[] {"1","2","3","4","5","6","7","8","9"}; 
		this.board = new String[COLS][ROWS];
		this.mutableSlots = new boolean[COLS][ROWS];
		initializeBoard();
		initializeMutableSlots();
	}
	
	//instantiate a SudokuPuzzle object to be an exact copy of an existing puzzle object
	public SudokuPuzzle(SudokuPuzzle puzzle) 
	{
		this.ROWS = puzzle.ROWS;
		this.COLS = puzzle.COLS;
		this.SECTORWIDTH = puzzle.SECTORWIDTH;
		this.SECTORHEIGHT = puzzle.SECTORHEIGHT;
		this.VALIDNUMBERS = puzzle.VALIDNUMBERS;
		this.board = new String[COLS][ROWS];
		for(int c = 0;c < COLS;c++) 
		{
			for(int r = 0;r < ROWS;r++) 
			{
				board[c][r] = puzzle.board[c][r];
			}
		}
		this.mutableSlots = new boolean[COLS][ROWS];
		for(int c = 0; c < COLS; c++) 
		{
			for(int r = 0; r < ROWS; r++) 
			{
				this.mutableSlots[c][r] = puzzle.mutableSlots[c][r];
			}
		}
	}
	
	public int getSectorWidth()
	{
		return this.SECTORWIDTH;
	}
	
	public int getSectorHeight()
	{
		return this.SECTORHEIGHT;
	}
	
	public int getCols()
	{
		return this.COLS;
	}
	
	public int getRows()
	{
		return this.ROWS;
	}
	
	public String [] getValidNumbers() 
	{
		return this.VALIDNUMBERS;
	}
	
	public boolean isValidMove(String val, int col, int row) 
	{
		if(this.inRange(col,row)) 
		{
			if(!this.numInCol(val,col) && !this.numInRow(val,row) && !this.numInBox(val,col,row)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public void makeMove(String val, int col, int row, boolean isMutable) 
	{
		if(this.isValidValue(val) && this.isValidMove(val,col,row) && this.isSlotMutable(col, row)) 
		{
			this.board[col][row] = val;
			this.mutableSlots[col][row] = isMutable;
		}
	}
	
	public boolean numInBox(String val, int col, int row) 
	{
		if(this.inRange(row, col)) 
		{
			int boxCol = col / this.SECTORWIDTH;
			int boxRow = row / this.SECTORHEIGHT;
			int startingCol = (boxCol*this.SECTORWIDTH);
			int startingRow = (boxRow*this.SECTORHEIGHT);
			
			
			for(int r = startingRow;r <= (startingRow+this.SECTORHEIGHT)-1;r++) 
			{
				for(int c = startingCol;c <= (startingCol+this.SECTORWIDTH)-1;c++) 
				{
					if(this.board[c][r].equals(val)) 
					{
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean numInCol(String val, int col) 
	{
		if(col <= this.COLS) 
		{
			for(int row=0;row < this.ROWS;row++) 
			{
				if(this.board[col][row].equals(val)) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean numInRow(String val, int row) 
	{
		if(row <= this.ROWS) 
		{
			for(int col=0;col < this.COLS;col++) 
			{
				if(this.board[col][row].equals(val)) 
				{
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isSlotMutable(int col,int row) 
	{
		return this.mutableSlots[col][row];
	}
	
	public boolean isSlotAvailable(int col,int row) 
	{
		 return (this.inRange(col,row) && this.board[col][row].equals("") && this.isSlotMutable(col, row));
	}
	
	
	
	public String getValue(int col,int row) 
	{
		if(this.inRange(col,row)) {
			return this.board[col][row];
		}
		return "";
	}
	
	public String [][] getBoard() 
	{
		return this.board;
	}
	
	private boolean isValidValue(String val) 
	{
		for(String str : this.VALIDNUMBERS) 
		{
			if(str.equals(val)) return true;
		}
		return false;
	}
	
	public boolean inRange(int col,int row) 
	{
		return row <= this.ROWS && col <= this.COLS && row >= 0 && col >= 0;
	}
	
	public boolean boardFull() {
		for(int r = 0;r < this.ROWS;r++) {
			for(int c = 0;c < this.COLS;c++) {
				if(this.board[c][r].equals("")) return false;
			}
		}
		return true;
	}
	
	public void clearSlot(int col,int row) {
		this.board[col][row] = "";
	}
	
	@Override
	public String toString() {
		String str = "Game Board:\n";
		for(int row=0;row < this.ROWS;row++) {
			for(int col=0;col < this.COLS;col++) {
				str += this.board[col][row] + " ";
			}
			str += "\n";
		}
		return str+"\n";
	}
	
	private void initializeBoard() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLS;col++) {
				this.board[col][row] = "";
			}
		}
	}
	
	private void initializeMutableSlots() {
		for(int row = 0;row < this.ROWS;row++) {
			for(int col = 0;col < this.COLS;col++) {
				this.mutableSlots[col][row] = true;
			}
		}
	}
}
