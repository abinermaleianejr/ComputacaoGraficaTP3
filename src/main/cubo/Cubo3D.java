package main.cubo;

import com.jogamp.opengl.GL2;

public class Cubo3D {
    private final Face[] faces = {
            new Face("Frente", new float[]{0, 0, 1},
                    new float[][]{{-1, -1, 1}, {1, -1, 1}, {1, 1, 1}, {-1, 1, 1}},
                    new float[]{0.85f, 0.3f, 0.25f}, true),
            new Face("Tras", new float[]{0, 0, -1},
                    new float[][]{{1, -1, -1}, {-1, -1, -1}, {-1, 1, -1}, {1, 1, -1}},
                    new float[]{0.3f, 0.65f, 0.9f}, false),
            new Face("Esquerda", new float[]{-1, 0, 0},
                    new float[][]{{-1, -1, -1}, {-1, -1, 1}, {-1, 1, 1}, {-1, 1, -1}},
                    new float[]{0.9f, 0.7f, 0.25f}, false),
            new Face("Direita", new float[]{1, 0, 0},
                    new float[][]{{1, -1, 1}, {1, -1, -1}, {1, 1, -1}, {1, 1, 1}},
                    new float[]{0.4f, 0.8f, 0.45f}, true),
            new Face("Topo", new float[]{0, 1, 0},
                    new float[][]{{-1, 1, 1}, {1, 1, 1}, {1, 1, -1}, {-1, 1, -1}},
                    new float[]{0.65f, 0.4f, 0.85f}, true),
            new Face("Base", new float[]{0, -1, 0},
                    new float[][]{{-1, -1, -1}, {1, -1, -1}, {1, -1, 1}, {-1, -1, 1}},
                    new float[]{0.35f, 0.8f, 0.8f}, false)
    };
    private final TexturaRede textura = new TexturaRede();

    public void inicializar(GL2 gl) {
        textura.inicializar(gl);
    }

    public void liberar(GL2 gl) {
        textura.liberar(gl);
    }

    public void desenhar(GL2 gl) {
        for (Face face : faces) {
            if (face.isVisivel()) {
                face.desenhar(gl, textura);
            }
        }
    }

    public void desenharIndicador(GL2 gl) {
        for (Face face : faces) {
            face.desenhar(gl, null);
        }
    }

    public void alternarFace(int indice) {
        if (indice >= 0 && indice < faces.length) {
            faces[indice].setVisivel(!faces[indice].isVisivel());
        }
    }

    public Face[] getFaces() {
        return faces.clone();
    }
}
