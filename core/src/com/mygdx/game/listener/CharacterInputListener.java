package com.mygdx.game.listener;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.object.Character;

/**
 * Created by Lee on 2016-05-21.
 */
public class CharacterInputListener extends InputListener {
    private Character character;
    public boolean myCharacterMoveState[] = new boolean[4];
    public boolean myCharacterSkillState = false;

    public CharacterInputListener (Character character) {
        this.character = character;
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
                character.skillAttack();
                break;
            case Input.Keys.CONTROL_LEFT:
                character.attack();
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
            character.move(character.x, character.y, character.x, character.y - character.speedY);

        }

        if(this.myCharacterMoveState[1]) {
            character.move(character.x, character.y, character.x - character.speedX, character.y);
        }

        if(this.myCharacterMoveState[2]) {
            character.move(character.x, character.y, character.x + character.speedX, character.y);
        }

        if(this.myCharacterMoveState[3]) {
            character.move(character.x, character.y, character.x, character.y + character.speedY);
        }
    }
}
