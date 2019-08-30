package com.bn.applicationfeatures.app.engine;

import android.net.Uri;

public interface DownloadListener {
    void onDownloadStarted();

    void onProgressChanged(int contentLenght, int progress, int persent);

    void onCancelled();

    void onDownloaded(Uri uri, String mimeType);
}
