package library;

import java.util.List;
import java.util.ArrayList;


/**
 * Book is an immutable type representing an edition of a book -- not the physical object, 
 * but the combination of words and pictures that make up a book.  Each book is uniquely
 * identified by its title, author list, and publication year.  Alphabetic case and author 
 * order are significant, so a book written by "Fred" is different than a book written by "FRED".
 */
public class Book {

    // Rep
    private final int year;
    private final String title;
    private final List<String> authors;
    
    // Rep invariant:
    //   title.length > 0
    //   title.contains char dif from space
    //   authors list.length > 0
    //   author.contains char dif from space
    //   year>0
    // Abstraction Function:
    //   represents a book identifies by title, made by author in a specific year 
    // Safety from rep exposure:
    //   All fields are private;
    //   title is String and year is int so are guaranteed immutable;    //   
    //   authors is a mutable List, so Book() constructor and getAuthors() 
    //        make defensive copies to avoid sharing the rep's authors object with clients.
    
    /**
     * Make a Book.
     * @param title Title of the book. Must contain at least one non-space character.
     * @param authors Names of the authors of the book.  Must have at least one name, and each name must contain 
     * at least one non-space character.
     * @param year Year when this edition was published in the conventional (Common Era) calendar.  Must be nonnegative. 
     */
    public Book(String title, List<String> authors, int year) {
        this.title = title;
        this.year = year;
        this.authors = new ArrayList<String> (authors);
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        assert year >= 0;
        assert title.replace(" ", "").length() > 0;
        assert authors.size() > 0;
        boolean liste = true;
        for (String author : authors){
            if (author.replace(" ", "").length() <= 0){
                liste = false;
            }
        }
        assert liste;
    }
    
    /**
     * @return the title of this book
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * @return the authors of this book
     */
    public List<String> getAuthors() {
        return new ArrayList<String> (authors);
    }

    /**
     * @return the year that this book was published
     */
    public int getYear() {
        return year;
    }

    /**
     * @return human-readable representation of this book that includes its title,
     *    authors, and publication year
     */
    public String toString() {
        String res = "Titre du livre : " + this.title + "\n";
        res += "Auteurs du livre : \n";
        for(String author : authors){
            res += " - " + author + "\n";
        }
        res += "Année de publication : " + this.year +"\n";
        return res;
    }

    @Override
    public boolean equals(Object thatObject) {
        if (!(thatObject instanceof Book)) {
            return false;
        }
        Book that = (Book) thatObject;
        boolean liste = true;
        int longueur = this.authors.size();
        if (longueur != that.authors.size()){
            liste = false;
        }else{
            for (int i=0; i < longueur; i++){
                if (this.authors.get(i) != that.authors.get(i)){
                    liste = false;
                }
            }
        }
        return this.title == that.title && this.year == that.year && liste;
    }

    @Override
    public int hashCode() {
        final int titre = title.hashCode();
        final int annee = (int) year;
        final int liste = authors.hashCode();
        return 37 * (titre + annee) + liste;
    }



    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
