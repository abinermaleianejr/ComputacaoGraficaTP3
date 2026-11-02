package main.cameras;

public class CameraIsometrica extends Camera {
    @Override
    protected float[] getEye(float movingCubeX) {
        float zoom = getZoom();
        return new float[]{5.0f * zoom, 5.0f * zoom, 5.0f * zoom};
    }
}
