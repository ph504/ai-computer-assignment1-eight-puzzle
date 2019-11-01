package client.algorithms.search;

import client.algorithms.DataStructures;
import client.algorithms.Queue;

import server.Game.Board;
import server.Game.Directions;

import client.algorithms.Algorithm;
import server.Game.WrongMoveException;

import java.util.HashMap;
import java.util.HashSet;

public class BFS implements Algorithm {

    private DataStructures dataStructures;

    //              this map saves the path.
    private HashMap<String, String> giveChildGetParent;

    //              which move was it from.
    private HashMap<String, String> giveParentGetMove;

    //              saves the ones already have been visited. in order to avoid repetition.
    private HashSet<String> visited;

    //              the map required for each state to know how much cost it has been paid until now. (aka. g(N))
    private HashMap<String ,Integer> giveStateGetG;

    @Override
    public String makeMove(String[][] grid) {

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

                //  change the state.
                currentState = Board.getGrid(dataStructures.removeAccoringly().toString());
                myVirtualBoard = new Board(3, currentState);

            }
        }
        catch (ProduceTestingPassedException e) {

            //      if this is false it means, it never went through the while loop.
            //      **or it has been one of the first children which the beginning state made.**
            if (giveChildGetParent.containsKey(myVirtualBoard.toString())) {

                while (giveChildGetParent.get(myVirtualBoard.toString()) != null) {

                    currentState = Board.getGrid(giveChildGetParent.get(myVirtualBoard.toString()));
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

        //          this eventually shouldn't happen.
        return null;
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
            //      add to the data structure wether can be a queue or a stack or even a priority queue.
            dataStructures.add(myVirtualBoard.toString());
        }
    }

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

            System.out.println(e);
            giveParentGetMove.put(board.toString(), dir.toString());
            throw new ProduceTestingPassedException();
        }
    }

    private void inProgress(String [][] currentState, Board board, Directions dir){

        try {
            // doesn't allow the board to go deeper and looks for another child.
            /*if(giveStateGetG.get(board.toString()) <= 298)
                System.out.println(giveStateGetG.get(board.toString()));*/
            if(getLimit(giveStateGetG.get(board.toString()))) {

                tryMove(dir, board);
                //      added to the path. (used the Board toString() because i wanted it to be in the standard format).

                if (!visited.contains(board.toString())) {

                    Board myCurrentStateBoard = new Board(3, currentState);
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
            throw new ProduceTestingPassedException();
        }
    }

    //              getting the data structure needed for the algorithm.
    //              (which follows the strategy design pattern in order to avoid duplication).
    DataStructures<String> getDataStructure(){
        return new Queue<String>();
    }

    //              checks if the limit is exceeded.
    boolean getLimit(int depth){
        return true;
        // the limit for the DFS's depth.
    }
}
