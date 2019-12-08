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
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel 
{

	private SudokuPuzzle puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int usedWidth;
	private int usedHeight;
	private int fontSize;
	
	public SudokuPanel() 
	{
		//this.setPreferredSize(new Dimension(540,450));
		this.setPreferredSize(new Dimension(800,600));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new SudokuGenerator().generateRandomSudoku();
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	
	public SudokuPanel(SudokuPuzzle puzzle) 
	{
		this.setPreferredSize(new Dimension(540,450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		usedWidth = 0;
		usedHeight = 0;
		fontSize = 26;
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f,1.0f,1.0f));
		
		int slotWidth = this.getWidth()/puzzle.getCols();
		int slotHeight = this.getHeight()/puzzle.getRows();
		
		usedWidth = (this.getWidth()/puzzle.getCols())*puzzle.getCols();
		usedHeight = (this.getHeight()/puzzle.getRows())*puzzle.getRows();
		
		//
//		g2d.setColor(Color.lightGray);
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
		//this will draw the right most line
		//g2d.drawLine(usedWidth - 1, 0, usedWidth - 1,usedHeight);
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
		//this will draw the bottom line
		//g2d.drawLine(0, usedHeight - 1, usedWidth, usedHeight - 1);
		
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
		
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) 
		{
			//here to change the color of selected box
			g2d.setColor(new Color(0.0f,0.0f,1.0f,0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth,currentlySelectedRow * slotHeight,slotWidth,slotHeight);
		}
	}
	
	public void messageFromNumActionListener(String buttonValue) 
	{
		if(currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			puzzle.makeMove(buttonValue, currentlySelectedCol, currentlySelectedRow, true);
			repaint();
		}
	}
	
	public class NumActionListener implements ActionListener 
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			messageFromNumActionListener(((JButton) e.getSource()).getText());	
		}
	}
	
	private class SudokuPanelMouseAdapter extends MouseInputAdapter 
	{
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getButton() == MouseEvent.BUTTON1) 
			{
				int slotWidth = usedWidth/puzzle.getCols();
				int slotHeight = usedHeight/puzzle.getRows();
				currentlySelectedRow = e.getY() / slotHeight;
				currentlySelectedCol = e.getX() / slotWidth;
				e.getComponent().repaint();
			}
		}
	}
	
	public void newPuzzle(SudokuPuzzle puzzle) 
	{
		this.puzzle = puzzle;
	}
	
	public void setFontSize(int fontSize) 
	{
		this.fontSize = fontSize;
	}
}
