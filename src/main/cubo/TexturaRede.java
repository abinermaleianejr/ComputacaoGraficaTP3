package main.cubo;

import com.jogamp.opengl.GL2;
import main.util.TextureLoader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TexturaRede {
    private int id;

    public void inicializar(GL2 gl) {
        BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setColor(new Color(20, 35, 48, 65));
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        graphics.setColor(new Color(205, 225, 238, 230));
        graphics.setStroke(new java.awt.BasicStroke(3.0f));
        for (int position = 0; position <= 128; position += 16) {
            graphics.drawLine(position, 0, position, 128);
            graphics.drawLine(0, position, 128, position);
        }
        graphics.dispose();
        id = TextureLoader.criar(gl, image);
    }

    public void ativar(GL2 gl) {
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, id);
    }

    public void desativar(GL2 gl) {
        gl.glDisable(GL2.GL_TEXTURE_2D);
    }

    public boolean isDisponivel() {
        return id != 0;
    }

    public void liberar(GL2 gl) {
        if (id != 0) {
            gl.glDeleteTextures(1, new int[]{id}, 0);
            id = 0;
        }
    }
}
