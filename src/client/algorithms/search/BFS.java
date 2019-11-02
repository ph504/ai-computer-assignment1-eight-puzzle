package client.algorithms.search;

import client.algorithms.DataStructures;
import client.algorithms.Queue;

import server.Game.Board;
import server.Game.Directions;

import client.algorithms.Algorithm;
import server.Game.WrongMoveException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

public class BFS implements Algorithm {

    private static Stack<String> stackOfMoves; // the solution for the bug. of the second commit.

    private DataStructures dataStructures;

    //              this map saves the path.
    private HashMap<String, String> giveChildGetParent;

    //              which move was it from.
    private HashMap<String, String> giveParentGetMove;

    //              saves the ones already have been visited. in order to avoid repetition.
    private HashSet<String> visited;

    //              the map required for each state to know how much cost it has been paid until now. (aka. g(N))
    private HashMap<String ,Integer> giveStateGetG;

    // -----------------------
    @Override
    public String makeMove(String[][] grid) {

        if(stackOfMoves!=null){
            return stackOfMoves.pop();
        }
        else{
            return processAtOnce(grid);
        }
    }
    // -----------------------

    // ----------------------------------------------------------------------------------------------------------------------------
    private String processAtOnce(String [][] grid){
        dataStructures = getDataStructure();

        //          this map saves the path.
        giveChildGetParent = new HashMap<>();

        //          which move was it from.
        giveParentGetMove = new HashMap<>();

        //          saves the ones already have been visited. in order to avoid repetition.
        visited = new HashSet<>();

        //              the map required for each state to know how much cost it has been paid until now. (aka. g(N))
        giveStateGetG = new HashMap<>();

        //          copies the grid and then uses it.
        Board myVirtualBoard = new Board(3, grid);
        //          current state.
        String[][] currentState = Board.getGrid(myVirtualBoard);

        //          if the grid is already finished no need to do anything.
        if (!myVirtualBoard.isFinished()) {

            //       the expanding node should always get a visited tag.
            visited.add(myVirtualBoard.toString());

            /*       I need to save the move to know if
             *       it turns out good I use it once the
             *       graph is traversed. and returning by
             *       keeping the parents in the hashMap
             *       variable named giveChildGetParent.
             */

            //       --------------------------------------------------------------- Section 1 in the function.
            try {
                //  -------------------------------------------------------- DOWN:
                initialProgress(myVirtualBoard, Directions.DOWN);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                //  -------------------------------------------------------- UP:
                initialProgress(myVirtualBoard, Directions.UP);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                //  -------------------------------------------------------- LEFT:
                initialProgress(myVirtualBoard, Directions.LEFT);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                //  -------------------------------------------------------- RIGHT:
                initialProgress(myVirtualBoard, Directions.RIGHT);

                //  -------------------------------------------------------- Change state:
                currentState = Board.getGrid(dataStructures.removeAccoringly().toString());
                myVirtualBoard = new Board(3, currentState);
            }
            catch(ProduceTestingPassedException e){
                return giveParentGetMove.get(myVirtualBoard.toString());
            }
        }

        //       --------------------------------------------------------------- Section 2 in the function.

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

                inProgress(currentState, myVirtualBoard, Directions.LEFT);
                //  change back to the parent.
                myVirtualBoard = new Board(3, currentState);

                inProgress(currentState, myVirtualBoard, Directions.RIGHT);

                // for debug:
                if(dataStructures.size()<2){
                    System.out.println("hello");
                }

                //  change the state.
                currentState = Board.getGrid(dataStructures.removeAccoringly().toString());
                myVirtualBoard = new Board(3, currentState);

            }
        }

        //       --------------------------------------------------------------- Section 3 in the function.

                    // found state and preparing the move for the output.
        catch (ProduceTestingPassedException e) {

            //      if this is false it means, it never went through the while loop.
            //      **or it has been one of the first children which the beginning state made.**
            if (giveChildGetParent.containsKey(myVirtualBoard.toString())) {

                String answer = fillStackOfMoves(myVirtualBoard);

                //  debug log:
                System.out.println("a.i. made the move : " + answer);
                return answer;
            }

            //      this is true if it has been finished already in the first Section.
            else if (!giveParentGetMove.isEmpty()) {

                String answer = giveParentGetMove.get(myVirtualBoard.toString());

                //  debug log:
                System.out.println("a.i. made the move : " + answer);
                return answer;
            }
        }

        //          this eventually shouldn't happen.
        return null;
    }
    // ----------------------------------------------------------------------------------------------------------------------------

    // -----------------------
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
            //      add to the data structure wether can be a queue or a stack or even a priority queue.
            dataStructures.add(myVirtualBoard.toString());
        }
    }

    // -----------------------
    private void initialProgress(Board board, Directions dir){

        try {

            tryMove(dir, board);
            //      add the move to the corresponding board (itself not the parent which is the start state) map.
            giveParentGetMove.put(board.toString(), dir.toString());
            giveStateGetG.put(board.toString(), 1);
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

    // -----------------------
    /*
     *                  handles :
     *                              - not expanding the produced max depth.
     *                              - not saving the states that are already
     *                                visited and not adding them to the data structure.
     *                              - throws the ProduceTestingPassedException.
     *
     */
    private void inProgress(String [][] currentState, Board board, Directions dir){

        Board myCurrentStateBoard = new Board(3, currentState);
        //              variable added for debugging.
        //int depth = -1;
        try {

            //          for debugging.

            /*
                if(giveStateGetG.get(board.toString()) <= 298)
                    System.out.println(giveStateGetG.get(board.toString()));
            */

            //          doesn't allow the board to go deeper and looks for another child.
            if(getLimit(giveStateGetG.get(board.toString()))) {

                /*
                        doesn't add a visited state to the dataStructure.
                        handles adding the state to the dataStructure (not if i tis finished).
                        changes the board using the corresponding direction.
                */
                tryMove(dir, board);
                //      added to the path. (used the Board toString() because i wanted it to be in the standard format).

                if (!visited.contains(board.toString())) {

                    //  saving the parent and the depth cost.
                    giveChildGetParent.put(board.toString(),
                            myCurrentStateBoard.toString());
                    giveStateGetG.put(board.toString(), giveStateGetG.get(myCurrentStateBoard.toString()) + 1);
                }
            }

        }
        catch (WrongMoveException e){

            //System.out.println(e);
        }
        catch (ProduceTestingPassedException e){

            //System.out.println(e);

            giveChildGetParent.put(board.toString(),
                    new Board(3, currentState).toString());

            //      for debugging.
            /*
                if(giveStateGetG.containsKey(myCurrentStateBoard.toString()))
                   depth = giveStateGetG.get(myCurrentStateBoard.toString()) + 1;
            */
            throw new ProduceTestingPassedException();

        }
    }

    // -----------------------
    /*
                    iterates all the moves back and also
                    sends them to the stackOfMoves.

                    the arg:
                        board is the final state or the goal.
     */
    private String fillStackOfMoves(Board board){
        stackOfMoves = new Stack<>();

        while (giveChildGetParent.get(board.toString()) != null) {

            String parent = giveChildGetParent.get(board.toString());
            //      saving the move to the stack.
            stackOfMoves.push(getParentMove(parent, board.toString()));

            //      iterating upwards to the initial state.
            board = new Board(3,
                    Board.getGrid(parent));
        }

        //          this is the initial state that we began with.
        return giveParentGetMove.get(board.toString());
    }

    // -----------------------
    /*
                    detects the move made from the parent to the child.
                    the detection is made after the search is complete in order to
                    avoid saving extra and useless information.
     */
    private String getParentMove(String parent, String child){

        //          check for every direction in the Directions enum that if there is a move leading to the child in
        //          in order to add it to the stack.
        for (Directions dir: Directions.values()) {
            try {
                Board tempMadeBoard = new Board(3, Board.getGrid(parent));
                tempMadeBoard.makeMove(dir);

                if (tempMadeBoard.toString().equals(child)) {

                    return dir.toString();
                }
            }catch (WrongMoveException e){}
        }

        //          eventually shouldn't happen.
        return null;

    }

    // -----------------------
    //              getting the data structure needed for the algorithm.
    //              (which follows the strategy design pattern in order to avoid duplication).
    DataStructures<String> getDataStructure(){
        return new Queue<>();
    }

    // -----------------------
    //              checks if the limit is exceeded.
    boolean getLimit(int depth){
        return true;
        // the limit for the DFS's depth.
    }
}
