package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy
     * 
     * methods of the Extract class
     * 
     * for the getTimespan method
     * Partition the inputs as follows:
     *      size of the list :       0, 1, 2, > 2
     *      For size 0 : no expected output, just pass
     *      For the test for size > 2 the list is not ordered on the Timestamp
     *      Test to check if the input is modified by the method
     * 
     * for the getMentionedUsers method
     *      Checking with insensitive case
     * Partition the inputs as follows:
     *      numbers of mentionedUser :   0, 1, 2
     *      For 2 mentionedUser, 4 cases : 
     *          2 different users and 2 different mentions
     *          2 different users and 2 same mention
     *          one user and 2 different mentions
     *          one user and 2 same mention
     *      Testing the constraint on @username :
     *          case-insensitive
     *          username-mention format
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
    private static final Tweet tweet5 = new Tweet(5, "john", "@johann Indeed!", d3);
    private static final Tweet tweet6 = new Tweet(6, "alyssa", "@johann @john Thanks!", d4);
    private static final Tweet tweet7 = new Tweet(7, "bbitdiddle", "@johann @johann", d4);
    private static final Tweet tweet8 = new Tweet(8, "bbitdiddle", "@j-_8o*hann _@alan 9@shannon -@alan y@alan *@The0_phil-/", d4);
    private static final Tweet tweet9 = new Tweet(9, "bbitdiddle", "@joHAnn @johann", d4);

 
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    
    //
    // Tests for the getTimespan method of Extract class
    //
    
    // test if the list of tweets is modified by the method 
    @Test
    public void testGetTimespanNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        Extract.getTimespan(listeInit);
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }
    
    // covers of the size of the list = 0
    @Test
    public void testGetTimespanZeroTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        
        assertTrue("expected result for empty list", timespan.getStart() == timespan.getEnd());
    }
    
    // covers of the size of the list = 1
    @Test
    public void testGetTimespanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d1, timespan.getEnd());
    }
    
    // covers of the size of the list = 2
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    // covers of the size of the list = 4, "not ordered"
    @Test
    public void testGetTimespanFourTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet1, tweet3, tweet4));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());
    } 
    
    
    //
    // Tests for the getMentionedUsers method of Extract class
    //
    
    // usefull function to test if a string is in a set (with insensitive case)
    private static boolean contenir(Set<String> liste, String str) {
        for (String s : liste) {
            if (str.equalsIgnoreCase(s)) return true;
        }
        return false;
    }
    
    // test if the list of tweets is modified by the method 
    @Test
    public void testGetMentionedUsersNoModification() {
        List<Tweet>  listeInit = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        List<Tweet>  listeBis = Arrays.asList(tweet2, tweet1, tweet3, tweet4);
        Extract.getMentionedUsers(listeInit);
        
        assertTrue("expected no modified", listeBis.equals(listeInit));
    }
    
    // covers of the case no mention    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }
    
    // covers of the case : one mention
    @Test
    public void testGetMentionedUsersOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3));
        
        assertTrue("expected set contains alyssa or alYssa or ... ", contenir(mentionedUsers, "aLYssa"));
    }
    
    // covers of the case : two same mentions by two tweets
    @Test
    public void testGetMentionedUsersTwoSameMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet3, tweet4));
        
        assertTrue("expected set size is 1", mentionedUsers.size()==1);
    }
    
    // covers of the case : two different mentions by two tweets
    @Test
    public void testGetMentionedUsersTwoDiffMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet4, tweet5));
        
        assertTrue("expected set contains alyssa", contenir(mentionedUsers, "aLYssa"));
        assertTrue("expected set contains johann", contenir(mentionedUsers, "jOHann"));

    }
    
    // covers of the case : two different mentions by one tweet
    @Test
    public void testGetMentionedUsersTwoDiffByOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet6));
        
        assertTrue("expected set contains johann", contenir(mentionedUsers, "jOHann"));
        assertTrue("expected set contains john", contenir(mentionedUsers, "jOHn"));

    }
    
    // covers of the case : two same mentions by one tweet
    @Test
    public void testGetMentionedUsersTwoSameByOneMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet7));
        
        assertTrue("expected set contains johann", contenir(mentionedUsers, "jOHann"));
        assertTrue("expected set contains just 1 element", mentionedUsers.size()==1);

    }
    
    // covers of the case : username-mention format
    @Test
    public void testGetMentionedUsersFormatUsernameMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet8));
        
        assertTrue("expected set contain j-_8o", contenir(mentionedUsers, "j-_8O"));
        assertTrue("expected set contain The0_phil-", contenir(mentionedUsers, "The0_phil-"));
        assertTrue("expected set contain just 2 elements", mentionedUsers.size()==2);

    }
    
    // covers of the case : case-insensitive
    @Test
    public void testGetMentionedUsersCaseInsensitive() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet9));
        
        assertTrue("expected set contains johann", contenir(mentionedUsers, "jOHann"));
        assertTrue("expected set contains just 1 element", mentionedUsers.size()==1);

    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */


    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */

}
