package library;

import library.Library;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import library.BookCopy.Condition;

public class Main {
    
    private static final Book book1 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
    private static final Book book2 = new Book("Java Course", Arrays.asList("Fred"), 1540);
    
    private static String compaBook(Book b){
        String res = b.getTitle();
        for(String author : b.getAuthors()){
            res += author;
        }
        res += b.getYear();
        return res;
    }
    
    private static List<String> keywords(Book book){
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
    
    public static void main(String[] args)  {

        Book book1 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        Book book2 = new Book("Java Course", Arrays.asList("John", "FRed", "Fred"), 1990);
        Book book3 = new Book("Java Course", Arrays.asList("Fred"), 1540);
        Book book4 = new Book("Python Course", Arrays.asList("Fred"), 1540);
        Book book5 = new Book("Java Course", Arrays.asList("John", "Fred", "FRed"), 1990);
        System.out.println(book1.hashCode()== book1.hashCode());
        System.out.println(book1.hashCode()== book2.hashCode());
        System.out.println(book2.hashCode() == book3.hashCode());
        System.out.println(book4.hashCode() == book3.hashCode());
        System.out.println(book1.hashCode() == book5.hashCode());
        System.out.println("Fred".hashCode());
        System.out.println("FRed".hashCode());
        List<String> auteurs1 = new ArrayList <String> (Arrays.asList("John", "FRed", "Fred"));
        System.out.println(auteurs1.hashCode());
        List<String> auteurs2 = new ArrayList <String> (Arrays.asList("John", "Fred", "FRed"));
        BookCopy copy = new BookCopy(book1);
        System.out.println(compaBook(book1));
        Condition test = copy.getCondition();
        test = Condition.DAMAGED;
        


        Library library = new BigLibrary();
        Book book = new Book("Bartleby, the : Scrivener", Arrays.asList("Herman Melville"), 1853);

        System.out.println(keywords(book));


        // buy one copy of the book and check it out, then back in
        BookCopy copy1 = library.buy(book);
        library.checkout(copy1);
        System.out.println(Collections.emptySet());
        
        assertFalse(library.isAvailable(copy1));
        library.checkin(copy1);


        // do the same with a second copy of the book
        BookCopy copy2 = library.buy(book);
        library.checkout(copy2);

        library.checkin(copy2);

        String str = "John Wayne, the best";

        List<String> items = Arrays.asList(str.split("\\W"));
        System.out.println(items);
        
    }
    


}
