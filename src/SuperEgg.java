
public class SuperEgg //class for the super egg
{
	int y;
	int x;
	Board b;
	Player p;
	/**
	 * Creating egg at given position on board
	 */
	public SuperEgg (Player p, int y, int x, Board b)
	{
		//initializing variables
		this.y = y;
		this.x = x;
		this.b = b;
		this.p = p;
		draw(y, x);
	}
	/** Creating super egg at this position on the board
	 * @param y the top y coordinate of the super egg
	 * @param x the leftmost x coordinate of the super egg
	 */
	public void draw (int y, int x) //method for drawing the superegg across a 3 by 3 grid
	{
		b.putPeg("egg00", y, x);
		b.putPeg("egg01", y, x+1);
		b.putPeg("egg02", y, x+2);
		b.putPeg("egg10", y+1, x);
		b.putPeg("egg11", y+1, x+1);
		b.putPeg("egg12", y+1, x+2);
		b.putPeg("egg20", y+2, x);
		b.putPeg("egg21", y+2, x+1);
		b.putPeg("egg22", y+2, x+2);
	}
	/** Controlling movement of egg
	 */
	public void tick() //moving all 9 pieces of the superegg at a time
	{
		b.removePeg(y, x);
		b.removePeg(y, x+1);
		b.removePeg(y, x+2);
		b.removePeg(y+1, x);
		b.removePeg(y+1, x+1);
		b.removePeg(y+1, x+2);
		b.removePeg(y+2, x);
		b.removePeg(y+2, x+1);
		b.removePeg(y+2, x+2);
		x++;
		if (x + 3 < b.getColumns())
		{
			b.putPeg("egg00", y, x);
			b.putPeg("egg01", y, x+1);
			b.putPeg("egg02", y, x+2);
			b.putPeg("egg10", y+1, x);
			b.putPeg("egg11", y+1, x+1);
			b.putPeg("egg12", y+1, x+2);
			b.putPeg("egg20", y+2, x);
			b.putPeg("egg21", y+2, x+1);
			b.putPeg("egg22", y+2, x+2);
		}
		else
		{
			p.supereggs.remove(this);
			return;
		}
		//destroying walls if hit by superegg
		if (p.allwalls[y][x])
		{
			b.removePeg(y, x);
			p.allwalls[y][x] = false;
		}
		if (p.allwalls[y+1][x])
		{
			b.removePeg(y+1, x);
			p.allwalls[y+1][x] = false;
		}
		if (p.allwalls[y+2][x])
		{
			b.removePeg(y+2, x);
			p.allwalls[y+2][x] = false;
		}
	}
	
}
