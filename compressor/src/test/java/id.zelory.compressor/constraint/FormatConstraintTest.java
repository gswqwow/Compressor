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
public class FormatConstraintTest {

    @Test
    public void when_extension_is_not_equal_with_format_constraint_should_not_satisfied() {
        FormatConstraint constraint = new FormatConstraint(CompressFormat.JPEG);
        assertEquals(false, constraint.isSatisfied(new File("a_file.webp")));
    }

    @Test
    public void when_extension_is_not_equal_with_format__constraint_should_not_satisfied() {
        FormatConstraint constraint = new FormatConstraint(CompressFormat.WEBP);
        assertEquals(true, constraint.isSatisfied(new File("a_file.webp")));
    }

    /**
     * TODO
     */
    @Test
    public void when_trying_satisfy_constraint__it_should_save_image_with_selected_format() {

    }

    @Test
    public void verify_extension() {
        Compression compression = new Compression();
        compression.format(CompressFormat.PNG);
        compression.compressionDefault();
        if (!(compression.constraints.get(0) instanceof FormatConstraint)) {
            fail("DefaultConstraint");
        }
    }
}
