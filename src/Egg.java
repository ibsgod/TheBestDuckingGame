public class Egg //class for Egg
{
	int y;
	int x;
	Board b;
	Player p;
	/**
	 * Creating egg at given position on board
	 */
	public Egg (Player p, Board b, int y, int x)
	{
		//initializing variables
		this.y = y;
		this.x = x;
		this.b = b;
		this.p = p;
		//changing icons if mario mode 
		if (Game.mario)
		{
			Game.eggIcon = "yoshegg";
			Game.wallIcon = "marbrick";
		}
		b.putPeg(Game.eggIcon, y, x);
	}
	/**Tick method controlling movement 
	 */
	public void tick()
	{
		if (x < b.getColumns()-1)
		{
			b.removePeg(y, x++);
			b.putPeg(Game.eggIcon, y, x);
		}
		if (x == b.getColumns()-1)
		{
			b.removePeg(y, x);
			p.eggs.remove(this);
		}
	}
	/**Destroy the egg when hit by a wall
	 * @param walls array of walls on the board 
	 */
	public void checkHit(boolean walls[][])
	{
		//delete egg if it hits a wall
		if (walls[y][x])
		{
			b.removePeg(y, x);
			b.putPeg(Game.wallIcon, y, x);
			p.eggs.remove(this);
		}
	}
}
