package sudokuGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class GamePanel extends JPanel 
{
	private GameFrame frame;
	private GamePuzzle puzzle;
	private int currentlyCol;
	private int currentlyRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	
	public GamePanel(GameFrame frame) 
	{
		//initialize the game
		this.setPreferredSize(new Dimension(800,600));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new GameGenerator().generateRandomSudoku();
		currentlyCol = -1;
		currentlyRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	
	public GamePanel(GamePuzzle puzzle) 
	{
		//create a copy of an existing game
		this.setPreferredSize(new Dimension(800,600));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlyCol = -1;
		currentlyRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		//initialize 
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		usedWidth = (this.getWidth()/puzzle.getCols())*puzzle.getCols();
		usedHeight = (this.getHeight()/puzzle.getRows())*puzzle.getRows();
		int slotWidth = this.getWidth()/puzzle.getCols();
		int slotHeight = this.getHeight()/puzzle.getRows();

		g2d.setColor(new Color(1.0f,1.0f,1.0f));
		//set up box colors
		for(int i=0; i<puzzle.getRows();i++) {
			for(int j=0; j<puzzle.getCols();j++) {
				if(!puzzle.isSlotMutable(j, i)) {
					g2d.setColor(Color.lightGray);
					g2d.fillRect(j * slotWidth, i * slotHeight,slotWidth,slotHeight);
				}else {
					g2d.setColor(Color.WHITE);
					g2d.fillRect(j * slotWidth, i * slotHeight,slotWidth,slotHeight);
				}
			}
		}
		//draw the lines
		drawLines(g2d);
		
		//draw the texts
		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		for(int row=0;row < puzzle.getRows();row++) 
		{
			for(int col=0;col < puzzle.getCols();col++) 
			{
				if(!puzzle.isSlotAvailable(col, row)) 
				{
					int textWidth = (int) f.getStringBounds(puzzle.getValue(col, row), fContext).getWidth();
					int textHeight = (int) f.getStringBounds(puzzle.getValue(col, row), fContext).getHeight();
					g2d.drawString(puzzle.getValue(col, row), (col*slotWidth)+((slotWidth/2)-(textWidth/2)), (row*slotHeight)+((slotHeight/2)+(textHeight/2)));
				}
			}
		}
		
		if(currentlyCol != -1 && currentlyRow != -1) 
		{
			//here to change the color of selected box
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentlyCol * slotWidth,currentlyRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	private void drawLines(Graphics2D g2d) {
		int slotWidth = this.getWidth()/puzzle.getCols();
		int slotHeight = this.getHeight()/puzzle.getRows();
		
		g2d.setColor(new Color(0.0f,0.0f,0.0f));
		for(int x = 0;x <= usedWidth;x+=slotWidth) 
		{
			if((x/slotWidth) % puzzle.getSectorWidth() == 0) 
			{
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			}
			else 
			{
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}

		for(int y = 0;y <= usedHeight;y+=slotHeight) 
		{
			if((y/slotHeight) % puzzle.getSectorHeight() == 0) 
			{
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			}
			else 
			{
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}
	}
	
	public boolean boardFull() {
		//check whether the game has finished
		return puzzle.boardFull();
	}
	
	public void checkWin() {
		//send the victory message
		if(puzzle.boardFull())
		{
			JOptionPane.showMessageDialog(frame, "You Win!\nYour time was: "+(GameFrame.counter/60)+" minutes and "+(GameFrame.counter%60)+" seconds");
		}
	}
	
	public class NumActionListener implements ActionListener 
	{
		//when you click on a number button
		@Override
		public void actionPerformed(ActionEvent e) 
		{	
			if(currentlyCol != -1 && currentlyRow != -1) {
				puzzle.makeMove(((JButton) e.getSource()).getText(), currentlyCol, currentlyRow, true);
				repaint();
				//checkWin();
			}
		}
	}
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter 
	{
		//keep a track on what position you are clicking on
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getButton() == MouseEvent.BUTTON1) 
			{
				int slotWidth = usedWidth/puzzle.getCols();
				int slotHeight = usedHeight/puzzle.getRows();
				currentlyRow = e.getY() / slotHeight;
				currentlyCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
	
	public void newPuzzle(GamePuzzle puzzle) 
	{
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) 
	{
		this.fontSize = fontSize;
	}
}
