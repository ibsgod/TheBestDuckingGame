import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**  Board GUI for implementation with various games
 *   Author: Kirill Levin, Troy Vasiga, Chris Ingram
 *      
 */

public class Board extends JPanel //Board class
{
  //variables for painting grid
  private static final int X_DIM = 30;
  private static final int Y_DIM = 30;
  public static final int X_OFFSET = 30;
  private static final int Y_OFFSET = 30;
  private static final double MIN_SCALE = 0.25;
  private static final int GAP = 150;
  private static final int FONT_SIZE = 20;
  
  public static JFrame f;
  private static Player p;
  public int textx;
  public int texty;
   
  // Preset images for pieces
  private static final ImageIcon[] PIECES = new ImageIcon[19];     
  // String used to indicate each image
  private static final String[] PIECE_NAMES = {"duck", "piig", "egg", "wall", "shep", "bobomb","yoshi", "yoshegg","marbrick","goomba","egg00","egg01", "egg02","egg10", "egg11","egg12", "egg20","egg21", "egg22"};
  // 2D ARRAY TO STORE ALL OF THE PIECE IMAGES THAT ARE ON THE BOARD
  private ImageIcon[][] grid;
  private String message = "";
  private int numLines = 0;
  private int[][] line = new int[4][100];  // maximum number of lines is 100
  private int originalWidth;
  private int originalHeight;
  public double scale;
  
  
  /** A constructor to build a 2D board.
   */
  public Board (int rows, int cols)
  {
    super( true );
    f = new JFrame( "Board game" ); //creating JFrame for the board
    originalWidth = 2*X_OFFSET+X_DIM*cols;
    originalHeight = 2*Y_OFFSET+Y_DIM*rows+GAP+FONT_SIZE+100;
    f.setBounds( 0, 0, originalWidth, originalHeight );                                   
    f.setPreferredSize(new Dimension(originalWidth, originalHeight) );                                   
    f.setResizable(false);
  	setFocusable(true);
  	f.setBackground(Color.black);
    this.grid = new ImageIcon[cols][rows];
	
	//key listener that will call Player's keyPressed method or pause game
    this.addKeyListener(new KeyListener()
	{
		public void keyPressed(KeyEvent e)
		{
			p.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e)
		{
			p.keyReleased(e);
			if (e.getKeyCode() == KeyEvent.VK_SPACE)
			{
				if (!Game.pause)
				{
					Game.pause = true;
				}
			}
		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}
	}
	);
    
        // PUT ALL OF THE IMAGES OF THE PIECES INTO AN ARRAY ===========================
        PIECES [0] = new ImageIcon(getClass().getResource("duck.png"));
        PIECES [1] = new ImageIcon(getClass().getResource("piig.png"));
        PIECES [2] = new ImageIcon(getClass().getResource("egg.png"));
        PIECES [3] = new ImageIcon(getClass().getResource("wall.jpg"));
        PIECES [4] = new ImageIcon(getClass().getResource("shep.png"));
        PIECES [5] = new ImageIcon(getClass().getResource("bobomb.png"));
        PIECES [6] = new ImageIcon(getClass().getResource("yoshi.png"));
        PIECES [7] = new ImageIcon(getClass().getResource("yoshegg.png"));
        PIECES [8] = new ImageIcon(getClass().getResource("marbrick.png"));
        PIECES [9] = new ImageIcon(getClass().getResource("goomba.png"));
        PIECES [10] = new ImageIcon(getClass().getResource("egg00.png"));
        PIECES [11] = new ImageIcon(getClass().getResource("egg01.png"));
        PIECES [12] = new ImageIcon(getClass().getResource("egg02.png"));
        PIECES [13] = new ImageIcon(getClass().getResource("egg10.png"));
        PIECES [14] = new ImageIcon(getClass().getResource("egg11.png"));
        PIECES [15] = new ImageIcon(getClass().getResource("egg12.png"));
        PIECES [16] = new ImageIcon(getClass().getResource("egg20.png"));
        PIECES [17] = new ImageIcon(getClass().getResource("egg21.png"));
        PIECES [18] = new ImageIcon(getClass().getResource("egg22.png"));
        
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setContentPane( this );
	    f.pack();
	    f.setVisible(true);
  }
 
