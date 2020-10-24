package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class SizeConstraint implements Constraint {

//    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    private int iteration = 0;
    private long maxFileSize;
    private int stepSize;
    private int maxIteration;
    private int minQuality;

    public SizeConstraint(long maxFileSize, Integer stepSize, Integer maxIteration, Integer minQuality) {
        this.maxFileSize = maxFileSize;
        this.stepSize = stepSize == null ? 10 : stepSize;
        this.maxIteration = maxIteration == null ? 10 : maxIteration;
        this.minQuality = minQuality == null ? 10 : minQuality;
//        HiLog.info(label, "Size constructor()");
//        HiLog.info(label, "maxFileSize : " + this.maxFileSize);
//        HiLog.info(label, "stepSize : " + this.stepSize);
//        HiLog.info(label, "maxIteration : " + this.maxIteration);
//        HiLog.info(label, "minQuality : " + this.minQuality);
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        boolean result = imageFile.length() <= maxFileSize || iteration >= maxIteration;
//        HiLog.info(label, "Size  isSatisfied : " + result);
        return result;
    }

    public File satisfy(File imageFile) {
        iteration++;
        int quality = (100 - iteration * stepSize) >= minQuality ? (100 - iteration * stepSize) : minQuality;
        return Util.overWrite(imageFile, Util.loadBitmap(imageFile), null, quality);
    }
}
