package twitter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> authorList = new ArrayList<Tweet>();
        for (Tweet tweet : tweets){
            String author = tweet.getAuthor();
            if (author.equalsIgnoreCase(username) == true) {
                authorList.add(tweet);
            }
        }
        return authorList;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        Instant start = timespan.getStart();
        Instant end = timespan.getEnd();
        List<Tweet> inTimespanList = new ArrayList<Tweet>();
        for (Tweet tweet : tweets){
            Instant time = tweet.getTimestamp();
            if (start.compareTo(time) <=0 && end.compareTo(time) >=0) {
                inTimespanList.add(tweet);
            }
        }
        return inTimespanList;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */        
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> textList = new ArrayList<Tweet>();
        for (Tweet tweet : tweets){
            String text = tweet.getText();
            boolean found = false;
            for (String word : words){
                String mentionRegex = "(^|\\s)" + Pattern.quote(word) + "($|\\s)";
                Pattern mentionPattern = Pattern.compile(mentionRegex, Pattern.CASE_INSENSITIVE);
                Matcher scanner = mentionPattern.matcher(text);
                if (scanner.find()){
                    found = true;
                    break;
                }
            }
            if (found == true){
                textList.add(tweet);
            }            
        }
        return textList;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
