package id.zelory.compressor;

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

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class CompressorTest {


    @Test
    public void compressTest() {
        PowerMockito.mockStatic(Util.class);
        Context context = mock(Context.class);
        File file = mock(File.class);
        when(Util.copyToCache(context, file)).thenReturn(file);
        when(mock(DefaultConstraint.class).isSatisfied(file)).thenReturn(true);
        Compressor compressor = new Compressor();

        File compress = compressor.compress(context, file, null);
        Assert.assertEquals(compress, isNotNull());
    }

}
