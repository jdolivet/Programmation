package library;

import java.util.List;
import java.util.Set;
import java.util.Map;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.ArrayList;

/**
 * BigLibrary represents a large collection of books that might be held by a city or
 * university library system -- millions of books.
 * 
 * In particular, every operation needs to run faster than linear time (as a function of the number of books
 * in the library).
 */
public class BigLibrary implements Library {

    // rep
    // Map {Book book : [AvailableSet<Book>, OutsideSet<Book>]} for the library
    // Map {String word : Set<Book>} for the search
    private Map<Book, List<Set<BookCopy>>> library;
    private Map<String, Set<Book>> dataBase;
    // rep invariant:
    //    the intersection of AvailableSet and OutsideSet is the empty set
    //
    // abstraction function:
    //    represents the collection of books 
    //      for each book 2 sets : AvailableSet and OutsideSet,
    //      where if a book copy is in AvailableSet then it is available,
    //      and if a copy is in AvailableSet then it is checked out
    //
    // Safety from rep exposure:
    //   fields are private
    //   library and dataBase are mutable Sets, so  
    //        make defensive copies to avoid sharing the rep's authors object with clients.
    
    private Comparator<Book> comparator = (Book b1, Book b2) -> compaBook(b1).compareTo(compaBook(b2));

    
    // Comparator for the TreeMap
    private String compaBook(Book b){
        String res = b.getTitle();
        for(String author : b.getAuthors()){
            res += author;
        }
        res += b.getYear();
        return res;
    }
    
    // Recup all keywords (in lower case) of a book (even stop words)
    private List<String> keywords(Book book){
        List<String> keywords = new ArrayList<String>();
        // Split all the fields to get max of keywords
        String start = book.getTitle();
        List<String> startList = Arrays.asList(start.split("\\W"));
        for (String keyword : startList){
            if (keyword.length() > 0){
                keywords.add(keyword.toLowerCase());
            }
        }
        for(String author : book.getAuthors()){
            List<String> startAuthor = Arrays.asList(author.split("\\W"));
            for (String keyword : startAuthor){
                if (keyword.length() > 0){
                    keywords.add(keyword.toLowerCase());
                }
            }
        }
        return keywords;
    }
    
    public BigLibrary() {        
        this.library = new TreeMap<Book, List<Set<BookCopy>>>(comparator);
        this.dataBase = new TreeMap<String, Set<Book>>();
        checkRep();
    }
    
    // assert the rep invariant
    private void checkRep() {
        Set<Book> total = library.keySet();
        for (Book book : total){
            for (BookCopy copie : library.get(book).get(0)){
                assert !library.get(book).get(1).contains(copie);
            }
            for (BookCopy copie : library.get(book).get(1)){
                assert !library.get(book).get(0).contains(copie);
            }
        }
    }

    @Override
    public BookCopy buy(Book book) {
        // insert the book in the tree with 1 more available copy
        BookCopy copy = new BookCopy(book);
        if (library.get(book) == null){
            Set<BookCopy> available = new HashSet<BookCopy>();
            Set<BookCopy> outside = new HashSet<BookCopy>();
            List<Set<BookCopy>> indicators = Arrays.asList(available, outside);
            library.put(book, indicators);
        }
        library.get(book).get(0).add(copy);
        
        // Check the datas of the book and insert them in the dataBase
        List<String> keywordsList = keywords(book);
        for(String keyword : keywordsList){
            if (dataBase.get(keyword) == null){
                Set<Book> keywordBooks = new TreeSet<Book>(comparator);
                dataBase.put(keyword, keywordBooks);
            }
            dataBase.get(keyword).add(book);
        }        
        checkRep();
        return copy;
    }
    
    @Override
    public void checkout(BookCopy copy) {
        Book book = copy.getBook();
        if (library.get(book).get(0).remove(copy)){
            library.get(book).get(1).add(copy);
        }
        checkRep();
    }
    
    @Override
    public void checkin(BookCopy copy) {
        Book book = copy.getBook();
        if (library.get(book).get(1).remove(copy)){
            library.get(book).get(0).add(copy);
        }
        checkRep();
    }
    
    @Override
    public Set<BookCopy> allCopies(Book book) {
        if (library.get(book) == null){
            return Collections.emptySet();
        }
        List<Set<BookCopy>> datas = library.get(book);
        Set<BookCopy> result = new HashSet<BookCopy>(datas.get(0));
        result.addAll(datas.get(1));
        return result;
    }

    @Override
    public Set<BookCopy> availableCopies(Book book) {
        if (library.get(book) == null){
            return Collections.emptySet();
        }
        List<Set<BookCopy>> datas = library.get(book);
        Set<BookCopy> result = new HashSet<BookCopy>(datas.get(0));
        return result;

    }
    
    @Override
    public boolean isAvailable(BookCopy copy) {
        Book book = copy.getBook();
        if (library.get(book) == null){
            return false;
        }
        Set<BookCopy> available = library.get(book).get(0);
        return (available.contains(copy));
    }
    
    /**
     * Search for books in this library's collection.
     * @param query search string
     * @return list of books in this library's collection (both available and checked out) 
     * whose title or author match the search string, ordered by decreasing amount of match.
     * A book should appear at most once on the list. 
     * Word : sequence of alphanumeric character a-zA-Z0-9
     * Each book of the library's collection with one word of the query (in the title or the authors)
     *      will appear in the list.
     *      nb matches = nb of copies * time the book appear in the search
     * Keyword matching and ranking support: 
     *     - exact matching of title and author: i.e., if a copy of a book is in the library's 
     *           collection, then find(book.getTitle()) and find(book.getAuthors().get(i)) 
     *           must include book among the results.
     *     - date ordering: if two matching books have the same title and author but different
     *           publication dates, then the newer book should appear earlier on the list. 
     */
    @Override
    public List<Book> find(String query) {
        // recup keywords of the query (in lower case)
        List<String> rechercheInit = Arrays.asList(query.split("\\W"));
        Set<String> recherche = new HashSet<String>();
        for (String word : rechercheInit){
            if (word.length() > 0){
                recherche.add(word.toLowerCase());
            }
        }
        // search in the dataBase for all words of the query and put them in a set
        // Construct a Virtual library to order the results by nb of matches
        Library virtualLib = new BigLibrary();
        Set<Book> allResult = new TreeSet<Book>(comparator);
        for (String word : recherche){
            if (dataBase.get(word) != null){
                Set<Book> potentials = dataBase.get(word);
                // check if exists yet!
                for (Book potential : potentials){
                    int nbCopies = allCopies(potential).size();
                    if (nbCopies != 0){
                        allResult.add(potential);
                    }
                    for (int i = 0; i < nbCopies; i++){
                        virtualLib.buy(potential);
                    }
                }
            }
        }
        // Now, sort the result, first with nb of copies then with decrease date
        List<Book> result = new ArrayList<Book>(allResult);
        Comparator<Book> sorting = Comparator.comparingInt((Book b) -> virtualLib.allCopies(b).size()).thenComparingInt((Book b) -> b.getYear());
        result.sort(sorting.reversed());
        return result;
    }    
    
    @Override
    public void lose(BookCopy copy) {
        Book book = copy.getBook();
        if (library.get(book) != null){
            Set<BookCopy> available = library.get(book).get(0);
            Set<BookCopy> outside = library.get(book).get(1);
            available.remove(copy);
            outside.remove(copy);
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
