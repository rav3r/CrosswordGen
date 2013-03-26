package crossword.generator;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.CwException;
import crossword.Direction;
import crossword.board.Board;
import crossword.board.BoardCell;



public abstract class Strategy
{
	public abstract CwEntry findEntry(Crossword crossword) throws CwException;
	public void updateBoard(Board board, CwEntry entry, int id) // dodaj s³owo do krzy¿ówki
	{
		int length = entry.getWord().length();
		
		int ex = entry.getX();
		int ey = entry.getY();
		
		String word = entry.getWord();
		
		board.getCell(ex, ey).setWordId(id);
		
		if(entry.getDir() == Direction.HORIZ)
		{
			BoardCell c = null;
			c = board.getCell(ex, ey);
			c.disableVertStart();
			
			for(int x=0; x<length; x++)
			{
				c = board.getCell(x+ex, ey);
				c.setContent(Character.toString(word.charAt(x)));
				
				// poziome has³o nie mo¿e byæ czêœci¹ innego poziomego has³a
				
				c.disableHorizStart();
				c.disableHorizInner();
				c.disableHorizEnd();
				
				// pozycjê wy¿ej nie mo¿e siê koñczyæ pionowe has³o:
				
				c = board.getCell(x+ex, ey-1);
				if(c != null)
				{
					c.disableVertEnd();
				}
				
				// pozycjê ni¿ej nie mo¿e siê zaczynaæ pionowe has³o:
				
				c = board.getCell(x+ex, ey+1);
				if(c != null)
				{
					c.disableVertStart();
				}
			}
			
			// na lewo nie mo¿e byæ nic:
			c = board.getCell(ex-1, ey);
			if(c != null)
			{
				c.disableEverything();
			}
			
			// na prawo te¿:
			c = board.getCell(ex+length, ey);
			if(c != null)
			{
				c.disableEverything();
			}
		} else // VERTI
		{
			BoardCell c = null;
			c = board.getCell(ex, ey);
			c.disableHorizStart();
			
			for(int y=0; y<length; y++)
			{
				c = board.getCell(ex, y+ey);
				c.setContent(Character.toString(word.charAt(y)));
				
				// pionowe has³o nie mo¿e byæ czêœci¹ innego pionowego has³a
				
				c.disableVertStart();
				c.disableVertInner();
				c.disableVertEnd();
				
				// pozycjê z lewej nie mo¿e siê koñczyæ poziome has³o:
				
				c = board.getCell(ex-1, y+ey);
				if(c != null)
				{
					c.disableHorizEnd();
				}
				
				// pozycjê z prawej nie mo¿e siê zaczynaæ poziome has³o:
				c = board.getCell(ex+1, y+ey);
				if(c != null)
				{
					c.disableHorizStart();
				}
			}
			
			// z góry nie mo¿e byæ nic:
			c = board.getCell(ex, ey-1);
			if(c != null)
			{
				c.disableEverything();
			}
			
			// z do³u te¿:
			c = board.getCell(ex, ey+length);
			if(c!=null)
			{
				c.disableEverything();
			}
		}
	}
}
