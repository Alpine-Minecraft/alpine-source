package me.alpine.util;

import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@UtilityClass
public final class FileUtil {
    public ArrayList<String> readLines(final File file) {
        final ArrayList<String> lines = new ArrayList<>();
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
