package main.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class Camera {
    private volatile float yawOffset;
    private volatile float pitchOffset;
    private volatile float zoom = 1.0f;

    public void configurarProjecao(GL2 gl, GLU glu, float aspectRatio) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0, aspectRatio, 0.1, 100.0);
    }

    public void aplicar(GL2 gl, GLU glu, float movingCubeX) {
        float[] eye = getEye(movingCubeX);
        float[] target = getTarget(movingCubeX);
        float x = eye[0] - target[0];
        float y = eye[1] - target[1];
        float z = eye[2] - target[2];

        double yaw = Math.toRadians(yawOffset);
        float rotatedX = x * (float) Math.cos(yaw) + z * (float) Math.sin(yaw);
        float rotatedZ = -x * (float) Math.sin(yaw) + z * (float) Math.cos(yaw);
        double pitch = Math.toRadians(pitchOffset);
        float rotatedY = y * (float) Math.cos(pitch) - rotatedZ * (float) Math.sin(pitch);
        rotatedZ = y * (float) Math.sin(pitch) + rotatedZ * (float) Math.cos(pitch);

        glu.gluLookAt(target[0] + rotatedX, target[1] + rotatedY, target[2] + rotatedZ,
                target[0], target[1], target[2], 0.0, 1.0, 0.0);
    }

    protected float[] getEye(float movingCubeX) {
        return new float[]{5.0f, 5.0f, 5.0f * zoom};
    }

    protected float[] getTarget(float movingCubeX) {
        return new float[]{0.0f, 0.0f, 0.0f};
    }

    protected float getZoom() {
        return zoom;
    }

    public void rodar(float deltaX, float deltaY) {
        yawOffset += deltaX * 0.5f;
        pitchOffset = Math.max(-80.0f, Math.min(80.0f, pitchOffset + deltaY * 0.5f));
    }

    public void zoom(float quantidade) {
        zoom = Math.max(0.35f, Math.min(2.5f, zoom + quantidade));
    }
}
