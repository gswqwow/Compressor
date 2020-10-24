package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import id.zelory.compressor.extutil.Intrinsics;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class FormatConstraint implements Constraint {

    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    private final CompressFormat format;

    public FormatConstraint(CompressFormat format) {
        this.format = format;
        HiLog.info(label, "Format constructor()  format : " + this.format.getName());
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        boolean result = this.format == Util.compressFormat(imageFile);
        HiLog.info(label, "Format  isSatisfied : " + result);
        return result;
    }

    public File satisfy(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return Util.overWriteDefault(imageFile, Util.loadBitmap(imageFile), this.format, 0, 8, null);
    }
}
