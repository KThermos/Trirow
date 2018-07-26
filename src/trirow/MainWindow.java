package trirow;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;

/*************
 * The main window class that handles the frame for the game component
 * @author Andy
 *
 */
public class MainWindow extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = 1L;
	private JToolBar gToolBar;
	private GameComponent gGameComp;
	private JButton gGameStateButton;
	private JComboBox gPlayer1;
	private JComboBox gPlayer2;
	
	public static void main(String[] args) {
		MainWindow tWindow = new MainWindow();
		tWindow.setVisible(true);
	}
	
	/**********
	 * Default constructor
	 */
	public MainWindow() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// set the title
		this.setTitle("TriRow");
		
		// add the tool bar
		this.add(getToolBar(), BorderLayout.NORTH);
		
		// add the game graphics
		this.add(getGameComp(), BorderLayout.SOUTH);
				
		this.setBackground(Color.WHITE);
		this.setResizable(false);
		this.pack();

		getGameComp().start((Player)gPlayer1.getSelectedItem(),
				(Player)gPlayer2.getSelectedItem());
	}
	
	private JToolBar getToolBar() {
		if(gToolBar == null) {
			gToolBar = new JToolBar();
			gToolBar.setFloatable(false);
			
			gGameStateButton = new JButton("Reset");
			gGameStateButton.addActionListener(this);
			gToolBar.add(gGameStateButton);
					
			gToolBar.add(new JToolBar.Separator());
			
			gToolBar.add(new JLabel("Blue:"));
			
			gPlayer1 = new JComboBox();
			gPlayer1.addItem(new Player("Human", new HumanAgent(), Color.BLUE));
			gPlayer1.addItem(new Player("Computer (Random)", new RandomAIAgent(), Color.BLUE));
			gPlayer1.addActionListener(this);
			gToolBar.add(gPlayer1);

			gToolBar.add(new JLabel("Red:"));
			
			gPlayer2 = new JComboBox();
			gPlayer2.addItem(new Player("Human", new HumanAgent(), Color.RED));
			gPlayer2.addItem(new Player("Computer (Random)", new RandomAIAgent(), Color.RED));
			gPlayer2.addActionListener(this);
			gToolBar.add(gPlayer2);
			
			JButton tUndo = new JButton("Undo");
			tUndo.addActionListener(this);
			gToolBar.add(tUndo);
		}
		return gToolBar;
	}
	
	private GameComponent getGameComp() {
		if(gGameComp == null) {
			gGameComp = new GameComponent();
		}
		return gGameComp;
	}

	public void actionPerformed(ActionEvent mEvent) {
		String tCmd = mEvent.getActionCommand();
		if(tCmd.equals("Reset")) {
			getGameComp().start((Player)gPlayer1.getSelectedItem(),
					(Player)gPlayer2.getSelectedItem());
		} else if(tCmd.equals("Undo")) {
			getGameComp().getGame().undo();
			getGameComp().repaint();
		}
		if(mEvent.getSource() == gPlayer1) {
			getGameComp().getGame().setPlayer1((Player)gPlayer1.getSelectedItem());
			getGameComp().repaint();
		} else if (mEvent.getSource() == gPlayer2) {
			getGameComp().getGame().setPlayer2((Player)gPlayer2.getSelectedItem());	
			getGameComp().repaint();
		}
	}
	
}
