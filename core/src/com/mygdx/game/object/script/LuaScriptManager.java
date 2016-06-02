package com.mygdx.game.object.script;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.object.NPC;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * Created by Lee on 2016-06-02.
 */
public class LuaScriptManager implements IScript {
    /* The globals. */
    private Globals globals = JsePlatform.standardGlobals();

    /* The chunk. */
    private LuaValue chunk;

    /* The script file exists. */
    private boolean scriptFileExists;

    /* NPC */
    private NPC npc;

    public LuaScriptManager(NPC npc, String scriptFileName) {
        this.npc = npc;

        if (!Gdx.files.internal(scriptFileName).exists()) {
            scriptFileExists = false;
            return;
        } else {
            scriptFileExists = true;
        }

        chunk = globals.loadfile(scriptFileName);

        // very important step.
        // subsequent calls to script method do not work
        // if the chunk is not called here
        chunk.call();
    }

    @Override
    public boolean canExecute() {
        return scriptFileExists;
    }

    @Override
    public void executeInit() {
        if (!canExecute()) {
            return;
        }

        // there must be a corresponding init method in the external lua script file
        globals.get("init").invoke(
                new LuaValue[] {
                        CoerceJavaToLua.coerce(npc)
                }
        );
    }

    @Override
    public void executeAIUpdate() {
        if (!canExecute()) {
            return;
        }

        // there must be a corresponding keyPressed method in the external lua file
        globals.get("ai_update").invoke(
                new LuaValue[] {
                        CoerceJavaToLua.coerce(npc)
                }
        );
    }
}
