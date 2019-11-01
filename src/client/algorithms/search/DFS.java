package client.algorithms.search;

import client.algorithms.DataStructures;

public class DFS extends BFS{

    private final static int MAX_DEPTH_LIMIT = 300;

    @Override
    public DataStructures<String> getDataStructure(){
        return new client.algorithms.Stack<>();
    }

    @Override
    boolean getLimit(int depth) {
        return depth < MAX_DEPTH_LIMIT;
    }
}
