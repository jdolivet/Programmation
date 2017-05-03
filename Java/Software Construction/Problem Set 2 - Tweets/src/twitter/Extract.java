package twitter;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        List<Instant> instantList = new ArrayList<Instant>();
        for(Tweet tweet : tweets){  // extract the Timestamp of each tweets
            instantList.add(tweet.getTimestamp());
        }
        if(instantList.isEmpty()){
            Timespan result = new Timespan(Instant.MIN,Instant.MIN);
            return result;
        }else {
            instantList.sort((a, b)->a.compareTo(b));
            int taille = instantList.size();
            Timespan result = new Timespan(instantList.get(0),instantList.get(taille - 1));
            return result;         
        }
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    private static String mentionRegex = "(^|[^\\w_-])@([\\w_-]+)";
    private static Pattern mentionPattern = Pattern.compile(mentionRegex);
    
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> textList = new HashSet<String>();
        for(Tweet tweet : tweets){  // extract the text of each tweets
            textList.add(tweet.getText());
        }
        Set<String> nameList = new HashSet<String>();
        for(String text : textList){  
            Matcher scanner = mentionPattern.matcher(text);
            while (scanner.find()) {
                String result = scanner.group(2);
                result = result.toLowerCase();
                nameList.add(result);
            }
        }
        return nameList;         
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
