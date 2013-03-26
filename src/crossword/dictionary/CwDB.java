package crossword.dictionary;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;

public class CwDB implements Serializable
{
	private static final long serialVersionUID = -8529692098544971115L;
	
	protected LinkedList<Entry> dict = new LinkedList<Entry>();
	
	public CwDB()
	{
	}
	
	public CwDB(String filename)
	{
		createDB(filename);
	}
	
	public void saveDB(String filename) // zapisuje baze s³ów do pliku
	{
		 try
		 {
			 FileOutputStream fstream = new FileOutputStream(filename);
			
			 DataOutputStream out = new DataOutputStream(fstream);
			 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			 
			 for(Entry e:dict)
			 {
				 bw.write(e.getWord()+"\n");
				 bw.write(e.getClue()+"\n");
			 }
			
			 bw.close();
		 }
		 catch (Exception e)
		 {
			 JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		 }
	}
	
	public void createDB(String filename) // wczytuje baze s³ów z pliku
	{
		 try
		 {
			 dict.clear();
			 
			 FileInputStream fstream = new FileInputStream(filename);
			
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));

			  boolean endOfFile = false;
			  while(!endOfFile)
			  {
				  String word;
				  String clue;
				  if((word = br.readLine()) != null &&
				  	(clue = br.readLine()) != null)
				  {
				  	this.add(word, clue);
				  }
				  else
					  endOfFile = true;
			  }
			
			  in.close();
		 }
		 catch (Exception e)
		 {
			 JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
		 }
	}
	
	public void add(String word, String clue) // zapisuje baze s³ów do pliku
	{ 
		dict.add(new Entry(word, clue));
	}
	
	public Entry get(String word) // zwraca pozycjê zawieraj¹c¹ podane s³owo
	{
		for(Entry e:dict)
		{
			if(e.getWord().equals(word))
				return e;
		}
		return null;
	}
}
