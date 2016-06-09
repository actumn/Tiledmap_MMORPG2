package com.mygdx.game.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.object.Player;

/**
 * Created by Lee on 2016-05-21.
 */
public class CharacterInputListener extends InputListener {
    private Player player;
    public boolean myCharacterMoveState[] = new boolean[4];
    public boolean myCharacterSkillState = false;

    public CharacterInputListener (Player player) {
        this.player = player;
    }
    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
                myCharacterMoveState[0] = true;
                break;
            case Input.Keys.LEFT:
                myCharacterMoveState[1] = true;
                break;
            case Input.Keys.RIGHT:
                myCharacterMoveState[2] = true;
                break;
            case Input.Keys.UP:
                myCharacterMoveState[3] = true;
                break;
            case Input.Keys.SPACE:
                player.skillAttack();
                break;
            case Input.Keys.CONTROL_LEFT:
                player.attack();
                break;
            default:
                break;
        }
        return super.keyDown(event, keycode);
    }
    @Override
    public boolean keyUp(InputEvent event, int keycode) {
        switch (keycode) {
            case Input.Keys.DOWN:
                myCharacterMoveState[0] = false;
                break;
            case Input.Keys.LEFT:
                myCharacterMoveState[1] = false;
                break;
            case Input.Keys.RIGHT:
                myCharacterMoveState[2] = false;
                break;
            case Input.Keys.UP:
                myCharacterMoveState[3] = false;
                break;
        }

        return super.keyUp(event, keycode);
    }

    public void update() {
        if(this.myCharacterMoveState[0]) {
            player.move(player.x, player.y, player.x, player.y - player.speedY);

        }

        if(this.myCharacterMoveState[1]) {
            player.move(player.x, player.y, player.x - player.speedX, player.y);
        }

        if(this.myCharacterMoveState[2]) {
            player.move(player.x, player.y, player.x + player.speedX, player.y);
        }

        if(this.myCharacterMoveState[3]) {
            player.move(player.x, player.y, player.x, player.y + player.speedY);
        }
    }
}
