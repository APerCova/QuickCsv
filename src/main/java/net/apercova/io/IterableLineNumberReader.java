package net.apercova.io;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Allows to iterate over a {@link LineNumberReader} while keep line
 * number tracking.
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @version 1.0 2017.08
 * @version 1.1 2017.12 Adding {@link #hasMoreLines()} and {@link #getLine()}
 * Use:
 * <pre>
 * {@code
 *  IterableLineNumberReader it = new IterableLineNumberReader(reader);
 *  while (it.hasMoreLines()){
 *      int lineNum = it.getLineNumber();
 *      String nextLine = it.getLine();
 *      System.out.printf("#[%d]-%s%n",lineNum, nextLine);
 *  }}
 *  </pre>
 */
public class IterableLineNumberReader extends LineNumberReader implements Iterator<String>, Iterable<String>{

    protected boolean readForward;
    protected String nextLine;
    protected List<Throwable> suppressed;

    public IterableLineNumberReader(Reader in) {
        super(in);
        readForward = true;
        suppressed = new LinkedList<Throwable>();
    }

    public IterableLineNumberReader(Reader in, int sz) {
        super(in, sz);
        readForward = true;
        suppressed = new LinkedList<Throwable>();
    }

    /**
     * Exception list caused by reading errors when iterating.
     * {@link Iterator#next()} does not declare a {@code throws} statement.
     * @return Suppressed exceptions list.
     */
    public List<Throwable> getSuppressed() {
        return suppressed;
    }

    /**
     * Returns an instance of this {@link IterableLineNumberReader} as {@link Iterator}.
     * @return Iterator.
     */
    public Iterator<String> iterator() {
        return this;
    }

    /**
     * Looks for forward text line within underlying reader.
     */
    protected void readNextLine(){
        try {
            nextLine = readLine();
        }catch(IOException e){
            suppressed.add(e);
            nextLine = null;
        }
    }

    /**
     * @see Iterator#hasNext
     */
    public boolean hasNext() {
        //look forward if next line is available
        readNextLine();
        readForward = false;
        return nextLine != null;
    }

    /**
     * Retrieves value of the next read text line.
     * @see Iterator#next
     * @return Value of the next read text line.
     */
    public String next() {
        if(readForward){
            //read next line
            readNextLine();
        }else{
            readForward = true;
        }
        return nextLine;
    }

    /**
     * Not supported.
     * @see Iterator#remove
     * @throws UnsupportedOperationException Not supported.
     */
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns {@code true} if there's more lines to read.
     * (In other words, returns {@code true} if {@link #getLine()} would
     * return an element rather than throwing an exception.)
     *
     * @return {@code true} if there's more lines to read
     */
    public boolean hasMoreLines() {
        return this.hasNext();
    }

    /**
     * Retrieves value of the next read text line.
     * @return The next read text line.
     */
    public String getLine() {
        return this.next();
    }

}