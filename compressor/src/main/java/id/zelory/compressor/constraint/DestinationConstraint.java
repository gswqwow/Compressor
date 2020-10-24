package id.zelory.compressor.constraint;

import id.zelory.compressor.extutil.Intrinsics;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created on : January 25, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public class DestinationConstraint implements Constraint {

    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    private static final Logger logger = Logger.getLogger(DestinationConstraint.class.getName());
    private final File destination;

    public DestinationConstraint(File destination) {
        super();
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        this.destination = destination;
        HiLog.info(label, "Destination constructor()  destination : "+this.destination.getName());
    }

    public boolean isSatisfied(File imageFile) {
        HiLog.info(label, "Destination  isSatisfied : "
                + Intrinsics.areEqual(imageFile.getAbsolutePath(), this.destination.getAbsolutePath()));
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        return Intrinsics.areEqual(imageFile.getAbsolutePath(), this.destination.getAbsolutePath());
    }

    public File satisfy(File imageFile) {
        Intrinsics.checkParameterIsNotNull(imageFile, "imageFile");
        File result;
        try {
            result = Files.copy(imageFile.toPath(), this.destination.toPath()).toFile();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return null;
        }
        return result;
    }
}
