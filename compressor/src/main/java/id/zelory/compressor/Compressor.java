package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.Constraint;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;

public final class Compressor {

    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");

    public File compress(Context context, File imageFile, Compression compression) {
        HiLog.info(label, "compress()");
        HiLog.info(label, "imageFile in compress() : " + imageFile.getName());
        if(compression == null){
            compression = new Compression();
            compression.compressionDefault(imageFile);
        }
        File result = Util.copyToCache(context, imageFile);
        HiLog.info(label, "resultFile in compress() : " + result.getName());
        for (Constraint constraint : compression.getConstraints()) {
            while (!constraint.isSatisfied(result)) {
                result = constraint.satisfy(result);
            }
        }
        return result;
    }
}
