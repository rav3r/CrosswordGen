package crossword.generator;

import java.util.Calendar;
import java.util.Random;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.Direction;
import crossword.board.Board;
import crossword.dictionary.Entry;

public class CrossStrategy extends Strategy
{	
	public CwEntry findEntry(Crossword crossword) // dopasowuje nowe s�owo do krzy��wki, has�a mog� si� przecina�
	{	
		Board b = crossword.getBoard();
		
		Random rand = new Random(Calendar.getInstance().getTimeInMillis());
		
		for(int k=0; k<100; k++) // 100 razy spr�buj co� doda�
		for(int i=rand.nextInt(b.getWidth()); i<b.getWidth(); i++) // wylosuj pozycj� x
		{
			for(int j=rand.nextInt(b.getHeight()); j<b.getHeight(); j++) // wylosuj pozycje y
			{
				boolean horiz = (rand.nextInt() % 2) == 0; // pionowo czy poziomo?
				
				if((b.getCell(i, j).canBeHorizStart() && horiz) || (b.getCell(i, j).canBeVertStart() && !horiz)) // pole mo�e by� pocz�tkiem krzy��wki?
				{
					String regex = null;
					int maxLength = 0;
					
					if(horiz)
					{
						regex = b.createPattern(i, j, b.getWidth()-1, j);
						for(int x=i; x<b.getWidth(); x++) // szukaj maksymalnego rozmiaru s�owa
						{
							if(b.getCell(x, j).canBeHorizInner() == false)
								break;
							maxLength++;
						}
						
					} else
					{
						regex = b.createPattern(i, j, i, b.getHeight()-1);
						for(int y=j; y<b.getHeight(); y++) // szukaj maksymalnego rozmiaru s�owa
						{
							if(b.getCell(i, y).canBeHorizInner() == false)
								break;
							maxLength++;
						}
					}
					
					if(maxLength < 2) // nie dopuszczamy jednej litery
						continue;
					
					for(int p=maxLength; p>0; p--)
					{
						int currLength = p;
						
						if(currLength==0)
							continue;
						
						Entry ent = crossword.getCwDB().getRandom("^"+regex.substring(0,currLength)+"$"); // szukaj s�owa kt�re pasuje do krzy��wki
						
						// je�li znaleziono s�owo kt�rego jeszcze nie ma w krzy��wce, oraz je�li to s�owo mo�na doda� do krzy��wki to je zwr��
						if(ent != null && !crossword.contains(ent.getWord()) && 
								((horiz && b.getCell(i+currLength-1,j).canBeHorizEnd()) || (!horiz && b.getCell(i,j+currLength-1).canBeVertEnd())))
						{
							return new CwEntry(ent.getWord(), ent.getClue(), i, j, horiz?Direction.HORIZ:Direction.VERT);
						}
					}
				}
			}
		}
		
		
		return null;
	}

}
