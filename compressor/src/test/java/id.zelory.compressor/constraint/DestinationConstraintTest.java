package id.zelory.compressor.constraint;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

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

//    /**
//     * TODO IOException 需要抛出来吗
//     */
//    @Test
//    public void when_trying_satisfy_constraint__it_should_copy_image_to_destination() throws IOException {

//        File imageFile = mock(File.class);
//        File destination = mock(File.class);
//        DestinationConstraint constraint = new DestinationConstraint(destination);
//        imageFile.toPath();
//
//        Files mock = mock(Files.class);
//        when(mock.copy(imageFile.toPath(), destination.toPath(), any())).thenReturn(mock(Path.class));
//        constraint.satisfy(imageFile);
//        verify(Files.copy(imageFile.toPath(), destination.toPath(),any()));
//    }


//    @Test
//    public void verify_extension(){
//        Compression compression = new Compression();
//        compression.destination(mock(File.class));
//
//        if(!(compression.constraints.get(0) instanceof  DestinationConstraint)){
//            fail("exp");
//        }
//    }

}
