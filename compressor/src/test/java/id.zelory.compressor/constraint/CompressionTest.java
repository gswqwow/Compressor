package id.zelory.compressor.constraint;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public final class CompressionTest {
    @Test
    public final void add_constraint_should_save_it_to_constraint_list() {
        Compression compression = new Compression();

        Constraint mock = mock(DefaultConstraint.class);
        compression.constraint(mock);
        compression.constraint(mock);

        // Then
        assertEquals(compression.constraints.size(), 2);
    }
}