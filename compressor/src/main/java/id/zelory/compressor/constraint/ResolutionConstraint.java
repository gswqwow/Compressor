package id.zelory.compressor.constraint;

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

    public ResolutionConstraint(int width, int height) {
        this.width = width;
        this.height = height;
     }

    /**
     * TODO
     * @param var1
     * @return
     */
    @Override
    public boolean isSatisfied(File var1) {
        return BitmapFactory.Options().run {
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(imageFile.absolutePath, this)
            calculateInSampleSize(this, width, height) <= 1
        }
    }

    /**
     * TODO
     * @param var1
     * @return
     */
    @Override
    public File satisfy(File var1) {
        return decodeSampledBitmapFromFile(imageFile, width, height).run {
            determineImageRotation(imageFile, this).run {
                overWrite(imageFile, this)
            }
        }
    }
}
