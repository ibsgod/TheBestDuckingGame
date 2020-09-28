import java.util.ArrayList;

public class SuperEnemy //class for the super enemies
{
	Board b;
	int y;
	int x;
	int health = 3; //for keeping track of health
	/**
	 * Creating enemy at given position on board
	 */
	public SuperEnemy(Board b, int y, int x, boolean mario)
	{
		//initializing variables and placing on board
		b.putPeg(Game.superEnemyIcon, y, x);
		this.b = b;
		this.y = y;
		this.x = x;
	}
	/** Tick method controlling movement
	 */
	public void tick()
	{
		//movement
		if (x > 0)
		{
			b.removePeg(y, x--);
			b.putPeg(Game.superEnemyIcon, y, x);
		}
		if (x == 0)
		{
			b.removePeg(y, x);
		}
	}
	/** Checking for collisions with eggs, or walls, and returning true if so 
	 * @param walls array of walls 
	 * @param eggs list of all eggs
	 * @param supereggs list of all super eggs
	 * @return returns true if the enemy dies to the hit
	 */
	public boolean checkHit(boolean walls[][], ArrayList<Egg> eggs, ArrayList<SuperEgg> supereggs) 
	{
		if (walls[y][x])
		{
			health = 0;
			walls[y][x] = false;
		}
		for (int i = 0; i < eggs.size(); i++)
		{	
			try
			{
				if (eggs.get(i).x == x && eggs.get(i).y == y)
				{
					b.removePeg(y, x);
					eggs.remove(i);
					health --;
					if (health > 0)
					{
						b.putPeg(Game.superEnemyIcon, y, x); //keep on the board if not dead yet
					}
				}
			}
			catch (Exception e)
			{
			}
		}
		for (int i = 0; i < supereggs.size(); i++)
		{
			try
			{
				if (supereggs.get(i).x+2 == x && (supereggs.get(i).y == y || supereggs.get(i).y+1 == y || supereggs.get(i).y+2 == y))
				{
					b.removePeg(y, x);
					supereggs.get(i).draw(supereggs.get(i).y, supereggs.get(i).x);
					return true;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		if (health <= 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
