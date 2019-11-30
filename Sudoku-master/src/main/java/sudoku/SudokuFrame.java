package sudoku;

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

	
	public SudokuFrame() {
		//this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku");
		this.setMinimumSize(new Dimension(800,600));
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");
		JMenu newGame = new JMenu("New Game");
		JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game");
		sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX,30));
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
		nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE,26));
		JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");
		twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE,20));
		
		/*
		 * need to include this when solving algorithm is improved
		 JMenuItem sixteenBySizteenGame = new JMenuItem("16 By 16 Game");
		sixteenBySizteenGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXTEENBYSIXTEEN,16));
		*/
		newGame.add(sixBySixGame);
		newGame.add(nineByNineGame);
		newGame.add(twelveByTwelveGame);
		//newGame.add(sixteenBySizteenGame);
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
		windowPanel.setLayout(new FlowLayout());
		windowPanel.setPreferredSize(new Dimension(800,600));
		
		buttonSelectionPanel = new JPanel();
		buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

		sPanel = new SudokuPanel();
		
		windowPanel.add(sPanel);
		windowPanel.add(buttonSelectionPanel);
		
		this.add(windowPanel);
		
		rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26);
	}
	
	public void rebuildInterface(SudokuPuzzleType puzzleType,int fontSize) {
		SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
		sPanel.newSudokuPuzzle(generatedPuzzle);
		sPanel.setFontSize(fontSize);
		buttonSelectionPanel.removeAll();
		for(String value : generatedPuzzle.getValidValues()) {
			JButton b = new JButton(value);
			b.setPreferredSize(new Dimension(45,45));
			b.addActionListener(sPanel.new NumActionListener());
			buttonSelectionPanel.add(b);
		}
		sPanel.repaint();
		buttonSelectionPanel.revalidate();
		buttonSelectionPanel.repaint();
	}
	
	private class NewGameListener implements ActionListener {

		private SudokuPuzzleType puzzleType;
		private int fontSize;
		
		public NewGameListener(SudokuPuzzleType puzzleType,int fontSize) {
			this.puzzleType = puzzleType;
			this.fontSize = fontSize;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			rebuildInterface(puzzleType,fontSize);
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
}
