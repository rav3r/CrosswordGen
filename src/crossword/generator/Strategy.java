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
	public void updateBoard(Board board, CwEntry entry, int id) // dodaj s�owo do krzy��wki
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
				
				// poziome has�o nie mo�e by� cz�ci� innego poziomego has�a
				
				c.disableHorizStart();
				c.disableHorizInner();
				c.disableHorizEnd();
				
				// pozycj� wy�ej nie mo�e si� ko�czy� pionowe has�o:
				
				c = board.getCell(x+ex, ey-1);
				if(c != null)
				{
					c.disableVertEnd();
				}
				
				// pozycj� ni�ej nie mo�e si� zaczyna� pionowe has�o:
				
				c = board.getCell(x+ex, ey+1);
				if(c != null)
				{
					c.disableVertStart();
				}
			}
			
			// na lewo nie mo�e by� nic:
			c = board.getCell(ex-1, ey);
			if(c != null)
			{
				c.disableEverything();
			}
			
			// na prawo te�:
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
				
				// pionowe has�o nie mo�e by� cz�ci� innego pionowego has�a
				
				c.disableVertStart();
				c.disableVertInner();
				c.disableVertEnd();
				
				// pozycj� z lewej nie mo�e si� ko�czy� poziome has�o:
				
				c = board.getCell(ex-1, y+ey);
				if(c != null)
				{
					c.disableHorizEnd();
				}
				
				// pozycj� z prawej nie mo�e si� zaczyna� poziome has�o:
				c = board.getCell(ex+1, y+ey);
				if(c != null)
				{
					c.disableHorizStart();
				}
			}
			
			// z g�ry nie mo�e by� nic:
			c = board.getCell(ex, ey-1);
			if(c != null)
			{
				c.disableEverything();
			}
			
			// z do�u te�:
			c = board.getCell(ex, ey+length);
			if(c!=null)
			{
				c.disableEverything();
			}
		}
	}
}
