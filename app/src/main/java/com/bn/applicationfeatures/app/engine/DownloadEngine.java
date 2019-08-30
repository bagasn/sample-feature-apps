package com.bn.applicationfeatures.app.engine;

import android.net.Uri;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.bn.applicationfeatures.app.util.FileUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadEngine extends AsyncTask<String, Integer, Uri> {

    private static final String TAG = "DownloadEngine";
    private String errorMessage = "Something going wrong on class DownloadEngine";

    private String[] incorrectCharacters = new String[]{
            "\\", "/", "\"", "*", "?", "|", "#"
    };

    private String mUrl;
    private boolean sslEnable;
    private String mFilename;
    private String extension;
    private String mimeType;
    private File mDirectory;

    private DownloadListener mListener = null;

    public DownloadEngine(@NonNull String url, @NonNull String filename, @NonNull File targetDir) {
        mUrl = url;
        mFilename = filename;
        mDirectory = targetDir;

        extension = MimeTypeMap.getFileExtensionFromUrl(mUrl);
        mimeType = "*/*";
        sslEnable = isSslEnable(mUrl);
    }

    public DownloadEngine(@NonNull String url, @NonNull File targetDir) {
        mUrl = url;
        mFilename = "";
        mDirectory = targetDir;

        extension = MimeTypeMap.getFileExtensionFromUrl(mUrl);
        mimeType = "*/*";
        sslEnable = isSslEnable(mUrl);
    }

    public void setDownloadListener(DownloadListener listener) {
        mListener = listener;
    }

    public String getFileExtension() {
        return extension;
    }

    public String getFileMimeType() {
        return mimeType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private File getTargetFile(File dir, String filename) {
        if (!dir.exists())
            dir.mkdirs();

        if (filename.isEmpty()) {
            filename = "File_" + System.currentTimeMillis() + "." + extension;
        } else {
            for (String s : incorrectCharacters) {
                filename = filename.replace(s, "");
            }

            if (!filename.endsWith(extension))
                filename += "." + extension;
        }

        File file = new File(dir, filename);
        int indexing = 1;
        while (file.exists()) {
            filename = filename.replace("." + extension, "");
            file = new File(dir, filename + "(" + indexing + ")." + extension);
        }
        return file;
    }

    private boolean isSslEnable(String mUrl) {
        mUrl = mUrl.toLowerCase();
        return mUrl.startsWith("https");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mListener != null)
            mListener.onDownloadStarted();
    }

    @Override
    protected void onPostExecute(Uri uri) {
        super.onPostExecute(uri);

        if (mListener != null)
            mListener.onDownloaded(uri, getFileMimeType());
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        if (mListener != null)
            mListener.onProgressChanged(values[0], values[1], values[2]);
    }

    @Override
    protected void onCancelled(Uri uri) {
        super.onCancelled(uri);

        try {
            FileUtil.delateFile(uri.getPath());
        } catch (NullPointerException e) {
            Log.e(TAG, "cancelWithUri -> ", e);
        }

        if (mListener != null)
            mListener.onCancelled();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        if (mListener != null)
            mListener.onCancelled();
    }

    @Override
    protected Uri doInBackground(String... strings) {
        try {
            URL url = new URL(mUrl);
            HttpURLConnection conn;
            if (sslEnable)
                conn = (HttpsURLConnection) url.openConnection();
            else
                conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                errorMessage = "Connection Error [" + conn.getResponseCode() + "]";
                Log.e(TAG, "URL: " + mUrl + ",\n"
                        + "Error Code [" + conn.getResponseCode()
                        + "],\nMessage: " + conn.getResponseMessage());
                return null;
            }

            mimeType = conn.getContentType();

            File targetFile = getTargetFile(mDirectory, mFilename);
            FileOutputStream outputStream = new FileOutputStream(targetFile);

            InputStream inputStream = new BufferedInputStream(conn.getInputStream());

            int lenght = conn.getContentLength();
            byte[] buffer = new byte[1024];
            int counter = 0;
            int reader;
            while ((reader = inputStream.read(buffer, 0, buffer.length)) != -1) {
                if (isCancelled()) {
                    outputStream.close();
                    inputStream.close();
                    return null;
                }

                counter += reader;
                if (lenght > 0) {
                    publishProgress(counter, lenght, (counter * 100) / lenght);
                }

                outputStream.write(buffer, 0, reader);
            }

            outputStream.close();
            inputStream.close();

            return Uri.fromFile(targetFile);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }
}
