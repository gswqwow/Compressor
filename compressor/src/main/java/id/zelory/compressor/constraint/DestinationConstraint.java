package id.zelory.compressor.sample.constraint;

import id.zelory.compressor.sample.exception.Intrinsics;

import java.io.File;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class DestinationConstraint implements Constraint {
    private final File destination;

    public DestinationConstraint( File destination) {
        super();
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        this.destination = destination;
    }

    public boolean isSatisfied( File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return Intrinsics.areEqual(imageFile.getAbsolutePath(), this.destination.getAbsolutePath());
    }

    /**
     * TODO 未完成
     *
     * @param imageFile
     * @return
     */
    public File satisfy( File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
//        return FilesKt.copyTo$default(imageFile, this.destination, true, 0, 4, (Object)null);
        return null;
    }

}
