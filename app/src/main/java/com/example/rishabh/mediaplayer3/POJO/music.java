package com.example.rishabh.mediaplayer3.POJO;

/**
 * Created by RISHABH on 23-07-2017.
 */

public class music {
    String currenttitle;
    String currentartist;
    String Path;


    public String getPath() {
        return Path;
    }

    public String getCurrenttitle() {
        return currenttitle;
    }

    public String getCurrentartist() {
        return currentartist;
    }

    public music(String currenttitle, String currentartist,String Path) {

        this.currenttitle = currenttitle;
        this.currentartist = currentartist;
        this.Path =Path;
    }
}
