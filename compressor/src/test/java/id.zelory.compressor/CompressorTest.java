package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.Constraint;
import id.zelory.compressor.constraint.DefaultConstraint;
import ohos.app.Context;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class CompressorTest {

    @Test
    public void compressTest() throws Exception {
//        PowerMockito.mockStatic(Util.class);
//        Context context = mock(Context.class);
//        File file = mock(File.class);
//        when(Util.copyToCache(context, file)).thenReturn(file);
//
//        Compressor compressor = new Compressor();
//
//        DefaultConstraint defaultConstraint = mock(DefaultConstraint.class);
//        Compression compression = new Compression();
////        compression.compressionDefault();
//        compression.constraint(new DefaultConstraint());
//
//        File compress = compressor.compress(context, file, compression);
//        Assert.assertEquals(compress, isNotNull());
    }
}
