package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class,ImageSource.class,ImageSource.SourceOptions.class})
public class DefaultConstraintTest {

    @Test
    public void when_satisfy_function_not_yet_invoked_constraint_should_not_satisfied() throws Exception {
        Constraint constraint = new DefaultConstraint(null,null,null,null);
        File file = mock(File.class);
        assertEquals(false, constraint.isSatisfied(file));
    }

    @Test
    public void when_satisfy_function_is_invoked_constraint_should_satisfied() {
        Constraint constraint = new DefaultConstraint(null,null,null,null);
        PowerMockito.mockStatic(Util.class);

        when(Util.decodeSampledBitmapFromFile(mock(File.class),180,180))
                .thenReturn(mock(PixelMap.class));
        when(Util.overWrite(mock(File.class),mock(PixelMap.class),CompressFormat.JPEG,10)).thenReturn(mock(File.class));
        constraint.satisfy(mock(File.class));
        assertEquals(true, constraint.isSatisfied(mock(File.class)));
    }

    @Test
    public  void compressionDefaultTest(){
        Compression compression = new Compression();
        compression.compressionDefault();
        if(!(compression.getConstraints().get(0) instanceof DefaultConstraint)){
            fail("DefaultConstraint");
        }
    }
}
