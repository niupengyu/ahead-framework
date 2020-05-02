package com.github.niupengyu.core.filter;

import java.io.File;
import java.io.FilenameFilter;

public class FileFilterForName implements FilenameFilter {

    private String end;

    public FileFilterForName(String end){
        this.end=end;
    }

    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(end)) {
            return true;
        }
        return false;
    }
}
