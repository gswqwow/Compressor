package id.zelory.compressor.constraint;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class SizeConstraint implements  Constraint{

    private int iteration= 0;
    private final long maxFileSize;
    private final int stepSize;
    private final int maxIteration;
    private final int minQuality;

    public SizeConstraint(long maxFileSize, int stepSize, int maxIteration, int minQuality) {
        this.maxFileSize = maxFileSize;
        this.stepSize = stepSize;
        this.maxIteration = maxIteration;
        this.minQuality = minQuality;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        return imageFile.length() <= maxFileSize || iteration >= maxIteration;
    }

    /**
     * TODO
     * @param var1
     * @return
     */
    @Override
    public File satisfy(File var1) {
        iteration++;
//        val quality = (100 - iteration * stepSize).takeIf { it >= minQuality } ?: minQuality;
//        return overWrite(imageFile, loadBitmap(imageFile), quality = quality)
        return null;
    }
}
