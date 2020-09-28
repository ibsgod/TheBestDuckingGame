public class Main //Class containing the main method, controlling the opening and closing of windows
{
	public static void main (String args[])
    {
    	while (true) //loop so player can keep retrying indefinitely
    	{
    		Menu menu = new Menu (); //creating the menu window
        	menu.setVisible(true);
			while (!menu.start) //waiting for start button to be clicked 
	    	{
	    		try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
			menu.dispose(); //closing menu and opening game with chose difficulty and mode
    		Game g = new Game (menu.diff, menu.mario);
	    	while (!Game.done) //waiting for game to be done
	    	{
	    		try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	End end = new End (Game.score, Game.diff);//creating end screen when game is finished
	    	end.setVisible(true);
	    	Game.done = false;
	    	Game.b.f.dispose(); //closing game
    		while (!End.retry) //waiting for retry button to be clicked
    		{
	    		try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		End.retry = false;
    		End.stopMusic();
			end.dispose(); //closing end screen
    	}
    }
}
