package com.raymondqk.raysqlitepractice.utils;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.raymondqk.raysqlitepractice.model.PubInfo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by 陈其康 raymondchan on 2016/8/17 0017.
 */
public class FileUtils {

    public static final String TAG = "TestFile";
    //外置存储器根目录File
    private File externalFile;
    //外置存储器根目录 绝对路径
    private File externalPath;

    //我们自定义的外置储存目录文件夹
    private File appExternalDir;




    public FileUtils(Activity activity) {
        externalFile = Environment.getExternalStorageDirectory();
        externalPath = Environment.getExternalStorageDirectory().getAbsoluteFile();
        appExternalDir = new File(externalPath + "/" + "ishere");
        createDir();
    }

    public void createDir() {
        if (!appExternalDir.exists()) {
            if (appExternalDir.mkdir()) {
                Log.i(TAG, appExternalDir.getAbsolutePath() + " 文件夹创成功");
            } else {
                Log.e(TAG, appExternalDir.getAbsolutePath() + " 文件夹创建失败");
            }
        }
    }

    public void createFilePubinfo(final PubInfo pubInfo) {

        String filename = pubInfo.getProject();
        File file = new File(appExternalDir, filename + ".txt");
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    Log.i(TAG, filename + " 创建成功！");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, filename + " 创建失败！");
                //                        return false;
            }
        }
        //写入数据
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = new FileOutputStream(file, false);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            StringBuilder builder = new StringBuilder();
            builder.append("项目名称：" + pubInfo.getProject() + "\n");
            builder.append("发起组织：" + pubInfo.getOrgnization() + "\n");
            builder.append("发起人：" + pubInfo.getPublisher() + "\n");
            builder.append("发布时间：" + pubInfo.getPubdate() + "\n");
            builder.append("截止时间：" + pubInfo.getDeadline() + "\n");
            builder.append("项目内容：\n" + pubInfo.getBrief() + "\n");
            writer.write(builder.toString());
            Log.i(TAG, filename + " 写入完成！");
            //                    return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //                    return false;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //                return false;
    }
}


