package crossword.io;

import crossword.Crossword;

public interface Writer
{
	public void write(Crossword crossword);
	public long getUniqueID();
}
