package crossword.board;

import java.io.Serializable;

public class BoardCell implements Serializable
{
	private static final long serialVersionUID = -6384405389445599536L;
	
	private String content;
	private int wordId = -1;
	private boolean could[][]; // (horiz,vert) x (start, ineer, end)
	
	BoardCell()
	{
		could = new boolean[2][3];
		
		for(int i=0; i<2; i++)
			for(int j=0; j<3; j++)
				could[i][j] = true; // na pocz¹tku pole mo¿e byæ wszystkim
	}
	
	// gettery i settery
	
	public void disableHorizStart()
	{
		could[0][0] = false;
	}
	
	public void disableHorizInner()
	{
		could[0][1] = false;
	}

	public void disableHorizEnd()
	{
		could[0][2] = false;
	}
	
	public void disableVertStart()
	{
		could[1][0] = false;
	}
	
	public void disableVertInner()
	{
		could[1][1] = false;
	}

	public void disableVertEnd()
	{
		could[1][2] = false;
	}
	
	public void disableEverything()
	{
		for(int i=0; i<2; i++)
			for(int j=0; j<3; j++)
				could[i][j] = false;
	}
	
	public boolean canBeHorizStart()
	{
		return could[0][0];
	}
	
	public boolean canBeHorizInner()
	{
		return could[0][1];
	}
	
	public boolean canBeHorizEnd()
	{
		return could[0][2];
	}
	
	public boolean canBeVertStart()
	{
		return could[1][0];
	}
	
	public boolean canBeVertInner()
	{
		return could[1][1];
	}
	
	public boolean canBeVertEnd()
	{
		return could[1][2];
	}
	
	public void setContent(String newContent)
	{
		content = newContent;
	}
	
	public String getContent()
	{
		return content;
	}

	public int getWordId() 
	{
		return wordId;
	}

	public void setWordId(int wordId)
	{
		this.wordId = wordId;
	}
}
