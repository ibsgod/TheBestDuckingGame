import java.awt.Font;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;

public class Player //Class for the user-controlled character
{
	//booleans for movement
	public static boolean right = false;
	public static boolean left = false;
	public static boolean up = false;
	public static boolean down = false;
	public int health = 10; 
	public Clip clip; //clip for playing audio
	int x;
	int y;
	Board b;
	int walls = 5;
	SuperEgg pepe;
	boolean allwalls[][]; //2D boolean array tells us if there's a wall at a certain coordinate
	public ArrayList<Egg> eggs = new ArrayList<Egg>(); //list of all egg objects currently on the board
	public ArrayList<SuperEgg> supereggs = new ArrayList<SuperEgg>(); //list of all egg objects currently on the board
	/**
	 * Creating player at given position on board
	 */
	public Player(Board b, int y, int x)
	{
		if (Game.mario)
		{
			playSound("yoshisound.wav"); //plays a sound when mario mode is selected
		}
		//initializing variables and putting player at starting position
		this.b = b;
		allwalls = new boolean [b.getRows()][b.getColumns()];
		this.x = x;
		this.y = y;
		b.putPeg(Game.playerIcon, y, x);
	}
	/** Playing background music
	 * @param audio filename for the audio file
	 * @param setting boolean determines whether music is to be started or stopped 
	 */
	public void playMusic (String audio, boolean setting) //setting boolean determines whether music is to be started or stopped
	{
		if (setting)
		{
			try 
			{
				URL sound = getClass().getResource(audio);
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
		        	clip = AudioSystem.getClip();
		        	clip.open(audioInputStream);
		        	System.out.println(clip);
		        	clip.start();
				FloatControl gainControl =
						(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
				System.out.println(clip);

		        	clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			clip.stop();
		}
	}
	public Clip playMusic (String audio, Clip cl, boolean setting) //setting boolean determines whether music is to be started or stopped
	{
		if (setting)
		{
			try
			{
				URL sound = getClass().getResource(audio);
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
				cl = AudioSystem.getClip();
				cl.open(audioInputStream);
				System.out.println(cl);
				System.out.println(clip);
				cl.start();
				FloatControl gainControl =
						(FloatControl) cl.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
				System.out.println(cl);

				cl.loop(Clip.LOOP_CONTINUOUSLY);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			cl.stop();
		}
		return cl;
	}
	/** Playing sound effects
	 *  @param audio filename for the audio file
	 */
	public void playSound(String audio) 
	{
		try 
		{
			URL sound = getClass().getResource(audio);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(sound);
	        Clip clipp = AudioSystem.getClip(); //separate clip object was to be used so sounds could play simultaneously without hassle
	        clipp.open(audioInputStream);
	        clipp.start();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/** Receiving keyboard input, performing movement and other actions
	 * @param e The key that was pressed
	 */
	public void keyPressed(KeyEvent e) //all keyboard inputs
	{
		if (!Game.pause)
		{
			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				right = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				left = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_UP)
			{
				up = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				down = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_W)
			{
				putWall(y, x+1); //putting wall to the right of the player
			}
			if (e.getKeyCode() == KeyEvent.VK_R && Game.special == 300)
			{
				if (y == 0)
				{
					pepe = new SuperEgg(this, y, x+1, b);
				}
				else if (y == b.getRows()-1)
				{
					pepe = new SuperEgg(this, y-2, x+1, b);
				}
				else
				{
					pepe = new SuperEgg(this, y-1, x+1, b);
				}
				supereggs.add(pepe);
				playSound("gregheffley.wav");
				Game.special = 0;
			}
			if (e.getKeyCode() == KeyEvent.VK_E && x < b.getColumns()-1) //if 'E' is pressed and player is not at the right edge of the board
			{	
				Random rand = new Random();
				int a = rand.nextInt(3);
				//playing a random sound effect out of 3 to give variety 
				if (Game.mario)
				{
					switch(a)
					{
						case 0: playSound("yoshithrow1.wav");
						break;
						case 1: playSound("yoshithrow2.wav");
						break;
						case 2: playSound("yoshithrow3.wav");
						break;
					}
				}
				else
				{
					playSound("egg.wav");
				}
				Egg egg = new Egg(this, b, y, x+1); //creating a new egg to the right of the player 
	        	eggs.add(egg); //adding the egg to the list
			}
		}
	}
	/** Receiving keyboard input, performing movement and other actions
	 * @param e The key that was released
	 */
	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			up = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}
	/**tick method handles movement restrictions (not going off the board or walking through walls)
	 */
	public void tick()
	{
		if (right == true && x < b.getColumns()-1 && !allwalls[y][x+1])
        {
        	b.removePeg(y, x++);
        	b.putPeg(Game.playerIcon, y, x);
        }
        if (left == true && x > 0 && !allwalls[y][x-1])
        {
        	b.removePeg(y, x--);
        	b.putPeg(Game.playerIcon, y, x);
        }
        if (up == true && y > 0 && !allwalls[y-1][x])
        {
        	b.removePeg(y--, x);
        	b.putPeg(Game.playerIcon, y, x);
        }
        if (down == true && y < b.getRows()-1 && !allwalls[y+1][x])
        {
        	b.removePeg(y++, x);
        	b.putPeg(Game.playerIcon, y, x);
        }
	}
	/**creates a wall if walls are remaining in inventory, player is not at the right edge of the board, and a wall isn't already present at the location
	 * @param y the y coordinate
	 * @param x  the x coordinate
	 */
	public void putWall(int y, int x) 
	{
		if (walls > 0 && x < b.getColumns()-1 && x > 0 && !allwalls[y][x])
		{
				Wall w = new Wall(b, y, x);
				allwalls[y][x] = true;
				walls--;
				playSound("wall.wav");
		}
	}
}
