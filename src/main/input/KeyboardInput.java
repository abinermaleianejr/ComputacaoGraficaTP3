package main.input;

import main.Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private final Main application;

    public KeyboardInput(Main application) {
        this.application = application;
    }

    @Override
    public void keyTyped(KeyEvent event) {
        if (!event.isControlDown()) {
            application.tratarTecla(event.getKeyChar());
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            application.fechar();
            return;
        }
        if (event.isControlDown()) {
            int cameraIndex = event.getKeyCode() - KeyEvent.VK_1;
            if (cameraIndex >= 0 && cameraIndex <= 2) {
                application.selecionarCamera(cameraIndex);
            }
            return;
        }

        switch (event.getKeyCode()) {
            case KeyEvent.VK_LEFT: application.moverLuz(-0.2f, 0.0f, 0.0f); break;
            case KeyEvent.VK_RIGHT: application.moverLuz(0.2f, 0.0f, 0.0f); break;
            case KeyEvent.VK_UP: application.moverLuz(0.0f, 0.2f, 0.0f); break;
            case KeyEvent.VK_DOWN: application.moverLuz(0.0f, -0.2f, 0.0f); break;
            case KeyEvent.VK_PAGE_UP: application.moverLuz(0.0f, 0.0f, 0.2f); break;
            case KeyEvent.VK_PAGE_DOWN: application.moverLuz(0.0f, 0.0f, -0.2f); break;
            default: break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }
}
