package com.example.leny.gltest;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Leny on 03.03.2016.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    private final MyGLRenderer myGLRenderer;

    public MyGLSurfaceView(Context context)
    {
        super(context);
        setEGLContextClientVersion(2);
        myGLRenderer=new MyGLRenderer();
        setRenderer(myGLRenderer);
        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
