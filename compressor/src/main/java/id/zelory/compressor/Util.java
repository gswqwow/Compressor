package id.zelory.compressor;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import id.zelory.compressor.constraint.CompressFormat;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.app.Context;
import ohos.global.resource.Resource;
import ohos.global.resource.ResourceManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImagePacker;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Util {

    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static CompressFormat compressFormat;

    private static String cachePath(Context context) {
        StringBuilder path = new StringBuilder();
        File cacheDir = context.getCacheDir();
        Intrinsics.checkExpressionValueIsNotNull(cacheDir, "context.cacheDir");
        return path.append(cacheDir.getPath()).append(File.separator).append("compressor").append(File.separator).toString();
    }

    public static CompressFormat compressFormat() {
        Intrinsics.checkParameterIsNotNull(compressFormat, "Util.compressFormat");
        return compressFormat;
    }

    public static CompressFormat compressFormat(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        String fileName = imageFile.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if("png".equals(extension))
            compressFormat = CompressFormat.PNG;
        else if("webp".equals(extension))
            compressFormat = CompressFormat.WEBP;
        else
            compressFormat = CompressFormat.JPEG;
        return compressFormat;
    }

    public static PixelMap loadBitmap(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        ImageSource imageSource = ImageSource.create(imageFile.getAbsolutePath(),null);
        PixelMap bitmap = imageSource.createPixelmap(null);
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.rotateDegrees = determineImageRotation(imageFile);
        decodingOpts.desiredSize = bitmap.getImageInfo().size;
        PixelMap result = imageSource.createPixelmap(decodingOpts);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static PixelMap decodeSampledBitmapFromFile(File imageFile, int reqWidth, int reqHeight) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        ImageSource imageSource = ImageSource.create(imageFile.getAbsolutePath(),null);
        Size size = imageSource.getImageInfo().size;
        ImageSource.DecodingOptions decodingOpts = new ImageSource.DecodingOptions();
        decodingOpts.sampleSize = calculateInSampleSize(size, reqWidth, reqHeight);
        decodingOpts.rotateDegrees = determineImageRotation(imageFile);
        decodingOpts.desiredSize = new Size(reqWidth, reqHeight);
        //decodingOpts.rotateDegrees = 0;
        PixelMap bitmap = imageSource.createPixelmap(decodingOpts);
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.decodeFile…eFile.absolutePath, this)");
        Intrinsics.checkExpressionValueIsNotNull(bitmap, "BitmapFactory.Options().…absolutePath, this)\n    }");
        return bitmap;
    }

    public static int calculateInSampleSize(Size size, int reqWidth, int reqHeight) {
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

    public static float determineImageRotation(File imageFile){
        int orientation = 0;
        try {
            Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
            com.drew.metadata.Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            if (metadata != null) {
                //Collection<ExifDirectoryBase> imageDirectories = metadata.getDirectoriesOfType(ExifDirectoryBase.class);
                for (com.drew.metadata.Directory director : metadata.getDirectories()) {
                    if (director.containsTag(ExifDirectoryBase.TAG_ORIENTATION)) {
                        orientation = director.getInt(ExifDirectoryBase.TAG_ORIENTATION);
                        break;
                    }
                }
            }
        } catch (ImageProcessingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (MetadataException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        float rotation = 0f;
        switch(orientation) {
            case 3:
                rotation = 180f;
                break;
            case 6:
                rotation = 90f;
                break;
            case 8:
                rotation = 270f;
        }
        return rotation;
    }

    public static File copyToCache(Context context, File imageFile) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        File path = new File(cachePath(context));
        path.mkdirs();
        File result = new File(cachePath(context) + imageFile.getName());
        try {
            copyFile(imageFile, result);
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }

    public static File overWrite(File imageFile, PixelMap bitmap, CompressFormat format, int quality) {
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

    public static void saveBitmap(PixelMap bitmap, File destination, CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(bitmap, "bitmap");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        if(format == null){
            format = compressFormat(destination);
        }
        File file = destination.getParentFile();
        if (file != null) {
            file.mkdirs();
        }

        HiLog.info(label, "quality : " + quality);

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
            logger.log(Level.SEVERE, e.getMessage());
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

    public static void copyFile(File source, File dest)
            throws Exception {
        try(
                FileInputStream input = new FileInputStream(source);
                FileOutputStream output = new FileOutputStream(dest);
                ) {
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
    }

    public static File resPathToFile(Context context, String resPath) throws IOException {
        ResourceManager resourceManager = context.getResourceManager();
        Resource resource = resourceManager.getRawFileEntry(resPath).openRawFile();
        byte[] buffer = new byte[resource.available()];
        resource.read(buffer);
        String[] fileNames = resPath.split(File.separator);
        File tempFile = new File(context.getCacheDir() + File.separator + fileNames[fileNames.length - 1]);
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(buffer);
        return tempFile;
    }

}

