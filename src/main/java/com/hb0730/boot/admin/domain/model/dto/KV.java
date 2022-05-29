package com.hb0730.boot.admin.domain.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * The type Kv.
 *
 * @param <M> the type parameter
 * @param <N> the type parameter
 * @description:
 * @author: qiaojinfeng3
 * @date: 2021 /7/29 17:38
 */
@Data
@NoArgsConstructor
public class KV<M, N> implements Serializable {
    private static final long serialVersionUID = -4157337732452502725L;
    private M key;
    private N value;

    /**
     * Instantiates a new Kv.
     *
     * @param key   the key
     * @param value the value
     */
    public KV(M key, N value) {
        this.key = key;
        this.value = value;
    }
}
