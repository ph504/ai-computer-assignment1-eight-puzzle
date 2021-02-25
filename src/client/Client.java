package client;
import client.algorithms.*;
import client.algorithms.search.AStar;
import client.algorithms.search.BFS;
import client.algorithms.search.DFS;
import client.algorithms.search.IDAStar;
import client.game.Solver;
import client.networking.NetworkHandler;
import server.Game.Board;

import java.util.Scanner;


public class Client {

    private static final int MAP_SIZE = 3;

    public static void main(String[] args) {
        NetworkHandler networkHandler = new NetworkHandler();
        Solver solver = getSolver();
        String serverMessage = networkHandler.initializeGame(MAP_SIZE);
        startGame(networkHandler, serverMessage, solver);
    }

//  select your algorithm in this method (select one)
    private static Solver getSolver() {
        System.out.println("please enter a number between 1 to 5 for the following commands : " +
                "\n1. Play Yourself!" +
                "\n2. A.I. play with DFS algorithm!" +
                "\n3. A.I. play with BFS algorithm!" +
                "\n4. A.I. play with A* algorithm!" +
                "\n5. A.I. ply with IDA* algorithm!");
        Scanner input = new Scanner(System.in);
        boolean failure = true;
        while(failure) {
            try {
                failure = false;
                switch (input.nextLine()) {
                    case "1":
                        return new Solver(MAP_SIZE, new UserInputAlgorithm());
                    case "2":
                        return new Solver(MAP_SIZE, new DFS());
                    case "3":
                        return new Solver(MAP_SIZE, new BFS());
                    case "4":
                        return new Solver(MAP_SIZE, new AStar());
                    case "5":
                        return new Solver(MAP_SIZE, new IDAStar());
                    default:
                        throw new RuntimeException();
                }

            } catch (RuntimeException e) {
                failure = true;
                System.out.println("invalid input please try again : ");
            }
        }
        return new Solver(MAP_SIZE, new UserInputAlgorithm());
    }

    private static void startGame(NetworkHandler networkHandler, String serverMessage, Solver solver) {
        while (!serverMessage.equals(Board.FINISH_STATEMENT)) {
            System.out.print(serverMessage);
            serverMessage = networkHandler.tryGetMoveFromClient(serverMessage, solver);
        }
        System.out.println(serverMessage); // print finished!
        serverMessage = networkHandler.tryGetMoveFromClient(serverMessage, solver);
        System.out.println(serverMessage); // print the final situation of the board.


    }

}
