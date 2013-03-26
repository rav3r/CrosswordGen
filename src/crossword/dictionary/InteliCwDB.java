package crossword.dictionary;

import java.io.Serializable;
import java.util.*;

public class InteliCwDB extends CwDB implements Serializable
{	
	private static final long serialVersionUID = -4455139357588205761L;

	public InteliCwDB()
	{
	}
	
	public InteliCwDB(String filename)
	{
		super(filename);
	}

	public LinkedList<Entry> findAll(String pattern) // odnajduje s³owa na podstawie wyra¿enia regularnego
	{
		LinkedList<Entry> matches = new LinkedList<Entry>();
		
		ListIterator<Entry> it = dict.listIterator();
		 
		while(it.hasNext())
		{
			Entry ent = it.next();
			if(ent.getWord().matches(pattern))
				matches.add(ent);
		}
		
		return matches;
	}
	
	public Entry getRandom(int length) // zwraca losowe s³owo o zadanej d³ugoœci
	{	
		LinkedList<Entry> matches = new LinkedList<Entry>();
		
		ListIterator<Entry> it = dict.listIterator();
		 
		while(it.hasNext())
		{
			Entry ent = it.next();
			if(ent.getWord().length() == length)
				matches.add(ent);
		}
		
		Random generator = new Random(Calendar.getInstance().getTimeInMillis());
		
		return matches.get(generator.nextInt(matches.size()));
	}
	
	public Entry getRandom(String pattern) // zwraca losowe s³owo pasuj¹ce do wzorca
	{
		LinkedList<Entry> matches = findAll(pattern);
		if(matches.isEmpty())
			return null;
		
		Random generator = new Random(Calendar.getInstance().getTimeInMillis());
		
		return matches.get(generator.nextInt(matches.size()));
	}
	
	public void add(String word, String clue) // dodaje s³owo do alfabetycznie posortowanej listy
	{
		Entry newEnt = new Entry(word.toLowerCase(), clue);
		
		ListIterator<Entry> it = dict.listIterator();
		
		int index = 0;
		
		while(it.hasNext())
		{
			Entry ent = it.next();
			if(ent.getWord().compareTo(newEnt.getWord())>0)
				break;
			index++;
		}
		
		dict.add(index, newEnt);
	}
	
	public void print() // (debug) wypisuje wszystkie s³owa z bazy
	{
		ListIterator<Entry> it = dict.listIterator();
		 
		while(it.hasNext())
		{
			Entry ent = it.next();
			System.out.printf(ent.getWord()+"\n");
		}
	}
}
