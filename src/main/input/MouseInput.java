package main.input;

import main.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {
    private final Main application;
    private int previousX;
    private int previousY;

    public MouseInput(Main application) {
        this.application = application;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        previousX = event.getX();
        previousY = event.getY();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        application.rodarCamera(event.getX() - previousX, event.getY() - previousY);
        previousX = event.getX();
        previousY = event.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        application.zoomCamera(event.getWheelRotation() * 0.1f);
    }
}
