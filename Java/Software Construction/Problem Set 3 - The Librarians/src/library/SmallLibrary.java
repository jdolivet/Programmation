package library;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;


/** 
 * SmallLibrary represents a small collection of books, like a single person's home collection.
 */
public class SmallLibrary implements Library {

    // This rep is required! 
    // Do not change the types of inLibrary or checkedOut, 
    // and don't add or remove any other fields.
    // (BigLibrary is where you can create your own rep for
    // a Library implementation.)

    // rep
    private Set<BookCopy> inLibrary;
    private Set<BookCopy> checkedOut;
    
    // rep invariant:
    //    the intersection of inLibrary and checkedOut is the empty set
    //
    // abstraction function:
    //    represents the collection of books inLibrary union checkedOut,
    //      where if a book copy is in inLibrary then it is available,
    //      and if a copy is in checkedOut then it is checked out
    //
    // Safety from rep exposure:
    //   fields are private
    //   inLibrary and checkedOut are mutable Sets, so  
    //        make defensive copies to avoid sharing the rep's authors object with clients.
    
    public SmallLibrary() {
        this.inLibrary = new HashSet<BookCopy>();
        this.checkedOut = new HashSet<BookCopy>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        Set<BookCopy> total = new HashSet<BookCopy> (inLibrary);
        total.addAll(checkedOut);
        assert total.size() == inLibrary.size() + checkedOut.size();
    }

    @Override
    public BookCopy buy(Book book) {
        BookCopy copy = new BookCopy(book);
        inLibrary.add(copy);
        checkRep();
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        if (inLibrary.remove(copy)){
            checkedOut.add(copy);
        }
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        if (checkedOut.remove(copy)){
            inLibrary.add(copy);
        }
        checkRep();
    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        return inLibrary.contains(copy);
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        Set<BookCopy> result = new HashSet<BookCopy>();
        for (BookCopy copy : inLibrary){
            if (copy.getBook().equals(book)){
                result.add(copy);
            }
        }
        checkRep();
        for (BookCopy copy : checkedOut){
            if (copy.getBook().equals(book)){
                result.add(copy);
            }
        }
        checkRep();
        return result;
    }
    
    @Override
    public Set<BookCopy> availableCopies(Book book) {
        Set<BookCopy> result = new HashSet<BookCopy>();
        for (BookCopy copy : inLibrary){
            if (copy.getBook().equals(book)){
                result.add(copy);
            }
        }
        checkRep();
        return result;
    }

    @Override
    public List<Book> find(String query) {
        // Recup all the copies
        List<BookCopy> listeCopies = new ArrayList<BookCopy>();
        Set<Book> ensLivre = new HashSet<Book>();
        String recherche = query.toLowerCase();
        for (BookCopy copy : inLibrary){
            Book livre = copy.getBook();
            String infos = livre.toString().toLowerCase();
            if (infos.contains(recherche)){
                listeCopies.add(copy);
                ensLivre.add(livre);
            }
        }
        checkRep();
        for (BookCopy copy : checkedOut){
            Book livre = copy.getBook();
            String infos = livre.toString().toLowerCase();
            if (infos.contains(recherche)){
                listeCopies.add(copy);
                ensLivre.add(livre);
            }
        }
        checkRep(); 
        // explore the list to find all the book and the nb of copies of each one
        Map<Integer, List<Book>> explore = new HashMap<Integer, List<Book> >();
        int maxFreq = 0;
        for (Book livre : ensLivre){
            int compteur = 0;
            for (BookCopy copie : listeCopies){
                if (copie.getBook().equals(livre)){
                    compteur ++;
                }
            }
            if (compteur > maxFreq){ 
                maxFreq = compteur;
                }
            if (explore.get(compteur) == null){
                explore.put(compteur, new ArrayList<Book>());
            }            
            explore.get(compteur).add(livre);
        }
        // sort by matches and dates adding at the end of result
        List<Book> result = new ArrayList <Book>();
        for (int i = maxFreq; i > 0 ; i--){
            if (explore.get(i) != null){
                List<Book> subListe = explore.get(i);
                subListe.sort((book1, book2) -> book2.getYear() - book1.getYear() );
                result.addAll(subListe);
            }
        }
        return result;
    }
    
    @Override
    public void lose(BookCopy copy) {
        for (BookCopy copie : inLibrary){
            if (copie.equals(copy)){
                inLibrary.remove(copy);
                break;
            }
        }
        checkRep();
        for (BookCopy copie : checkedOut){
            if (copie.equals(copy)){
                checkedOut.remove(copy);
                break;
            }
        }
        checkRep();
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
