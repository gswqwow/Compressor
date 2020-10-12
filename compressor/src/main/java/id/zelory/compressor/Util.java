package id.zelory.compressor;

import id.zelory.compressor.constraint.CompressFormat;
import id.zelory.compressor.exception.Intrinsics;
import ohos.app.Context;

import java.io.*;
import java.nio.file.Files;

public final class Util {

    private static CompressFormat compressFormat;

    private static final String cachePath(Context context) {
        StringBuilder path = new StringBuilder();
        File cacheDir = context.getCacheDir();
        Intrinsics.checkExpressionValueIsNotNull(cacheDir, "context.cacheDir");
        return path.append(cacheDir.getPath()).append(File.separator).append("compressor").append(File.separator).toString();
    }

    public static final CompressFormat compressFormat() {
        Intrinsics.checkParameterIsNotNull(compressFormat, "Util.compressFormat");
        return compressFormat;
    }

    public static final CompressFormat compressFormat(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        String fileName = imageFile.getName();
        String extension = fileName.substring(fileName.indexOf(".") + 1);
        switch (extension){
            case "png":
                compressFormat = CompressFormat.PNG;
            case "webp":
                compressFormat = CompressFormat.WEBP;
            default:
                compressFormat = CompressFormat.JPEG;
        }
        return compressFormat;
    }

    public static final Bitmap loadBitmap(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "bitmap");
        return determineImageRotation(imageFile, bitmap);
    }

    public static final Bitmap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.decodeFile…eFile.absolutePath, this)");
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.Options().…absolutePath, this)\n    }");
        return bitmap;
    }

    public static final int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(options, "options");
        int height = ((Number)options.outHeight).intValue();
        int width = ((Number)options.outWidth).intValue();
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;

            for(int halfWidth = width / 2; halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth; inSampleSize *= 2) {
            }
        }

        return inSampleSize;
    }

    public static final Bitmap determineImageRotation(File imageFile, Bitmap bitmap) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exif.getAttributeInt("Orientation", 0);
        Matrix matrix = new Matrix();
        switch(orientation) {
            case 3:
                matrix.postRotate(180.0F);
            case 4:
            case 5:
            case 7:
            default:
                break;
            case 6:
                matrix.postRotate(90.0F);
                break;
            case 8:
                matrix.postRotate(270.0F);
        }

        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        Intrinsics.checkExpressionValueIsNotNull(result, "Bitmap.createBitmap(bitm…map.height, matrix, true)");
        return result;
    }

    public static final File copyToCache(Context context, File imageFile) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        File result = null;
        try {
            result = Files.copy(imageFile.toPath(),
                    new File(cachePath(context) + imageFile.getName()).toPath()).toFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final File overWrite(File imageFile, Bitmap bitmap, CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        if(format == null){
            format = compressFormat(imageFile);
        }
        File file;
        if (format == compressFormat(imageFile)) {
            file = imageFile;
        } else {
            StringBuilder path = new StringBuilder();
            String imagePath = imageFile.getAbsolutePath();
            Intrinsics.checkExpressionValueIsNotNull(imagePath, "imageFile.absolutePath");
            file = new File(path.append(imagePath.substring(0, imagePath.lastIndexOf("."))).append('.').append(format.getName()).toString());
        }

        File result = file;
        imageFile.delete();
        saveBitmap(bitmap, result, format, quality);
        return result;
    }

    public static File overWriteDefault(File file, Bitmap bitmap, CompressFormat format, int quality, int flag, Object obj) {
        if ((flag & 4) != 0) {
            format = compressFormat(file);
        }

        if ((flag & 8) != 0) {
            quality = 100;
        }

        return overWrite(file, bitmap, format, quality);
    }

    public static final void saveBitmap(Bitmap bitmap, File destination, CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        if(format == null){
            format = compressFormat(destination);
        }
        File file = destination.getParentFile();
        if (file != null) {
            file.mkdirs();
        }

        try (
            FileOutputStream fileOutputStream = new FileOutputStream(destination.getAbsolutePath());
        ){
            bitmap.compress(format, quality, (OutputStream)fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBitmapDefault(Bitmap bitmap, File file, CompressFormat format, int quality, int flag, Object obj) {
        if ((flag & 4) != 0) {
            format = compressFormat(file);
        }

        if ((flag & 8) != 0) {
            quality = 100;
        }

        saveBitmap(bitmap, file, format, quality);
    }

}

final class WhenMappings {

    public static final int[] EnumSwitchMapping = new int[CompressFormat.values().length];

    static {
        EnumSwitchMapping[CompressFormat.PNG.ordinal()] = 1;
        EnumSwitchMapping[CompressFormat.WEBP.ordinal()] = 2;
    }
}