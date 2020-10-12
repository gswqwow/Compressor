package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;

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
    private final int stepSize = 10;
    private final int maxIteration = 10;
    private final int minQuality = 10;

    public SizeConstraint(long maxFileSize) {
        this.maxFileSize = maxFileSize;
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
    public File satisfy(File imageFile) {
        iteration++;
        int quality = (100 - iteration * stepSize).takeIf { it >= minQuality } ?: minQuality;
        return Util.overWrite(imageFile, Util.loadBitmap(imageFile), null, quality);
    }
}
