JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        crossword/board/Board.java \
		crossword/board/BoardCell.java \
        crossword/dictionary/CwDB.java \
		crossword/dictionary/Entry.java \
		crossword/dictionary/InteliCwDB.java \
        crossword/generator/CrossStrategy.java \
		crossword/generator/SimpleStrategy.java \
		crossword/generator/Strategy.java \
		crossword/io/CwBrowser.java \
		crossword/io/Reader.java \
		crossword/io/Writer.java \
		crossword/view/DrawingPanel.java \
		crossword/view/MainFrame.java \
        crossword/Crossword.java \
		crossword/CwEntry.java \
		crossword/CwException.java \
		crossword/Direction.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
        $(RM) *.class