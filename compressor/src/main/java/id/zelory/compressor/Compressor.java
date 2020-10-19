package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.Constraint;
import ohos.app.Context;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.File;

public final class Compressor {

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");

    public File compress(Context context, File imageFile) {
        HiLog.error(label,"compress");
        Compression compression = new Compression();
        compression.compressionDefault();
        File result = Util.copyToCache(context, imageFile);
        for (Constraint constraint : compression.constraints) {
            while (!constraint.isSatisfied(result)) {
                result = constraint.satisfy(result);
            }
        }
        return result;
    }
}
