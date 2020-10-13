package id.zelory.compressor.constraint;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompressionFailTest {
    @Test
    public void testFail() {
        fail("exp");
    }
    @Test
    public void testok() {
        assertEquals(1,1);
    }
}
