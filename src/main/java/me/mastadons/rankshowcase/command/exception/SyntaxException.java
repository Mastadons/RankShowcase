package me.mastadons.rankshowcase.command.exception;

public class SyntaxException extends RuntimeException {

    public SyntaxException(String syntax) {
        super("Invalid syntax. Please use: " + syntax);
    }
}
