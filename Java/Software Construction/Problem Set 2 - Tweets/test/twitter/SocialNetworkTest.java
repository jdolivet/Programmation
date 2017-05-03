package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;


import org.junit.Test;

public class SocialNetworkTest {

    /*
     * Testing strategy
     *  
     * methods of the SocialNetwork class
     *  
     * for the guessFollowsGraph method
     * Partition the inputs as follows:
     *      For empty list
     *      For one tweet, and follow nobody
     *      For one tweet, and follow one
     *      For one tweet, and follow himself
     *      For one tweet, and follow two different in the same
     *      For two tweets from the same
     *      For two tweets from two different, and follow the same in each
     *      For two tweets from two different, and follow one different in each
     *      Check the insensitive case  
     *      
     * for the influencer method
     * Partition the inputs as follows:
     *      For empty map
     *      For map with empty set
     *      For another map to check the order
     *      
     *      all tests must be insensitive case
     *      
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T14:00:00Z");

    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #miT @joHn", d2);
    private static final Tweet tweet3 = new Tweet(3, "johann", "Interesting!#Mit @alyssa @bbitdiddle", d3);
    private static final Tweet tweet4 = new Tweet(4, "alan", "@alyssa #python#MIT #java @alyssa", d2);
    private static final Tweet tweet5 = new Tweet(5, "bbitdiddle", "+@johann #java Indeed!", d3);
    private static final Tweet tweet6 = new Tweet(6, "johann", "Interesting! @aLyssa @johann @joHann", d4);
    private static final Tweet tweet7 = new Tweet(7, "bBITdiddle", "Interesting! @aLyssa @johann @joHann", d4);
    private static final Tweet tweet8 = new Tweet(8, "john", "fa$st", d4);

    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //
    // Tools for the tests in SocialNetwork class
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
    
    
    // usefull function to get the index of a string in a string list (with insensitive case on key)
    // because tests don't know what's the case of key
    private static int rang(List<String> liste, String str) {
        for (String s : liste) {
            if (s.equalsIgnoreCase(str)){
                return liste.indexOf(s);
            }
        }
        return -1;
    }
    
    // usefull function to test if each string in a set is unique (with different case)
    private static boolean unique(Set<String> liste) {
        if (liste.isEmpty()){
            return true;
        }
        for (String s1 : liste) {
            for (String s2 : liste) {
                if (s1 != s2) {
                    if (s1.equalsIgnoreCase(s2)){
                        return false;
                    }                    
                }
            }
        }
        return true;
    }


    
    //
    // Tests for the guessFollowsGraph method of SocialNetwork class
    //
 
    // test if the list of tweets is modified by the method 
    @Test
    public void testGuessFollowsGraphNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        SocialNetwork.guessFollowsGraph(listeInit);
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }

    // test for one tweet, following another one insensitive case
    @Test
    public void testGuessFollowsGraphOneTweetOneQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        
        assertTrue("expected correspondant value contains at least john", contenir(recup(followsGraph,"bbitdiddle"),"john"));
    }
    
    
    // test for one tweet, check if username non author or non 0 mention is add
    @Test
    public void testGuessFollowsGraphOneTweetNoGhost() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet8));
                  
        assertFalse("add legal username", contenir(recup(followsGraph,"john"),"fa$st"));
    } 
    
    // test for one tweet, following himself and insensitive case
    @Test
    public void testGuessFollowsGraphOneTweetHimselfQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet6));
        
        assertFalse("expected correspondant value not to contain johann", contenir(recup(followsGraph,"johann"),"johann"));
    }
    
    // test for one tweet, following others two different
    @Test
    public void testGuessFollowsGraphOneTweetTwoQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        
        assertTrue("expected correspondant value contains alyssa", contenir(recup(followsGraph,"johann"),"alyssa"));
        assertTrue("expected correspondant value contains bbitdiddle", contenir(recup(followsGraph,"johann"),"bbitdiddle"));
    }
    
    // test for two tweet, from the same following two different
    @Test
    public void testGuessFollowsGraphTwoTweetFromSameTwoQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2,tweet5));
        
        assertTrue("expected correspondant value contains john", contenir(recup(followsGraph,"bbitdiddle"),"johann"));
        assertTrue("expected correspondant value contains johann", contenir(recup(followsGraph,"bbitdiddle"),"john"));
    }

    
    // test for two tweet, from different following the same
    @Test
    public void testGuessFollowsGraphTwoTweetFromDifferentSameQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3,tweet4));
        
        assertTrue("expected johann value contains alyssa", contenir(recup(followsGraph,"johann"),"alyssa"));
        assertTrue("expected john value contains alyssa", contenir(recup(followsGraph,"alan"),"alyssa"));
    }
    
    // test for two tweet, from different following two different
    @Test
    public void testGuessFollowsGraphTwoTweetFromDifferentTwoQuote() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5,tweet4));
        
        assertTrue("expected bbitdiddle value contains johann", contenir(recup(followsGraph,"bbitdiddle"),"johann"));
        assertTrue("expected alan value contains alyssa", contenir(recup(followsGraph,"alan"),"alyssa"));
    }
    
    // test for two tweet, from same with different sensitive case
    @Test
    public void testGuessFollowsGraphTwoTweetFromSameSensitiveCase() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet5,tweet7));
        
        assertTrue("expected no repeated username in the set", unique(recup(followsGraph, "bBITdiddle")));
    }
    

    //
    // Tests for the influencers method of SocialNetwork class
    //
    
    // Test for the empty map
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }
    
    // Test for the empty nodes
    @Test
    public void testInfluencersEmptyNodes() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        Set<String> set= new HashSet<String>();
        followsGraph.put("pim", set);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected pim", influencers.get(0) == "pim");
    }
    
    // Test for map 
    @Test
    public void testInfluencersThreeKeys() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        // 1st set or pim
        Set<String> set1= new HashSet<String>();
        set1.add("john");
        set1.add("aLYssa");
        set1.add("johann");
        followsGraph.put("pim", set1);
        // 2nd set or pam
        Set<String> set2= new HashSet<String>();
        set2.add("shannon");
        set2.add("alySsa");
        followsGraph.put("pam", set2);
        // 3nd set or pom
        Set<String> set3= new HashSet<String>();
        set3.add("alyssa");
        set3.add("shannon");
        followsGraph.put("pom", set3);
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected list of size 7", influencers.size() == 7);
        assertTrue("expected index of alyssa is 0", rang(influencers,"alyssa") == 0);
        assertTrue("expected index of shannon is 1", rang(influencers,"shannon") == 1);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
