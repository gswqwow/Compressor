package id.zelory.compressor;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import id.zelory.compressor.constraint.CompressFormat;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.agp.utils.Matrix;
import ohos.app.Context;
import ohos.media.image.ImagePacker;
import ohos.media.image.ImageSource;
import ohos.media.image.ImageSource.IncrementalSourceOptions;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

import java.io.*;
import java.nio.file.Files;
import java.util.Collection;

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
        if("png".equals(extension))
            compressFormat = CompressFormat.PNG;
        else if("webp".equals(extension))
            compressFormat = CompressFormat.WEBP;
        else
            compressFormat = CompressFormat.JPEG;
        return compressFormat;
    }

    public static final PixelMap loadBitmap(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        ImageSource imageSource = ImageSource.create(imageFile.getAbsolutePath(),null);
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.rotateDegrees = determineImageRotation(imageFile);
        PixelMap bitmap = imageSource.createPixelmap(decodingOpts);
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "bitmap");
        return bitmap;
    }

    public static final PixelMap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        ImageSource imageSource = ImageSource.create(imageFile.getAbsolutePath(),null);
        Size size = imageSource.getImageInfo().size;
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.sampleSize = calculateInSampleSize(size, reqWidth, reqHeight);
        decodingOpts.rotateDegrees = determineImageRotation(imageFile);
        PixelMap bitmap = imageSource.createPixelmap(decodingOpts);
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.decodeFile…eFile.absolutePath, this)");
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.Options().…absolutePath, this)\n    }");
        return bitmap;
    }

    public static final int calculateInSampleSize(Size size, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(size, "size");
        int height = size.height;
        int width = size.width;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            for(int halfWidth = width / 2; halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth; inSampleSize *= 2) {
            }
        }

        return inSampleSize;
    }

    public static final float determineImageRotation(File imageFile){
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        int orientation = 0;
        try {
            com.drew.metadata.Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            Collection<ExifDirectoryBase> imageDirectories = metadata.getDirectoriesOfType(ExifDirectoryBase.class);
            for(ExifDirectoryBase director : imageDirectories)
            {
                if (director.containsTag(ExifDirectoryBase.TAG_ORIENTATION))
                {
                    orientation = director.getInt(ExifDirectoryBase.TAG_ORIENTATION);
                    break;
                }
            }
        } catch (ImageProcessingException e) {
        } catch (MetadataException e) {
        } catch (IOException e) {
        }
        float rotation = 0f;
        switch(orientation) {
            case 3:
                rotation = 180f;
                break;
            case 4:
            case 5:
            case 7:
            case 6:
                rotation = 90f;
            case 8:
                rotation = 270f;
        }
        return rotation;
    }

//    public static final PixelMap determineImageRotation(File imageFile, PixelMap bitmap) {
//        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
//        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
//
//        Matrix matrix = new Matrix();
//        switch(orientation) {
//            case 3:
//                matrix.postRotate(180.0F);
//            case 4:
//            case 5:
//            case 7:
//            default:
//                break;
//            case 6:
//                matrix.postRotate(90.0F);
//                break;
//            case 8:
//                matrix.postRotate(270.0F);
//        }
//
//        PixelMap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//        Intrinsics.checkExpressionValueIsNotNull(result, "Bitmap.createBitmap(bitm…map.height, matrix, true)");
//        return result;
//    }

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

    public static final File overWrite(File imageFile, PixelMap bitmap, CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        if(format == null){
            format = compressFormat(imageFile);
        }
        if(quality == 0){
            quality = 100;
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

    public static File overWriteDefault(File file, PixelMap bitmap, CompressFormat format, int quality, int flag, Object obj) {
        if ((flag & 4) != 0) {
            format = compressFormat(file);
        }

        if ((flag & 8) != 0) {
            quality = 100;
        }

        return overWrite(file, bitmap, format, quality);
    }

    public static final void saveBitmap(PixelMap bitmap, File destination, CompressFormat format, int quality) {
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
            ImagePacker imagePacker = ImagePacker.create();
            ImagePacker.PackingOptions packingOptions = new ImagePacker.PackingOptions();
            packingOptions.format = "image/jpeg"; //TODO 鸿蒙目前只支持jpeg编码，且这里的格式是MIME-TYPE格式
            packingOptions.quality = quality;
            imagePacker.initializePacking(fileOutputStream, packingOptions);
            imagePacker.addImage(bitmap);
            imagePacker.finalizePacking();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveBitmapDefault(PixelMap bitmap, File file, CompressFormat format, int quality, int flag, Object obj) {
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
