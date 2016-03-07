package com.example.leny.gltest;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Leny on 03.03.2016.
 */
public class Square {

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;
    private final String vertexShaderCode="uniform mat4 uMVPMatrix;"+"attribute vec4 vPosition;" + "void main() {" + "  gl_Position = uMVPMatrix *vPosition;" + "}";
    private final String fragmentShaderCode = "precision mediump float;" + "uniform vec4 vColor;" + "void main() {" + "  gl_FragColor = vColor;" + "}";
    float _color[] = {1.0f, 1.0f, 1.0f, 0.0f };

    private int mMVPMatrixHandle;

    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = {
            -0.1f,  0.1f, 0.0f,   // top left
            -0.1f, -0.1f, 0.0f,   // bottom left
            0.1f, -0.1f, 0.0f,   // bottom right
            0.1f,  0.1f, 0.0f }; // top right

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    private final int mProgram;


    public Square() {
        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);
        int vertexShader=MyGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,vertexShaderCode);
        int fragmentShader=MyGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
        mProgram=GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram,vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);


        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }


    private int mPositionHandle;
    private int mColorHandle;
    private final int vertexCount=squareCoords.length/COORDS_PER_VERTEX;
    private final  int vertexStride=COORDS_PER_VERTEX*4;

    public void draw(float[]mvpMatrix){
        GLES20.glUseProgram(mProgram);
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(mColorHandle, 1, _color, 0);
        mMVPMatrixHandle=GLES20.glGetUniformLocation(mProgram,"uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle,1,false,mvpMatrix,0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
    private final float[] mRotationMatrix=new float[16];
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix=new float[16];
    private final float[] mViewMatrix=new float[16];
    private float xMove=0.0f, yMove=0.0f;

    public void setMovement(float x, float y){xMove=x;yMove=y;}
    public void setColor(float[] color){_color=color;}
    public float[] getmProjectionMatrix(){return mProjectionMatrix;}

    float _x=0.0f;
    float _y=0.0f;
    public void setPosition(float x, float y){_x=x;_y=y;}

    public float[] getmMVPMatrix()
    {
        float[] mModelMatrix=new float[16];
        float[] mTempMatrix;
        Matrix.setIdentityM(mModelMatrix, 0);


        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        long time = SystemClock.uptimeMillis() % 4000;
        float angle=0.090f*((int) time);
        float distanceX = xMove * ((int) time);
        float distanceY = yMove*((int) time);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);



        Matrix.translateM(mModelMatrix, 0, distanceX, distanceY, 0f);
        Matrix.translateM(mModelMatrix,0,_x,_y,0f);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        mTempMatrix=mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix,0, mTempMatrix,0,mRotationMatrix,0);

        mTempMatrix=mMVPMatrix.clone();
        Matrix.multiplyMM(mMVPMatrix, 0, mTempMatrix,0,mModelMatrix,0);
        return mMVPMatrix;
    }
}
