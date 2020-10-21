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
