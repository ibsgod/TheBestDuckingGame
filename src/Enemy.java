import java.util.ArrayList;

public class Enemy //class for the normal enemies 
{
	Board b;
	int y;
	int x;
	/**
	 * Creating enemy at given position on board
	 */
	public Enemy(Board b, int y, int x)
	{
		//initializing variables and putting enemy on the board
		b.putPeg(Game.enemyIcon, y, x);
		this.b = b;
		this.y = y;
		this.x = x;
	}
	/** Tick method controlling movement
	 */
	public void tick()
	{
		if (x > 0)
		{
			b.removePeg(y, x--);
			b.putPeg(Game.enemyIcon, y, x);
		}
		if (x == 0)
		{
			b.removePeg(y, x);
		}
	}
	/** Checking for collisions with player, eggs, or walls, and returning true if so 
	 * @param p the player
	 * @param walls array of walls 
	 * @param eggs list of all eggs
	 * @param supereggs list of all super eggs
	 * @return returns true if the enemy dies to the hit
	 */
	public boolean checkHit(Player p, boolean walls[][], ArrayList<Egg> eggs, ArrayList<SuperEgg> supereggs)
	{
		if (p.x == x && p.y == y)
		{
			b.removePeg(y, x);
			b.putPeg(Game.playerIcon, y, x);
			p.health--;
			return true;
		}
		if (walls[y][x])
		{
			b.putPeg(Game.wallIcon, y, x);
			return true;
		}
		for (int i = 0; i < eggs.size(); i++)
		{	
			try
			{
				if (eggs.get(i).x == x && eggs.get(i).y == y)
				{
					b.removePeg(y, x);
					eggs.remove(i);
					return true;
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
			catch (Exception e)
			{
			}
		}
		return false;
	}
}
