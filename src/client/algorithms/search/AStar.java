package client.algorithms.search;

import client.algorithms.Algorithm;
import server.Game.Board;
import server.Game.Directions;
import server.Game.WrongMoveException;

import java.util.*;

//------------------------------------------------------------------------------------- AStar class.
public class AStar implements Algorithm{

    private static Stack<String> stackOfMoves;

    private LinkedList<State> openList;
    private LinkedList<State> closedList;

    private HashMap<State, State> giveChildGetParent;

    // -------------------------------------
    @Override
    public String makeMove(String[][] grid) {

        if (stackOfMoves == null)
            return processOnce(grid);
        else
            return stackOfMoves.pop();
    }

    // -------------------------------------
    private String processOnce(String [][] grid){

        openList = new LinkedList<>();

        closedList = new LinkedList<>();

        giveChildGetParent = new HashMap<>();

        Comparator<State> comparator = new Comparator<State>() {
            @Override
            public int compare(State o1, State o2) {
                return o1.compareTo(o2);
            }
        };

        Board myVirtualBoard = new Board(3, grid);
        //                  the iterative board that changes and expands the children.
        Board currentStateBoard;
        //                  initialize the parent state.
        State currentState = new State(myVirtualBoard.toString());

        while (!myVirtualBoard.isFinished()){


            //              update the parent state.
            closedList.add(currentState);

            for (Directions dir: Directions.values()) {

                if(!dir.equals(currentState.bannedMove)) {
                    //          change back the board to the original parent.
                    //          or make a copy of the original expanding state.
                    currentStateBoard = new Board(3, Board.getGrid(myVirtualBoard.toString()));

                    expand(currentStateBoard, currentState, dir);
                }
            }

            //              sort the openList.
            //                  in order to take out the best state with the lowest amount of f.
            Collections.sort(openList, comparator);

            //              update current state.
            currentState = openList.remove();

            //              update the original expanding Board.
            myVirtualBoard = new Board(3, Board.getGrid(currentState.toString()));

        }


        //                  return and get to the original state and return the move.
        return iterateBack(myVirtualBoard);
    }

    // -------------------------------------
    private void expand(Board parentBoard, State parent, Directions dir){


        try {

            //              make move and change the parent board to its child.
            parentBoard.makeMove(dir);
            //              expanding the currentState.
            State child = new State(parentBoard.toString());
            //              add the child to the lists.
            handleList(child, parent);

            banMove(dir, child);
        }
        catch (WrongMoveException e){}
    }

    // -------------------------------------
    static void banMove(Directions dir, State child){

        switch (dir.toString().toUpperCase()){

            case "DOWN":
                child.bannedMove = Directions.UP;
                break;
            case "UP":
                child.bannedMove = Directions.DOWN;
                break;
            case "LEFT":
                child.bannedMove = Directions.RIGHT;
                break;
            case "RIGHT":
                child.bannedMove = Directions.LEFT;
                break;
        }
    }

    // -------------------------------------
    private String iterateBack(Board myFinal){
        stackOfMoves = new Stack<>();

        if(!myFinal.isFinished()){

            System.err.println("something is wrong and the the state is not the goal we wanted.");
            throw new RuntimeException();
        }
        else{

            State itr = giveBoardGetState(myFinal);

            while (giveChildGetParent.get(itr) != null){

                //          gets the parent and looks for which move it can go to the child.
                State parent = giveChildGetParent.get(itr);
                stackOfMoves.push
                        (findMove(giveStateGetBoard(parent), itr.toString()));
                itr = parent;

            }

            return stackOfMoves.pop();
        }

    }

    // -------------------------------------
    static Board giveStateGetBoard(State state){
        return new Board(3, Board.getGrid(state.toString()));
    }

    // -------------------------------------
    static State giveBoardGetState(Board board){
        return new State(board.toString());
    }

    // -------------------------------------
    static String findMove(Board parent, String child){

        return BFS.getParentMove(parent.toString(), child);
    }

    // -------------------------------------
    private void handleList(State child, State parent) {

        if (!closedList.contains(child)) {

            setParent(child, parent);
            if(child.compareTo(parent) > 0)
                System.out.println("heuristic is wrong");
            openList.add(child);
        }

        else if (giveChildGetParent.get(child) != null)
            //              do not update the cost or the parent if it's not better.
            if (giveChildGetParent.get(child).compareTo(parent) > 0) {

                setParent(child, parent);
                if(child.compareTo(parent) < 0)
                    System.out.println("heuristic is wrong");
                openList.add(child);
            }
    }

    // -------------------------------------
    private void setParent(State child, State parent){
        giveChildGetParent.put(child, parent);
        child.setParent(parent);
    }
}

//------------------------------------------------------------------------------------- State class.
class State implements Comparable<State>{

    private String boardString;
    private int heuristic;
    private int cost;

    Directions bannedMove;


    // -------------------------------------
    State(String boardString){

        this.boardString = boardString;

        cost = 0;
        heuristic = heuristicFunction(Board.getGrid(boardString));
    }

    // -------------------------------------
    State(String boardString, State parent){

        this(boardString);

        if(parent != null) {

            cost = parent.cost + 1;
        }
    }

    // -------------------------------------
    public int getF() {

        return cost + heuristic;
    }

    // -------------------------------------
    public void setParent(State parent) {

        cost = parent.cost + 1;
    }

    // -------------------------------------
    private int heuristicFunction(String [][] grid){

        int sum = 0;
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid.length; j++) {

                int k;
                if(grid[i][j].equals(" "))
                    k = 0;
                else
                    k = Integer.parseInt(grid[i][j]);
                int ik = (k-1) / 3;
                int jk = (k-1) % 3;
                sum += Math.abs(i - ik) + Math.abs(j - jk);
            }
        }

        return sum;
    }

    // -------------------------------------
    @Override
    public String toString() {

        return boardString;
    }

    // -------------------------------------
    @Override
    public int compareTo(State o) {

        return this.getF() - o.getF();
    }

    // -------------------------------------
    @Override
    public int hashCode() {
        return boardString.hashCode();
    }

    // -------------------------------------
    @Override
    public boolean equals(Object obj) {
        return boardString.equals(obj.toString());
    }

    // -------------------------------------
}
