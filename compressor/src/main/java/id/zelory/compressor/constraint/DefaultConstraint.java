package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.media.image.PixelMap;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class DefaultConstraint implements Constraint {

    private boolean isResolved = false;
    private static final int DEF_WIDTH = 612;
    private static final int DEF_HEIGHT = 816;
    private static final CompressFormat DEF_FORMAT = CompressFormat.JPEG;
    private static final int DEF_QUALITY = 80;

    public DefaultConstraint() {

    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return isResolved;
    }

    @Override
    public File satisfy(File imageFile) {
        PixelMap bitmap = Util.decodeSampledBitmapFromFile(imageFile, DEF_WIDTH, DEF_HEIGHT);
        File result = Util.overWrite(imageFile, bitmap, DEF_FORMAT, DEF_QUALITY);
        isResolved = true;
        return result;
    }
}