package crossword.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.print.*;
import java.io.File;

import javax.swing.*;

import crossword.Crossword;
import crossword.board.Board;
import crossword.generator.CrossStrategy;
import crossword.generator.SimpleStrategy;
import crossword.generator.Strategy;
import crossword.io.CwBrowser;

public class MainFrame implements ActionListener, ItemListener
{
	private JSpinner heightSpinner;
	private JSpinner widthSpinner;
	private Crossword crossword;
	private DrawingPanel drawing;
	private JCheckBox resolveCheckbox;
	private JCheckBox simpleCwCheckbox;
	private JFrame frame;
	
	public void run(Crossword _crossword) // odpal aplikacjê
	{
		// tworzenie interfejsu
		crossword = _crossword;
		Board board = _crossword.getBoard();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("generator krzy¿ówek");
		
		// menu
		
		JPanel panel = new JPanel();
		
		// nowa krzy¿ówka
		JPanel newCrossword = new JPanel();
		newCrossword.setBorder(BorderFactory.createTitledBorder("nowa krzy¿ówka"));
		
		JLabel label = new JLabel();
		label.setText("wysokoœæ");
		newCrossword.add(label);
		
		SpinnerModel heightModel =new SpinnerNumberModel(5, 5, 10, 1);
		heightSpinner = new JSpinner(heightModel);
		
		newCrossword.add(heightSpinner);
		
		JLabel widthLabel = new JLabel();
		widthLabel .setText("szerokoœæ");
		newCrossword.add(widthLabel );
		
		SpinnerModel widthModel =new SpinnerNumberModel(5, 5, 10, 1);
		widthSpinner = new JSpinner(widthModel);
		
		newCrossword.add(widthSpinner);
		
		simpleCwCheckbox = new JCheckBox("simpleCw");
		simpleCwCheckbox.setText("tylko poziomo");
		newCrossword.add(simpleCwCheckbox);
		
		JButton genButton = new JButton();
		genButton.setText("generuj!");
		genButton.setActionCommand("generate");
		genButton.addActionListener(this);
		
		newCrossword.add(genButton);
		
		panel.add(newCrossword);
		
		// z pliku
		JPanel fromFile = new JPanel();
		fromFile.setBorder(BorderFactory.createTitledBorder("z pliku"));
		
		JButton fromFileButton = new JButton();
		fromFileButton.setText("wczytaj");
		fromFileButton.setActionCommand("open");
		fromFileButton.addActionListener(this);
		fromFile.add(fromFileButton);
		
		JButton dictFileButton = new JButton();
		dictFileButton.setText("wczytaj s³ownik");
		dictFileButton.setActionCommand("open-dict");
		dictFileButton.addActionListener(this);
		fromFile.add(dictFileButton);
	
		panel.add(fromFile);
		
		// kontrola
		JPanel control = new JPanel();
		control.setBorder(BorderFactory.createTitledBorder("kontrola"));
		
		JButton printButton = new JButton();
		printButton.setActionCommand("print");
		printButton.setText("drukuj!");
		printButton.addActionListener(this);
		control.add(printButton);
		
		JButton saveButton = new JButton();
		saveButton.setText("zapisz!");
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		control.add(saveButton);
		
		resolveCheckbox = new JCheckBox("resolve");
		resolveCheckbox.setText("rozwi¹¿");
		resolveCheckbox.addItemListener(this);
		control.add(resolveCheckbox);
	
		panel.add(control);
		
		frame.add(panel, BorderLayout.PAGE_START);
		
		// utwórz panel na który bêd¹ rysowane krzy¿ówki
		drawing = new DrawingPanel(board, crossword);
		frame.add(drawing, BorderLayout.CENTER);
		
		frame.setSize(new Dimension(1000,800));
		
		frame.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) // przycisk zosta³ wciœniêty
	{
		if ("generate".equals(e.getActionCommand())) // generuj now¹ krzy¿ówkê
		{
			int boardWidth = ((Integer)widthSpinner.getValue()).intValue();
			int boardHeight = ((Integer)heightSpinner.getValue()).intValue();
			
			Strategy strategy = null;
			if(simpleCwCheckbox.isSelected())
				strategy = new SimpleStrategy(crossword, boardHeight);
			else
				strategy = new CrossStrategy();
			try
			{
				crossword.generate(strategy, boardWidth, boardHeight);
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null, ex.getLocalizedMessage());
			}
			frame.repaint();
		} else if("print".equals(e.getActionCommand())) // drukuj krzy¿ówkê
		{
			System.out.print("\n\nasdasd\n\n");
			
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(drawing);
			boolean doPrint = job.printDialog();
			if (doPrint) 
			{
				  try
				  {
				       job.print();
				  } catch (PrinterException q)
				  {
					  JOptionPane.showMessageDialog(null, q.getLocalizedMessage());
				  }
				}
		} else if("save".equals(e.getActionCommand())) // zapisz krzy¿ówkê
		{   
		    JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    chooser.setAcceptAllFileFilterUsed(false);

		    if (chooser.showOpenDialog(drawing) == JFileChooser.APPROVE_OPTION)
		    {
		    	CwBrowser browser = new CwBrowser(chooser.getSelectedFile().getAbsolutePath());
		    	browser.write(crossword);
		    }
		    else
		      System.out.println("No Selection ");
		} else if("open".equals(e.getActionCommand())) // wczytaj krzy¿ówkê
		{
		    JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
			javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter()
			{
				public String getDescription()
				{
					return "*.cwdb";
				}

				public boolean accept(File file)
				{
					return file.getName().endsWith(".cwdb");
				}
			};
		    chooser.setFileFilter(filter);

		    if (chooser.showOpenDialog(drawing) == JFileChooser.APPROVE_OPTION)
		    {
		    	CwBrowser browser = new CwBrowser(chooser.getSelectedFile().getName());
		    	crossword = browser.read(chooser.getSelectedFile().getName());
		    	drawing.setCrossword(crossword);
		    	frame.repaint();
		    }
		    else
		      System.out.println("No Selection ");
		} else if("open-dict".equals(e.getActionCommand())) // wczytaj s³ownik
		{
		    JFileChooser chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
			javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter()
			{
				public String getDescription()
				{
					return "*.txt";
				}

				public boolean accept(File file)
				{
					return file.getName().endsWith(".txt");
				}
			};
		    chooser.setFileFilter(filter);

		    if (chooser.showOpenDialog(drawing) == JFileChooser.APPROVE_OPTION)
		    {
		    	crossword.getCwDB().createDB(chooser.getSelectedFile().getName());
		    }
		    else
		      System.out.println("No Selection ");
		}
	}
	public void itemStateChanged(ItemEvent e) // checkbox "rozwi¹¿"
	{
		Object source = e.getItemSelectable();

	    if (source == resolveCheckbox)
	    {
	    	drawing.setResolved(resolveCheckbox.isSelected());
	    	frame.repaint();
	    }
	}
}
