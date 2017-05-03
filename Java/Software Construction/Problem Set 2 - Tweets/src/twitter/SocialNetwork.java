package twitter;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SocialNetwork provides methods that operate on a social network.
 * 
 * A social network is represented by a Map<String, Set<String>> where map[A] is
 * the set of people that person A follows on Twitter, and all people are
 * represented by their Twitter usernames. Users can't follow themselves. If A
 * doesn't follow anybody, then map[A] may be the empty set, or A may not even exist
 * as a key in the map; this is true even if A is followed by other people in the network.
 * Twitter usernames are not case sensitive, so "ernie" is the same as "ERNie".
 * A username should appear at most once as a key in the map or in any given
 * map[A] set.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class SocialNetwork {

    /**
     * Guess who might follow whom, from evidence found in tweets.
     * 
     * @param tweets
     *            a list of tweets providing the evidence, not modified by this
     *            method.
     * @return a social network (as defined above) in which Ernie follows Bert
     *         if and only if there is evidence for it in the given list of
     *         tweets.
     *         One kind of evidence that Ernie follows Bert is if Ernie
     *         @-mentions Bert in a tweet. This must be implemented. Other kinds
     *         of evidence may be used at the implementor's discretion.
     *         All the Twitter usernames in the returned social network must be
     *         either authors or @-mentions in the list of tweets.
     */
    
    // adapting the getMentionedUser to find the mentions just after the @
    private static String mentionRegex = "@([\\w_-]+)";
    private static Pattern mentionPattern = Pattern.compile(mentionRegex);
    // return all the mentioned user of a tweet in a list of string
    private static Set<String> getMentionedUsersAdapted(Tweet tweet) {
        String text = tweet.getText();
        Set<String> nameList = new HashSet<String>();
        Matcher scanner = mentionPattern.matcher(text);
        while (scanner.find()) {
            String result = scanner.group(1);
            result = result.toLowerCase();
            nameList.add(result);
        }
        return nameList;         
    }
    
    // adapting thegetMentionedUsersAdapted to find the keywords just after the #
    private static String mentionHashtagRegex = "#([\\w_-]+)";
    private static Pattern mentionHashtagPattern = Pattern.compile(mentionHashtagRegex);
    // return all the hashtags of a tweet in a list of string
    private static Set<String> getHashtag(Tweet tweet) {
        String text = tweet.getText();
        Set<String> nameList = new HashSet<String>();
        Matcher scanner = mentionHashtagPattern.matcher(text);
        while (scanner.find()) {
            String result = scanner.group(1);
            result = result.toLowerCase();
            nameList.add(result);
        }
        return nameList;         
    }
        
    public static Map<String, Set<String>> guessFollowsGraph(List<Tweet> tweets) {
        Map<String, Set<String>> mapFollowing = new HashMap<String, Set<String>>();     
        // Start the process
        for (Tweet tweet : tweets){
            String author = tweet.getAuthor();
            author = author.toLowerCase();
            Set<String> alreadyFollowing = mapFollowing.getOrDefault(author, new HashSet<String>());
            // first method : with @ in the tweet
            Set<String> followingName = getMentionedUsersAdapted(tweet);
            for (String name : followingName){
                if (name.equals(author) == false){
                    alreadyFollowing.add(name);
                }
            }
            // second method : with common #
            Set<String> listHashtag = getHashtag(tweet);
            for (Tweet twit : tweets){
                if (twit.getAuthor().equalsIgnoreCase(author) == false){
                    Set<String> listHashtagbis = getHashtag(twit);
                    for (String word : listHashtagbis){
                        if (listHashtag.contains(word)){
                            String authToAdd = twit.getAuthor().toLowerCase();
                            alreadyFollowing.add(authToAdd);
                        }       
                    }
                }
            }
            mapFollowing.put(author, alreadyFollowing);
        }
        return mapFollowing;
    }

    /**
     * Find the people in a social network who have the greatest influence, in
     * the sense that they have the most followers.
     * 
     * @param followsGraph
     *            a social network (as defined above)
     * @return a list of all distinct Twitter influents usernames in followsGraph, in
     *         descending order of follower count.
     */
    public static List<String> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> influents = new HashMap<String, Integer>();
        // take all the people just one time and the frequency in a map
        Set<String> peoples = new HashSet<String>();
        for(String cle : followsGraph.keySet()){
            if(peoples.add(cle.toLowerCase())){
                influents.put(cle.toLowerCase(), 0);
            }        
            for(String valeur : followsGraph.get(cle)){
                valeur = valeur.toLowerCase();
                if(peoples.add(valeur)){
                    int freq = 1;
                    influents.put(valeur, freq);
                }else{
                    int freq = influents.get(valeur);
                    freq += 1;
                    influents.put(valeur, freq);
                }
            }            
        }  
        // Prepare a comparator to order the map with the value
        Set<Entry<String, Integer>> set = influents.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        // Construct the list
        List<String> influentsPeople = new ArrayList<String>();
        for(Map.Entry<String, Integer> entry:list){
            influentsPeople.add(entry.getKey());
        }
        return influentsPeople;
    }

    /* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
 