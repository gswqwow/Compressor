package id.zelory.compressor.constraint;

import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CompressionFailTest {
    private static final Logger logger = Logger.getLogger(CompressionFailTest.class.getName());
    //private static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MY_TAG");

    @Test
    public void testFail() {
        //fail("exp");
        logger.info("mytest");
        //HiLog.warn(label, "Failed to visit %{private}s, reason:%{public}d.", "www.baidu.com", "123");
    }
    @Test
    public void testok() {
        assertEquals(1,1);
    }
}
