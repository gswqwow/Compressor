package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class ResolutionConstraint implements Constraint {
    private final int width;
    private final int height;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");

    public ResolutionConstraint(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean isSatisfied(File imageFile) {
        HiLog.error(label,"ResolutionConstraint-isSatisfied");
        ImageSource imageSource = ImageSource.create(imageFile.getAbsolutePath(), null);
        Size size = imageSource.getImageInfo().size;
        return Util.calculateInSampleSize(size, width, height) <= 1;
    }

    public File satisfy(File imageFile) {
        PixelMap bitmap = Util.decodeSampledBitmapFromFile(imageFile, width, height);
        return Util.overWrite(imageFile, bitmap, null, 0);
    }
}
