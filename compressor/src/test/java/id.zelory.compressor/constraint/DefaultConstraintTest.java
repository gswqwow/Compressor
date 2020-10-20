package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DefaultConstraintTest {
    @Test
    public void when_satisfy_function_not_yet_invoked_constraint_should_not_satisfied() throws Exception {
        Constraint constraint = new DefaultConstraint();
        HiLogLabel hiLogLabel = PowerMockito.mock(HiLogLabel.class);
        PowerMockito.whenNew(HiLogLabel.class).withArguments(anyInt(),anyInt(),anyString())
                .thenReturn(hiLogLabel);
        when(hiLogLabel).init
        File file = mock(File.class);
        assertEquals(false, constraint.isSatisfied(file));
    }

    @Test
    public void when_satisfy_function_is_invoked_constraint_should_satisfied() {
        Constraint constraint = new DefaultConstraint();
        Util util = mock(Util.class);
        when(constraint.satisfy(mock(File.class))).thenReturn(mock(File.class));
        when(util.decodeSampledBitmapFromFile(any(),any(),any())).thenReturn(mock(PixelMap.class));
        when(util.overWrite(any(),any(),any(),any())).thenReturn(mock(File.class));
        assertEquals(true, constraint.isSatisfied(mock(File.class)));
    }
}
