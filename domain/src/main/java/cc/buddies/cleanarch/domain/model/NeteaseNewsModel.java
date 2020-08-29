package cc.buddies.cleanarch.domain.model;

import java.io.Serializable;

public class NeteaseNewsModel implements Serializable {

    private String title;
    private String image;
    private String path;
    private String passtime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPasstime() {
        return passtime;
    }

    public void setPasstime(String passtime) {
        this.passtime = passtime;
    }
}
