import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.InputMethodEvent;
import javax.swing.JProgressBar;

public class End extends JFrame { //Class for the end screen once game is over

        private JPanel contentPane;
        private JTextField textField;
        static boolean retry = false; //boolean for retrying
        static Clip clip; //clip for playing audio
        static boolean shown = false;
        /**
    	 * Creating end screen
    	 * @param score score achieved
    	 * @param diff difficulty played on
    	 */
        public End(int score , int diff) { //taking score and difficulty as parameters
                //setting up window and contentPane
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 450, 300);
                contentPane = new JPanel();
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setBackground(new Color(250, 210, 250));
                contentPane.setLayout(null);
                shown = false;
                URL sound;
                //playing end music depending on mode
                if (Game.mario)
                {
                        sound = getClass().getResource("endmario.wav");
                }
                else
                {
                        sound = getClass().getResource("endmusic.wav");
                }
                AudioInputStream audioInputStream;
                try {
	                audioInputStream = AudioSystem.getAudioInputStream(sound);
	                clip = AudioSystem.getClip();
	                clip.open(audioInputStream);
	                clip.start();
                }
                 catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
    
                //displaying score and difficulty
                JLabel lblNewLabel = new JLabel("The End");
                lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
                lblNewLabel.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 32));
                lblNewLabel.setBounds(122, 11, 190, 73);
                contentPane.add(lblNewLabel);
                
                JLabel lblDi = new JLabel("You scored " + score + " on difficulty of " + diff + "!");
                lblDi.setFont(new Font("Yu Gothic UI Light", Font.PLAIN, 18));
                lblDi.setHorizontalAlignment(SwingConstants.CENTER);
                lblDi.setBounds(69, 106, 296, 38);
                contentPane.add(lblDi);
         
                textField = new JTextField(); //textfield for writing name 
                textField.setBounds(25, 189, 116, 20);
                contentPane.add(textField);
                textField.setColumns(10);
                textField.addKeyListener( //open highscores screen when enter is pressed 
                		new KeyListener()
                		{
							public void keyPressed(KeyEvent e) {
								if (e.getKeyCode() == KeyEvent.VK_ENTER)
								{
									if (!shown)
									{
										if (textField.getText().length() > 0 && textField.getText().length() <= 12)
		                                {
		                                        HighScores hiscores = new HighScores(textField.getText(), score, diff); //opens high score window when pressed
		                                        hiscores.setVisible(true);
		                                        shown = true;
		                                }
		                                else
		                                {
		                                        JOptionPane.showMessageDialog(contentPane, "Please enter a name 1-12 characters long.");
		                                }
									}
									else
									{
										JOptionPane.showMessageDialog(contentPane, "You have already submitted. Please retry or quit.");
									}
								}
							}
							public void keyReleased(KeyEvent e) {
							}
							public void keyTyped(KeyEvent e) {
							}
                		}
                		);
                
                JButton btnSubmitScore = new JButton("Submit");
                btnSubmitScore.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) {
                        	if (!shown)
							{
								if (textField.getText().length() > 0 && textField.getText().length() <= 12)
                                {
                                        HighScores hiscores = new HighScores(textField.getText(), score, diff); //opens high score window when pressed
                                        hiscores.setVisible(true);
                                        shown = true;
                                }
                                else
                                {
                                        JOptionPane.showMessageDialog(contentPane, "Please enter a name 1-12 characters long.");
                                }
							}
							else
							{
								JOptionPane.showMessageDialog(contentPane, "You have already submitted. Please retry or quit.");
							}
                        }
                });
                btnSubmitScore.setBounds(141, 188, 95, 22);
                contentPane.add(btnSubmitScore);
                
                
                //retry button
                JButton btnRetry = new JButton("Retry");
                btnRetry.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent arg0) 
                        {
                        	retry = true;
                        }
                });
                btnRetry.setBounds(299, 188, 89, 23);
                contentPane.add(btnRetry);
        }
        /**method for stopping the music
         */
        public static void stopMusic()
        {
                clip.stop();
        }
}
