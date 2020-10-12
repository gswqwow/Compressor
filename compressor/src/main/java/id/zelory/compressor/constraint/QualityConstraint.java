package id.zelory.compressor.sample.constraint;

import id.zelory.compressor.sample.exception.Intrinsics;
import id.zelory.compressor.loadBitmap;
import id.zelory.compressor.overWrite;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class QualityConstraint implements Constraint {
    private boolean isResolved = false;

    private final  int quality;

    public QualityConstraint(int quality){
        this.quality = quality;
    }

    @Override
    public boolean isSatisfied(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return this.isResolved;
    }

    /**
     * TODO
     * @param imageFile
     * @return
     */
    @Override
    public File satisfy(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
//        File result = UtilKt.overWrite$default(imageFile, UtilKt.loadBitmap(imageFile), (CompressFormat)null, this.quality, 4, (Object)null);
        this.isResolved = true;
//        return result;
        return null;
    }
}

