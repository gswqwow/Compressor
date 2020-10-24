package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class DefaultConstraint implements Constraint {

//    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    private boolean isResolved = false;
    private int width;
    private int height;
    private CompressFormat format;
    private int quality;

    public DefaultConstraint(Integer width, Integer height, CompressFormat format, Integer quality) {
        this.width = width == null ? 612 : width;
        this.height = height == null ? 816 : height;
        this.format = format == null ? CompressFormat.JPEG : format;
        this.quality = quality == null ? 80 : quality;
//        HiLog.info(label, "DefaultConstraint constructor()");
//        HiLog.info(label, "width : " + this.width);
//        HiLog.info(label, "height : " + this.height);
//        HiLog.info(label, "format : " + this.format.getName());
//        HiLog.info(label, "quality : " + this.quality);
    }

    @Override
    public boolean isSatisfied(File imageFile) {
//        HiLog.info(label, "Default  isSatisfied : " + isResolved);
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return isResolved;
    }

    @Override
    public File satisfy(File imageFile) {
        PixelMap bitmap = Util.decodeSampledBitmapFromFile(imageFile, width, height);
        File result = Util.overWrite(imageFile, bitmap, format, quality);
        isResolved = true;
        return result;
    }
}