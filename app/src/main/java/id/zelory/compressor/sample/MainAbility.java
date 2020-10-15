package id.zelory.compressor.sample;

import id.zelory.compressor.ResourceTable;
import id.zelory.compressor.sample.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.io.IOException;

public class MainAbility extends Ability {

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Ability_main);
//        actualImageView.setBackgroundColor(getRandomColor())
//        clearImage();
        setupClickListener();
    }

    private void setupClickListener() {
        Button chooseImageButton = (Button)findComponentById(ResourceTable.Id_chooseImageButton);
        if(chooseImageButton != null){
            chooseImageButton.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
                    MainAbility.this.chooseImage();
                }
            });
        }
        Button compressImageButton = (Button)findComponentById(ResourceTable.Id_chooseImageButton);
        if(compressImageButton != null){
            compressImageButton.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
//                    compressImage();
                }
            });
        }
        Button customCompressImageButton = (Button)findComponentById(ResourceTable.Id_chooseImageButton);
        if(customCompressImageButton != null){
            customCompressImageButton.setClickedListener(new Component.ClickedListener() {
                @Override
                public void onClick(Component component) {
//                    customCompressImage();
                }
            });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        Operation operation = new Intent.OperationBuilder()
                .withAction(Intent.ENTITY_VIDEO)
                .build();
        intent.setOperation(operation);
        startAbilityForResult(intent, 1);
    }

//    private void compressImage() {
//        actualImage?.let { imageFile ->
//                lifecycleScope.launch {
//            // Default compression
//            compressedImage = Compressor.compress(this@MainActivity, imageFile)
//            setCompressedImage()
//        }
//        } ?: showError("Please choose an image!")
//    }
//
//    private void customCompressImage() {
//        actualImage?.let { imageFile ->
//                lifecycleScope.launch {
//            // Default compression with custom destination file
//                /*compressedImage = Compressor.compress(this@MainActivity, imageFile) {
//                    default()
//                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.also {
//                        val file = File("${it.absolutePath}${File.separator}my_image.${imageFile.extension}")
//                        destination(file)
//                    }
//                }*/
//
//            // Full custom
//            compressedImage = Compressor.compress(this@MainActivity, imageFile) {
//                resolution(1280, 720)
//                quality(80)
//                format(Bitmap.CompressFormat.WEBP)
//                size(2_097_152) // 2 MB
//            }
//            setCompressedImage()
//        }
//        } ?: showError("Please choose an image!")
//    }
//
//    private void setCompressedImage() {
//        compressedImage?.let {
//            compressedImageView.setImageBitmap(BitmapFactory.decodeFile(it.absolutePath))
//            compressedSizeTextView.text = String.format("Size : %s", getReadableFileSize(it.length()))
//            Toast.makeText(this, "Compressed image save in " + it.path, Toast.LENGTH_LONG).show()
//            Log.d("Compressor", "Compressed image save in " + it.path)
//        }
//    }
//
//    private void clearImage() {
//        actualImageView.setBackgroundColor(getRandomColor())
//        compressedImageView.setImageDrawable(null)
//        compressedImageView.setBackgroundColor(getRandomColor())
//        compressedSizeTextView.text = "Size : -"
//    }
//
    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        super.onAbilityResult(requestCode, resultCode, resultData);
        if(requestCode == 1){
            HiLog.warn(label, "resultCode : "+resultCode);
            HiLog.warn(label, "resultData : "+resultData.toString());
        }

//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            if (data == null) {
//                showError("Failed to open picture!")
//                return
//            }
//            try {
//                actualImage = FileUtil.from(this, data.data)?.also {
//                    actualImageView.setImageBitmap(loadBitmap(it))
//                    actualSizeTextView.text = String.format("Size : %s", getReadableFileSize(it.length()))
//                    clearImage();
//                }
//            } catch (IOException e) {
//                showError("Failed to read picture data!")
//                e.printStackTrace();
//            }
//        }
    }
//
//    private void showError(errorMessage: String) {
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//    }
//
//    private void getRandomColor() = Random().run {
//        Color.argb(100, nextInt(256), nextInt(256), nextInt(256))
//    }
//
//    private void getReadableFileSize(size: Long): String {
//        if (size <= 0) {
//            return "0"
//        }
//        val units = arrayOf("B", "KB", "MB", "GB", "TB")
//        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
//        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())) + " " + units[digitGroups]
//    }
}
