package com.jhondoe.main.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.jhondoe.enums.GameState;

public class GameListener extends GameCore implements Runnable, KeyListener, MouseListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void keyPressed(KeyEvent e) {
        executeKey(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        executeKey(e, false);
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        executeMouseClick(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        executeMouseClick(e, false);
    }

    private void executeMouseClick(MouseEvent e, boolean pressed) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Game.player.setMouseShoot(pressed);
            Game.player.setMouseX((e.getX() / SCALE));
            Game.player.setMouseY((e.getY() / SCALE));
        }
    }

    private void executeKey(KeyEvent e, boolean pressed) {
        executeKey(e, pressed, false);
    }

    private void executeKey(KeyEvent e, boolean pressed, boolean ignore) {
        if (ignore) {
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            Game.player.setShift(pressed);
        }

        if (e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_J) {
            Game.player.setShoot(pressed);
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (getGameState().equals(GameState.MENU)) {
                Game.menu.setEnter(pressed);
            }
            if (getGameState().equals(GameState.PLAY)) {
                Game.player.setEnter(pressed);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setGameState(GameState.MENU);
            Game.menu.setPause(true);
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (getGameState().equals(GameState.PLAY)) {
                    Game.player.setLeft(pressed);
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (getGameState().equals(GameState.PLAY)) {
                    Game.player.setRight(pressed);
                }
                break;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (getGameState().equals(GameState.MENU)) {
                    Game.menu.setUp(pressed);
                }
                if (getGameState().equals(GameState.PLAY)) {
                    Game.player.setUp(pressed);
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (getGameState().equals(GameState.MENU)) {
                    Game.menu.setDown(pressed);
                }
                if (getGameState().equals(GameState.PLAY)) {
                    Game.player.setDown(pressed);
                }
                break;
        }
    }

    @Override
    public void run() {

    }
}
