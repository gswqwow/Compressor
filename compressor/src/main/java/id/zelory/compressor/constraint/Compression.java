package id.zelory.compressor.constraint;


import id.zelory.compressor.exception.Intrinsics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


class Compression {
    private final List<Constraint> constraints;

    public Compression() {
        this.constraints  = new ArrayList<Constraint>();
    }

    public final List<Constraint> getConstraints() {
        return this.constraints;
    }

    public final void constraint( Constraint constraint) {
        Intrinsics.checkParameterIsNotNull(constraint,"constraint");
        this.constraints.add(constraint);
    }

    /**
     * todo
     * @param width
     * @param height
     * @param format
     * @param quality
     */
    public void compressionDefault(int width, int height,  CompressFormat format, int quality) {
        Intrinsics.checkParameterIsNotNull(format,"format");
        width = width == 0 ? 612 : width;
        height = height == 0 ? 816 : height;
        quality = quality == 0 ? 80 : quality;
        this.constraint(new DefaultConstraint(width, height, format, quality));
    }
    public void destination(File destination){
        Intrinsics.checkParameterIsNotNull(destination,"destination");
        this.constraint(new DestinationConstraint(destination));
    }

    public void format(CompressFormat format){
        Intrinsics.checkParameterIsNotNull(format, "format");
        constraint(new FormatConstraint(format));
    }
    public void quality(int quality){
        Intrinsics.checkParameterIsNotNull(format, "format");
        constraint(new QualityConstraint(quality));
    }
    public void resolution( int width, int height){
        constraint(new ResolutionConstraint(width, height));
    }
}
