package main.util;

import com.jogamp.opengl.GL2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ShaderLoader {
    private ShaderLoader() {
    }

    public static int carregarPrograma(GL2 gl, String caminhoVertex, String caminhoFragment) {
        int vertex = compilar(gl, GL2.GL_VERTEX_SHADER, caminhoVertex);
        int fragment;
        try {
            fragment = compilar(gl, GL2.GL_FRAGMENT_SHADER, caminhoFragment);
        } catch (RuntimeException exception) {
            gl.glDeleteShader(vertex);
            throw exception;
        }

        int program = gl.glCreateProgram();
        gl.glAttachShader(program, vertex);
        gl.glAttachShader(program, fragment);
        gl.glLinkProgram(program);
        int[] status = new int[1];
        gl.glGetProgramiv(program, GL2.GL_LINK_STATUS, status, 0);
        gl.glDeleteShader(vertex);
        gl.glDeleteShader(fragment);
        if (status[0] == GL2.GL_FALSE) {
            String log = obterLogPrograma(gl, program);
            gl.glDeleteProgram(program);
            throw new IllegalStateException("Falha ao ligar o programa GLSL: " + log);
        }
        return program;
    }

    private static int compilar(GL2 gl, int tipo, String caminho) {
        String source = lerRecurso(caminho);
        int shader = gl.glCreateShader(tipo);
        gl.glShaderSource(shader, 1, new String[]{source}, new int[]{source.length()}, 0);
        gl.glCompileShader(shader);
        int[] status = new int[1];
        gl.glGetShaderiv(shader, GL2.GL_COMPILE_STATUS, status, 0);
        if (status[0] == GL2.GL_FALSE) {
            String log = obterLogShader(gl, shader);
            gl.glDeleteShader(shader);
            throw new IllegalStateException("Falha ao compilar " + caminho + ": " + log);
        }
        return shader;
    }

    private static String lerRecurso(String caminho) {
        String resource = caminho.startsWith("/") ? caminho.substring(1) : caminho;
        try (InputStream input = ShaderLoader.class.getClassLoader().getResourceAsStream(resource)) {
            if (input == null) {
                throw new IllegalArgumentException("Shader nao encontrado no classpath: " + caminho);
            }
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new IllegalStateException("Nao foi possivel ler o shader: " + caminho, exception);
        }
    }

    private static String obterLogShader(GL2 gl, int shader) {
        int[] length = new int[1];
        gl.glGetShaderiv(shader, GL2.GL_INFO_LOG_LENGTH, length, 0);
        byte[] log = new byte[Math.max(length[0], 1)];
        gl.glGetShaderInfoLog(shader, log.length, length, 0, log, 0);
        return new String(log, 0, Math.max(0, length[0]), StandardCharsets.UTF_8);
    }

    private static String obterLogPrograma(GL2 gl, int program) {
        int[] length = new int[1];
        gl.glGetProgramiv(program, GL2.GL_INFO_LOG_LENGTH, length, 0);
        byte[] log = new byte[Math.max(length[0], 1)];
        gl.glGetProgramInfoLog(program, log.length, length, 0, log, 0);
        return new String(log, 0, Math.max(0, length[0]), StandardCharsets.UTF_8);
    }
}
