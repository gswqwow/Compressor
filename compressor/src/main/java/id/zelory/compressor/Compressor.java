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
        HiLog.error(label,"Compression");
        compression.compressionDefault();
        HiLog.error(label,"CompressionDefault");
        File result = Util.copyToCache(context, imageFile);
        HiLog.error(label,"constraints" + compression.constraints.size());
        HiLog.error(label,"result" + result.getName());
        for (Constraint constraint : compression.constraints) {
            while (!constraint.isSatisfied(result)) {
                HiLog.error(label,"constraint" + constraint.toString());
                result = constraint.satisfy(result);
            }
        }
        return result;
    }
}
