package com.whitefan.jdlite.bean;

import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;


public class SimpleFileDownloadListener extends FileDownloadListener {
    @Override
    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i("====","pending");

    }

    @Override
    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i("====","progress");
    }

    @Override
    protected void completed(BaseDownloadTask task) {
        Log.i("====","completed");
    }

    @Override
    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
        Log.i("====","paused");
    }

    @Override
    protected void error(BaseDownloadTask task, Throwable e) {
        Log.i("====","error");
        Log.i("====",e.toString());
    }

    @Override
    protected void warn(BaseDownloadTask task) {
        Log.i("====","warn");
    }
}
