package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class QualityConstraint implements Constraint {
    private boolean isResolved = false;

    private final int quality;
    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");

    public QualityConstraint(int quality) {
        this.quality = quality;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return this.isResolved;
    }

    public File satisfy(File imageFile) {
        HiLog.error(label,"QualityConstraint-isSatisfied");
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        File result = Util.overWriteDefault(imageFile, Util.loadBitmap(imageFile), null, this.quality, 4, null);
        this.isResolved = true;
        return result;
    }
}

