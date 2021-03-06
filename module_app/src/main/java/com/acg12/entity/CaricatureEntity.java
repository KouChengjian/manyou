package com.acg12.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/12/29 14:18
 * Description:
 */
public class CaricatureEntity {

    private int comicId;
    private int type; // 1:酷克
    private String cover;
    private String title;
    private int isCollect; // 是否收藏
    private List<CaricatureChaptersEntity> chaptersList = new ArrayList<>();


    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CaricatureChaptersEntity> getChaptersList() {
        return chaptersList;
    }

    public void setChaptersList(List<CaricatureChaptersEntity> chaptersList) {
        this.chaptersList = chaptersList;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }
}
