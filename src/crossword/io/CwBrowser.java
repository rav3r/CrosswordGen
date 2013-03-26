package crossword.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.LinkedList;

import crossword.Crossword;

public class CwBrowser
{
	private CwReader reader;
	private CwWriter writer;
	
	public CwBrowser(String dir)
	{
		reader = new CwReader(dir);
		writer = new CwWriter(dir);
	}
	
	public void write(Crossword crossword) // oddeleguj do writera
	{
		writer.write(crossword);
	}
	
	public Crossword read(String file) // oddeleguj do readera
	{
		return reader.read(file);
	}
	
	public void showAll() // wyœwietla wszystkie ID krzy¿ówek z danego katalogu
	{
		LinkedList<Crossword> cws = reader.getAllCws();
		
		for(Crossword c: cws)
		{
			System.out.println(c.getID());
		}
	}
	
	class CwReader implements Reader
	{
		private String dirname;
		
		CwReader(String dir)
		{
			dirname = dir;
		}
		
		public LinkedList<Crossword> getAllCws() // zwraca wszystkie krzy¿ówki z danego katalogu
		{
			LinkedList<Crossword> cwds = new LinkedList<Crossword>();
			
			File dir = new File(dirname);

			FilenameFilter filter = new FilenameFilter()
			{
			    public boolean accept(File dir, String name)
			    {
			        return name.endsWith(".cwdb"); // tylko pliki koñcz¹ce siê na cwdb
			    }
			};
			
			File[] files = dir.listFiles(filter);
			
			for(File f:files)
			{
				try 
				{
					FileInputStream fin = new FileInputStream(f.getAbsolutePath());
					ObjectInputStream ois = new ObjectInputStream(fin);
					Crossword crossword = (Crossword)ois.readObject();
					crossword.setID(new Integer(f.getName().substring(0, f.getName().length()-5)).intValue());
					cwds.add(crossword);
					ois.close();
				}
				catch (Exception e)
				{ 
					e.printStackTrace(); 
				}
			}
			
			return cwds;
		}
		
		Crossword read(String filename) // wczytuje pojedyncz¹ krzy¿ówkê
		{
			try 
			{
				FileInputStream fin = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fin);
				Crossword crossword = (Crossword)ois.readObject();
				ois.close();
				return crossword;
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
			return null;
		}
	}
	
	class CwWriter implements Writer
	{
		String dirname;
		
		CwWriter(String dir)
		{
			dirname = dir;
		}
		
		public void write(Crossword crossword) // zapisuje krzy¿ówkê do pliku o wygenerowanej nazwie na podstawie obecnego czasu
		{
			try
			{
				FileOutputStream fout = new FileOutputStream(dirname+"/"+getUniqueID()+".cwdb");
			    ObjectOutputStream oos = new ObjectOutputStream(fout);
			    oos.writeObject(crossword);
			    oos.close();
			}
			catch (Exception e)
			{ 
				e.printStackTrace();
			}
		}

		public long getUniqueID()
		{
			return Calendar.getInstance().getTimeInMillis();
		}
	}
}
