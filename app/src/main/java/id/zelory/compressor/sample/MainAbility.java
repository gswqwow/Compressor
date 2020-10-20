package id.zelory.compressor.sample;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.ResourceTable;
import id.zelory.compressor.constraint.CompressFormat;
import id.zelory.compressor.constraint.Compression;

import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.agp.colors.RgbColor;
import ohos.agp.components.Button;
import ohos.agp.components.Image;
import ohos.agp.components.Image.ScaleMode;
import ohos.agp.components.Text;
import ohos.agp.components.element.ShapeElement;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.log10;

public class MainAbility extends Ability {

    static final HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0, "DEBUG");
    List<File> pngFileList = new ArrayList<File>();
    File imageFile = null;
    int index = 0;
    EventRunner eventRunner = EventRunner.create(true);
    EventHandler eventHandler = new CompressHandler(eventRunner);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Ability_main);
//        setBackgroundColor();
//        clearImage();
        Image image = (Image)findComponentById(ResourceTable.Id_actualImageView);
        image.setScaleMode(ScaleMode.INSIDE);
        setupClickListener();
    }

    private void setupClickListener() {
        Button chooseImageButton = (Button)findComponentById(ResourceTable.Id_chooseImageButton);
        if(chooseImageButton != null){
            chooseImageButton.setClickedListener(component -> chooseImage());
        }
        Button compressImageButton = (Button)findComponentById(ResourceTable.Id_compressImageButton);
        if(compressImageButton != null){
            compressImageButton.setClickedListener(component -> {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        File result = compressImage();
                        InnerEvent event = InnerEvent.get(1, 0, result);
                        eventHandler.sendEvent(event);
                    }
                };
                eventHandler.postTask(task);
            });
        }
        Button customCompressImageButton = (Button)findComponentById(ResourceTable.Id_customCompressImageButton);
        if(customCompressImageButton != null){
            customCompressImageButton.setClickedListener(component -> {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        File result = customCompressImage();
                        InnerEvent event = InnerEvent.get(1, 0, result);
                        eventHandler.sendEvent(event);
                    }
                };
                eventHandler.postTask(task);
            });
        }
    }

    private void chooseImage() {
        HiLog.debug(label,"chooseImage");
        clearImage();
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
        Text actualSize = (Text)findComponentById(ResourceTable.Id_actualSizeTextView);
        actualSize.setText("Size : " + getReadableFileSize(imageFile.length()));
//        Intent intent = new Intent();
//        Operation operation = new Intent.OperationBuilder()
//                .withAction(Intent.ACTION_QUERY_WEATHER)
//                .build();
//        intent.setOperation(operation);
//        startAbilityForResult(intent, 1);
    }

    @Override
    protected void onAbilityResult(int requestCode, int resultCode, Intent resultData) {
        HiLog.debug(label,"onAbilityResult");
        super.onAbilityResult(requestCode, resultCode, resultData);
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

    private void findPng(File file) {
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

    private File compressImage() {
        HiLog.debug(label,"compressImage");
        Compressor compressor = new Compressor();
        return compressor.compress(getContext(), imageFile, null);
    }

    private File customCompressImage() {
        HiLog.debug(label,"customCompressImage");
        Compressor compressor = new Compressor();
        Compression compression = new Compression();
        compression.resolution(1280, 720);
        compression.quality(80);
        compression.format(CompressFormat.WEBP);
        compression.size(2048, 0, 0);
        return compressor.compress(getContext(), imageFile, compression);
    }

    private void setCompressedImage(File compressedImage) {
        Image image = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        ImageSource imageSource = ImageSource.create(compressedImage,null);
        PixelMap bitmap = imageSource.createPixelmap(null);
        image.setPixelMap(bitmap);
        Text compressedSize = (Text)findComponentById(ResourceTable.Id_compressedSizeTextView);
        compressedSize.setText("Size : " + getReadableFileSize(compressedImage.length()));

    }

    private void clearImage() {
        Image actualImage = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        actualImage.setPixelMap(null);
        Image compressedImage = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        compressedImage.setPixelMap(null);
        Text actualSizeText = (Text)findComponentById(ResourceTable.Id_actualSizeTextView);
        actualSizeText.setText("Size : -");
        Text compressedSizeText = (Text)findComponentById(ResourceTable.Id_compressedSizeTextView);
        compressedSizeText.setText("Size : -");
        setBackgroundColor();
    }

    private void setBackgroundColor(){
        Image actualImage = (Image)findComponentById(ResourceTable.Id_actualImageView);
        ShapeElement actualBackground = new ShapeElement();
        actualBackground.setRgbColor(getRandomColor());
        actualImage.setBackground(actualBackground);
        Image compressedImage = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        ShapeElement compressedBackground = new ShapeElement();
        compressedBackground.setRgbColor(getRandomColor());
        compressedImage.setBackground(compressedBackground);
    }

//    private void showError(String errorMessage) {
//        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
//    }

    private RgbColor getRandomColor() {
        Random r = new Random();
        return new RgbColor(r.nextInt(256), r.nextInt(256), r.nextInt(256), 100);
    }

    private String getReadableFileSize(Long size) {
        if (size <= 0) {
            return "0";
        }
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int)(log10(size.doubleValue()) / log10(1024.0));
        return new DecimalFormat("#,##0.#").format(Math.pow(size / 1024.0, digitGroups)) + " " + units[digitGroups];
    }

    class CompressHandler extends EventHandler {

        public CompressHandler(EventRunner runner) throws IllegalArgumentException {
            super(runner);
        }

        @Override
        protected void processEvent(InnerEvent event) {
            super.processEvent(event);
            switch(event.eventId){
                case 1:
                    File imageFile = (File)event.object;
                    setCompressedImage(imageFile);
                    break;
            }
        }
    }
}


