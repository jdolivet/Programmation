package twitter;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;




public class Main {
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2016-02-17T13:00:00Z");
    private static final Instant d4 = Instant.parse("2016-02-17T14:00:00Z");

    
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    private static final Tweet tweet3 = new Tweet(3, "johann", "Interesting! @alyssa", d3);
    private static final Tweet tweet4 = new Tweet(4, "alan", "Yeah! @alyssa", d2);
    private static final Tweet tweet5 = new Tweet(5, "john", "@johann Indeed!", d3);
    private static final Tweet tweet6 = new Tweet(6, "alyssa", "@johann @alan @shannon @jo_hn Thanks!", d4);
    private static final Tweet tweet7 = new Tweet(7, "bbitdiddle", "@joh*ann bis!", d4);  
    private static final Tweet tweet8 = new Tweet(8, "bbitdiddle", "@jo*hann _@alan 9@shannon -@alan y@alan", d4);

    private static boolean unique(Set<String> liste) {
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
    
    public static Map<String, Integer> influencers(Map<String, Set<String>> followsGraph) {
        Map<String, Integer> influents = new HashMap<String, Integer>();
        // take all the people just one time and the frequency
        Set<String> peoples = new HashSet<String>();
        for(String cle : followsGraph.keySet()){
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
        return influents;
    }
    
    private static String mentionRegex = "[\\w_-]+";
    private static Pattern mentionPattern = Pattern.compile(mentionRegex);
    
    public static void main(String[] args) {
        // for the Extract methods
        List<Tweet> listTweet = Arrays.asList(tweet6);
        Timespan timespan = Extract.getTimespan(listTweet);
        System.out.println(timespan);
        Set<String> mentionedUsers = Extract.getMentionedUsers(listTweet);
        System.out.println(mentionedUsers);
        // f
        HashSet<String> liste = new HashSet<String>();
        liste.add("alyssa");
        liste.add("aLyssa");
        System.out.println(liste);
        System.out.println(unique(liste));
        HashSet<String> liste2 = new HashSet<String>();
        liste2.add("alyssa");
        liste2.add("joHn");
        System.out.println(liste2);
        System.out.println(unique(liste2));
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
        followsGraph.put("jo$hn", Collections.EMPTY_SET);
System.out.println(SocialNetwork.influencers(followsGraph));
Set<String> nameList = new HashSet<String>();
nameList.add("john");
nameList.add("jo$hn");
System.out.println(nameList);
Matcher scanner = mentionPattern.matcher("jo$hn");
System.out.println(Collections.emptyMap().entrySet());

    }
}
