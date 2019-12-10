package sudokuGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
public class GameFrame extends JFrame {
	
	public static int counter=0;
	int fontSize=27;
	private JPanel buttonPanel;
	private GamePanel gamePanel;
	private Timer timer;	
	private JLabel timerText; 
	
	public GameFrame() {
		//initial setup
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Sudoku Game");
		this.setMinimumSize(new Dimension(950,850));
		//add the menu bar
		this.setJMenuBar(createMenuBar());
		//add the timer text
		timerText = new JLabel("Time: 0 min 0 sec");
		timerText.setHorizontalAlignment(SwingConstants.CENTER);
		timerText.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		this.add(timerText,BorderLayout.PAGE_START);
		//add the timer
		this.createTimer();
		//add the panels
		JPanel windowPanel = new JPanel();
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(900,85));
		gamePanel = new GamePanel(this);
		windowPanel.add(gamePanel,BorderLayout.CENTER);
		windowPanel.add(buttonPanel,BorderLayout.PAGE_END);
		this.add(windowPanel);
		//restart
		restart();
	}
	
	public void restart() {
		//generate a new puzzle
		GamePuzzle generatedPuzzle = new GameGenerator().generateRandomSudoku();
		gamePanel.newPuzzle(generatedPuzzle);
		gamePanel.setFontSize(fontSize);
		//generate new button panel
		buttonPanel.removeAll();
		for(String value : generatedPuzzle.getValidNumbers()) {
			JButton button = new JButton(value);
			button.setPreferredSize(new Dimension(80,80));
			button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			button.addActionListener(gamePanel.new NumActionListener());
			buttonPanel.add(button);
		}
		//add a "clear" button
		JButton button1 = new JButton("clear");
		button1.setPreferredSize(new Dimension(80,80));
		button1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		button1.addActionListener(gamePanel.new NumActionListener());
		buttonPanel.add(button1);
		//reset the timer
		counter=0;
		timer.restart();
		//repaint the interface
		gamePanel.repaint();
		buttonPanel.revalidate();
		buttonPanel.repaint();
	}
	
	private JMenuBar createMenuBar() {
		//create the menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Game");
		JMenu newGame = new JMenu("New Game");
		JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
		nineByNineGame.addActionListener(new NewGameListener());
		newGame.add(nineByNineGame);
		file.add(newGame);
		menuBar.add(file);
		return menuBar;
	}
	
	private void createTimer() {
		//create the timer
		timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	int milliseconds=counter*1000;
            	int seconds = (int) (milliseconds / 1000) % 60 ;
            	int minutes = (int) ((milliseconds / (1000*60)) % 60);
            	String text = "Time: "+minutes+" min "+seconds+" sec";
                timerText.setText(text);
                counter++;
                if(gamePanel.boardFull()) {
                	//stop the timer when the game has finished
                	timer.stop();
                }
            }});
		timer.start();
	}
	
	
	private class NewGameListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			restart();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameFrame frame = new GameFrame();
				frame.setVisible(true);
			}
		});
	}
	

}
