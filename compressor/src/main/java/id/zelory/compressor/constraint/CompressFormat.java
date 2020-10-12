package id.zelory.compressor.constraint;

public enum CompressFormat {

    JPEG("jpg"),
    PNG("png"),
    WEBP("webp");

    private String name;

    CompressFormat(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
