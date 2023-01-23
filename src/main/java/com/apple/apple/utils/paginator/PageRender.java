package com.apple.apple.utils.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;
    private Page<T> page;

    private int totalPages;
    //size = elementos por pagina
    private int size;

    private int currentPage;

    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        paginas = new ArrayList<>();

        size = page.getSize();
        totalPages = page.getTotalPages();
        currentPage = page.getNumber() + 1;

        int desde, hasta;
        if (totalPages <= size) {
            desde = 1;
            hasta = totalPages;
        } else {
            if (currentPage <= size / 2) {
                desde = 1;
                hasta = size;
            } else if (currentPage >= totalPages - size / 2) {
                desde = totalPages - size + 1;
                hasta = size;
            } else {
                desde = currentPage - size/2;
                hasta = size;
            }
        }
        for (int i=0; i<hasta;i++) {
            paginas.add(new PageItem(desde + i,currentPage == desde + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public Page<T> getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getSize() {
        return size;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isHasNext() {
        return page.hasNext();
    }

    public boolean isHasPrevious() {
        return page.hasPrevious();
    }
}
