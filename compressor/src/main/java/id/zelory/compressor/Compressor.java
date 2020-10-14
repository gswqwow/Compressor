package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.Constraint;
import ohos.app.Context;

import java.io.File;

public final class Compressor {

    public File compress(Context context, File imageFile) {
        Compression compression = new Compression();
        compression.compressionDefault();
        File result = Util.copyToCache(context, imageFile);
        for(Constraint constraint : compression.constraints){
            while(!constraint.isSatisfied(result)){
                result = constraint.satisfy(result);
            }
        }
        return result;
    }
}
