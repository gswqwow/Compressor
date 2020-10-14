package id.zelory.compressor.constraint;


import id.zelory.compressor.extutil.Intrinsics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Compression {
    public final List<Constraint> constraints;

    public Compression() {
        this.constraints = new ArrayList<>();
    }

    public final List<Constraint> getConstraints() {
        return this.constraints;
    }

    public final void constraint(Constraint constraint) {
        Intrinsics.checkParameterIsNotNull(constraint, "constraint");
        this.constraints.add(constraint);
    }

    public void compressionDefault() {
        this.constraint(new DefaultConstraint());
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
        constraint(new SizeConstraint(maxFileSize));
    }
}
