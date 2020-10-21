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
}
