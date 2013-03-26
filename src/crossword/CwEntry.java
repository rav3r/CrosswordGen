package crossword;

import java.io.Serializable;

import crossword.dictionary.Entry;

public class CwEntry extends Entry implements Serializable
{
	private static final long serialVersionUID = -6368316821955854725L;
	
	private int x;
	private int y;
	private Direction dir; // poziomo lub pionowo
	
	CwEntry()
	{
		super();
	}
	
	public CwEntry(String word, String clue, int _x, int _y, Direction _dir)
	{
		super(word, clue);
		x = _x;
		y = _y;
		dir = _dir;
	}
	
	public Direction getDir()
	{
		return dir;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
