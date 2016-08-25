package com.raymondqk.raysqlitepractice.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.raymondqk.raysqlitepractice.R;
import com.raymondqk.raysqlitepractice.utils.VerifyPermissionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by 陈其康 raymondchan on 2016/8/25 0025.
 */
public class AdvApkDownloadActivity extends AppCompatActivity {

    public static final int FLAG_DIALOG_DELETE = 1;
    public static final int FLAG_DIALOG_DOWNLOAD = 2;
    public static final String DOWNLOAD_TEST = "download_test";
    private TextView mTv_progress;
    private ProgressBar mPb_download;
    private ImageButton mIb_bili;
    private int mProgress;
    private DownloadHandler mHandler;
    private URLConnection mConnection;
    private int mContentLength;
    private String mDownloadFilePath;
    private File mDownloadFile;

    public TextView getTv_progress() {
        return mTv_progress;
    }

    public ProgressBar getPb_download() {
        return mPb_download;
    }


    public int getProgress() {
        return mProgress;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_download_apk);
        getSupportActionBar().setTitle("下载Demo");
        VerifyPermissionUtils.verifyStoragePermission(this);

        mHandler = new DownloadHandler(AdvApkDownloadActivity.this);

        mTv_progress = (TextView) findViewById(R.id.tv_adv_download_progress);
        mTv_progress.setVisibility(View.GONE);
        mPb_download = (ProgressBar) findViewById(R.id.pb_adv_progress);
        mPb_download.setVisibility(View.GONE);

        mIb_bili = (ImageButton) findViewById(R.id.ib_adv_down_bili);
        mIb_bili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String httpUrl = "http://dl.hdslb.com/mobile/latest/iBiliPlayer-bili.apk";
                /*先用Thread来执行下载操作*/
//                downloadWithThread(httpUrl);

