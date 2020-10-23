package id.zelory.compressor.constraint;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Files.class})
public class DestinationConstraintTest {

    @Test
    public void when_destination_is_not_equal_with_image_file_constraint_should_not_satisfied(){
        DestinationConstraint constraint = new DestinationConstraint(new File("a_file.webp"));
        assertEquals(constraint.isSatisfied(new File("another_file.png")),false);
    }

    @Test
    public void when_destination_is_equal_with_image_file_constraint_should_satisfied(){
        DestinationConstraint constraint = new DestinationConstraint(new File("a_file.jpg"));
        assertEquals(constraint.isSatisfied(new File("a_file.jpg")),true);
    }

    @Test
    public void verify_extension() {
        File file = mock(File.class);
        // Given
        Compression compression = new Compression();

        compression.destination(file);

        if (!(compression.getConstraints().get(0) instanceof DestinationConstraint)) {
            fail("DestinationConstraint");
        }
    }
}
