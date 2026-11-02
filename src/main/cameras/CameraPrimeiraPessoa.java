package main.cameras;

public class CameraPrimeiraPessoa extends Camera {
    @Override
    protected float[] getEye(float movingCubeX) {
        return new float[]{movingCubeX - 2.0f * (getZoom() - 1.0f), 0.15f, 0.0f};
    }

    @Override
    protected float[] getTarget(float movingCubeX) {
        return new float[]{-movingCubeX, 0.0f, 0.0f};
    }
}
