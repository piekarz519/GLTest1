package com.example.leny.gltest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Leny on 03.03.2016.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Square mSquare;
    private Square mSquare2;
    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        GLES20.glClearColor(0.162f,0.157f,0.157f,1.0f);

        mSquare=new Square();
        mSquare2=new Square();

    }


    public void onSurfaceChanged(GL10 unused,int width,int height){
        GLES20.glViewport(0, 0, width, height);
        float ratio=(float)width/height;
        Matrix.frustumM(mSquare.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
        Matrix.frustumM(mSquare2.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
    }



    public void onDrawFrame(GL10 unused){
       float color1[]={1.0f,0.157f,0.157f,1.0f};
        float color2[]={0.0f,0.324f,0.157f,1.0f};
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mSquare.setColor(color1);
        mSquare2.setColor(color2);
        mSquare.setMovement(0.0002f, -0.0002f);
        mSquare2.draw(mSquare2.getmMVPMatrix());
        mSquare.draw(mSquare.getmMVPMatrix());
    }
    public static int loadShader(int type,String shaderCode){
        int shader=GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader,shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }
}
