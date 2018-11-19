package com.hacknife.iplayer;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;


public class MediaManager implements TextureView.SurfaceTextureListener {

    public static final String TAG = "MediaManager";
    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;

    public static PlayerTextureView textureView;
    public static SurfaceTexture savedSurfaceTexture;
    public static Surface surface;
    public static MediaManager sMediaManager;
    public int positionInList = -1;
    public PlayerEngine engine;
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public HandlerThread pMediaHandlerThread;
    public MediaHandler pMediaHandler;
    public Handler pMainThreadHandler;

    public MediaManager() {
        pMediaHandlerThread = new HandlerThread(TAG);
        pMediaHandlerThread.start();
        pMediaHandler = new MediaHandler(pMediaHandlerThread.getLooper());
        pMainThreadHandler = new Handler();
        if (engine == null)
            engine = new MediaEngine();
    }

    public static MediaManager instance() {
        if (sMediaManager == null) {
            synchronized (MediaManager.class) {
                if (sMediaManager == null) {
                    sMediaManager = new MediaManager();
                }
            }
        }
        return sMediaManager;
    }

    //这几个方法是不是多余了，为了不让其他地方动MediaInterface的方法
    public static void setDataSource(DataSource dataSource) {
        instance().engine.dataSource = dataSource;
    }

    public static DataSource getDataSource() {
        return instance().engine.dataSource;
    }


    //    //正在播放的url或者uri
    public static Object getCurrentUrl() {
        return instance().engine.dataSource == null ? null : instance().engine.dataSource.getCurrentUrl();
    }


    public static long getCurrentPosition() {
        return instance().engine.getCurrentPosition();
    }

    public static long getDuration() {
        return instance().engine.getDuration();
    }

    public static void seekTo(long time) {
        instance().engine.seekTo(time);
    }

    public static void pause() {
        instance().engine.pause();
    }

    public static void start() {
        instance().engine.start();
    }

    public static boolean isPlaying() {
        return instance().engine.isPlaying();
    }

    public void releaseMediaPlayer() {
        pMediaHandler.removeCallbacksAndMessages(null);
        Message msg = new Message();
        msg.what = HANDLER_RELEASE;
        pMediaHandler.sendMessage(msg);
    }

    public void prepare() {
        releaseMediaPlayer();
        Message msg = new Message();
        msg.what = HANDLER_PREPARE;
        pMediaHandler.sendMessage(msg);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if (PlayerManager.getCurrentVideo() == null) return;
        if (savedSurfaceTexture == null) {
            savedSurfaceTexture = surfaceTexture;
            prepare();
        } else {
            textureView.setSurfaceTexture(savedSurfaceTexture);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return savedSurfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }


    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    currentVideoWidth = 0;
                    currentVideoHeight = 0;
                    engine.prepare();
                    if (savedSurfaceTexture != null) {
                        if (surface != null) {
                            surface.release();
                        }
                        surface = new Surface(savedSurfaceTexture);
                        engine.setSurface(surface);
                    }
                    break;
                case HANDLER_RELEASE:
                    engine.release();
                    break;
            }
        }
    }
}