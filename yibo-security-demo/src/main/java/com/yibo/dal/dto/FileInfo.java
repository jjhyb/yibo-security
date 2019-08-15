package com.yibo.dal.dto;

/**
 * @author: huangyibo
 * @Date: 2019/7/27 18:54
 * @Description:
 */
public class FileInfo {

    public FileInfo(String path){
        this.path = path;
    }

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
