package id.zelory.compressor.sample;

import id.zelory.compressor.Compressor;
import id.zelory.compressor.ResourceTable;
import id.zelory.compressor.Util;
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
import ohos.agp.window.dialog.ToastDialog;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.ImageSource;
import ohos.media.image.PixelMap;
import ohos.media.image.common.Size;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static java.lang.Math.log10;

public class MainAbility extends Ability {

    static HiLogLabel label = new HiLogLabel(HiLog.LOG_APP,0x00, "DEBUG");
    long time;
    List<File> picFiles = new ArrayList<>();
    File imageFile = null;
    int index;
    EventRunner eventRunner = EventRunner.create(true);
    EventHandler eventHandler = new CompressHandler(eventRunner);

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_Ability_main);
        HiLog.info(label, "onStart   Thread : " + EventRunner.current().toString());
        setBackgroundColor();
        clearImage();
        setupClickListener();
        try{
            picFiles.add(Util.resPathToFile(getContext(), "resources/rawfile/pic1.jpg"));
            picFiles.add(Util.resPathToFile(getContext(), "resources/rawfile/pic2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        HiLog.info(label, "Button2   Thread : " + EventRunner.current().toString());
                        File result = compressImage(imageFile);
                        InnerEvent event = InnerEvent.get(1, 0, result);
                        eventHandler.sendEvent(event, 0, EventHandler.Priority.IMMEDIATE);
                    }
                };
                eventHandler.postTask(task);

                ToastDialog toast = new ToastDialog(getContext());
                toast.setContentText("Start compress!");
                toast.show();
            });
        }
        Button customCompressImageButton = (Button)findComponentById(ResourceTable.Id_customCompressImageButton);
        if(customCompressImageButton != null){
            customCompressImageButton.setClickedListener(component -> {
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        File result = customCompressImage(imageFile);
                        InnerEvent event = InnerEvent.get(1, 0, result);
                        eventHandler.sendEvent(event);
                    }
                };
                eventHandler.postTask(task);

                ToastDialog toast = new ToastDialog(getContext());
                toast.setContentText("Start custom compress!");
                toast.show();
            });
        }
    }

    private void chooseImage() {
        clearImage();
        if(index == picFiles.size()){
            index = 0;
        }
        imageFile = picFiles.get(index++);
        Image image = (Image)findComponentById(ResourceTable.Id_actualImageView);
        image.setPixelMap(Util.loadBitmap(imageFile));
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

    private File compressImage(File file) {
        HiLog.info(label, "compressImage()");
        this.time = new Date().getTime();
        Compressor compressor = new Compressor();
        return compressor.compress(getContext(), file, null);
    }

    private File customCompressImage(File file) {
        this.time = new Date().getTime();
        ImageSource imageSource = ImageSource.create(file, null);
        Size size = imageSource.getImageInfo().size;
        Compressor compressor = new Compressor();
        Compression compression = new Compression();
        compression.resolution(size.width, size.height);
        compression.quality(80);
        compression.format(CompressFormat.JPEG);
        compression.size(50000, 0, 0);
        return compressor.compress(getContext(), file, compression);
    }

    private void setCompressedImage(File compressedImage) {
        this.time = new Date().getTime() - this.time;
        HiLog.info(label, "time is " + this.time);
        this.time = 0;
        Image image = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        ImageSource imageSource = ImageSource.create(compressedImage,null);
        PixelMap bitmap = imageSource.createPixelmap(null);
        image.setPixelMap(bitmap);
        Text compressedSize = (Text)findComponentById(ResourceTable.Id_compressedSizeTextView);
        compressedSize.setText("Size : " + getReadableFileSize(compressedImage.length()));

//        ToastDialog toast = new ToastDialog(getContext());
//        toast.setContentText("Compressed image save in " + compressedImage.getPath());
//        toast.show();

        Text toastText = (Text)findComponentById(ResourceTable.Id_toastTextView);
        toastText.setText("Compressed image save in " + compressedImage.getPath());
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
    }

    private void setBackgroundColor(){
        Image actualImage = (Image)findComponentById(ResourceTable.Id_actualImageView);
        actualImage.setScaleMode(ScaleMode.INSIDE);
        ShapeElement actualBackground = new ShapeElement();
        actualBackground.setRgbColor(getRandomColor());
        actualImage.setBackground(actualBackground);
        Image compressedImage = (Image)findComponentById(ResourceTable.Id_compressedImageView);
        compressedImage.setScaleMode(ScaleMode.INSIDE);
        ShapeElement compressedBackground = new ShapeElement();
        compressedBackground.setRgbColor(getRandomColor());
        compressedImage.setBackground(compressedBackground);
    }

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
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups)) + " " + units[digitGroups];
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
                    HiLog.info(label, "CompressHandler   Thread : " + EventRunner.current().toString());
                    File imageFile = (File)event.object;
                    setCompressedImage(imageFile);
                    break;
            }
        }
    }
}


