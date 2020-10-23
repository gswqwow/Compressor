package id.zelory.compressor;

import id.zelory.compressor.constraint.*;
import ohos.app.Context;
import ohos.media.image.ImageSource;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = {Util.class, ImageSource.class})
public class CompressorTest {

    @Test
    public void compress_with_default_specs_should_execute_default_constraint() throws Exception {
        // 传参mock示例
        DefaultConstraint dc = PowerMockito.mock(DefaultConstraint.class);
        Compression compression  = PowerMockito.mock(Compression.class);
        File file = new File("/");

        PowerMockito.whenNew(DefaultConstraint.class).withAnyArguments().thenReturn(dc);
        PowerMockito.whenNew(Compression.class).withAnyArguments().thenReturn(compression);

        PowerMockito.doNothing().when(compression).compressionDefault(file);

        List<Constraint> list =  new ArrayList<>();
        list.add(dc);
        //list.add(dc);

        PowerMockito.when(compression.getConstraints()).thenReturn(list);

        PowerMockito.when(dc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(dc.satisfy(any())).thenReturn(file);

        Context context = PowerMockito.mock(Context.class);
        PowerMockito.mockStatic(Util.class);
        PowerMockito.when(Util.copyToCache(context, file)).thenReturn(file);

        Compressor compressor = new Compressor();
        File compressFile = compressor.compress(context, file, compression);
        Assert.assertEquals(file,compressFile);
    }

    @Test
    public void compress_with_default_specs_should_execute_constraints() throws Exception {
        // 传参mock示例
        DefaultConstraint dc = PowerMockito.mock(DefaultConstraint.class);
        DestinationConstraint dec = PowerMockito.mock(DestinationConstraint.class);
        FormatConstraint fc = PowerMockito.mock(FormatConstraint.class);
        QualityConstraint qc = PowerMockito.mock(QualityConstraint.class);
        ResolutionConstraint rc = PowerMockito.mock(ResolutionConstraint.class);
        SizeConstraint sc = PowerMockito.mock(SizeConstraint.class);

        Compression compression  = PowerMockito.mock(Compression.class);
        File file = new File("/");

        PowerMockito.whenNew(DefaultConstraint.class).withAnyArguments().thenReturn(dc);
        PowerMockito.whenNew(Compression.class).withAnyArguments().thenReturn(compression);

        PowerMockito.doNothing().when(compression).compressionDefault(file);

        List<Constraint> list =  new ArrayList<>();
        list.add(dc);
        list.add(dec);
        list.add(fc);
        list.add(qc);
        list.add(rc);
        list.add(sc);

        PowerMockito.when(compression.getConstraints()).thenReturn(list);

        PowerMockito.when(dc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(dc.satisfy(any())).thenReturn(file);
        PowerMockito.when(dec.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(dec.satisfy(any())).thenReturn(file);
        PowerMockito.when(fc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(fc.satisfy(any())).thenReturn(file);
        PowerMockito.when(qc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(qc.satisfy(any())).thenReturn(file);
        PowerMockito.when(rc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(rc.satisfy(any())).thenReturn(file);
        PowerMockito.when(sc.isSatisfied(any())).thenReturn(true);
        PowerMockito.when(sc.satisfy(any())).thenReturn(file);

        Context context = PowerMockito.mock(Context.class);
        PowerMockito.mockStatic(Util.class);
        PowerMockito.when(Util.copyToCache(context, file)).thenReturn(file);

        Compressor compressor = new Compressor();
        File compressFile = compressor.compress(context, file, compression);
        Assert.assertEquals(file,compressFile);
    }
}
