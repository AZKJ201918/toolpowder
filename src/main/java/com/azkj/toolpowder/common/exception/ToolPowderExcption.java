package com.azkj.toolpowder.common.exception;

public class ToolPowderExcption extends Exception {


    public ToolPowderExcption(String message) {
        super(message);
    }


    public int getStatusCode() {
        return 500;
    }
}
