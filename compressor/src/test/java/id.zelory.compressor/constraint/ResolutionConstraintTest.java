package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.ImageSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.fail;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ImageSource.class, Util.class})
public class ResolutionConstraintTest {

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
