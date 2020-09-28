import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSlider;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PauseScreen extends JFrame { //Class for the pause screen when space is pressed

	private JPanel contentPane;
	public boolean pause = true; //boolean for when to continue
	JLabel lblTheBestDucking;


	public PauseScreen() { //constructor for pause screen
		
		//setting up the window, and contentPane
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 300, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(250, 210, 250));
		
		
		JLabel pauseLabel = new JLabel("Paused");
		pauseLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		pauseLabel.setBounds(37, 12, 200, 50);
		pauseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(pauseLabel);
		
		JLabel continueLabel = new JLabel("Press Space to continue");
		continueLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		continueLabel.setBounds(41, 50, 200, 50);
		continueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(continueLabel);
		
		this.addKeyListener(new KeyListener() //continue when space is pressed
		{
			public void keyPressed(KeyEvent e)
			{
			}
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					pause = false;
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		}
		);
		
		
		
	}
}
