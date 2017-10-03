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
    Map<Integer, List<Integer>> friendship; // can't be modified other than follow and unfollow
    Map<Integer, List<Tweet>> tweets;  // save tweets with its owner as key

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
        if(following == null || following.isEmpty()) return getTimeline(user_id);

        List<Tweet> result = new ArrayList<>();

        // maxheap for top k tweets
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>(new Comparator<Tweet>(){
            @Override
            public int compare(Tweet a, Tweet b){
                return b.id - a.id;
            }
        });

        Map<Integer, List<Tweet>> timelineContainer = new HashMap<>();
        Map<Integer, Integer> indexContainer = new HashMap<>();

        // retrive all following's timeline
        for(int i : following){
            List<Tweet> temp = getTimeline(i);
            if(temp != null && !temp.isEmpty()){
                timelineContainer.put(i, temp);
                maxHeap.add(temp.get(0));
                indexContainer.put(i, 0);
            }
        }

        // retrive user's time line
        List<Tweet> userTimeline = getTimeline(user_id);
        if(userTimeline != null && !userTimeline.isEmpty()){
            timelineContainer.put(user_id, userTimeline);
            maxHeap.add(userTimeline.get(0));
            indexContainer.put(user_id, 0);
        }

        int count = 0;
        while(!maxHeap.isEmpty() && count < 10){
            Tweet tempTweet = maxHeap.poll();
            result.add(tempTweet);
            int index = indexContainer.get(tempTweet.user_id);
            index++;
            List<Tweet> tempTimeline = timelineContainer.get(tempTweet.user_id);
            if(index < tempTimeline.size()) maxHeap.add(tempTimeline.get(index));
            indexContainer.put(tempTweet.user_id, index);
            count++;
        }

        return result;
    }

    /*
     * @param user_id: An integer
     * @return: a list of 10 new posts recently and sort by timeline
     */
    public List<Tweet> getTimeline(int user_id) {
        List<Tweet> total = tweets.get(user_id);
        if(total == null) return new ArrayList<Tweet>();
        else{
            List<Tweet> result = new ArrayList<>();

            // retrive tweets from tail to head in order to get sorted tweets by timeline
            for(int i = total.size()-1; i >= total.size()-10 && i >= 0; i--){
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
        System.out.println("follow: " + friendship.get(from_user_id).toString());
    }

    /*
     * @param from_user_id: An integer
     * @param to_user_id: An integer
     * @return: nothing
     */
    public void unfollow(int from_user_id, int to_user_id) {
        // use Integer to remove specific value
        friendship.get(from_user_id).remove(Integer.valueOf(to_user_id));
        System.out.println("unfollow: " + friendship.get(from_user_id).toString());

    }
}