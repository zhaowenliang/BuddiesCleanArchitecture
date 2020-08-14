package cc.buddies.cleanarch.data.http.model;

import java.io.Serializable;
import java.util.List;

public class PageModel<T> implements Serializable {

    private int curPage;    // 页码
    private int offset;     // 偏移
    private int size;       // 当前页数量
    private int pageCount;  // 总页数
    private int total;      // 总数量

    private boolean over;   // 是否已经结束

    private List<T> datas;  // 数据列表

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
