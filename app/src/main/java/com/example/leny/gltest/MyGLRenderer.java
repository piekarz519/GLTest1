package com.example.leny.gltest;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.Random;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Leny on 03.03.2016.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Square mSquare;
    private Square mSquare2;
    private Square mSquare3;
    private Square mSquare4;
    private Square mSquare5;


    public void onSurfaceCreated(GL10 unused, EGLConfig config){
        GLES20.glClearColor(0.162f,0.157f,0.157f,1.0f);

        mSquare=new Square();
        mSquare2=new Square();
        mSquare3=new Square();
        mSquare4=new Square();
        mSquare5=new Square();

    }


    public void onSurfaceChanged(GL10 unused,int width,int height){
        GLES20.glViewport(0, 0, width, height);
        float ratio=(float)width/height;
        Matrix.frustumM(mSquare.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
        Matrix.frustumM(mSquare2.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
        Matrix.frustumM(mSquare3.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
        Matrix.frustumM(mSquare4.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);
       Matrix.frustumM(mSquare5.getmProjectionMatrix(),0,-ratio,ratio,-1,1,3,7);

    }


    public void onDrawFrame(GL10 unused){

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        float R=0.0f,G=0.0f,B=0.0f;
        float color1[]= {0.73345f,0.4562f,0.6324f,1.0f};
        float color2[]= {0.145f,0.4562f,0.914f,1.0f};
        float color3[]= {1.0f,0.5762f,0.264f,1.0f};
        float color4[]= {0.67345f,1.0f,0.234f,1.0f};
        float color5[]= {0.412345f,0.11f,1.0f,1.0f};
        mSquare.setColor(color1);
        mSquare2.setColor(color2);
        mSquare3.setColor(color3);
        mSquare4.setColor(color4);
        mSquare5.setColor(color5);
        mSquare.setMovement(0.000042f, 0.000032f);
        mSquare2.setMovement(-0.00008f, 0.000052f);
        mSquare3.setMovement(-0.000012f, -0.000032f);
        mSquare4.setMovement(0.0000132f, -0.000096f);
        mSquare.setPosition(0.2f, -0.3f);
        mSquare2.setPosition(-0.4f, 0.3f);
        mSquare3.setPosition(0.3f, -0.1f);
        mSquare4.setPosition(-0.45f, 0.32f);
        mSquare3.draw(mSquare5.getmMVPMatrix());
        mSquare3.draw(mSquare4.getmMVPMatrix());
        mSquare3.draw(mSquare3.getmMVPMatrix());
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
