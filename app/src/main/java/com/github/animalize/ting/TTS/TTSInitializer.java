package com.github.animalize.ting.TTS;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class TTSInitializer {
    private static final String SAMPLE_DIR_NAME = "baiduTTS";

    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";

    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static Context appContext;
    private static String ttsDataDir;

    public static void initialEnv(Context context) {
        appContext = context.getApplicationContext();
        File fileDir = appContext.getFilesDir();

        File dataDir = new File(fileDir, SAMPLE_DIR_NAME);
        ttsDataDir = dataDir.getAbsolutePath();
        if (!dataDir.isDirectory()) {
            makeDir(ttsDataDir);
        }

        copyFromAssetsToSdcard(
                false,
                SPEECH_FEMALE_MODEL_NAME,
                ttsDataDir + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false,
                SPEECH_MALE_MODEL_NAME,
                ttsDataDir + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false,
                TEXT_MODEL_NAME,
                ttsDataDir + "/" + TEXT_MODEL_NAME);

        //copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);

        copyFromAssetsToSdcard(
                false,
                "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME,
                ttsDataDir + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(
                false,
                "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME,
                ttsDataDir + "/" + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(
                false,
                "english/" + ENGLISH_TEXT_MODEL_NAME,
                ttsDataDir + "/" + ENGLISH_TEXT_MODEL_NAME);
    }

    public static String getTextModelName() {
        if (ttsDataDir == null) {
            return null;
        }
        return ttsDataDir + "/" + TEXT_MODEL_NAME;
    }

    public static String getSpeechMaleModelName() {
        if (ttsDataDir == null) {
            return null;
        }
        return ttsDataDir + "/" + SPEECH_MALE_MODEL_NAME;
    }

    public static String getSpeechFemaleModelName() {
        if (ttsDataDir == null) {
            return null;
        }
        return ttsDataDir + "/" + SPEECH_FEMALE_MODEL_NAME;
    }


    private static void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
    private static void copyFromAssetsToSdcard(boolean isCover,
                                               String source,
                                               String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = appContext.getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
