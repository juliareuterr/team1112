package com.peter.wagstaff.jddemo.backend;

//Abstract class used to set a listener's action
public abstract class ListenerAction<InputObject> {

    /**
     * Undefined action to be performed using some input
     * @param input Object of this ListenerAction's type
     */
    public abstract void doAction(InputObject input);
}

