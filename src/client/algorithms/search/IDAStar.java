package client.algorithms.search;

import client.algorithms.Algorithm;
import java.util.Stack;
import server.Game.Board;
import server.Game.Directions;
import server.Game.WrongMoveException;

import java.util.HashMap;

public class IDAStar implements Algorithm {

    private int threshold;
    private State finalState;
    private HashMap<State, State> giveChildGetParent;
    private Stack<String> stackOfMoves;
    private Stack<State> stackOfStates;

    public IDAStar(){
        stackOfStates = new Stack<>();
        giveChildGetParent = new HashMap<>();
    }

    @Override
    public String makeMove(String[][] grid) {
        if(stackOfMoves != null){
            return stackOfMoves.pop();
        }
        else
            return process(grid);
    }

    private String process(String [][] grid){
        State currentState = new State(new Board(3, grid).toString());
        threshold = currentState.getF();
        while (true){
            int returnValue = searchForFinalState(grid);
            if(returnValue == -1) { // means the goal has been found.
                break;
            }
            else if(returnValue == Integer.MAX_VALUE){
                return null;
            }
            threshold = returnValue;
        }

        return iterateBack();

    }

    //the return value is -1 which means the goal has been found or infinite which means the goal isn't accessible from the grid given from the problem.
    //or has a minimum f which is larger than the grid's f.
    private int searchForFinalState(String [][] grid){

        Board currentStateBoard = new Board(3, grid);
        State currentState = new State(currentStateBoard.toString());
        State temp = currentState;

        int min = Integer.MAX_VALUE;
        do{

            if(temp.getF() > threshold) {
                if (temp.getF() < min)
                    min = temp.getF();


            }
            else {

                expand(temp);
            }

            if (AStar.giveStateGetBoard(temp).isFinished()) {

                finalState = temp;
                return -1;
            }
            temp = stackOfStates.pop();

            if(temp.equals("1\t7\t2\t\n \t5\t3\t\n4\t8\t6\t\n"))
                System.out.println("bug found");
            if(temp.equals("1\t7\t2\t\n5\t \t3\t\n4\t8\t6\t\n"))
                System.out.println("bug found");
            //System.out.println("in while");
        }while(!stackOfStates.isEmpty());
        return min;
    }

    private void expand(State state){

        for (Directions dir: Directions.values()) {
            if(!dir.equals(state.bannedMove)) {
                Board board = AStar.giveStateGetBoard(state);
                try {
                    board.makeMove(dir);
                    State child = AStar.giveBoardGetState(board);
                    child.setParent(state);
                    stackOfStates.push(child);
                    giveChildGetParent.put(child, state);
                    AStar.banMove(dir, child);
                } catch (WrongMoveException e) {
                }
            }
        }
    }

    String iterateBack(){
        stackOfMoves = new Stack<>();
        Board myFinal = AStar.giveStateGetBoard(finalState);
        if(!myFinal.isFinished()){

            System.err.println("something is wrong and the the state is not the goal we wanted.");
            throw new RuntimeException();
        }
        else{

            State itr = AStar.giveBoardGetState(myFinal);

            while (giveChildGetParent.get(itr) != null){

                //System.out.println(itr);

                //          gets the parent and looks for which move it can go to the child.
                State parent = giveChildGetParent.get(itr);
                stackOfMoves.push
                        (BFS.getParentMove(parent.toString(), itr.toString()));
                itr = parent;
            }

            return stackOfMoves.pop();
        }
    }

}
