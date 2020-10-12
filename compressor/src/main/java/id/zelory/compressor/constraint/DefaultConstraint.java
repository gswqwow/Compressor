package id.zelory.compressor.sample.constraint;

import id.zelory.compressor.sample.exception.Intrinsics;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class DefaultConstraint implements Constraint{

    public boolean isResolved = false;

    public DefaultConstraint(int width, int height,CompressFormat format, int quality) {
        super();
        Intrinsics.checkParameterIsNotNull(format,"format");
        this.width = width;
        this.height = height;
        this.format = format;
        this.quality = quality;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return isResolved;
    }

    /**
     * todo 未完成
     * @param var1
     * @return
     */
    @Override
    public File satisfy(File var1) {
//        val result = decodeSampledBitmapFromFile(imageFile, width, height).run {
//            determineImageRotation(imageFile, this).run {
//                overWrite(imageFile, this, format, quality)
//            }
//        }
//        isResolved = true
//        return result
        return null;
    }
}