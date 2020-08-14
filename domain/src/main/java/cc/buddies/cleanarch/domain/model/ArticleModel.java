package cc.buddies.cleanarch.domain.model;

import java.io.Serializable;

public class ArticleModel implements Serializable {

            /*
            "apkLink": "",
            "audit": 1,
            "author": "",
            "canEdit": false,
            "chapterId": 502,
            "chapterName": "自助",
            "collect": false,
            "courseId": 13,
            "desc": "",
            "descMd": "",
            "envelopePic": "",
            "fresh": false,
            "id": 14704,
            "link": "https://www.jianshu.com/p/dc75ae526dab",
            "niceDate": "2020-08-10 17:09",
            "niceShareDate": "2020-08-10 17:09",
            "origin": "",
            "prefix": "",
            "projectLink": "",
            "publishTime": 1597050572000,
            "realSuperChapterId": 493,
            "selfVisible": 0,
            "shareDate": 1597050572000,
            "shareUser": "彭旭锐",
            "superChapterId": 494,
            "superChapterName": "广场Tab",
            "tags": [ ],
            "title": "密码学 | 什么是散列算法？",
            "type": 0,
            "userId": 30587,
            "visible": 1,
            "zan": 0
            */

    private int id;
    private String author;
    private int chapterId;
    private String chapterName;

    private String link;

    private String niceDate;
    private String niceShareDate;
    private long shareDate;
    private String shareUser;

    private long publishTime;

    private String title;
    private int type;
    private int userId;
    private int zan;
    private boolean collect;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getNiceShareDate() {
        return niceShareDate;
    }

    public void setNiceShareDate(String niceShareDate) {
        this.niceShareDate = niceShareDate;
    }

    public long getShareDate() {
        return shareDate;
    }

    public void setShareDate(long shareDate) {
        this.shareDate = shareDate;
    }

    public String getShareUser() {
        return shareUser;
    }

    public void setShareUser(String shareUser) {
        this.shareUser = shareUser;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getZan() {
        return zan;
    }

    public void setZan(int zan) {
        this.zan = zan;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
    }
}
