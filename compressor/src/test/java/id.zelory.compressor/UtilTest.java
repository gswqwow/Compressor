package id.zelory.compressor;

import id.zelory.compressor.constraint.CompressFormat;
import ohos.media.image.ImagePacker;
import ohos.media.image.PixelMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.FileOutputStream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class,ImagePacker.class})
public class UtilTest {

    @Test
    public void saveBitmapTest() throws Exception {
        File destination = mock(File.class);
        PixelMap bitmap = mock(PixelMap.class);
        FileOutputStream fileOutputStream = mock(FileOutputStream.class);

        PowerMockito.whenNew(FileOutputStream.class).withAnyArguments().thenReturn(fileOutputStream);
        ImagePacker imagePacker = mock(ImagePacker.class);
        PowerMockito.mockStatic(ImagePacker.class);
        
        PowerMockito.mockStatic(ImagePacker.PackingOptions.class);
        ImagePacker.PackingOptions packingOptions = mock(ImagePacker.PackingOptions.class);
        PowerMockito.whenNew(ImagePacker.PackingOptions.class).withAnyArguments().thenReturn(packingOptions);
        when(ImagePacker.create()).thenReturn(imagePacker);

        Util.saveBitmap(bitmap,destination,CompressFormat.JPEG,10);

   }
}
