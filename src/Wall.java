
public class Wall 
{
	int y;
	int x;
	Board b;
	/**
	 * Creating wall at given position on board
	 */
	public Wall (Board b, int y, int x)
	{
		this.y = y;
		this.x = x;
		this.b = b;
		if (Game.mario)
		{
			b.putPeg("marbrick", y, x);
		}
		else
		{
			b.putPeg("wall", y, x);
		}
	}
}
