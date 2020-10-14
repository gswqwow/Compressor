package id.zelory.compressor.sample;

import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.utils.net.Uri;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created on : June 18, 2016
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
class FileUtil {
    static final Logger logger = Logger.getLogger(FileUtil.class.getName());
    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    private FileUtil() {

    }

    public static File from(Context context, Uri uri) throws IOException {
        DataAbilityHelper helper = DataAbilityHelper.creator(context, uri);
        InputStream inputStream = null;
        FileOutputStream out = null;
        File tempFile = null;
        try {
            FileDescriptor fd = helper.openFile(uri, null);
            inputStream = new FileInputStream(fd);
            String fileName = getFileName(context, uri);
            String[] splitName = splitFileName(fileName);
            tempFile = File.createTempFile(splitName[0], splitName[1]);
            tempFile = rename(tempFile, fileName);
            tempFile.deleteOnExit();
            out = new FileOutputStream(tempFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DataAbilityRemoteException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            copy(inputStream, out);
            inputStream.close();
        }

        if (out != null) {
            out.close();
        }
        return tempFile;
    }

    private static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }

    private static String getFileName(Context context, Uri uri) {
        DataAbilityHelper helper = DataAbilityHelper.creator(context, uri);
        String result = null;
//        if (uri.getScheme().equals("content")) {
//            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
//            try {
//                if (cursor != null && cursor.moveToFirst()) {
//                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//        }
        if (result == null) {
            result = uri.getDecodedPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private static File rename(File file, String newName) {
        File newFile = new File(file.getParent(), newName);
        if (!newFile.equals(file)) {
            if (newFile.exists() && newFile.delete()) {
                logger.info("Delete old " + newName + " file");
            }
            if (file.renameTo(newFile)) {
                logger.info("Rename file to " + newName);
            }
        }
        return newFile;
    }

    private static long copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
