package cc.buddies.cleanarch.domain.request;

import java.io.Serializable;

public class NeteaseNewsParams implements Serializable {

    private int page;
    private int count;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
