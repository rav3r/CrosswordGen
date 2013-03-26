package crossword;
import java.io.Serializable;
import java.util.*;

import javax.swing.JOptionPane;

import crossword.board.Board;
import crossword.dictionary.*;
import crossword.generator.Strategy;
import crossword.view.*;

public class Crossword implements Serializable
{
	public static void main(String []args) // g��wna funkcja programu
	{
		try
		{
			MainFrame mf = new MainFrame();
			mf.run(new Crossword());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
		}
	}
	
	private static final long serialVersionUID = 1L;
	private LinkedList<CwEntry> entries = new LinkedList<CwEntry>();		// has�a kt�re s� ju� w krzy��wce
	private Board b = new Board(5,5);										// wysoko�� i szeroko�� planszy to domy�lnie 5x5
	private InteliCwDB cwdb;												// inteligentna baza hase� i odpowiedzi
	private long ID;
	
	Crossword()
	{
		ID = -1;
		cwdb = new InteliCwDB("cwdb.txt");
	}
	
	public void setID(long id)
	{
		ID = id;
	}
	
	public long getID()
	{
		return ID;
	}
	
	public Iterator<CwEntry> getROEntryIter() // sta�y iterator, rzuca wyj�tki gdy pr�buje si� zmieni� dane na kt�re wskazuje
	{
		return Collections.unmodifiableCollection(entries).iterator();
	}
	
	public Board getBoard()
	{
		return b;
	}
	
	public Board getBoardCopy() // (nie u�ywane)
	{
		return b.copy();
	}
	
	public InteliCwDB getCwDB()
	{
		return cwdb;
	}
	
	public void setCwDB(InteliCwDB newCwdb)
	{
		cwdb = newCwdb;
	}
	
	public boolean contains(String word) // czy mamy ju� w krzy��wce takie s�owo?
	{
		ListIterator<CwEntry> it = entries.listIterator();
		 
		while(it.hasNext())
		{
			if(it.next().getWord().matches(word))
				return true;
		}
		
		return false;
	}
	
	public final void addCwEntry(CwEntry cwe, Strategy s, int id) // dodaj nowe s�owo do krzy��wki
	{
		entries.add(cwe);
		s.updateBoard(b,cwe, id);
	}
	
	public final void generate(Strategy s, int width, int height) throws CwException // wygeneruj krzy��wk� o rozmiarze width x height u�ywaj�c strategii s
	{	
		b = new Board(width,height);
		entries.clear();
		
		CwEntry e = null;
		int id = 0;
		while((e = s.findEntry(this)) != null)
		{
			id++;
			addCwEntry(e, s, id);
		}
		
		if(id == 0)
			throw new CwException("Nie uda�o si� doda� �adnego has�a do krzy��wki, mo�liwe �e baza danych jest zbyt ma�a.");
	}
	
	public void print() // (debug) wypisywanie hase� z krzy��wki
	{
		ListIterator<CwEntry> it = entries.listIterator();
		 
		while(it.hasNext())
		{
			CwEntry ent = it.next();
			System.out.printf(ent.getWord()+"\n");
		}
	}
}
