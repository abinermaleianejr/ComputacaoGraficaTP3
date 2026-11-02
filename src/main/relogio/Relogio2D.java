package main.relogio;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

import java.time.LocalTime;

public class Relogio2D {
    private final Ponteiro horas = new Ponteiro(0.50f, 0.045f);
    private final Ponteiro minutos = new Ponteiro(0.70f, 0.027f);
    private final Ponteiro segundos = new Ponteiro(0.82f, 0.012f);
    private Tema tema = Tema.CLARO;
    private boolean antiHorario;

    public void desenhar(GL2 gl, int tamanhoViewport) {
        float[] face = tema.getMostrador();
        gl.glColor3f(face[0], face[1], face[2]);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        gl.glVertex2f(0.0f, 0.0f);
        for (int i = 0; i <= 120; i++) {
            double angle = 2.0 * Math.PI * i / 120.0;
            gl.glVertex2f(0.92f * (float) Math.cos(angle), 0.92f * (float) Math.sin(angle));
        }
        gl.glEnd();

        float[] border = tema.getCorHoras();
        gl.glColor3f(border[0], border[1], border[2]);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINE_LOOP);
        for (int i = 0; i < 120; i++) {
            double angle = 2.0 * Math.PI * i / 120.0;
            gl.glVertex2f(0.92f * (float) Math.cos(angle), 0.92f * (float) Math.sin(angle));
        }
        gl.glEnd();

        gl.glLineWidth(1.0f);
        gl.glBegin(GL.GL_LINES);
        for (int tick = 0; tick < 60; tick++) {
            double angle = Math.toRadians(90.0 - tick * 6.0);
            float innerRadius = tick % 5 == 0 ? 0.80f : 0.86f;
            gl.glColor3f(border[0], border[1], border[2]);
            gl.glVertex2f(innerRadius * (float) Math.cos(angle),
                    innerRadius * (float) Math.sin(angle));
            gl.glVertex2f(0.89f * (float) Math.cos(angle), 0.89f * (float) Math.sin(angle));
        }
        gl.glEnd();

        LocalTime time = LocalTime.now();
        float direction = antiHorario ? 1.0f : -1.0f;
        float secondsValue = time.getSecond() + time.getNano() / 1_000_000_000.0f;
        float minutesValue = time.getMinute() + secondsValue / 60.0f;
        float hoursValue = time.getHour() % 12 + minutesValue / 60.0f;
        float secondsAngle = 90.0f + direction * secondsValue * 6.0f;
        float minutesAngle = 90.0f + direction * minutesValue * 6.0f;
        float hoursAngle = 90.0f + direction * hoursValue * 30.0f;

        float radiusPixels = Math.min(tamanhoViewport, Math.max(1, tamanhoViewport)) / 2.3f;
        horas.desenhar(gl, hoursAngle, radiusPixels, tema.getCorHoras());
        minutos.desenhar(gl, minutesAngle, radiusPixels, tema.getCorMinutos());
        segundos.desenhar(gl, secondsAngle, radiusPixels, tema.getCorSegundos());

        gl.glPointSize(7.0f);
        gl.glColor3f(border[0], border[1], border[2]);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2f(0.0f, 0.0f);
        gl.glEnd();
        gl.glPointSize(1.0f);
    }

    public void ajustarPonteiroHoras(boolean aumentar) {
        ajustar(horas, aumentar);
    }

    public void ajustarPonteiroMinutos(boolean aumentar) {
        ajustar(minutos, aumentar);
    }

    public void ajustarPonteiroSegundos(boolean aumentar) {
        ajustar(segundos, aumentar);
    }

    private void ajustar(Ponteiro ponteiro, boolean aumentar) {
        if (aumentar) {
            ponteiro.aumentarDimensoes();
        } else {
            ponteiro.diminuirDimensoes();
        }
    }

    public void alternarTema() {
        tema = tema == Tema.CLARO ? Tema.ESCURO : Tema.CLARO;
    }

    public void alternarSentido() {
        antiHorario = !antiHorario;
    }

    public Tema getTema() {
        return tema;
    }

    public Ponteiro getPonteiroHoras() {
        return horas;
    }

    public Ponteiro getPonteiroMinutos() {
        return minutos;
    }

    public Ponteiro getPonteiroSegundos() {
        return segundos;
    }

    public boolean isAntiHorario() {
        return antiHorario;
    }
}
