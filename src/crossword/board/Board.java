package crossword.board;

import java.io.Serializable;
import java.util.*;

public class Board implements Serializable
{
	private static final long serialVersionUID = -7908016469453222492L;
	
	private BoardCell [][]board;
	
	public Board(int width, int height)
	{
		board = new BoardCell[width][height];
	}
	
	public Board copy() // tworze kopiê samej siebie
	{
		Board b = new Board(getWidth(), getHeight());
		
		for(int i=0; i<getWidth(); i++)
		{
			for(int j=0; j<getHeight(); j++)
			{
				b.board[i][j] = this.board[i][j];
			}
		}
		
		return b;
	}
	
	public int getWidth()
	{
		return board.length;
	}
	
	public int getHeight()
	{
		return board[0].length;
	}
	
	public BoardCell getCell(int x, int y) // pobiera pole, jeœli go nie by³o to go tworzy
	{
		if(x<0 || y<0 || x>=getWidth() || y>=getHeight())
			return null;
		
		if(board[x][y] == null)
			board[x][y] = new BoardCell();
		
		return board[x][y];
	}
	
	public void setCell(int x, int y, BoardCell c) // ustawia pole (nie u¿ywane)
	{
		if(x<0 || y<0 || x>=getWidth() || y>=getHeight())
			return;

		board[x][y] = c;
	}
	
	public LinkedList<BoardCell> getStartCells() // zwraca pola które mog¹ byæ startowe (nie u¿ywane)
	{
		LinkedList<BoardCell> matches = new LinkedList<BoardCell>();
		
		for(BoardCell[] ba: board)
		{
			for(BoardCell b: ba)
			{
				if(b.canBeVertStart() || b.canBeHorizStart())
					matches.add(b);
			}
		}
		
		return matches;
	}
	
	public String createPattern(int fromx, int fromy, int tox, int toy) // tworzy wyra¿enie regularne dla poziomego lub pionowego has³a
	{
		String regex = new String();
		
		for(int x=fromx; x<=tox; x++)
			for(int y=fromy; y<=toy; y++)
			{
				if(getCell(x, y).getContent() == null)
					regex += ".";
				else
					regex += getCell(x, y).getContent();

			}
		
		return regex;
	}
}
