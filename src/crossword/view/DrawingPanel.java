package crossword.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.util.Iterator;

import javax.swing.*;

import crossword.Crossword;
import crossword.CwEntry;
import crossword.Direction;
import crossword.board.Board;

public class DrawingPanel extends JPanel implements Printable
{
	private static final long serialVersionUID = -3137231706923337817L;
	private Crossword crossword;
	private boolean resolve = false;
	
	public void setResolved(boolean resolved) // rysowaæ odpowiedzi?
	{
		resolve = resolved;
	}
	
	DrawingPanel(Board b, Crossword c)
	{
		crossword = c;
	}
	
	public void setCrossword(Crossword c)
	{
		crossword = c;
	}
	
	public void paint(Graphics g) // rysowanie krzy¿ówki
	{	
		Font defaultFont = g.getFont();
		Font arial = new Font("Arial", 0, 10);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		
		final int cellWidth = 30;
		
		Board board = crossword.getBoard();
		
		final int sx = 10;
		final int sy = 10;
		
		for(int j=0; j<board.getHeight(); j++)
		{
			for(int i=0; i<board.getWidth(); i++)
			{
				if(board.getCell(i, j).getContent() != null)
				{
					if(resolve) // rozwi¹¿ = wpisz literkê do tej kratki
						g.drawString(board.getCell(i, j).getContent().toUpperCase(),
								sx+i*cellWidth+cellWidth/3, sy+(j+1)*cellWidth-cellWidth/3);
					g.drawRect(sx+i*cellWidth, sy+j*cellWidth, cellWidth-2, cellWidth-2); // kwadrat
					if(board.getCell(i,j).getWordId() != -1) // wypisz numerek has³a
					{
						g.setFont(arial);
						int id = board.getCell(i,j).getWordId();
						g.drawString(""+id, sx+i*cellWidth+2, sy+j*cellWidth+12);
						g.setFont(defaultFont);
					}
				}
			}
		}
		
		final int kx = 10; 									// pocz¹tek odpowiedzi (x)
		final int ky = 30 + board.getHeight()*cellWidth; 	// pocz¹tek odpowiedzi (y)
	
		
		// wypisywanie pytañ:
		Iterator<CwEntry> it = crossword.getROEntryIter();
		
		boolean anyHoriz = false; // s¹ jakieœ poziome?
		boolean anyVert = false;  // s¹ jakieœ pionowe?
		
		while(it.hasNext())
		{
			if(it.next().getDir() == Direction.HORIZ)
				anyHoriz = true;
			else
				anyVert = true;
		}
		
		g.setFont(arial); // ustaw ma³y font
		
		int posy = 0;
		final int enterPx = 12;
		// pisz poziome pytania
		if(anyHoriz)
		{
			it = crossword.getROEntryIter();
			g.drawString("Poziomo:", kx, ky);
			posy += enterPx;
			for(int i=1; it.hasNext(); i++)
			{
				CwEntry ent = it.next();
				if(ent.getDir() == Direction.HORIZ)
				{
					g.drawString(i+"). "+ent.getClue(), kx, ky+posy);
					posy += enterPx;
				}
			}
			posy += enterPx;
		}
		// pisz pionowe pytania
		if(anyVert)
		{
			it = crossword.getROEntryIter();
			g.drawString("Pionowo:", kx, ky+posy);
			posy += enterPx;
			for(int i=1; it.hasNext(); i++)
			{
				CwEntry ent = it.next();
				if(ent.getDir() == Direction.VERT)
				{
					g.drawString(i+"). "+ent.getClue(), kx, ky+posy);
					posy += enterPx;
				}
			}
		}
		
	}
	
	 public int print(Graphics g, PageFormat pf, int page) throws PrinterException // drukowanie krzy¿ówki (callback)
     {
		 if (page > 0) 
			 return NO_SUCH_PAGE;

		 Graphics2D g2d = (Graphics2D)g;
		 g2d.translate(pf.getImageableX(), pf.getImageableY());


		 paint(g);

		 return PAGE_EXISTS;
	 }
	
}
