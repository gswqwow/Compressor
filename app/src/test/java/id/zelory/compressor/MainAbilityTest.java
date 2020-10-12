package id.zelory.compressor;

import org.junit.Assert;
import org.junit.Test;

public class MainAbilityTest {
    @Test
    public void onStart() {
        Hello hello = new Hello();
        Assert.assertEquals("In compressor", hello.getWords());
    }
}