                /**
                 * 通过AsyncTask执行下载操作
                 */
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final File file = createDownLoadFile(httpUrl);
                            if (file != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(AdvApkDownloadActivity.this);
                                        dialog.setTitle("提示AsyncTask");
                                        dialog.setIcon(R.mipmap.bilibili_141px);
                                        dialog.setCancelable(false);
                                        dialog.setNegativeButton("取消", null);
                                        if (mDownloadFile.exists()) {
                                            dialog.setMessage("文件已存在，是否删除并重新下载？");
                                            dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    new DownloadAsyncTask().execute(true);
                                                }
                                            });
                                        } else {
                                            dialog.setMessage("是否下载哔哩哔哩客户端？");
                                            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    new DownloadAsyncTask().execute(false);
                                                }
                                            });
                                        }
                                        dialog.show();

                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void downloadWithThread(final String httpUrl) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createDownLoadFile(httpUrl);
                    //工作线程更新UI 方法之：runOnUIThread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showDialog();
                        }
                    });
                    Log.i(DOWNLOAD_TEST, "这里应该是Dialog还未关闭就执行");
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }).start();


    }

    private void showDialog() {
        //弹出是否删除的对话框
        AlertDialog.Builder dialog = new AlertDialog.Builder(AdvApkDownloadActivity.this);
        dialog.setTitle("提示");
        dialog.setIcon(R.mipmap.bilibili_141px);
        dialog.setCancelable(false);
        dialog.setNegativeButton("取消", null);

        if (mDownloadFile.exists()) {
            dialog.setMessage("文件已存在，是否删除重新下载？");
            dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Message msg = Message.obtain();
                    msg.what = FLAG_DIALOG_DELETE;
                    mHandler.sendMessage(msg);

                }
            });
            dialog.show();
        } else {
            //弹出开始下载确认对话框
            dialog.setMessage("是否下载Bilibili客户端？");
            dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Message msg = Message.obtain();
                    msg.what = FLAG_DIALOG_DOWNLOAD;
                    mHandler.sendMessage(msg);
                }
            });
            dialog.show();
        }
        Log.i(DOWNLOAD_TEST, "原来Dialog没关闭，就往下执行了");
    }

    private File createDownLoadFile(String httpUrl) throws IOException {
        URL url = new URL(httpUrl);
        mConnection = url.openConnection();
        mContentLength = mConnection.getContentLength();
        Log.i(DOWNLOAD_TEST, "文件大小：" + mContentLength);
        //创建文件夹及文件
        String dir = Environment.getExternalStorageDirectory() +
                File.separator + "isHere" + File.separator;
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String filename = "bilibili.apk";
        mDownloadFilePath = dir + filename;
        mDownloadFile = new File(mDownloadFilePath);
        if (mDownloadFile != null) {
            return mDownloadFile;
        }
        return null;
    }


    private void writeToFile(URLConnection connection, File downloadFile, int contentLength) {
        try {
            if (downloadFile.createNewFile()) {
                int downloadSize = 0;
                mProgress = 0;
                InputStream in = connection.getInputStream();
                byte[] bytes = new byte[1024];
                int length = 0;
                OutputStream out = new FileOutputStream(downloadFile);
                while ((length = in.read(bytes)) != -1) {
                    out.write(bytes, 0, length);
                    downloadSize += length;
                    mProgress = downloadSize * 100 / contentLength;
                    Log.i(DOWNLOAD_TEST, "下载进度：" + mProgress + "%");

                    /**
                     * 子线程更新UI 方法: view.post
                     */
                    mTv_progress.post(new Runnable() {
                        @Override
                        public void run() {
                            mTv_progress.setText(mProgress + "%");
                            mPb_download.setProgress(mProgress);
                        }
                    });
                }
                Log.i(DOWNLOAD_TEST, "下载完成：" + mProgress + "%");
                out.close();
                in.close();
            }
        } catch (Exception e) {

        }

    }

    class DownloadHandler extends Handler {

        WeakReference<AdvApkDownloadActivity> mActivity;

        public DownloadHandler(AdvApkDownloadActivity activity) {
            mActivity = new WeakReference<AdvApkDownloadActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            //获得弱引用
            AdvApkDownloadActivity activity = mActivity.get();
            //子线程更新UI 方法: 先于工作线程之前
            mTv_progress.setVisibility(View.VISIBLE);
            mPb_download.setVisibility(View.VISIBLE);
            switch (msg.what) {
                case FLAG_DIALOG_DOWNLOAD:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            writeToFile(mConnection, mDownloadFile, mContentLength);
                        }
                    }).start();
                    break;
                case FLAG_DIALOG_DELETE:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mDownloadFile.delete()) {
                                writeToFile(mConnection, mDownloadFile, mContentLength);
                            }
                        }
                    }).start();
                    break;
            }

        }
    }

    class DownloadAsyncTask extends AsyncTask<Boolean, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTv_progress.setVisibility(View.VISIBLE);
            mPb_download.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean!=null){
                if (aBoolean==true){
                    Toast.makeText(AdvApkDownloadActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(AdvApkDownloadActivity.this, "下载失败", Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mTv_progress.setText(values[0] + "%");
            mPb_download.setProgress(values[0]);

        }

        @Override
        protected Boolean doInBackground(Boolean... params) {
            //false:文件不存在  true：文件以存在
            if (params[0] == true) {
                mDownloadFile.delete();
            }
            try {
                if (mDownloadFile.createNewFile()) {
                    int downloadSize = 0;
                    mProgress = 0;
                    InputStream in = mConnection.getInputStream();
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    OutputStream out = new FileOutputStream(mDownloadFile);
                    while ((length = in.read(bytes)) != -1) {
                        out.write(bytes, 0, length);
                        downloadSize += length;
                        mProgress = downloadSize * 100 / mContentLength;
                        Log.i(DOWNLOAD_TEST, "下载进度：" + mProgress + "%");
                        publishProgress(mProgress);
                    }
                    Log.i(DOWNLOAD_TEST, "下载完成：" + mProgress + "%");
                    out.close();
                    in.close();
                    return true;
                }
            } catch (Exception e) {
                return false;
            }

            return null;
        }
    }
}
