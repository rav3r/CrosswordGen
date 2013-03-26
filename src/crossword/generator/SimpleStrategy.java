package crossword.generator;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.CwException;
import crossword.Direction;
import crossword.board.Board;
import crossword.dictionary.*;

public class SimpleStrategy extends Strategy
{
	private int itemsToGenerate; // liczba s³ów które trzeba jeszcze wygenerowaæ
	private String secret; // rozwi¹zanie (pierwsza kolumna)
	
	public SimpleStrategy(Crossword crossword, int secretLength)
	{
		secret = crossword.getCwDB().getRandom(secretLength).getWord();
		itemsToGenerate = secret.length();
	}
	
	public CwEntry findEntry(Crossword crossword) throws CwException
	{
		if(itemsToGenerate<=0)
			return null;
		itemsToGenerate--;
		
		CwEntry ent = null;
		
		int maxRands = 100;
		int secretLen = secret.length();
		Board b = crossword.getBoard();
		
		// losuj s³owo zaczynaj¹ce siê na odpowiedni¹ literkê i je zwróæ
		do
		{
			maxRands--;
			if(maxRands<0)
			{
				throw new CwException("Przekroczono limit prób wygenerowania krzy¿ówki, spróbuj ponownie albo zmieñ s³ownik lub wymiary krzy¿ówki.");
			}
			String c = Character.toString(secret.charAt(secretLen-itemsToGenerate-1));
			Entry e = crossword.getCwDB().getRandom(c+".{0,"+(b.getWidth()-1)+"}"); // rozmiar s³owa >= 1
			
			if(e == null)
				throw new CwException("Nie mo¿na znaleŸæ odpowiedniej liczby hase³ zaczynaj¹cych siê na literê \""+c+"\"");
			
			ent = new CwEntry(e.getWord(), e.getClue(), 0, secretLen-itemsToGenerate-1, Direction.HORIZ); // dodaj s³owo na odpowiednim miejscu
			
		} 
		while(crossword.contains(ent.getWord()));
		
		
		return ent;
	}
}
