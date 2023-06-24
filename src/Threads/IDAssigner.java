package Threads;

/**
 * The IDAssigner class is responsible for assigning unique IDs to objects.
 */
public class IDAssigner {
    private int baseID;

    /**
     * Constructs an IDAssigner object with the specified base ID.
     *
     * @param baseID the base ID from which the assignment starts
     */
    public IDAssigner(int baseID) {
        this.baseID = baseID;
    }

    /**
     * Retrieves the next available ID.
     *
     * @return the next available ID
     */
    public int next() {
        return baseID++;
    }
}
