
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JProgressBar;

public class Game //Class containing the main game loop, spawning enemies, controlling movement, and hit detection
{
	//creating global variables
    public static Board b;
    public static int score = 0;
    static int diff; //difficulty selected variable
    public static boolean done = false; // determining if game has ended
    public static boolean mario = false; //boolean based on which mode player is playing
    //variables for image icons based on what mode player is playing
    static String playerIcon;
    static String enemyIcon;
    static String eggIcon;
    static String wallIcon;
    static String superEnemyIcon;
    static int special = 0; // counter variable for special move
    static boolean pause = false;
    static Color col;
    static int colorChange = 0;
    static int red = 255;
    static int green = 0;
    static int blue = 0;
    /**
     * Creating game using selected settings
     * @param diff difficulty selected
     * @param mario whether or not mario mode was picked
     */
    public Game (int diff, boolean mario) //constructor takes difficulty and mode selected from menu as parameter
	{   
    	Game.diff = diff;
    	Game.mario = mario;
		Game.play(Game.diff); //running the play method with the difficulty as argument
    }
    
    /** Main method for playing the game
     * @param diff difficulty for playing
     */
    public static void play(int diff)
    {
    	b = new Board(15, 50); //creating the 2D board
    	//icons set to normal by default
    	playerIcon = "duck"; 
        enemyIcon = "piig";
        eggIcon = "egg";
        wallIcon = "wall";
        superEnemyIcon = "shep";
        special = 0;
        score = 0;
		Player p = new Player(b, 5, 5); //creating the player, using the board object and starting coordinates as parameters
		b.setPlayer(p); //this method had to be included in order to let the board call methods on the player in a non-static way
		if (!mario)
		{
			//playing normal music if normal mode
			if (diff <= 33)
			{
				p.playMusic("calmmusic.wav", true);
			}
			else if (diff <= 66)
			{
				p.clip = p.playMusic("mediummusic.wav", p.clip, true);
			}
			else if (diff <= 99)
			{
				p.playMusic("extrememusic.wav", true);
			}
			else
			{
				p.playMusic("hamusic.wav", true);
			}
		}
		else
		{
			//changing music and icons if mario mode was selected
			if (diff <= 33)
			{
				p.playMusic("ezmario.wav", true);
			}
			else if (diff <= 66)
			{
				p.playMusic("medmario.wav", true);
			}
			else if (diff <= 99)
			{
				p.playMusic("extremio.wav", true);
			}
			else
			{
				p.playMusic("wtfio.wav", true);
			}
			playerIcon = "yoshi";
			enemyIcon = "goomba";
			eggIcon = "yoshegg";
			wallIcon = "marbrick";
			superEnemyIcon = "bobomb";
		}
		System.out.println(p.clip);
		//creating JProgressBar object to show charge for special move
		JProgressBar specialBar = new JProgressBar();
		specialBar.setBounds(b.textx+820,b.texty-20, 150, 50);
		specialBar.setMaximum(300);
		b.add(specialBar); //adding to the board 
		specialBar.setValue(special);
		//setting timer delays to determine speed 
        long startTime = System.currentTimeMillis();
		long enemySpawnSpeed = 0L;
		long startTime1 = System.currentTimeMillis();
		long enemySpeed = 0L;
		long startTime2 = System.currentTimeMillis();
		long playerSpeed = 0L;
		long startTime3 = System.currentTimeMillis();
		long eggSpeed = 0L;
		int pigsCreated = 0;
		long spawnTime = (Math.round(20.0 * (100 - diff) + Math.pow(diff/5.0, 2))); //fine-tuned formula to gradually increase difficulty by lowering spawn time as game went on
		ArrayList<Enemy> allPigs = new ArrayList<Enemy>(); //list containing all pig objects on the board
		ArrayList<SuperEnemy> allSheep = new ArrayList<SuperEnemy>(); //list containing all sheep objects on the board
		ArrayList<SuperEgg> supereggs = new ArrayList<SuperEgg>();
		//main game loop
        while (!done)
        {
        	//creating pause screen when paused
        	if (pause)
        	{
        		PauseScreen p1 = new PauseScreen();
        		p1.setVisible(true);
        		while (p1.pause) //waiting for continue
        		{
        			try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        		pause = false;
        		p1.dispose();
        	}	
        	if (p.health <= 0) //lose condition (there's no winning in our game haha)
        	{
        		done = true;
        	}
        	b.putPeg(playerIcon, p.y, p.x);
        	//updating timers each loop
        	enemySpawnSpeed = (new Date()).getTime() - startTime;
        	enemySpeed = (new Date()).getTime() - startTime1;
        	playerSpeed = (new Date()).getTime() - startTime2;
        	eggSpeed = (new Date()).getTime() - startTime3;
        	//looping through all the pigs in the list to check hit with player, walls, or eggs
        	for (int i = 0; i < allPigs.size(); i++) 
        	{
    			if (allPigs.get(i).checkHit(p, p.allwalls, p.eggs, p.supereggs))
    			{
    				allPigs.remove(i); //removing from list if hit
    				score ++; 
    			}
        	}
        	//looping through sheep for hit 
        	for (int i = 0; i < allSheep.size(); i++)
        	{
        		try //needed to surround in a try catch as in rare cases the list update wouldn't sync up with the game loop and throw a NullPointerException
        		{
	    			if (allSheep.get(i).checkHit(p.allwalls, p.eggs, p.supereggs)) 
	    			{
	    				allSheep.remove(i);
	    				score += 3;
	    			}
	    			if (p.x == allSheep.get(i).x && p.y == allSheep.get(i).y )
	    			{
	    				b.removePeg(allSheep.get(i).y, allSheep.get(i).x);
	    				b.putPeg(playerIcon, allSheep.get(i).y, allSheep.get(i).x);
	    				allSheep.remove(i);
	    				p.health -= 3; //player takes 3 damage from sheep instead of 1
	    			}
        		}
        		catch (Exception e)
        		{	
        		}
        	}
        	//checking eggs hitting walls
        	for (int i = 0; i < p.eggs.size(); i++) 
        	{
        		try
        		{
        			p.eggs.get(i).checkHit(p.allwalls);
        		}
        		catch (Exception e)
        		{
        		}
        	}
        	//the timers work by incrementing each game loop, and when they reach their certain limits, perform an action and reset
        	if (playerSpeed >= 75) 
        	{
        		p.tick(); //running tick method for player
        		startTime2 = System.currentTimeMillis();
	     		playerSpeed = 0L;
        	}
        	if (enemySpeed >= 100)
        	{
        		//changing color of the background by gradually changing rgb values (this speed just happened to be same as the enemy move speed)
        		if (colorChange%3 == 0)
        		{
        			if (red == 0)
        			{
        				colorChange ++;
        			}
        			else
        			{
        				red --;
        				green ++;
        			}
        		}
        		if (colorChange%3 == 1)
        		{
        			if (green == 0)
        			{
        				colorChange ++;
        			}
        			else
        			{
        				green --;
        				blue ++;
        			}
        		}
        		if (colorChange%3 == 2)
        		{
        			if (blue == 0)
        			{
        				colorChange ++;
        			}
        			else
        			{
        				blue --;
        				red ++;
        			}
        		}
            	col = new Color(red, green, blue);
            	b.f.setBackground(col);
        		//hit detection has to be checked again as the player, eggs, and the enemies all move at different speeds, so their coordinates will not always sync up
        		for (int i = 0; i < allPigs.size(); i++)
        		{
        			try
        			{
	        			allPigs.get(i).tick();
	        			if (allPigs.get(i).x == 0)
	        			{
	        				p.health --;
	        				allPigs.remove(i);
	        			}
	        			if (allPigs.get(i).checkHit(p, p.allwalls, p.eggs, p.supereggs))
	        			{
	        				allPigs.remove(i);
	        				score ++;
	        			}
        			}
        			catch (Exception e)
        			{
        				
        			}
        		}
        		for (int i = 0; i < allSheep.size(); i++)
        		{
        			allSheep.get(i).tick();
        			if (allSheep.get(i).x == 0 )
        			{
        				p.health -= 3;
        				b.removePeg(allSheep.get(i).y, allSheep.get(i).x);
        				allSheep.remove(i);
        			}
        			else if (allSheep.get(i).checkHit(p.allwalls, p.eggs, p.supereggs))
	    			{
        				b.removePeg(allSheep.get(i).y, allSheep.get(i).x);
	    				allSheep.remove(i);
	    				score += 3;
	    			}
	    			else if (p.x == allSheep.get(i).x && p.y == allSheep.get(i).y )
	    			{
	    				b.removePeg(allSheep.get(i).y, allSheep.get(i).x);
	    				b.putPeg(playerIcon, allSheep.get(i).y, allSheep.get(i).x);
	    				allSheep.remove(i);
	    				p.health -= 3;
            		}	
        		}
        		startTime1 = System.currentTimeMillis();
	     		enemySpeed = 0L;
    		}
        	if (enemySpawnSpeed >= spawnTime)
        	{
	        	Random rand = new Random(); //random number to determine spawn coordinate for enemy
	        	if (pigsCreated % 5 == 0 && pigsCreated > 0) //every 5th pig
	     		{
	     			spawnTime -= diff/3; //making spawning faster as more enemies are created, based on difficulty
	     			SuperEnemy e = new SuperEnemy(b, rand.nextInt(b.getRows()), b.getColumns()-1, mario); //a sheep will be spawned every 5 pigs
	     			allSheep.add(e);
	     		}
	        	else
	        	{
	        		Enemy e = new Enemy(b, rand.nextInt(b.getRows()), b.getColumns()-1);
	        		allPigs.add(e);
	        	}
	        	startTime = System.currentTimeMillis();
	     		enemySpawnSpeed = 0L;
	     		pigsCreated ++;
        	}
        	if (eggSpeed >= 30)
        	{
        		for (int i = 0; i < p.supereggs.size(); i++)
        		{
        			try
        			{
        				p.supereggs.get(i).tick(); //running egg's tick method
        			}
        			catch(Exception e)
        			{
        				
        			}
        		}
        		for (int i = 0; i < p.eggs.size(); i++)
        		{
        			p.eggs.get(i).tick(); //running egg's tick method
        		}
        		//checking collisions once more
        		for (int i = 0; i < allPigs.size(); i++)
            	{
        			if (allPigs.get(i).checkHit(p, p.allwalls, p.eggs, p.supereggs))
        			{
        				allPigs.remove(i);
        				score ++;
        			}
            	}
        		for (int i = 0; i < allSheep.size(); i++)
            	{
        			if (allSheep.get(i).checkHit(p.allwalls, p.eggs, p.supereggs))
        			{
        				b.removePeg(allSheep.get(i).y, allSheep.get(i).x);
        				allSheep.remove(i);
        				score += 3;
        			}
            	}
        		startTime3 = System.currentTimeMillis();
	     		eggSpeed = 0L;
	     		//charging special move 
	     		if (special < 300)
	     		{
		     		special ++;
		     		specialBar.setValue(special);
	     		}
        	}
        	//displaying text at the bottom
        	String message = "HP: " + p.health + "     Walls: " + p.walls + "     Score: " + score + "     Special Move: " + special/3;
        	b.displayMessage(message);
    	
        }
        //stops playing music and returns once game loop is finished
        p.playMusic("", false);
        done = true;
        return;
    }

}