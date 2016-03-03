package com.example.leny.gltest;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Leny on 03.03.2016.
 */
public class Triangle {
    private FloatBuffer vertexBuffer;

    private final String vertexShaderCode="attribute vec4 vPosition;" + "void main() {" + "  gl_Position = vPosition;" + "}";
    private final String fragmentShaderCode = "precision mediump float;" + "uniform vec4 vColor;" + "void main() {" + "  gl_FragColor = vColor;" + "}";

    static final int COORDS_PER_VERTEX =3;
    static float triangleCoords[] = {   // in counterclockwise order:
            // Triangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,



    };
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private final int mProgram;
    public Triangle(){
        ByteBuffer bb=ByteBuffer.allocateDirect(triangleCoords.length*4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer=bb.asFloatBuffer();
        vertexBuffer.put(triangleCoords);
        vertexBuffer.position(0);
        int vertexShader=MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader=MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShaderCode);
        mProgram=GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram,fragmentShader);
        GLES20.glLinkProgram(mProgram);

    }
    private int mPositionHandle;
    private int mColorHandle;
    private final int vertexCount=triangleCoords.length/COORDS_PER_VERTEX;
    private final  int vertexStride=COORDS_PER_VERTEX*4;
    public void draw(){
        GLES20.glUseProgram(mProgram);
        mPositionHandle=GLES20.glGetAttribLocation(mProgram,"vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle=GLES20.glGetUniformLocation(mProgram,"vColor");
        GLES20.glUniform4fv(mColorHandle,1,color,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

}
