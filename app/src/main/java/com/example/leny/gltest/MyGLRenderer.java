package com.example.leny.gltest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Leny on 03.03.2016.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Square mSquare;
    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        GLES20.glClearColor(0.162f,0.157f,0.157f,1.0f);
        mTriangle=new Triangle();
        mSquare=new Square();
    }


    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix=new float[16];
    private final float[] mViewMatrix=new float[16];


    public void onSurfaceChanged(GL10 unused,int width,int height){
        GLES20.glViewport(0, 0, width, height);
        float ratio=(float)width/height;
        Matrix.frustumM(mProjectionMatrix,0,-ratio,ratio,-1,1,3,7);
    }
    public void onDrawFrame(GL10 unused){
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //mTriangle.draw();
        Matrix.setLookAtM(mViewMatrix,0,0,0,-3,0f,0f,0f,0f,1.0f,0.0f);
        Matrix.multiplyMM(mMVPMatrix,0,mProjectionMatrix,0,mViewMatrix,0);
        mSquare.draw(mMVPMatrix);
    }
    public static int loadShader(int type,String shaderCode){
        int shader=GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
