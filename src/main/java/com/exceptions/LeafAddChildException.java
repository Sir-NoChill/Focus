package com.exceptions;

public class LeafAddChildException extends Exception {

    public LeafAddChildException() {
        super("Leaf elements cannot have children");
    }

}