  /** Painting text at bottom of screen
   */
  private void paintText(Graphics g)
  {
    g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, (int)(Math.round(FONT_SIZE*scale + 15))));
    
    int x = (int)Math.round(X_OFFSET*scale);
    int y = (int)Math.round((Y_OFFSET+Y_DIM*grid[0].length)*scale + GAP + 50  ) ;
    g.setColor(Game.col);
    g.fillRect(x,y, this.getSize().width, (int)Math.round(GAP+FONT_SIZE*scale) );
    g.setColor( Color.white );
    g.drawString(message, x, y + (int)Math.round(FONT_SIZE*scale));
    texty = y;
    textx = x + (int)Math.round(GAP+FONT_SIZE*scale);
  }
  /** Painting grid and icons
   */
  private void paintGrid(Graphics g)
  {
	    for (int i = 0; i < this.grid.length; i++)
	    {
	      for (int j = 0; j < this.grid[i].length; j++)
	      {    
			  int gradient = 255 - Math.min(Math.abs(j - 7), Math.abs(j - 8)) * 10; //gradually change rgb to create the gradients
			  if (i == 0)
			  {
				  g.setColor(new Color(0, 0, gradient)); //setting leftmost column to the blue pond
			  }
			  else
			  {
				  g.setColor(new Color(0, gradient, 0)); //rest should be green for grass
			  }
	        int curX = (int)Math.round((X_OFFSET+X_DIM*i)*scale);
	        int curY = (int)Math.round((Y_OFFSET+Y_DIM*j)*scale) ;
	        int nextX = (int)Math.round((X_OFFSET+X_DIM*(i+1))*scale);
	        int nextY = (int)Math.round((Y_OFFSET+Y_DIM*(j+1))*scale);
	        int deltaX = nextX-curX; 
	        int deltaY = nextY-curY;
	                                   
	        g.fillRect( curX, curY, deltaX, deltaY );
	        // PUT THE IMAGE ON THE SQUARE IF ONE IS SUPPOSED TO BE THERE
	        ImageIcon curImage = this.grid[i][j];
	        if (curImage != null) // Draw pegs if they exist
	        {
	          curImage.paintIcon(this, g, curX, curY);         
	        }
	      }
	    }
	    ((Graphics2D) g).setStroke( new BasicStroke(5f) );
	  
	    int curX = (int)Math.round(X_OFFSET*scale);
	    int curY = (int)Math.round(Y_OFFSET*scale);
	    int nextX = (int)Math.round((X_OFFSET+X_DIM*grid.length)*scale);
	    int nextY = (int)Math.round((Y_OFFSET+Y_DIM*grid[0].length)*scale);
	    g.drawLine(curX, curY, nextX, curY);
	    g.drawLine(curX, nextY, nextX, nextY);
  }
  /** Paints a line between 2 give coordinates
   */
  private void drawLine(Graphics g)
  {
    for (int i =0; i < numLines; i++ ) 
    {
      ((Graphics2D) g).setStroke( new BasicStroke( 5.0f*(float)scale) );
      g.drawLine( (int)Math.round((X_OFFSET+X_DIM/2.0+line[0][i]*X_DIM)*scale), 
                  (int)Math.round((Y_OFFSET+Y_DIM/2.0+line[1][i]*Y_DIM)*scale), 
                  (int)Math.round((X_OFFSET+X_DIM/2.0+line[2][i]*X_DIM)*scale), 
                  (int)Math.round((Y_OFFSET+Y_DIM/2.0+line[3][i]*Y_DIM)*scale) );
    }
  }
  
  /**Convert a String to the corresponding ImageIcon, do nothing if image not found
   */
  private ImageIcon convertImageIcon(String thePiece )
  {
    for( int i=0; i<PIECE_NAMES.length; i++ )
    {
      if( PIECE_NAMES[i].equalsIgnoreCase( thePiece ) )
        return PIECES[i];
    }
    return null;
  }
 
  
  /** The method that draws everything
   */
  public void paintComponent( Graphics g ) 
  {
    this.setScale();
    this.paintGrid(g);
    this.drawLine(g);
    this.paintText(g);
  }
  
  /** Setting the scale for the window 
   */
  public void setScale()
  {
    double width = (0.0+this.getSize().width) / this.originalWidth;
    double height = (0.0+this.getSize().height) / this.originalHeight;
    this.scale = Math.max( Math.min(width,height), MIN_SCALE ); 
  }
  
  /** Sets the message to be displayed under the board
   * @param theMessage The message to be painted
   */
  public void displayMessage(String theMessage)
  {
    message = theMessage;
    this.repaint();
  }
  
  
  /** Put an image on the gameboard.
   * @param theImage String for the image
   * @param row x coordinate to be painted in
   * @param col y coordinate to be painted in
   */
  public void putPeg(String theImage, int row, int col)
  {
    this.grid[col][row] = this.convertImageIcon(theImage );
    this.repaint();
  }
  
  
  /** Remove an image from the gameboard.
   * @param row x coordinate to have image removed from
   * @param col y coordinate to have image removed from
   */
  public void removePeg(int row, int col)
  {
    this.grid[col][row] = null;
    repaint();
  }
  
   
  /** Draws a line on the board using the given co-ordinates as endpoints
   */
  public void drawLine(int row1, int col1, int row2, int col2)
  {
    this.line[0][numLines]=col1;
    this.line[1][numLines]=row1;
    this.line[2][numLines]=col2;
    this.line[3][numLines]=row2;
    this.numLines++;
    repaint();
  }
  
  /**
   * Sets player to do keyPressed actions 
   * @param p Player to be set
   */
  public void setPlayer(Player p)
  {
	  Board.p = p;
  }
  /**
   * @return the width of the grid
   */
  public int getColumns()
  {
    return this.grid.length;
  }
  /**
   * @return the height of the grid
   */
  public int getRows()
  {
    return this.grid[0].length;
  }

}
