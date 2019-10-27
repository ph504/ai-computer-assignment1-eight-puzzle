package client.algorithms.search;

public class ProduceTestingPassedException extends RuntimeException {
    ProduceTestingPassedException(){super("the state you were looking for has been found!");}
}
