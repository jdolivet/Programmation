package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


import org.junit.Test;

public class MySocialNetworkTest {

    /*
     * Testing strategy
     *  
     * methods of the MySocialNetwork class
     *  
     * for the guessFollowsGraph method
     * just add test for the hashtag in common
     * make a list of 4 tweets with one common for 3 and one for 2
     * 
     *      
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #miT", d2);
    private static final Tweet tweet3 = new Tweet(3, "johann", "Interesting! #Mit #java", d3);
    private static final Tweet tweet4 = new Tweet(4, "alan", "#mIt", d2);
    private static final Tweet tweet5 = new Tweet(5, "jOhann", "#MIT #python", d2);
    private static final Tweet tweet6 = new Tweet(6, "shAnnon", "#javA", d2);
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //
    // Tools for the tests in MySocialNetwork class
    //
    
    // usefull function to test if a string is in a set (with insensitive case)
    private static boolean contenir(Set<String> liste, String str) {
        for (String s : liste) {
            if (str.equalsIgnoreCase(s)) return true;
        }
        return false;
    }
    
    // usefull function to get the set from one key (with insensitive case on key)
    // because tests don't know what's the case of key
    private static Set<String> recup(Map<String, Set<String>> dico, String cle) {
        for (String s : dico.keySet()) {
            if (s.equalsIgnoreCase(cle)){
                return dico.get(s);
            }
        }
        return Collections.emptySet();
    }
       
    
    //
    // Tests for the guessFollowsGraph method of SocialNetwork class
    //
 
    // test if the list of tweets is modified by the method 
    @Test
    public void testGuessFollowsGraphHashtag() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2, tweet6, tweet1, tweet3, tweet5, tweet4));
        
        assertTrue("expected no alyssa follows nobody", recup(followsGraph,"alySsa").size() == 0);
        assertTrue("expected bbitdiddle follows 2 people", recup(followsGraph,"bbitdiddle").size() == 2);
        assertTrue("expected bbitdiddle follows alan", contenir(recup(followsGraph,"bbitdiddle"),"alan"));
        assertTrue("expected bbitdiddle follows johann", contenir(recup(followsGraph,"bbitdiddle"),"johann"));
        assertTrue("expected johann follows 3 people", recup(followsGraph,"johann").size() == 3);
        assertTrue("expected johann follows bbitdiddle", contenir(recup(followsGraph,"johann"),"bbitdiddle"));
        assertTrue("expected johann follows alan", contenir(recup(followsGraph,"johann"),"alan"));
        assertTrue("expected johann follows shannon", contenir(recup(followsGraph,"johann"),"shannon"));
        assertTrue("expected alan follows 2 people", recup(followsGraph,"alan").size() == 2);
        assertTrue("expected alan follows bbitdiddle", contenir(recup(followsGraph,"alan"),"bbitdiddle"));
        assertTrue("expected alan follows johann", contenir(recup(followsGraph,"alan"),"bbitdiddle"));
        assertTrue("expected shannon follows 1 people", recup(followsGraph,"shannon").size() == 1);
        assertTrue("expected shannon follows johann", contenir(recup(followsGraph,"shannon"),"johann"));
    }
    
    
    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}

