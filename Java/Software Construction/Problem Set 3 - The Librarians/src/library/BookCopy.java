package library;

import java.util.List;


/**
 * BookCopy is a mutable type representing a particular copy of a book that is held in a library's
 * collection.
 */
public class BookCopy {

    // Rep
    private final int year;
    private final String title;
    private final List<String> authors;
    public static enum Condition {
        GOOD, DAMAGED
    };
    private Condition state;
    
    // Rep invariant:
    //   state is GOOD or DAMAGED
    // Abstraction Function:
    //   represents a book (identifies by title, made by author in a specific year) in a special condition 
    // Safety from rep exposure:
    //   fields are private
    //   Book is an immutable type and Condition is immutable

    
    /**
     * Make a new BookCopy, initially in good condition.
     * @param book the Book of which this is a copy
     */
    public BookCopy(Book book) {
        this.title = book.getTitle();
        this.year = book.getYear();
        this.authors = book.getAuthors();
        this.state = Condition.GOOD;
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert state.equals(Condition.GOOD) || state.equals(Condition.DAMAGED);
    }
    
    /**
     * @return the Book of which this is a copy
     */
    public Book getBook() {
        return new Book(title, authors, year);
    }
    
    /**
     * @return the condition of this book copy
     */
    public Condition getCondition() {
        return state;
    }

    /**
     * Set the condition of a book copy.  This typically happens when a book copy is returned and a librarian inspects it.
     * @param condition the latest condition of the book copy
     */
    public void setCondition(Condition condition) {
        this.state = condition;
        checkRep();
    }
    
    /**
     * @return human-readable representation of this book that includes book.toString()
     *    and the words "good" or "damaged" depending on its condition
     */
    public String toString() {
        Book book = new Book(title, authors, year);
        String result = book.toString();
        result += "Etat du livre : ";
        if (this.state.equals(Condition.GOOD)) {
            result += "good\n";
        }else{
            result += "damaged\n";
        }
        return result;
    }
    
    // uncomment the following methods if you need to implement equals and hashCode,
    // or delete them if you don't
    // @Override
    // public boolean equals(Object that) {
    //     throw new RuntimeException("not implemented yet");
    // }
    // 
    // @Override
    // public int hashCode() {
    //     throw new RuntimeException("not implemented yet");
    // }


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
