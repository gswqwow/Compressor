package id.zelory.compressor.constraint;

import id.zelory.compressor.extutil.Intrinsics;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    public File satisfy(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        File result = null;
        try {
            result = Files.copy(imageFile.toPath(), this.destination.toPath()).toFile();
        } catch (IOException e) {
            return null;
        }
        return result;
    }

}
