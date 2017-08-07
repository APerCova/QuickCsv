package com.quickcsv.util;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Allows to iterate over a {@link LineNumberReader} while keep line
 * number tracking.
 * @author <a href="https://twitter.com/apercova" target="_blank">{@literal @}apercova</a> <a href="https://github.com/apercova" target="_blank">https://github.com/apercova</a>
 * @version 1.0 2017.08
 * Use:
 * <pre>
 * {@code
 *  IterableLineNumberReader it = new IterableLineNumberReader(reader);
 *  while (it.hasNext()){
 *      String nextLine = it.next();
 *      int lineNum = it.getLineNumber();
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
        suppressed = new ArrayList<Throwable>();
    }

    public IterableLineNumberReader(Reader in, int sz) {
        super(in, sz);
        readForward = true;
        suppressed = new ArrayList<Throwable>();
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
}
