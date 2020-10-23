package id.zelory.compressor;

import id.zelory.compressor.constraint.CompressFormat;
import ohos.media.image.ImagePacker;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.ImageInfo;
import ohos.media.image.common.Size;
import org.junit.Assert;
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
@PrepareForTest({Util.class, ImagePacker.class, ImageSource.class})
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

        Util.saveBitmap(bitmap, destination, CompressFormat.JPEG, 10);
    }

    @Test
    public void compressFormatPNGTest() {
        File file = new File("/a.png");
        Assert.assertEquals(CompressFormat.PNG, Util.compressFormat(file));
    }

    @Test
    public void compressFormatWEBPTest() {
        File file = new File("/a.webp");
        Assert.assertEquals(CompressFormat.WEBP, Util.compressFormat(file));
    }

    @Test
    public void compressFormatJPEGTest() {
        File file = new File("/a.jpeg");
        Assert.assertEquals(CompressFormat.JPEG, Util.compressFormat(file));
    }

    @Test
    public void loadBitmapTest() throws Exception {
        File file = mock(File.class);

        PowerMockito.mockStatic(ImageSource.class);
        PowerMockito.mockStatic(ImageSource.DecodingOptions.class);

        ImageSource imageSource = mock(ImageSource.class);
        PixelMap pixelMap = mock(PixelMap.class);
        ImageSource.DecodingOptions decodingOptions = mock(ImageSource.DecodingOptions.class);
        ImageInfo imageInfo = mock(ImageInfo.class);
        Size size = PowerMockito.mock(Size.class);
        size.width = 1080;
        size.height = 1920;
        imageInfo.size = size;

        when(file.getAbsolutePath()).thenReturn("/");
        when(ImageSource.create("/", null)).thenReturn(imageSource);
        when(pixelMap.getImageInfo()).thenReturn(imageInfo);

        PowerMockito.whenNew(ImageSource.DecodingOptions.class).withAnyArguments().thenReturn(decodingOptions);
        when(imageSource.createPixelmap(decodingOptions)).thenReturn(pixelMap);
        when(imageSource.createPixelmap(null)).thenReturn(pixelMap);

        Assert.assertEquals(pixelMap, Util.loadBitmap(file));
    }

    @Test
    public void calculateInSampleSize1Test() {
        Size size = PowerMockito.mock(Size.class);
        size.width = 800;
        size.height = 800;

        Assert.assertEquals(2, Util.calculateInSampleSize(size, 400, 400));
    }

    @Test
    public void calculateInSampleSize2Test() {
        Size size = PowerMockito.mock(Size.class);
        size.width = 800;
        size.height = 800;

        Assert.assertEquals(1, Util.calculateInSampleSize(size, 1000, 1000));
    }

    @Test
    public void calculateInSampleSize3Test() {
        Size size = PowerMockito.mock(Size.class);
        size.width = 800;
        size.height = 800;

        Assert.assertEquals(1, Util.calculateInSampleSize(size, 1000, 500));
    }

    @Test
    public void calculateInSampleSize4Test() {
        Size size = PowerMockito.mock(Size.class);
        size.width = 800;
        size.height = 800;

        Assert.assertEquals(1, Util.calculateInSampleSize(size, 500, 500));
    }

    @Test
    public void calculateInSampleSize5Test() {
        Size size = PowerMockito.mock(Size.class);
        size.width = 800;
        size.height = 800;

        Assert.assertEquals(2, Util.calculateInSampleSize(size, 200, 400));
    }
}
