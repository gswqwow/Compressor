package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.ImageInfo;
import ohos.media.image.common.Size;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImageSource.class, Util.class})
public class ResolutionConstraintTest {

    @Test
    public void when_sampled_size_is_greater_than_1_constraint_should_not_satisfied() throws Exception {
        File file = mock(File.class);

        PowerMockito.mockStatic(ImageSource.class);
        PowerMockito.mockStatic(Util.class);

        ImageSource imageSource = mock(ImageSource.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        Size size = PowerMockito.mock(Size.class);
        size.width = 100;
        size.height = 100;
        imageInfo.size = size;

        when(file.getAbsolutePath()).thenReturn("/");
        when(ImageSource.create(file.getAbsolutePath(), null)).thenReturn(imageSource);
        when(imageSource.getImageInfo()).thenReturn(imageInfo);
        when(Util.calculateInSampleSize(size, 100, 100)).thenReturn(2);

        ResolutionConstraint resolutionConstraint = new ResolutionConstraint(100, 100);
        Assert.assertEquals(false, resolutionConstraint.isSatisfied(file));
    }

    @Test
    public void when_trying_satisfy_constraint_it_should_subsampling_image_and_overwrite_file() {
        File file = mock(File.class);
        PixelMap pixelMap = mock(PixelMap.class);

        PowerMockito.mockStatic(Util.class);

        when(Util.decodeSampledBitmapFromFile(file, 100, 100)).thenReturn(pixelMap);
        when(Util.overWrite(file, pixelMap, null, 0)).thenReturn(file);

        ResolutionConstraint resolutionConstraint = new ResolutionConstraint(100, 100);
        Assert.assertEquals(file, resolutionConstraint.satisfy(file));
    }

    @Test
    public void when_sampled_size_is_equal_1_constraint_should_satisfied() throws Exception {
        File file = mock(File.class);

        PowerMockito.mockStatic(ImageSource.class);
        PowerMockito.mockStatic(Util.class);

        ImageSource imageSource = mock(ImageSource.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        Size size = PowerMockito.mock(Size.class);
        size.width = 100;
        size.height = 100;
        imageInfo.size = size;

        when(file.getAbsolutePath()).thenReturn("/");
        when(ImageSource.create(file.getAbsolutePath(), null)).thenReturn(imageSource);
        when(imageSource.getImageInfo()).thenReturn(imageInfo);
        when(Util.calculateInSampleSize(size, 100, 100)).thenReturn(1);

        ResolutionConstraint resolutionConstraint = new ResolutionConstraint(100, 100);
        Assert.assertEquals(true, resolutionConstraint.isSatisfied(file));
    }

    @Test
    public void verify_extension() {
        // Given
        Compression compression = new Compression();

        // When
        compression.resolution(100, 100);
        if (!(compression.getConstraints().get(0) instanceof ResolutionConstraint)) {
            fail("ResolutionConstraint");
        }
    }
}
