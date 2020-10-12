// UtilKt.java
package id.zelory.compressor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import id.zelory.compressor.exception.Intrinsics;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class Util {
    private static final String separator;
    private static CompressFormat compressFormat;

    private static final String cachePath(Context context) {
        StringBuilder path = new StringBuilder();
        File cacheDir = context.getCacheDir();
        Intrinsics.checkExpressionValueIsNotNull(cacheDir, "context.cacheDir");
        return path.append(cacheDir.getPath()).append(separator).append("compressor").append(separator).toString();
    }

    public static final CompressFormat compressFormat() {
        Intrinsics.checkParameterIsNotNull(compressFormat, "Util.compressFormat");
        String var1 = FilesKt.getExtension(compressFormat);
        boolean var2 = false;
        if (var1 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        } else {
            String var10000 = var1.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(var10000, "(this as java.lang.String).toLowerCase()");
            var1 = var10000;
            CompressFormat var3;
            switch(var1.hashCode()) {
                case 111145:
                    if (var1.equals("png")) {
                        var3 = CompressFormat.PNG;
                        return var3;
                    }
                    break;
                case 3645340:
                    if (var1.equals("webp")) {
                        var3 = CompressFormat.WEBP;
                        return var3;
                    }
            }

            var3 = CompressFormat.JPEG;
            return var3;
        }
    }

    public static final String extension(@NotNull CompressFormat $this$extension) {
        // $FF: Couldn't be decompiled
    }

    public static final Bitmap loadBitmap(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "this");
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
        Pair var5 = TuplesKt.to(options.outHeight, options.outWidth);
        int height = ((Number)var5.component1()).intValue();
        int width = ((Number)var5.component2()).intValue();
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
        Intrinsics.checkParameterIsNotNull(format, "format");
        File file;
        if (format == compressFormat(imageFile)) {
            file = imageFile;
        } else {
            StringBuilder path = new StringBuilder();
            String imagePath = imageFile.getAbsolutePath();
            Intrinsics.checkExpressionValueIsNotNull(imagePath, "imageFile.absolutePath");
            file = new File(path.append(StringsKt.substringBeforeLast$default(imagePath, ".", (String)null, 2, (Object)null)).append('.').append(extension(format)).toString());
        }

        File result = file;
        imageFile.delete();
        saveBitmap(bitmap, result, format, quality);
        return result;
    }

    public static File overWriteDefault(File file, Bitmap bitmap, CompressFormat format, int var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            format = compressFormat(file);
        }

        if ((var4 & 8) != 0) {
            var3 = 100;
        }

        return overWrite(file, bitmap, format, var3);
    }

    public static final void saveBitmap(Bitmap bitmap, File destination, CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(format, "format");
        File file = destination.getParentFile();
        if (file != null) {
            file.mkdirs();
        }

        FileOutputStream fileOutputStream;
        boolean var12 = false;

        boolean var7;
        try {
            var12 = true;
            fileOutputStream = new FileOutputStream(destination.getAbsolutePath());
            bitmap.compress(format, quality, (OutputStream)fileOutputStream);
            var12 = false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (var12) {
                if (fileOutputStream != null) {
                    var7 = false;
                    boolean var8 = false;
                    boolean var10 = false;
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

            }
        }

        boolean var6 = false;
        var7 = false;
        int var9 = false;
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public static void saveBitmapDefault(Bitmap bitmap, File file, CompressFormat format, int var3, int var4, Object var5) {
        if ((var4 & 4) != 0) {
            format = compressFormat(file);
        }

        if ((var4 & 8) != 0) {
            var3 = 100;
        }

        saveBitmap(bitmap, file, format, var3);
    }

    static {
        separator = File.separator;
    }
}

public final class WhenMappings {

    public static final int[] EnumSwitchMapping = new int[CompressFormat.values().length];

    static {
        EnumSwitchMapping[CompressFormat.PNG.ordinal()] = 1;
        EnumSwitchMapping[CompressFormat.WEBP.ordinal()] = 2;
    }
}
