package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.ImageInfo;
import ohos.media.image.common.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

import java.io.File;
import java.util.zip.Inflater;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImageSource.class,Util.class})
public class ResolutionConstraintTest {

    /**
     * TODO
     */
    @Test
    public void when_sampled_size_is_greater_than_1_constraint_should_not_satisfied(){
        PowerMockito.mockStatic(String.class);
        PowerMockito.mockStatic(ImageSource.class);
        PowerMockito.mockStatic(ImageSource.SourceOptions.class);
        File file = mock(File.class);
        when(file.getAbsolutePath()).thenReturn("/");
        when(ImageSource.create(file.getAbsolutePath(),null)).thenReturn(mock(ImageSource.class));
        PowerMockito.mockStatic(Util.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        imageInfo.size=mock(Size.class);
        when(mock(ImageSource.class).getImageInfo()).thenReturn(imageInfo);
        when(Util.decodeSampledBitmapFromFile(mock(File.class),180,180))
                .thenReturn(mock(PixelMap.class));

        ResolutionConstraint constraint = new ResolutionConstraint(100, 100);

        assertEquals(false,constraint.isSatisfied(file));
    }
    @Test
    public void when_sampled_size_is_equal_1__constraint_should_satisfied(){
        ResolutionConstraint constraint = new ResolutionConstraint(100, 100);

        PowerMockito.mockStatic(Util.class);
        when(Util.loadBitmap(mock(File.class))).thenReturn(mock(PixelMap.class));
        when(Util.overWrite(mock(File.class), mock(PixelMap.class), CompressFormat.JPEG, 10)).thenReturn(mock(File.class));
        QualityConstraint qualityConstraint = new QualityConstraint(10);
        qualityConstraint.satisfy(mock(File.class));
        assertEquals(true, constraint.isSatisfied(mock(File.class)));
    }

    @Test
    public void when_trying_satisfy_constraint_it_should_subsampling_image_and_overwrite_file(){

    }

    @Test
    public void verify_extension(){

    }

}
