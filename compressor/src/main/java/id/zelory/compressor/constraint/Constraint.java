package id.zelory.compressor.constraint;

import java.io.File;

/**
 * Created on : January 24, 2020
 * Author     : zetbaitsu
 * Name       : Zetra
 * GitHub     : https://github.com/zetbaitsu
 */
public interface Constraint {
    boolean isSatisfied(File file);
    File satisfy(File file);
}