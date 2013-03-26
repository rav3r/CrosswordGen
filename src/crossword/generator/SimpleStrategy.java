package crossword.generator;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.CwException;
import crossword.Direction;
import crossword.board.Board;
import crossword.dictionary.*;

public class SimpleStrategy extends Strategy
{
	private int itemsToGenerate; // liczba s��w kt�re trzeba jeszcze wygenerowa�
	private String secret; // rozwi�zanie (pierwsza kolumna)
	
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
		
		// losuj s�owo zaczynaj�ce si� na odpowiedni� literk� i je zwr��
		do
		{
			maxRands--;
			if(maxRands<0)
			{
				throw new CwException("Przekroczono limit pr�b wygenerowania krzy��wki, spr�buj ponownie albo zmie� s�ownik lub wymiary krzy��wki.");
			}
			String c = Character.toString(secret.charAt(secretLen-itemsToGenerate-1));
			Entry e = crossword.getCwDB().getRandom(c+".{0,"+(b.getWidth()-1)+"}"); // rozmiar s�owa >= 1
			
			if(e == null)
				throw new CwException("Nie mo�na znale�� odpowiedniej liczby hase� zaczynaj�cych si� na liter� \""+c+"\"");
			
			ent = new CwEntry(e.getWord(), e.getClue(), 0, secretLen-itemsToGenerate-1, Direction.HORIZ); // dodaj s�owo na odpowiednim miejscu
			
		} 
		while(crossword.contains(ent.getWord()));
		
		
		return ent;
	}
}
