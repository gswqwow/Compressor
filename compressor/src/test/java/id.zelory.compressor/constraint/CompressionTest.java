package id.zelory.compressor.constraint;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.mock;


@RunWith(PowerMockRunner.class)
public final class CompressionTest {

    @Test
    public final void add_constraint_should_save_it_to_constraint_list() {
        Compression compression = new Compression();

        DefaultConstraint defaultConstraint = mock(DefaultConstraint.class);
        DefaultConstraint defaultConstraint1 = mock(DefaultConstraint.class);

        compression.constraint(defaultConstraint);
        compression.constraint(defaultConstraint1);

        Assert.assertEquals(2, compression.constraints.size());
    }
}