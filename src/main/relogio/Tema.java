package main.relogio;

public enum Tema {
    CLARO(
            new float[]{1.0f, 1.0f, 1.0f},
            new float[]{0.9f, 0.9f, 0.9f},
            new float[]{0.0f, 0.0f, 0.0f},
            new float[]{0.0f, 0.0f, 0.5f},
            new float[]{1.0f, 0.0f, 0.0f}),
    ESCURO(
            new float[]{0.0f, 0.0f, 0.0f},
            new float[]{0.2f, 0.2f, 0.2f},
            new float[]{1.0f, 1.0f, 1.0f},
            new float[]{0.5f, 0.5f, 1.0f},
            new float[]{1.0f, 0.5f, 0.5f});

    private final float[] fundo;
    private final float[] mostrador;
    private final float[] horas;
    private final float[] minutos;
    private final float[] segundos;

    Tema(float[] fundo, float[] mostrador, float[] horas, float[] minutos, float[] segundos) {
        this.fundo = fundo;
        this.mostrador = mostrador;
        this.horas = horas;
        this.minutos = minutos;
        this.segundos = segundos;
    }

    public float getFundo(int componente) {
        return fundo[componente];
    }

    public float[] getMostrador() {
        return mostrador.clone();
    }

    public float[] getCorHoras() {
        return horas.clone();
    }

    public float[] getCorMinutos() {
        return minutos.clone();
    }

    public float[] getCorSegundos() {
        return segundos.clone();
    }
}
