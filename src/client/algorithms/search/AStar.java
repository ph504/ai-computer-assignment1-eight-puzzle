package client.algorithms.search;

import client.algorithms.Algorithm;
import server.Game.Board;

import java.util.PriorityQueue;

public class AStar implements Algorithm{
    PriorityQueue<String> openList;

    @Override
    public String makeMove(String[][] grid) {
        Board myVirtualBoard = new Board(3, grid);
        while(myVirtualBoard.isFinished()){

        }
        return null;
    }
}
