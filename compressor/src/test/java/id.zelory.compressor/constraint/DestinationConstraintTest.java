package id.zelory.compressor.constraint;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class DestinationConstraintTest {


    @Test
    public void when_destination_is_not_equal_with_image_file__constraint_should_not_satisfied(){
        DestinationConstraint constraint = new DestinationConstraint(new File("a_file.webp"));
        assertEquals(constraint.isSatisfied(new File("another_file.png")),false);
    }

    @Test
    public void when_destination_is_equal_with_image_file__constraint_should_satisfied(){
        DestinationConstraint constraint = new DestinationConstraint(new File("a_file.jpg"));
        assertEquals(constraint.isSatisfied(new File("a_file.jpg")),true);
    }

    /**
     * TODO IOException 需要抛出来吗
     */
    @Test
    public void when_trying_satisfy_constraint__it_should_copy_image_to_destination() throws IOException {
        Files files  = mock(Files.class);
        when(Files.copy(mock(Path.class), any(), any())).thenReturn(mock(Path.class));
        File imageFile = new File("source.jpg");
        File destination = new File("destination.jpg");
        DestinationConstraint constraint = new DestinationConstraint(destination);

        constraint.satisfy(imageFile);
        verify(Files.copy(imageFile.toPath(), destination.toPath(),any()));
    }


    @Test
    public void verify_extension(){
        Compression compression = new Compression();
        compression.destination(mock(File.class));

        assertEquals(compression.constraints.get(0),mock(Constraint.class));
    }

}
