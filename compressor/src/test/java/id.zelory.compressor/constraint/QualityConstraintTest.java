package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.PixelMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class QualityConstraintTest {

    @Test
    public void when_satisfy_function_not_yet_invoked__constraint_should_not_satisfied() {
        QualityConstraint constraint = new QualityConstraint(10);

        assertEquals(false, constraint.isSatisfied(mock(File.class)));
    }

    @Test
    public void when_satisfy_function_is_invoked__constraint_should_satisfied() {
        PowerMockito.mockStatic(Util.class);
        when(Util.loadBitmap(mock(File.class))).thenReturn(mock(PixelMap.class));
        when(Util.overWrite(mock(File.class), mock(PixelMap.class), CompressFormat.JPEG, 10)).thenReturn(mock(File.class));
        QualityConstraint qualityConstraint = new QualityConstraint(10);
        qualityConstraint.satisfy(mock(File.class));
        assertEquals(true, qualityConstraint.isSatisfied(mock(File.class)));
    }

//    /**
//     * TODO
//     */
//    @Test
//    public void when_trying_satisfy_constraint__it_should_save_image_with_provided_quality() {
//        PowerMockito.mockStatic(Util.class);
//        when(Util.loadBitmap(mock(File.class))).thenReturn(mock(PixelMap.class));
//        when(Util.overWrite(mock(File.class), mock(PixelMap.class), CompressFormat.JPEG, 10)).thenReturn(mock(File.class));
//        File imageFile = mock(File.class);
//        int quality = 75;
//        QualityConstraint constraint = new QualityConstraint(quality);
//
//        // When
//        constraint.satisfy(imageFile);
//        Util.overWrite(imageFile, mock(PixelMap.class), any(), anyInt());
////        verify(Util.overWrite(imageFile, mock(PixelMap.class), any(), anyInt()));
//
//    }

    @Test
    public void verify_extension() {
        // Given
        Compression compression = new Compression();

        // When
        compression.quality(90);
        if (!(compression.constraints.get(0) instanceof QualityConstraint)) {
            fail("QualityConstraint");
        }
    }
}
