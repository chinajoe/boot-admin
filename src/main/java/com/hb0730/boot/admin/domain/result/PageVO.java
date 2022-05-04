package com.hb0730.boot.admin.domain.result;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type Page vo.
 *
 * @param <T> the type parameter
 * @description:
 * @author: qiaojinfeng3
 * @date: 2022 /5/4 22:12
 */
@Data
public class PageVO<T> implements Serializable {
    /**
     * The Records.
     */
    protected List<T> records;
    /**
     * The Total.
     */
    protected long total;
    /**
     * The Size.
     */
    protected long size;
    /**
     * The Current.
     */
    protected long current;
    /**
     * The Orders.
     */
    protected List<OrderItem> orders;
    /**
     * The Count id.
     */
    protected String countId;
    /**
     * The Max limit.
     */
    protected Long maxLimit;

    /**
     * Instantiates a new Page vo.
     */
    public PageVO() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        this.orders = new ArrayList();
    }

    /**
     * Sets records.
     *
     * @param records the records
     * @return the records
     */
    public PageVO<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    /**
     * Sets total.
     *
     * @param total the total
     * @return the total
     */
    public PageVO<T> setTotal(long total) {
        this.total = total;
        return this;
    }
}
