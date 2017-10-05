public class Memcache {
    Map<Integer, int[]> cache;

    public Memcache() {
        cache = new HashMap<>();
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: An integer
     */
    public int get(int curtTime, int key) {
        // retrive directly, don't use containsKey() in if statement
        int[] temp = cache.get(key);
        if(temp == null || (curtTime > temp[0]+temp[2]-1 && temp[2] != 0)) return Integer.MAX_VALUE;
        else return temp[1];
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param value: An integer
     * @param ttl: An integer
     * @return: nothing
     */
    public void set(int curtTime, int key, int value, int ttl) {
       int[] val = new int[]{curtTime, value, ttl};
       cache.put(key, val);
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @return: nothing
     */
    public void delete(int curtTime, int key) {
        cache.remove(key);
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int incr(int curtTime, int key, int delta) {
        int temp = get(curtTime, key);
        if(temp != Integer.MAX_VALUE){
            cache.get(key)[1] = temp + delta;
            return temp + delta;
        }
        return Integer.MAX_VALUE;
    }

    /*
     * @param curtTime: An integer
     * @param key: An integer
     * @param delta: An integer
     * @return: An integer
     */
    public int decr(int curtTime, int key, int delta) {
        int temp = get(curtTime, key);
        if(temp != Integer.MAX_VALUE){
            cache.get(key)[1] = temp - delta;
            return temp - delta;
        }
        return Integer.MAX_VALUE;
    }
}