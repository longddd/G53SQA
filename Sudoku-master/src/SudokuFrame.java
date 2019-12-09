package sudokuGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {

	private JPanel buttonSelectionPanel;
	private SudokuPanel sPanel;
	int counter=0;
	int fontSize=27;
	
	public SudokuFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(950,850));
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");
		JMenu newGame = new JMenu("New Game");
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
		nineByNineGame.addActionListener(new NewGameListener(0,26));

		newGame.add(nineByNineGame);
		file.add(newGame);
		menuBar.add(file);
		this.setJMenuBar(menuBar);
		
		//add a timer
		JLabel label; 
		Timer timer;	
		label = new JLabel("00 min 00 sec");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		this.add(label,BorderLayout.PAGE_START);
		
		timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int milliseconds=counter*1000;
            	int seconds = (int) (milliseconds / 1000) % 60 ;
            	int minutes = (int) ((milliseconds / (1000*60)) % 60);
            	String text = "Time: "+minutes+" min "+seconds+" sec";
                label.setText(text);
                counter++;
            }});
		
		timer.start();
				
		JPanel windowPanel = new JPanel();
//		windowPanel.setLayout(new FlowLayout());
//		windowPanel.setPreferredSize(new Dimension(800,600));
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(800,85));
		sPanel = new SudokuPanel(this);
		
		windowPanel.add(sPanel,BorderLayout.CENTER);
		windowPanel.add(buttonSelectionPanel,BorderLayout.PAGE_END);
		this.add(windowPanel);
		
		rebuildInterface();
	}
	
	public void rebuildInterface() {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku();
		sPanel.newPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);

		buttonSelectionPanel.removeAll();
		for(String value : generatedPuzzle.getValidNumbers()) {
			JButton b = new JButton(value);
			b.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			b.setPreferredSize(new Dimension(80,80));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	private class NewGameListener implements ActionListener {

		private int fontSize;
		
		public NewGameListener(int puzzleType,int fontSize) {
			this.fontSize = fontSize;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			rebuildInterface();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SudokuFrame frame = new SudokuFrame();
				frame.setVisible(true);
			}
		});
	}
	
	public int getElapsedTime()
	{
		return this.counter;
	}
}
