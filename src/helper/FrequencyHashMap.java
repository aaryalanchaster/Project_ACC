package helper;

//@author Pratikraj Rajput
public class FrequencyHashMap {
    /**
     * class HashMap stores a node of the LinkedList
     */
    public static class HashMap {
        private String key;
        private int value;
        private HashMap next;

        public HashMap(String key, int value, HashMap next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public String getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public HashMap getNext() {
            return next;
        }

        public void setNext(HashMap next) {
            this.next = next;
        }
    }

    // MAP is used to store the table
    private HashMap[] map;
    // Initial size of the table
    private final int INITIAL_CAPACITY = 16;
    // Number of keys in the HashMap
    private int size = 0;

    public FrequencyHashMap() {
        this.map = new HashMap[INITIAL_CAPACITY];
    }

    public FrequencyHashMap(int capacity) {
        this.map = new HashMap[capacity];
    }

    /**
     * @param key
     */
    public void insert(String key) {
        if (key == null)
            return;
        // Getting index from the hash function
        int index = myhash(key);
        // Default frequency of any key is 1
        HashMap newEntry = new HashMap(key, 1, null);
        // case where bucket is empty
        if (this.map[index] == null) {
            this.map[index] = newEntry;
            // Rehash if number of keys exceed map's capacity
            if (++this.size > this.map.length)
                rehash();
        } else {
            // case where bucket is not empty, node needs to traverse the LinkedList
            HashMap prev = null;
            HashMap curr = this.map[index];
            while (curr != null) {
                // If match is found, update frequency and return
                if (curr.getKey().equals(key)) {
                    curr.setValue(curr.getValue() + 1);
                    prev = null;
                    break;
                }
                prev = curr;
                curr = curr.getNext();
            }
            // Append a new node
            if (prev != null) {
                prev.setNext(newEntry);
                if (++this.size > this.map.length)
                    rehash();
            }
        }
    }

    /**
     * @param key
     * @return the frequency of the key if found, otherwise -1
     */
    public int get(String key) {
        if (key == null)
            return -1;
        // Getting index from the hash function
        int index = myhash(key);
        int value = 0;
        HashMap list = this.map[index];
        // Find the key from the LinkedList and return
        while (list != null) {
            if (list.getKey().equals(key)) {
                value = list.getValue();
                break;
            }
            list = list.getNext();
        }
        return value;
    }

    /**
     * @param key
     */
    public void remove(String key) {
        if (key == null)
            return;
        // Getting index from the hash function
        int index = myhash(key);
        HashMap previous = null;
        HashMap entry = this.map[index];
        // Find and delete the node from the LinkedList and return
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                // case where root matches the key
                if (previous == null) {
                    entry = entry.getNext();
                    this.map[index] = entry;
                    --this.size;
                    return;
                } else {
                    previous.setNext(entry.getNext());
                    --this.size;
                    return;
                }
            }
            previous = entry;
            entry = entry.getNext();
        }
    }

    /**
     * @param key
     * @return whether the key exists in the HashMap or not
     */
    public boolean contains(String key) {
        // Getting the index from the hash function
        int index = myhash(key);
        int value = -1;
        HashMap list = this.map[index];
        // Check and return the node from the LinkedList
        while (list != null) {
            if (list.getKey().equals(key)) {
                value = list.getValue();
                break;
            }
            list = list.getNext();
        }
        if (value == -1)
            return false;
        return true;
    }

    /**
     * @return the HashMap
     */
    public HashMap[] getMap() {
        return this.map;
    }

    /**
     * @param key
     * @return index on which to insert in the HashMap
     */
    private int myhash(String key) {
        // djb2 Hash Function
        int hash = 5381;
        for (int i = 0; i < key.length(); ++i)
            hash = ((hash << 5) + hash) + key.charAt(i);
        // 16n9RightShift
        hash ^= (hash >>> 16);
        hash ^= (hash >>> 9);
        // The bitwise on length instead of module performs better arithmetically inside
        // the CPU
        return hash & (this.map.length - 1);
    }

    private void rehash() {
        // Copy the old map
        HashMap[] oldMap = this.map;
        // Double the size of the table
        this.map = new HashMap[2 * this.map.length];
        // Resize
        this.size = 0;
        // Re-enter all the values
        for (HashMap entry : oldMap) {
            while (entry != null) {
                insert(entry.getKey());
                entry = entry.next;
            }
        }
    }
}