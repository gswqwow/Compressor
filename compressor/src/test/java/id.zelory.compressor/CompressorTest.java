package id.zelory.compressor;

import id.zelory.compressor.constraint.Compression;
import id.zelory.compressor.constraint.Constraint;
import id.zelory.compressor.constraint.DefaultConstraint;
import ohos.app.Context;
import org.junit.Assert;
import ohos.media.image.ImageSource;
import ohos.media.image.common.ImageInfo;
import ohos.media.image.common.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
        //Mockito.verify(compression).compressionDefault(file);
    }

    @Test
    public void compress_with_custom_specs_should_execute_all_constraint_provided() throws Exception {
        // null参数时mock示例
        DefaultConstraint dc = PowerMockito.mock(DefaultConstraint.class);
        Context context = PowerMockito.mock(Context.class);
        File file = new File("/");

        ImageSource imageSource = PowerMockito.mock(ImageSource.class);
        PowerMockito.mockStatic(ImageSource.class);
        PowerMockito.when(ImageSource.create(file, null)).thenReturn(imageSource);

        Size size = PowerMockito.mock(Size.class);
        size.height = 100;
        size.height = 100;
        ImageInfo imageInfo = PowerMockito.mock(ImageInfo.class);
        imageInfo.size = size;
        PowerMockito.when(imageSource.getImageInfo()).thenReturn(imageInfo);

        PowerMockito.whenNew(DefaultConstraint.class).withAnyArguments().thenReturn(dc);
        Compressor compressor = new Compressor();
        compressor.compress(context, file, null);

    }
}
