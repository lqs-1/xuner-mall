package com.lqs.mall.common.utils.pagination;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李奇凇
 * @date 2022年07月30日 下午10:44
 * @do 封装分页结果集
 */
@ToString
public class PageUtils implements Serializable {
    private int currentPage; // 当前页数
    private int pageSize; // 每页多少个
    private int totalSize; // 总共多少条数据
    private int totalPage; // 总页数
    private List<?> resultList; // 当前页数据


    public PageUtils(IPage<?> page) {
        this.currentPage = (int) page.getCurrent();
        this.pageSize = (int) page.getSize();
        this.totalPage = (int) page.getPages();
        this.totalSize = (int) page.getTotal();
        this.resultList = page.getRecords();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public List<?> getResultList() {
        return resultList;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setResultList(List<?> resultList) {
        this.resultList = resultList;
    }
}
