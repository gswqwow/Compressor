package id.zelory.compressor.constraint;


import id.zelory.compressor.extutil.Intrinsics;
import ohos.media.image.ImageSource;
import ohos.media.image.common.Size;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Compression {
    public final List<Constraint> constraints;

    public Compression() {
        this.constraints = new ArrayList<>();
    }

    public final void constraint(Constraint constraint) {
        Intrinsics.checkParameterIsNotNull(constraint, "constraint");
        this.constraints.add(constraint);
    }

    public void compressionDefault() {
        this.constraint(new DefaultConstraint(null, null,null,null));
    }

    public void compressionDefault(File imageFile) {
        ImageSource imageSource = ImageSource.create(imageFile, null);
        Size size = imageSource.getImageInfo().size;
        this.constraint(new DefaultConstraint(size.width, size.height,null,null));
    }

    public void destination(File destination) {
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        this.constraint(new DestinationConstraint(destination));
    }

    public void format(CompressFormat format) {
        Intrinsics.checkParameterIsNotNull(format, "format");
        constraint(new FormatConstraint(format));
    }

    public void quality(int quality) {
        constraint(new QualityConstraint(quality));
    }

    public void resolution(int width, int height) {
        constraint(new ResolutionConstraint(width, height));
    }

    public void size(long maxFileSize, int stepSize, int maxIteration) {
        stepSize = stepSize == 0 ? 10 : stepSize;
        maxIteration = maxIteration == 0 ? 10 : maxIteration;
        constraint(new SizeConstraint(maxFileSize, null, null, null));
    }
}
