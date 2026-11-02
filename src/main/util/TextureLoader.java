package main.util;

import com.jogamp.opengl.GL2;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TextureLoader {
    private TextureLoader() {
    }

    public static int carregar(GL2 gl, String caminho) {
        String resource = caminho.startsWith("/") ? caminho.substring(1) : caminho;
        try (InputStream input = TextureLoader.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalArgumentException("Textura nao encontrada no classpath: " + caminho);
            }
            BufferedImage image = ImageIO.read(input);
            if (image == null) {
                throw new IllegalArgumentException("Formato de imagem nao suportado: " + caminho);
            }
            return criar(gl, image);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel carregar a textura: " + caminho, exception);
        }
    }

    public static int criar(GL2 gl, BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        ByteBuffer pixels = ByteBuffer.allocateDirect(width * height * 4);
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                pixels.put((byte) ((argb >> 16) & 0xFF));
                pixels.put((byte) ((argb >> 8) & 0xFF));
                pixels.put((byte) (argb & 0xFF));
                pixels.put((byte) ((argb >> 24) & 0xFF));
            }
        }
        pixels.flip();

        int[] textureId = new int[1];
        gl.glGenTextures(1, textureId, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId[0]);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_RGBA, width, height, 0,
                GL2.GL_RGBA, GL2.GL_UNSIGNED_BYTE, pixels);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
        return textureId[0];
    }
}
