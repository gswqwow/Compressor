package id.zelory.compressor.constraint;

import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class CompressionTest {
    @Test
    public final void add_constraint_should_save_it_to_constraint_list() {
        Compression compression = new Compression();

        HiLogLabel label = mock(HiLogLabel.class);
        when(HiLog.error(any(),any()));
        Constraint mock = mock(DefaultConstraint.class);
        compression.constraint(mock);
        compression.constraint(mock);

        // Then
        assertEquals(2, compression.constraints.size());
    }
}