package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import main.cameras.Camera;
import main.cameras.CameraIsometrica;
import main.cameras.CameraOrtografica;
import main.cameras.CameraPrimeiraPessoa;
import main.cubo.Cubo3D;
import main.iluminacao.Luz;
import main.input.KeyboardInput;
import main.input.MouseInput;
import main.relogio.Relogio2D;
import main.relogio.Tema;
import main.util.TextureLoader;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Main implements GLEventListener {
    private static final float HALF_CUBE_SIZE = 1.0f;
    private static final float COLLISION_DISTANCE = HALF_CUBE_SIZE * 2.0f;
    private static final float MAX_DISTANCE = 3.0f;

    private final GLU glu = new GLU();
    private final Relogio2D relogio = new Relogio2D();
    private final Cubo3D cubo = new Cubo3D();
    private final Luz luz = new Luz();
    private final List<Camera> cameras = new ArrayList<>();
    private JFrame frame;
    private FPSAnimator animator;
    private Camera camera;
    private float distance = 2.0f;
    private float travelDirection = -1.0f;
    private float rotation;
    private long previousFrameNanos;
    private boolean running = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().abrirJanela());
    }

    private void abrirJanela() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        MouseInput mouseInput = new MouseInput(this);
        canvas.addGLEventListener(this);
        canvas.addKeyListener(new KeyboardInput(this));
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
        canvas.addMouseWheelListener(mouseInput);

        cameras.add(new CameraIsometrica());
        cameras.add(new CameraPrimeiraPessoa());
        cameras.add(new CameraOrtografica());
        camera = cameras.get(0);

        frame = new JFrame("Computacao Grafica TP3 - Relogio e Cubos 3D");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.add(canvas);
        frame.setSize(1100, 700);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent event) {
                pararAnimacao();
            }
        });
        animator = new FPSAnimator(canvas, 60, true);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.08f, 0.09f, 0.12f, 1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
        cubo.inicializar(gl);
        previousFrameNanos = System.nanoTime();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        cubo.liberar(drawable.getGL().getGL2());
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        long now = System.nanoTime();
        float deltaSeconds = Math.min((now - previousFrameNanos) / 1_000_000_000.0f, 0.05f);
        previousFrameNanos = now;
        atualizarCena(deltaSeconds);

        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();
        int clockWidth = Math.max(1, width * 2 / 5);
        desenharRelogio(gl, clockWidth, height);
        desenharCena(gl, clockWidth, width - clockWidth, height);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        previousFrameNanos = System.nanoTime();
    }

    private void atualizarCena(float deltaSeconds) {
        distance += travelDirection * 0.8f * deltaSeconds;
        if (distance <= COLLISION_DISTANCE / 2.0f) {
            distance = COLLISION_DISTANCE / 2.0f;
            travelDirection = 1.0f;
        } else if (distance >= MAX_DISTANCE) {
            distance = MAX_DISTANCE;
            travelDirection = -1.0f;
        }
        rotation = (rotation + 45.0f * deltaSeconds) % 360.0f;
    }

    private void desenharRelogio(GL2 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.15, 1.15, -1.15, 1.15);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();

        gl.glClearColor(relogio.getTema().getFundo(0), relogio.getTema().getFundo(1),
                relogio.getTema().getFundo(2), 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        relogio.desenhar(gl, Math.min(width, height));
        gl.glEnable(GL.GL_DEPTH_TEST);
    }

    private void desenharCena(GL2 gl, int x, int width, int height) {
        gl.glViewport(x, 0, Math.max(width, 1), height);
        gl.glClearColor(0.08f, 0.09f, 0.12f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        camera.configurarProjecao(gl, glu, Math.max(width, 1) / (float) Math.max(height, 1));
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        camera.aplicar(gl, glu, -distance);
        luz.aplicar(gl);

        desenharEixos(gl);
        gl.glPushMatrix();
        gl.glTranslatef(-distance, 0.0f, 0.0f);
        gl.glRotatef(rotation, 0.0f, 1.0f, 0.0f);
        cubo.desenhar(gl);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslatef(distance, 0.0f, 0.0f);
        gl.glRotatef(-rotation, 0.0f, 1.0f, 0.0f);
        cubo.desenhar(gl);
        gl.glPopMatrix();
    }

    private void desenharEixos(GL2 gl) {
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glLineWidth(2.0f);
        gl.glBegin(GL.GL_LINES);
        gl.glColor3f(1.0f, 0.15f, 0.15f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(2.0f, 0.0f, 0.0f);
        gl.glColor3f(0.15f, 1.0f, 0.15f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 2.0f, 0.0f);
        gl.glColor3f(0.2f, 0.45f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 2.0f);
        gl.glEnd();
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glEnable(GL2.GL_LIGHTING);
        desenharIndicador(gl, 2.0f, 0.0f, 0.0f, 1.0f, 0.15f, 0.15f);
        desenharIndicador(gl, 0.0f, 2.0f, 0.0f, 0.15f, 1.0f, 0.15f);
        desenharIndicador(gl, 0.0f, 0.0f, 2.0f, 0.2f, 0.45f, 1.0f);
    }

    private void desenharIndicador(GL2 gl, float x, float y, float z, float r, float g, float b) {
        gl.glPushMatrix();
        gl.glTranslatef(x, y, z);
        gl.glDisable(GL2.GL_LIGHTING);
        gl.glColor3f(r, g, b);
        gl.glScalef(0.12f, 0.12f, 0.12f);
        cubo.desenharIndicador(gl);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glPopMatrix();
    }

    public void tratarTecla(char tecla) {
        switch (tecla) {
            case 'h': relogio.ajustarPonteiroHoras(false); break;
            case 'H': relogio.ajustarPonteiroHoras(true); break;
            case 'm': relogio.ajustarPonteiroMinutos(false); break;
            case 'M': relogio.ajustarPonteiroMinutos(true); break;
            case 's': relogio.ajustarPonteiroSegundos(false); break;
            case 'S': relogio.ajustarPonteiroSegundos(true); break;
            case 'i':
            case 'I': relogio.alternarSentido(); break;
            case 't':
            case 'T': relogio.alternarTema(); break;
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6': cubo.alternarFace(tecla - '1'); break;
            default: break;
        }
    }

    public void selecionarCamera(int indice) {
        if (indice >= 0 && indice < cameras.size()) {
            camera = cameras.get(indice);
        }
    }

    public void moverLuz(float x, float y, float z) {
        luz.mover(x, y, z);
    }

    public void rodarCamera(float deltaX, float deltaY) {
        camera.rodar(deltaX, deltaY);
    }

    public void zoomCamera(float quantidade) {
        camera.zoom(quantidade);
    }

    public void fechar() {
        if (!running) {
            return;
        }
        running = false;
        pararAnimacao();
        if (frame != null) {
            frame.dispose();
        }
    }

    private void pararAnimacao() {
        if (animator != null && animator.isStarted()) {
            animator.stop();
        }
    }
}
