package main.cameras;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CameraPrimeiraPessoaTest {
    @Test
    void apontaDoPrimeiroCuboParaOSegundo() {
        ExposedCamera camera = new ExposedCamera();
        float[] eye = camera.eye(-2.0f);
        float[] target = camera.target(-2.0f);

        assertEquals(-2.0f, eye[0], 0.0001f);
        assertEquals(2.0f, target[0], 0.0001f);
        assertTrue(target[0] > eye[0]);
    }

    private static class ExposedCamera extends CameraPrimeiraPessoa {
        float[] eye(float movingCubeX) {
            return getEye(movingCubeX);
        }

        float[] target(float movingCubeX) {
            return getTarget(movingCubeX);
        }
    }
}
