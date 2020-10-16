package id.zelory.compressor.sample;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.ResourceTable;
import id.zelory.compressor.sample.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.ability.DataAbilityHelper;
import ohos.aafwk.ability.DataAbilityRemoteException;
import ohos.aafwk.content.Intent;
import ohos.aafwk.content.Operation;
import ohos.agp.components.Button;
import ohos.agp.components.Component;
import ohos.agp.components.Image;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.utils.net.Uri;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainAbility extends Ability {

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "MY_TAG");
    List<File> pngFileList = new ArrayList<File>();
    File imageFile = null;
    int index = 0;

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
            chooseImageButton.setClickedListener(component -> {
                try {
                    chooseImage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DataAbilityRemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        Button compressImageButton = (Button)findComponentById(ResourceTable.Id_compressImageButton);
        if(compressImageButton != null){
            compressImageButton.setClickedListener(component -> {
                try {
                    compressImage();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DataAbilityRemoteException e) {
                    e.printStackTrace();
                }
            });
        }
        Button customCompressImageButton = (Button)findComponentById(ResourceTable.Id_customCompressImageButton);
        if(customCompressImageButton != null){
//            customCompressImageButton.setClickedListener(component -> customCompressImage());
        }
    }

    private void chooseImage() throws FileNotFoundException, DataAbilityRemoteException {
        HiLog.error(label,"Choose Image");
        if(pngFileList.size() == 0){
            File file = new File("/");
            findPng(file);
        }
        if(index == pngFileList.size()){
            index = 0;
        }
        imageFile = pngFileList.get(index++);
        Image image = (Image)findComponentById(ResourceTable.Id_actualImageView);
        ImageSource imageSource = ImageSource.create(imageFile,null);
        PixelMap bitmap = imageSource.createPixelmap(null);
        image.setPixelMap(bitmap);

//        Intent intent = new Intent();
//        Operation operation = new Intent.OperationBuilder()
//                .withAction(Intent.ACTION_QUERY_WEATHER)
//                .build();
//        intent.setOperation(operation);
//        startAbilityForResult(intent, 1);
    }

    private void findPng(File file){
        if(pngFileList.size() >= 10){
            return;
        }
        if(file != null && file.list() != null && file.list().length > 0){
            for(File subFile : file.listFiles()){
                findPng(subFile);
            }
        }else if(file != null && file.getName() != null && !file.getName().equals("")){
            if(file.getName().contains(".png")){
                pngFileList.add(file);

            }
        }
    }

    private void compressImage() throws IOException, DataAbilityRemoteException {
        HiLog.error(label,"Compress Image");
        HiLog.error(label,"imageFile : "+ imageFile.getName());


//        File file = new File("/proc/self/task/14731/cwd/proc/self/task/14731/cwd/proc/self/task/14731/cwd/proc/self/task/14731/cwd/proc/self/task/14731/cwd/etc/charger/1080x2220/full_charge.pngfull_charge.png");

        //        Compressor compressor = new Compressor();
//        File compressedImage = compressor.compress(getContext(), file);

//        DataAbilityHelper helper = DataAbilityHelper.creator(this);
//        Uri uri = Uri.parse("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1307/10/c3/23153395_1373426315898.jpg");
//        File imageFile = FileUtil.from(getContext(), uri);
//        Image image = (Image)findComponentById(ResourceTable.Id_actualImageView);
//        ImageSource imageSource = ImageSource.create(imageFile,null);
//        PixelMap bitmap = imageSource.createPixelmap(null);
//        image.setPixelMap(bitmap);

//        Uri uri = Uri.parse("dataability://id.zelory.compressor.sample/base/media/sample.jpg");
//
//        File imageFile = FileUtil.from(getContext(), uri);
//        HiLog.error(label,"Image File : " + imageFile.getAbsolutePath());
        Compressor compressor = new Compressor();
        HiLog.error(label,"11111111111111");
        File compressedImage = compressor.compress(getContext(), imageFile);
        HiLog.error(label,"222222222222222");
        Image image = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        ImageSource imageSource = ImageSource.create(compressedImage,null);
        PixelMap bitmap = imageSource.createPixelmap(null);
        image.setPixelMap(bitmap);

//        actualImage?.let { imageFile ->
//                lifecycleScope.launch {
//            // Default compression
//            compressedImage = Compressor.compress(this@MainActivity, imageFile)
//            setCompressedImage()
//        }
//        } ?: showError("Please choose an image!")
    }
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
        HiLog.warn(label,"Result");
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
