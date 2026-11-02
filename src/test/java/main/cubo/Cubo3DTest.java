package main.cubo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Cubo3DTest {
    @Test
    void alternaFaceSemAlterarOutrasFaces() {
        Cubo3D cubo = new Cubo3D();

        assertEquals(6, cubo.getFaces().length);
        cubo.alternarFace(0);

        assertFalse(cubo.getFaces()[0].isVisivel());
        assertTrue(cubo.getFaces()[1].isVisivel());

        cubo.alternarFace(-1);
        cubo.alternarFace(6);

        assertFalse(cubo.getFaces()[0].isVisivel());
        assertTrue(cubo.getFaces()[1].isVisivel());
    }
}
