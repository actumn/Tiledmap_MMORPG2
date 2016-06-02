package com.mygdx.game.object.script;

/**
 * Created by Lee on 2016-06-02.
 */
public interface IScript {
    /*
        Can execute

        @return true, if successful
     */
    boolean canExecute();

    /*
        Execute init. This method is called only per each script.
     */
    void executeInit();

    /*
        Execute AI update.
     */
    void executeAIUpdate();
}