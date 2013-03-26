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
	public static void main(String []args) // g³ówna funkcja programu
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
	private LinkedList<CwEntry> entries = new LinkedList<CwEntry>();		// has³a które s¹ ju¿ w krzy¿ówce
	private Board b = new Board(5,5);										// wysokoœæ i szerokoœæ planszy to domyœlnie 5x5
	private InteliCwDB cwdb;												// inteligentna baza hase³ i odpowiedzi
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
	
	public Iterator<CwEntry> getROEntryIter() // sta³y iterator, rzuca wyj¹tki gdy próbuje siê zmieniæ dane na które wskazuje
	{
		return Collections.unmodifiableCollection(entries).iterator();
	}
	
	public Board getBoard()
	{
		return b;
	}
	
	public Board getBoardCopy() // (nie u¿ywane)
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
	
	public boolean contains(String word) // czy mamy ju¿ w krzy¿ówce takie s³owo?
	{
		ListIterator<CwEntry> it = entries.listIterator();
		 
		while(it.hasNext())
		{
			if(it.next().getWord().matches(word))
				return true;
		}
		
		return false;
	}
	
	public final void addCwEntry(CwEntry cwe, Strategy s, int id) // dodaj nowe s³owo do krzy¿ówki
	{
		entries.add(cwe);
		s.updateBoard(b,cwe, id);
	}
	
	public final void generate(Strategy s, int width, int height) throws CwException // wygeneruj krzy¿ówkê o rozmiarze width x height u¿ywaj¹c strategii s
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
			throw new CwException("Nie uda³o siê dodaæ ¿adnego has³a do krzy¿ówki, mo¿liwe ¿e baza danych jest zbyt ma³a.");
	}
	
	public void print() // (debug) wypisywanie hase³ z krzy¿ówki
	{
		ListIterator<CwEntry> it = entries.listIterator();
		 
		while(it.hasNext())
		{
			CwEntry ent = it.next();
			System.out.printf(ent.getWord()+"\n");
		}
	}
}
