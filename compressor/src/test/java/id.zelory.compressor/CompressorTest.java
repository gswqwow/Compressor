package id.zelory.compressor;

import id.zelory.compressor.constraint.DefaultConstraint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
public class CompressorTest {

    @Test
    public void compress_with_default_specs_should_execute_default_constraint() throws Exception {
        DefaultConstraint dc = PowerMockito.mock(DefaultConstraint.class);
        File file = PowerMockito.mock(File.class);
        PowerMockito.whenNew(DefaultConstraint.class).withAnyArguments().thenReturn(dc);
        PowerMockito.when(dc.isSatisfied(any())).thenReturn(false);
        PowerMockito.when(dc.satisfy(any())).thenReturn(file);

    }
}
