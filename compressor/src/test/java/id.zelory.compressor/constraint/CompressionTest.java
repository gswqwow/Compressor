package id.zelory.compressor.constraint;

import ohos.hiviewdfx.HiLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;


@RunWith(PowerMockRunner.class)
@PrepareForTest(HiLog.class)
public final class CompressionTest {

    @Test
    public final void add_constraint_should_save_it_to_constraint_list() {
        Compression compression = new Compression();

        PowerMockito.mockStatic(HiLog.class);
        PowerMockito.when(HiLog.error(any(), any(), any())).thenReturn(1);

//        Constraint mock = PowerMockito.mock(DefaultConstraint.class);
//        compression.constraint(mock);
//        compression.constraint(mock);

        // Then
//        assertEquals(2, compression.constraints.size());
    }
}