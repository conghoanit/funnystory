package com.hoanpc.apptruyencuoi;

public class Topic {
    private String imgPath,name;

    public Topic(String imgPath, String name) {
        this.imgPath = imgPath;
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
