package main.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class CameraOrtografica extends Camera {
    @Override
    public void configurarProjecao(GL2 gl, GLU glu, float aspectRatio) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        double halfHeight = 5.0 * getZoom();
        double halfWidth = halfHeight * aspectRatio;
        gl.glOrtho(-halfWidth, halfWidth, -halfHeight, halfHeight, -30.0, 30.0);
    }

    @Override
    protected float[] getEye(float movingCubeX) {
        return new float[]{0.0f, 0.0f, 10.0f * getZoom()};
    }
}
