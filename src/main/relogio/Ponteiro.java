package main.relogio;

public class Ponteiro {
    private float comprimento;
    private float espessura;

    public Ponteiro(float comprimento, float espessura) {
        this.comprimento = Math.max(0.1f, Math.min(comprimento, 0.9f));
        this.espessura = Math.max(0.005f, Math.min(espessura, 0.1f));
    }

    public void desenhar(com.jogamp.opengl.GL2 gl, float angulo, float raioEmPixeis,
                         float[] cor) {
        double radians = Math.toRadians(angulo);
        gl.glColor3f(cor[0], cor[1], cor[2]);
        gl.glLineWidth(Math.max(1.0f, espessura * raioEmPixeis));
        gl.glBegin(com.jogamp.opengl.GL.GL_LINES);
        gl.glVertex2f(0.0f, 0.0f);
        gl.glVertex2f(comprimento * (float) Math.cos(radians),
                comprimento * (float) Math.sin(radians));
        gl.glEnd();
        gl.glLineWidth(1.0f);
    }

    public void aumentarDimensoes() {
        comprimento += 0.05f;
        espessura += 0.005f;
        limitar(1.0f);
    }

    public void diminuirDimensoes() {
        comprimento -= 0.05f;
        espessura -= 0.005f;
        limitar(1.0f);
    }

    public void limitar(float raioMaximo) {
        comprimento = Math.max(0.1f, Math.min(comprimento, raioMaximo * 0.9f));
        espessura = Math.max(0.005f, Math.min(espessura, raioMaximo * 0.1f));
    }

    public float getComprimento() {
        return comprimento;
    }

    public float getEspessura() {
        return espessura;
    }
}
