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
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame { //Class for the menu window upon launching the program

	private JPanel contentPane;
	public int diff = 50; //for difficulty
	public boolean start = false; //determining if menu should close and game should start
	public boolean mario = false; //for mode chosen
	JLabel lblTheBestDucking;


	/**
	 * Constructor for menu
	 */
	public Menu() { 
		
		//setting up the window, and contentPane
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(250, 210, 250));
		
		JLabel lblDifficulty = new JLabel("Difficulty: 50");
		lblDifficulty.setHorizontalAlignment(SwingConstants.CENTER);
		lblDifficulty.setFont(new Font("Comic Sans MS", Font.PLAIN, 17));
		lblDifficulty.setBounds(272, 147, 136, 32);
		contentPane.add(lblDifficulty);
		
		
		JSlider slider = new JSlider(); //Constructing a difficulty slider so the user can interact with it
		slider.setValue(50);
		slider.addChangeListener(new ChangeListener() {
			//changes the difficulty depending on the value of the slider
			public void stateChanged(ChangeEvent arg0) {
				diff = slider.getValue();
				lblDifficulty.setText("Difficulty " + diff);
			}
		});
		slider.setSnapToTicks(true);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setBounds(238, 177, 200, 26);
		contentPane.add(slider);
		slider.setBackground(new Color(250, 210, 250));
		
		JButton btnNewButton = new JButton("Let's Go!");
		btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
		btnNewButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 45));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				start = true; //sets to true if button is pressed
			}
		});
		
		btnNewButton.setBounds(226, 209, 226, 137);
		contentPane.add(btnNewButton);
		
		lblTheBestDucking = new JLabel(new ImageIcon(getClass().getResource("nicksdumblogo.jpg"))); //JLabel for logo at the top 
		lblTheBestDucking.setHorizontalAlignment(SwingConstants.CENTER);
		lblTheBestDucking.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		lblTheBestDucking.setBounds(113, 11, 238, 112);
		contentPane.add(lblTheBestDucking);
		
		JLabel lblNewLabel = new JLabel("<html>Defend your pond from the pigs and sheep!<p>Arrow Keys to move.</p></html>");
		lblNewLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 128, 192, 84);
		contentPane.add(lblNewLabel);
		
		JButton button = new JButton();
		button.setIcon(new ImageIcon(getClass().getResource("R.png")));
		button.setBounds(10, 285, 25, 25);
		contentPane.add(button);
		
		JButton fakeButton = new JButton(); //'W' button
		fakeButton.setIcon(new ImageIcon(getClass().getResource("W.jpg")));
		fakeButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				lblTheBestDucking.setIcon(new ImageIcon(getClass().getResource("logohat.jpg"))); //changing the logo image if mouse is hovered over
				
			}
			public void mouseExited(MouseEvent e) {
				fakeButton.setIcon(new ImageIcon(getClass().getResource("W.jpg")));
				lblTheBestDucking.setIcon(new ImageIcon(getClass().getResource("nicksdumblogo.jpg"))); //changing it back if mouse isn't hovered over
			}
		});
		fakeButton.setBounds(10, 248, 25, 25);
		contentPane.add(fakeButton);
		

		JButton marioButton = new JButton(); //'E' button
		marioButton.setIcon(new ImageIcon(getClass().getResource("E.jpg")));
		marioButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				marioButton.setIcon(new ImageIcon(getClass().getResource("yeggie.jpg"))); //changing icon when hovered
			}
			@Override
			public void mouseExited(MouseEvent e) {
				marioButton.setIcon(new ImageIcon(getClass().getResource("E.jpg")));
			}
		});
		marioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) 
			{
				mario = true;
				start = true; //starting game with mario mode enabled
			}
		});
		marioButton.setBounds(10, 212, 25, 25);
		contentPane.add(marioButton);
		
		//how to play instructions displayed in JLabels
		JLabel lblToThrowAn = new JLabel("to throw an egg.");
		lblToThrowAn.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblToThrowAn.setBounds(40, 211, 123, 25);
		contentPane.add(lblToThrowAn);
		
		JLabel lblToPutA = new JLabel("to put a wall.");
		lblToPutA.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblToPutA.setBounds(40, 247, 123, 25);
		contentPane.add(lblToPutA);
		
		JLabel lblSheepTake = new JLabel("Sheep take 3 hits to kill.");
		lblSheepTake.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblSheepTake.setBounds(10, 321, 192, 25);
		contentPane.add(lblSheepTake);
		
		JLabel lblToUseYour = new JLabel("to use your super egg.");
		lblToUseYour.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		lblToUseYour.setBounds(40, 284, 176, 25);
		contentPane.add(lblToUseYour);
		
	}
}
