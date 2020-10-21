package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.File;
import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class SizeConstraintTest {

    @Test
    public void when_file_size_greater_than_max_file_size_constraint_should_not_satisfied(){
        File imageFile = PowerMockito.mock(File.class);
        PowerMockito.when(imageFile.length()).thenReturn(2000l);
        SizeConstraint constraint = new SizeConstraint(1000, null, null, null);
        assertEquals(constraint.isSatisfied(imageFile), false);
    }

    @Test
    public void when_file_size_equal_to_max_file_size_constraint_should_satisfied(){
        File imageFile = PowerMockito.mock(File.class);
        PowerMockito.when(imageFile.length()).thenReturn(1000l);
        SizeConstraint constraint = new SizeConstraint(1000,null,null,null);
        assertEquals(constraint.isSatisfied(imageFile), true);
    }

    @Test
    public void when_file_size_less_than_max_file_size_constraint_should_satisfied(){
        File imageFile = PowerMockito.mock(File.class);
        PowerMockito.when(imageFile.length()).thenReturn(900l);
        SizeConstraint constraint = new SizeConstraint(1000,null,null,null);
        assertEquals(constraint.isSatisfied(imageFile), true);
    }

}
