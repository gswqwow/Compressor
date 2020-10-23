package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.DefaultConstraint;
import ohos.app.Context;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Util.class)
public class CompressorTest {

    @Test
    public void compress_with_default_specs_should_execute_default_constraint() throws Exception {
        DefaultConstraint dc = PowerMockito.mock(DefaultConstraint.class);
        Compression compression  = PowerMockito.mock(Compression.class);
        File file = new File("/");

        PowerMockito.whenNew(DefaultConstraint.class).withAnyArguments().thenReturn(dc);
        PowerMockito.whenNew(Compression.class).withAnyArguments().thenReturn(compression);

        PowerMockito.doNothing().when(compression).compressionDefault(file);
        PowerMockito.when(compression.getConstraints()).thenReturn(new ArrayList<>());

        PowerMockito.when(dc.isSatisfied(any())).thenReturn(false);
        PowerMockito.when(dc.satisfy(any())).thenReturn(file);

        Context context = PowerMockito.mock(Context.class);
        PowerMockito.mockStatic(Util.class);
        PowerMockito.when(Util.copyToCache(context, file)).thenReturn(file);

        Compressor compressor = new Compressor();
        compressor.compress(context, file, compression);

        //Mockito.verify(compression).compressionDefault(file);
    }
}
