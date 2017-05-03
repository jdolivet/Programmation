package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * Testing strategy
     * 
     * methods of the Filter class
     *  
     * for the writtenBy method
     * Partition the inputs as follows:
     *      For empty list
     *      For each author : 0, 1, >1 tweets
     *      For 1 (with sup test for checking with insensitive case)
     *      For >1 tweet check if the order is the same as the original     
     *      Test to check if the input is modified by the method
     * 
     * for the inTimespan method
     * Partition the inputs as follows:
     *      For empty list and timestamp [d;d]
     *      For each timestamp : 0, 1, >1 tweets
     *      For >1 tweets check 
     *          tweets at the limits of the timespan, after, before
     *          if the order is the same as the original
     *          the input is not ordered with Timestamp
     *      Test to check if the input is modified by the method
     * 
     * for the containing method 
     * Partition the inputs as follows:
     *      For empty list of tweets or empty list of words
     *      For the words :
     *          no word in the tweets
     *          one word
     *          one word repeated, at the limits of the string, with sensitive case, with exotics charters, between space
     *          if the order is the same as the original
     *      Test to check if the input is modified by the method
     * 
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T14:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "johann", "Interesting! @alyssa", d3);
    private static final Tweet tweet4 = new Tweet(4, "alan", "Yeah! @alyssa", d2);
    private static final Tweet tweet5 = new Tweet(5, "alYssa", "@johann Indeed!", d3);
    private static final Tweet tweet6 = new Tweet(6, "aLYssa", "@johann @john Thanks!", d4);
    private static final Tweet tweet7 = new Tweet(7, "john", "talk 8_p:-+ talk Thanks!", d4);
    private static final Tweet tweet8 = new Tweet(8, "bbitdiddle", "Thanks! talk foo", d4);
    private static final Tweet tweet9 = new Tweet(9, "bbitdiddle", "@joHAnn -bar @johann", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //
    // Tests for the writtenBy method of Filter class
    //
    
    // test if the list of tweets is modified by the method 
    @Test
    public void testWrittenByNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        Filter.writtenBy(listeInit, "alyssa");
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }
    
    // test the empty list of tweets
    @Test
    public void testWrittenByNoTweetResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(), "johann");
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    
    // test the list of 2 tweets with none matching 
    @Test
    public void testWrittenByMultipleTweetsNoResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "johann");
        
        assertTrue("expected empty list", writtenBy.isEmpty());
    }
    
    // test the list of 2 tweets with one matching 
    @Test
    public void testWrittenByMultipleTweetsSingleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "alyssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    // same test caring about insensitive case
    @Test
    public void testWrittenByMultipleTweetsSingleResultInsensitiveCase() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet2), "aLYssa");
        
        assertEquals("expected singleton list", 1, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
    }
    
    // test if the list of 3 tweets with two matching 
    @Test
    public void testWrittenByMultipleTweetsDoubleResult() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet4, tweet6), "alyssa");
        
        assertEquals("expected singleton list", 2, writtenBy.size());
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet1));
        assertTrue("expected list to contain tweet", writtenBy.contains(tweet6));
    }
    
    // test if list of 3 tweets all matching (checking the order)
    @Test
    public void testWrittenByMultipleTweetsOrder() {
        List<Tweet> writtenBy = Filter.writtenBy(Arrays.asList(tweet1, tweet5, tweet6), "alyssa");
        
        assertEquals("expected same order", 0, writtenBy.indexOf(tweet1));
        assertEquals("expected same order", 1, writtenBy.indexOf(tweet5));
        assertEquals("expected same order", 2, writtenBy.indexOf(tweet6));
    }
    
    //
    // Tests for the inTimespan method of Filter class
    //
    
    // test if the list of tweets is modified by the method 
    @Test
    public void testInTimespanNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        Filter.inTimespan(listeInit, new Timespan(d1, d2));
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }
    
    // test the empty list of tweets
    @Test
    public void testInTimespanNoTweetResult() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(d1, d2));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // test the empty list of tweets with timestamp with width = 0
    @Test
    public void testInTimespanNoTweetTimespanLimitResult() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(), new Timespan(d1, d1));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // test the list of tweets with tweet inside timestamp with width = 0
    @Test
    public void testInTimespanOneTweetInsideTimespanLimitResult() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1), new Timespan(d1, d1));
        
        assertTrue("expected list to contain tweet 1", inTimespan.contains(tweet1));
    }
    
    // test the list of tweets with tweet outside timestamp with width = 0
    @Test
    public void testInTimespanOneTweetOutsideTimespanLimitResult() {
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2), new Timespan(d1, d1));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // test the list of tweets with 2 tweet inside timestamp
    @Test
    public void testInTimespanMultipleTweetsMultipleResults() {
        Instant testStart = Instant.parse("2016-02-17T09:00:00Z");
        Instant testEnd = Instant.parse("2016-02-17T12:00:00Z");
        
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(testStart, testEnd));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet1));
    }
    
    // test the list of tweets with 2 tweet inside timestamp (Limits)
    @Test
    public void testInTimespanMultipleTweetsMultipleLimitResults() {     
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet2), new Timespan(d1, d2));
        
        assertFalse("expected non-empty list", inTimespan.isEmpty());
        assertTrue("expected list to contain tweets", inTimespan.containsAll(Arrays.asList(tweet1, tweet2)));
    }
    
    // test the list of tweets with 1 tweet after timestamp 
    @Test
    public void testInTimespanMultipleTweetsSingleAfterResults() {     
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet3), new Timespan(d1, d2));
        
        assertTrue("expected list size 1", inTimespan.size() == 1);
        assertTrue("expected list to contain tweet1", inTimespan.contains(tweet1));
    }
    
    // test the list of tweets with 1 tweet before timestamp 
    @Test
    public void testInTimespanMultipleTweetsSingleBeforeResults() {     
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet1, tweet3), new Timespan(d2, d3));
        
        assertTrue("expected list size 1", inTimespan.size() == 1);
        assertTrue("expected list to contain tweet1", inTimespan.contains(tweet3));
    }
    
    // test the list of tweets with 1 tweet before timestamp 
    @Test
    public void testInTimespanMultipleTweetsMultipleOrderResults() {     
        List<Tweet> inTimespan = Filter.inTimespan(Arrays.asList(tweet2, tweet1, tweet3, tweet4), new Timespan(d1, d3));
        
        assertTrue("expected list size 4", inTimespan.size() == 4);
        assertEquals("expected same order", 0, inTimespan.indexOf(tweet2));
        assertEquals("expected same order", 1, inTimespan.indexOf(tweet1));
        assertEquals("expected same order", 2, inTimespan.indexOf(tweet3));
    }
    
    //
    // Tests for the containing method of Filter class
    //
    
    // test if the list of tweets is modified by the method 
    @Test
    public void testContainingNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        Filter.containing(listeInit, Arrays.asList("talk","foo"));
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }
        
    // test the empty list of tweets and list of words
    @Test
    public void testContainingNoTweetResult() {
        List<Tweet> inTimespan = Filter.containing(Arrays.asList(), Arrays.asList("talk","foo"));
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // test list of tweets and empty list of words
    @Test
    public void testContainingNoListResult() {
        List<Tweet> inTimespan = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList());
        
        assertTrue("expected empty list", inTimespan.isEmpty());
    }
    
    // test list of tweets and one word in two
    @Test
    public void testContaining() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet1, tweet2), Arrays.asList("talk"));
        
        assertFalse("expected non-empty list", containing.isEmpty());
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet1, tweet2)));
        assertEquals("expected same order", 0, containing.indexOf(tweet1));
    }
    
    // test list of tweets and words at limits of string and delimitation and repeat
    @Test
    public void testContainingWordsLimitString() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet7, tweet8, tweet9), Arrays.asList("talk","foo", "bar"));
        
        assertTrue("expected list size 2", containing.size() == 2);
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet7, tweet8)));
    }
    
    // test list of tweets and words with insensitive case
    @Test
    public void testContainingSensitiveCase() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet7, tweet8), Arrays.asList("tALk","foO"));
        
        assertTrue("expected list to contain tweets", containing.containsAll(Arrays.asList(tweet7, tweet8)));
    }
    
    // test list of tweets and words like defined
    @Test
    public void testContainingCorrectWord() {
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet7, tweet8), Arrays.asList("8_p:-+"));
        
        assertTrue("expected list to contain tweets", containing.contains(tweet7));
    }
    
    // test the list of tweets and the result in the same order
    @Test
    public void testContainingOrderResults() {     
        List<Tweet> containing = Filter.containing(Arrays.asList(tweet2, tweet5, tweet8, tweet7), Arrays.asList("tALk"));
        
        assertTrue("expected list size 3", containing.size() == 3);
        assertEquals("expected same order", 0, containing.indexOf(tweet2));
        assertEquals("expected same order", 1, containing.indexOf(tweet8));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
