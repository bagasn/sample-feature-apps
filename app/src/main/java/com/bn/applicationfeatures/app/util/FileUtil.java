package com.bn.applicationfeatures.app.util;

import android.content.Context;
import android.net.Uri;
import androidx.core.content.FileProvider;

import com.bn.applicationfeatures.BuildConfig;

import java.io.File;

public class FileUtil {

    private static String getProviderAuthorize() {
        return BuildConfig.APPLICATION_ID + ".fileprovider";
    }

    public static Uri getFileProvider(Context context, File file) {
        return FileProvider.getUriForFile(context, getProviderAuthorize(), file);
    }

    public static void delateFile(String path) {
        File file = new File(path);
        if (file.exists())
            file.delete();
    }

}
