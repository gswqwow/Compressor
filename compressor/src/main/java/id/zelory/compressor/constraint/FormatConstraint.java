package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import id.zelory.compressor.extutil.Intrinsics;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
class FormatConstraint implements Constraint {

    private final CompressFormat format;

    public FormatConstraint(CompressFormat format) {
        this.format = format;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return this.format == Util.compressFormat(imageFile);
    }

    public File satisfy(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return Util.overWriteDefault(imageFile, Util.loadBitmap(imageFile), this.format, 0, 8, null);
    }
}
