package cc.buddies.cleanarch.data.files;

import android.content.Context;

import androidx.annotation.NonNull;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileCache {

    // ExternalCache根路径类型
    public interface ExternalCacheRootPaths {
        String IMAGE = "images";
        String VIDEO = "videos";
    }

    public static File getImagePath(@NonNull Context context) {
        File externalCacheDir = context.getExternalCacheDir();
        return new File(externalCacheDir, ExternalCacheRootPaths.IMAGE);
    }

    public static void createFile(File file) throws IOException {
        FileUtils.touch(file);
    }

}
