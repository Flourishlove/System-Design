/**
 * Definition of Tweet:
 * public class Tweet {
 *     public int id;
 *     public int user_id;
 *     public String text;
 *     public static Tweet create(int user_id, String tweet_text) {
 *         // This will create a new tweet object,
 *         // and auto fill id
 *     }
 * }
 */


public class MiniTwitter {
    Map<Integer, List<Integer>> friendship;
    Map<Integer, List<Tweet>> tweets;

    public MiniTwitter() {
        friendship = new HashMap<>();
        tweets = new HashMap<>();
    }

    /*
     * @param user_id: An integer
     * @param tweet_text: a string
     * @return: a tweet
     */
    public Tweet postTweet(int user_id, String tweet_text) {
        // write your code here
        Tweet iTweet = Tweet.create(user_id, tweet_text);
        if(tweets.containsKey(user_id)) tweets.get(user_id).add(iTweet);
        else{
            List<Tweet> temp = new ArrayList<>();
            temp.add(iTweet);
            tweets.put(user_id, temp);
        }
        return iTweet;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new feeds recently and sort by timeline
     */
    public List<Tweet> getNewsFeed(int user_id) {
        List<Integer> following = friendship.get(user_id);
        if(following == null) return getTimeline(user_id);

        following.add(user_id);
        List<Tweet> result = new ArrayList<>();


        return result;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new posts recently and sort by timeline
     */
    public List<Tweet> getTimeline(int user_id) {
        List<Tweet> total = tweets.get(user_id);
        if(total == null || total.size() <= 10) return total;
        else{
            List<Tweet> result = new ArrayList<>();
            for(int i = total.size()-1; i >= 0; i--){
                result.add(total.get(i));
            }
            return result;
        }
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void follow(int from_user_id, int to_user_id) {
        if(friendship.containsKey(from_user_id)) friendship.get(from_user_id).add(to_user_id);
        else{
            List<Integer> temp = new ArrayList<>();
            temp.add(to_user_id);
            friendship.put(from_user_id, temp);
        }
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void unfollow(int from_user_id, int to_user_id) {
        // use Integer to remove specific value
        friendship.get(from_user_id).remove(Integer.valueOf(to_user_id));
    }
}