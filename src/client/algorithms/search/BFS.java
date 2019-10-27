package client.algorithms.search;

import server.Game.Board;
import server.Game.Directions;

import client.algorithms.Algorithm;
import server.Game.WrongMoveException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm {

    private Queue<String> queue;

    //          this map saves the path.
    private HashMap<String, String> giveChildGetParent;

    //          which move was it from.
    private HashMap<String, String> giveParentGetMove;

    //          saves the ones already have been visited. in order to avoid repetition.
    private HashSet<String> visited;

    @Override
    public String makeMove(String[][] grid) {

        queue = new LinkedList<>();

        //      this map saves the path.
        giveChildGetParent = new HashMap<>();

        //      which move was it from.
        giveParentGetMove = new HashMap<>();

        //      saves the ones already have been visited. in order to avoid repetition.
        visited = new HashSet<>();

        //      copies the grid and then uses it.
        Board myVirtualBoard = new Board(3, grid);
        //      current state.
        String[][] currentState = getGrid(myVirtualBoard);

        //      if the grid is already finished no need to do anything.
        if (!myVirtualBoard.isFinished()) {

           //  the expanding node should always get a visited tag.
            visited.add(myVirtualBoard.toString());

           /*   I need to save the move to know if
            *   it turns out good I use it once the
            *   graph is traversed. and returning by
            *   keeping the parents in the hashMap
            *   variable named giveChildGetParent.
            */
            try {
                // -------------------------------------------------------- DOWN:
                initialProgress(myVirtualBoard, Directions.DOWN);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                // -------------------------------------------------------- UP:
                initialProgress(myVirtualBoard, Directions.UP);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                // -------------------------------------------------------- LEFT:
                initialProgress(myVirtualBoard, Directions.LEFT);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                // -------------------------------------------------------- RIGHT:
                initialProgress(myVirtualBoard, Directions.RIGHT);

                // -------------------------------------------------------- Change state:
                currentState = getGrid(queue.remove());
                myVirtualBoard = new Board(3, currentState);
            }
            catch(ProduceTestingPassedException e){
                return giveParentGetMove.get(myVirtualBoard.toString());
            }
        }

        try {

            //      until the game isn't finished go through the graph.
            while (!myVirtualBoard.isFinished()) {

                //  the expanding node should always get a visited tag.
                visited.add(myVirtualBoard.toString());

                inProgress(currentState, myVirtualBoard, Directions.DOWN);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                inProgress(currentState, myVirtualBoard, Directions.UP);
                myVirtualBoard = new Board(3, currentState);

                inProgress(currentState, myVirtualBoard, Directions.RIGHT);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                inProgress(currentState, myVirtualBoard, Directions.LEFT);

                //  change the state.
                currentState = getGrid(queue.remove());
                myVirtualBoard = new Board(3, currentState);

            }
        }
        catch (ProduceTestingPassedException e) {

            //      if this is false it means, it never went through the while loop.
            //      **or it has been one of the first children which the beginning state made.**
            if (giveChildGetParent.containsKey(myVirtualBoard.toString())) {

                while (giveChildGetParent.get(myVirtualBoard.toString()) != null) {

                    currentState = getGrid(giveChildGetParent.get(myVirtualBoard.toString()));
                    myVirtualBoard = new Board(3, currentState);
                }
                String answer = giveParentGetMove.get(myVirtualBoard.toString());

                //  debug log:
                System.out.println("a.i. made the move : " + answer);
                return answer;
            }

            //      this is true if it hasn't been finished in the first place.
            else if (!giveParentGetMove.isEmpty()) {

                String answer = giveParentGetMove.get(myVirtualBoard.toString());

                //  debug log:
                System.out.println("a.i. made the move : " + answer);
                return answer;
            }
        }

        // this eventually shouldn't happen.
        return null;
    }

   /*
    *           getGrid function:
    *
    *           gets a board and converts it to a human understandable grid which means, that
    *           I am using this function to see the output of the movement that a.i. is making
    *           in its head in each step.
    *
    */
    private String [][] getGrid(Board board){

        return getGrid(board.toString());
    }

    private String [][] getGrid(String s){

        String [] row = s.split("\n");
        String [][] grid = new String[row.length][row.length];
        for (int i = 0; i < row.length; i++) {
            grid[i] = row[i].split("\t");
        }
        return grid;
    }

    private void tryMove(Directions dir, Board myVirtualBoard) throws ProduceTestingPassedException{

            //      tries move. also changes the grid within the board.
            myVirtualBoard.makeMove(dir);

        if(!visited.contains(myVirtualBoard.toString())) {

            //      the searching has ended if this is true.
            if (myVirtualBoard.isFinished())
            /*
                throwing exception makes the search and adding to the queue to stop because it is bfs
                and it finds the solution once it has been produced after an expansion.
            */
                throw new ProduceTestingPassedException();
            //      add to the queue.
            queue.add(myVirtualBoard.toString());
        }
    }

    private void initialProgress(Board board, Directions dir){

        try {

            tryMove(dir, board);
            //      add the move to the corresponding board (itself not the parent which is the start state) map.
            giveParentGetMove.put(board.toString(), dir.toString());
        }
        catch (WrongMoveException e){

            //System.out.println(e);
        }
        catch (ProduceTestingPassedException e){

            //System.out.println(e);
            giveParentGetMove.put(board.toString(), dir.toString());
            throw new ProduceTestingPassedException();
        }
    }

    private void inProgress(String [][] currentState, Board board, Directions dir){

        try {
            tryMove(dir, board);
            //      added to the path. (used the Board toString() because i wanted it to be in the standard format).

            if(!visited.contains(board.toString()))

            giveChildGetParent.put(board.toString(),
                    new Board(3, currentState).toString());

        }
        catch (WrongMoveException e){

            //System.out.println(e);
        }
        catch (ProduceTestingPassedException e){

            //System.out.println(e);

            giveChildGetParent.put(board.toString(),
                    new Board(3, currentState).toString());
            throw new ProduceTestingPassedException();
        }
    }
}
