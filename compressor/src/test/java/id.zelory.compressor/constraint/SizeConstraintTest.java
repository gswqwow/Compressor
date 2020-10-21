package id.zelory.compressor.constraint;

import id.zelory.compressor.Util;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Util.class})
public class SizeConstraintTest {

//    @Test
//    public void when_file_size_greater_than_max_file_size_constraint_should_not_satisfied(){
//        File imageFile = PowerMockito.mock(File.class);
//        PowerMockito.when(imageFile.length()).thenReturn(2000l);
//        SizeConstraint constraint = new SizeConstraint(1000);
//        assertEquals(constraint.isSatisfied(imageFile), false);
//    }
//
//    @Test
//    public void when_file_size_equal_to_max_file_size_constraint_should_satisfied(){
//        File imageFile = PowerMockito.mock(File.class);
//        PowerMockito.when(imageFile.length()).thenReturn(1000l);
//        SizeConstraint constraint = new SizeConstraint(1000);
//        assertEquals(constraint.isSatisfied(imageFile), true);
//    }
//
//    @Test
//    public void when_file_size_less_than_max_file_size_constraint_should_satisfied(){
//        File imageFile = PowerMockito.mock(File.class);
//        PowerMockito.when(imageFile.length()).thenReturn(900l);
//        SizeConstraint constraint = new SizeConstraint(1000);
//        assertEquals(constraint.isSatisfied(imageFile), true);
//    }
//
//    @Test
//    public void when_iteration_less_than_max_iteration_constraint_should_not_satisfied(){
//        File imageFile = PowerMockito.mock(File.class);
//        PowerMockito.when(imageFile.length()).thenReturn(2000l);
//
//        PowerMockito.mockStatic(Util.class);
//        PixelMap bitmap = PowerMockito.mock(PixelMap.class);
//        PowerMockito.when(Util.loadBitmap(any())).thenReturn(bitmap);
//
//        File resultFile = PowerMockito.mock(File.class);
//        PowerMockito.when(Util.overWrite(any(), any(), any(), any())).thenReturn(resultFile);
//
//        SizeConstraint constraint = new SizeConstraint(1000, null, 5, null);
//        constraint.satisfy(imageFile);
//        assertEquals(constraint.isSatisfied(imageFile), false);
//    }

//    @Test
//    fun `when iteration equal to max iteration, constraint should satisfied`() {
//        // Given
//        val imageFile = mockk<File>(relaxed = true)
//        every { imageFile.length() } returns 2000
//
//        mockkStatic("id.zelory.compressor.UtilKt")
//        every { loadBitmap(any()) } returns mockk()
//        every { overWrite(any(), any(), any(), any()) } returns mockk()
//
//        val constraint = SizeConstraint(1000, maxIteration = 5)
//
//        // When
//        repeat(5) {
//            constraint.satisfy(imageFile)
//        }
//
//        // Then
//        assertThat(constraint.isSatisfied(imageFile), equalTo(true))
//    }
//
//    @Test
//    fun `when trying satisfy constraint, it should save image with calculated quality`() {
//        // Given
//        mockkStatic("id.zelory.compressor.UtilKt")
//        every { loadBitmap(any()) } returns mockk()
//        every { overWrite(any(), any(), any(), any()) } returns mockk()
//
//        val imageFile = mockk<File>(relaxed = true)
//        val stepSize = 10
//        val quality = 100 - stepSize
//        val constraint = SizeConstraint(200, stepSize = stepSize)
//
//        // When
//        constraint.satisfy(imageFile)
//
//        // Then
//        verify { overWrite(imageFile, any(), any(), quality) }
//    }
//
//    @Test
//    fun `when trying satisfy constraint but calculated quality less than min quality, it should use min quality`() {
//        // Given
//        mockkStatic("id.zelory.compressor.UtilKt")
//        every { loadBitmap(any()) } returns mockk()
//        every { overWrite(any(), any(), any(), any()) } returns mockk()
//
//        val imageFile = mockk<File>(relaxed = true)
//        val stepSize = 50
//        val minQuality = 80
//        val constraint = SizeConstraint(200, stepSize, minQuality = minQuality)
//
//        // When
//        constraint.satisfy(imageFile)
//
//        // Then
//        verify { overWrite(imageFile, any(), any(), minQuality) }
//    }
//
//    @Test
//    fun `verify extension`() {
//        // Given
//        val compression = Compression()
//
//        // When
//        compression.size(9000)
//
//        // Then
//        assertThat(compression.constraints.first(), isA<SizeConstraint>())
//    }

}
