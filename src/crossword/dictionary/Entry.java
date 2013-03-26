package crossword.dictionary;

import java.io.Serializable;

public class Entry implements Serializable
{
	private static final long serialVersionUID = -25544540890301085L;
	
	private String word;
	private String clue;
	
	public Entry()
	{
	}
	
	public Entry(String _word, String _clue)
	{
		word = _word;
		clue = _clue;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public String getClue()
	{
		return clue;
	}
}
