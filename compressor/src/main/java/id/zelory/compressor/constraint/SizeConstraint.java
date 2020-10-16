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
    private int iteration = 0;
    private long maxFileSize;
    private final static int STEP_SIZE = 10;
    private final static int MAX_ITERATION = 10;
    private final static int MIN_QUALITY = 10;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");

    public SizeConstraint(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        HiLog.error(label,"SizeConstraint-isSatisfied");
        return imageFile.length() <= maxFileSize || iteration >= MAX_ITERATION;
    }

    public File satisfy(File imageFile) {
        iteration++;
        int quality = (100 - iteration * STEP_SIZE) >= MIN_QUALITY ? (100 - iteration * STEP_SIZE) : MIN_QUALITY;
        return Util.overWrite(imageFile, Util.loadBitmap(imageFile), null, quality);
    }
}
