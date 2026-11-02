package main.iluminacao;

import com.jogamp.opengl.GL2;

public class Luz {
    private volatile float x = 2.0f;
    private volatile float y = 3.0f;
    private volatile float z = 4.0f;

    public void aplicar(GL2 gl) {
        float[] ambiente = {0.2f, 0.2f, 0.2f, 1.0f};
        float[] difusa = {0.8f, 0.8f, 0.8f, 1.0f};
        float[] especular = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] posicao = {x, y, z, 1.0f};
        float[] material = {0.5f, 0.5f, 0.5f, 1.0f};

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, difusa, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, especular, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicao, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, material, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 48.0f);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_NORMALIZE);
    }

    public void mover(float deltaX, float deltaY, float deltaZ) {
        x = limitar(x + deltaX, -8.0f, 8.0f);
        y = limitar(y + deltaY, -8.0f, 8.0f);
        z = limitar(z + deltaZ, -2.0f, 10.0f);
    }

    private float limitar(float valor, float minimo, float maximo) {
        return Math.max(minimo, Math.min(maximo, valor));
    }

    public float[] getPosicao() {
        return new float[]{x, y, z};
    }
}
