package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

    @Test
    public void verify_extension() {
        Compression compression = new Compression();
        compression.format(CompressFormat.PNG);
        compression.compressionDefault();
        if (!(compression.getConstraints().get(0) instanceof FormatConstraint)) {
            fail("DefaultConstraint");
        }
    }
}
