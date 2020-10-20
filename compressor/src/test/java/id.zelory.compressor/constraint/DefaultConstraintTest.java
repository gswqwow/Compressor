package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImagePacker;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.ImageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class,ImageSource.class,ImageSource.SourceOptions.class})
public class DefaultConstraintTest {

    @Test
    public void when_satisfy_function_not_yet_invoked_constraint_should_not_satisfied() throws Exception {
        Constraint constraint = new DefaultConstraint();
        File file = mock(File.class);
        assertEquals(false, constraint.isSatisfied(file));
    }

    @Test
    public void when_satisfy_function_is_invoked_constraint_should_satisfied() {
        Constraint constraint = new DefaultConstraint();
//        Util util = mock(Util.class);
        PowerMockito.mockStatic(Util.class);
//        PowerMockito.mockStatic(ImageSource.SourceOptions.class);
//        PowerMockito.mockStatic(ImageSource.class);
//        ImageSource imageSource = mock(ImageSource.class);
//        mock(imageSource.SourceOptions.class);
//        when(imageSource.create(anyString(), any())).thenReturn(imageSource);

//        ImageInfo imageInfo = mock(ImageInfo.class);
//        when(imageSource.getImageInfo()).thenReturn(imageInfo);

//        File file = mock(File.class);
//        PixelMap pixelMap = mock(PixelMap.class);
//        when(Util.decodeSampledBitmapFromFile(file,anyInt(),anyInt())).thenReturn(pixelMap);

//        when(constraint.satisfy(mock(File.class))).thenReturn(mock(File.class));
//        Util.decodeSampledBitmapFromFile(any(),any(),any());

        PowerMockito.when(Util.decodeSampledBitmapFromFile(any(),any(),any())).thenReturn(mock(PixelMap.class));
        when(Util.overWrite(any(),any(),any(),any())).thenReturn(mock(File.class));
        assertEquals(true, constraint.isSatisfied(mock(File.class)));
    }
}
