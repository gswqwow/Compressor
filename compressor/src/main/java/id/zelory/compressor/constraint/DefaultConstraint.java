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
public class DefaultConstraint implements Constraint{

    public boolean isResolved = false;
    private final int width = 612;
    private final int height = 816;
    private final CompressFormat format = CompressFormat.JPEG;
    private final int quality = 80;


    public DefaultConstraint() {

    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return isResolved;
    }

    /**
     * @param imageFile
     * @return
     */
    @Override
    public File satisfy(File imageFile) {
        PixelMap bitmap = Util.decodeSampledBitmapFromFile(imageFile, width, height);
        File result = Util.overWrite(imageFile, bitmap, format, quality);
        isResolved = true;
        return result;
    }
}