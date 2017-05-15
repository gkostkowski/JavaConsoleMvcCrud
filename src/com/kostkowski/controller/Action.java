package com.kostkowski.controller;

/**
 * Created by Grzegorz Kostkowski on 2017-03-05.
 */
public enum Action {
    CREATE_SHELF("cs"), RETRIEVE_SHELF("rs"), UPDATE_SHELF("us"), DELETE_SHELF("ds"),
    CREATE_PROD("cp"), RETRIEVE_PROD("rp"), UPDATE_PROD("up"), DELETE_PROD("dp"),
    RETRIEVE_ALL("a"), EXIT("exit"), HELP("?"), CREATE_WORKER("cw"), RETRIEVE_WORKER("rw"), DELETE_WORKER("dw"),
    ASSIGN_WORKER("asgn"), RETRACT_WORKER("rtrct"),
    RETRACT_WORKER_BY_SALESMAN_ID("wrtr"), RETRACT_WORKER_BY_STALL_ID("trtr");

    private String val;
    Action(String v){val = v;}

    public static Action getAction(String v) {
        for(Action a: Action.values())
            if (a.val.equals(v))
                return a;
        return null;
    }
}
