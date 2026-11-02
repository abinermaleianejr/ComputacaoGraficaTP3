package main.cubo;

import com.jogamp.opengl.GL2;

public class Face {
    private final String nome;
    private final float[] normal;
    private final float[][] vertices;
    private final float[] cor;
    private final boolean texturizada;
    private volatile boolean visivel = true;

    public Face(String nome, float[] normal, float[][] vertices, float[] cor, boolean texturizada) {
        this.nome = nome;
        this.normal = normal.clone();
        this.vertices = copiarVertices(vertices);
        this.cor = cor.clone();
        this.texturizada = texturizada;
    }

    public void desenhar(GL2 gl, TexturaRede textura) {
        boolean aplicarTextura = texturizada && textura != null && textura.isDisponivel();
        if (aplicarTextura) {
            textura.ativar(gl);
        }
        gl.glColor3f(cor[0], cor[1], cor[2]);
        gl.glBegin(GL2.GL_QUADS);
        gl.glNormal3f(normal[0], normal[1], normal[2]);
        for (int i = 0; i < vertices.length; i++) {
            if (aplicarTextura) {
                gl.glTexCoord2f(i == 1 || i == 2 ? 1.0f : 0.0f, i >= 2 ? 1.0f : 0.0f);
            }
            gl.glVertex3f(vertices[i][0], vertices[i][1], vertices[i][2]);
        }
        gl.glEnd();
        if (aplicarTextura) {
            textura.desativar(gl);
        }
    }

    private float[][] copiarVertices(float[][] source) {
        float[][] copy = new float[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i].clone();
        }
        return copy;
    }

    public String getNome() {
        return nome;
    }

    public float[] getCor() {
        return cor.clone();
    }

    public boolean isVisivel() {
        return visivel;
    }

    public void setVisivel(boolean visivel) {
        this.visivel = visivel;
    }
}
