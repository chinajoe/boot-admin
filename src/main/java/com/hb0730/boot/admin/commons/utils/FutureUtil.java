package com.hb0730.boot.admin.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * FutureUtil
 * 异步处理工具类
 *
 * @author qiaojinfeng3
 * @date 2020 /1/13 17:01
 */
@Slf4j
public class FutureUtil {
    /**
     * Suppliers async list.
     *
     * @param <T>       the type parameter
     * @param suppliers the suppliers
     * @return the list
     */
    public static <T> List<T> suppliersAsync(Collection<Supplier<T>> suppliers) {
        // supplier封装成CompletableFuture<T>
        List<CompletableFuture<T>> futureList = suppliers.stream()
                .map(supplier -> CompletableFuture.supplyAsync(supplier)
                        .handleAsync((res, ex) -> {
                            if (null != ex) {
                                log.error("", ex);
                            }
                            return res;
                        })
                ).collect(Collectors.toList());

        /* 组合CompletableFuture */
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{}));

        /* 组合执行结果 */
        CompletableFuture<List<T>> resultCompletableFuture = allOfFuture.thenApplyAsync(v -> futureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));

        /* 阻塞获取结果 */
        try {
            return resultCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    /**
     * Suppliers async list.
     *
     * @param <T>       the type parameter
     * @param suppliers the suppliers
     * @param executor  the executor
     * @return the list
     */
    public static <T> List<T> suppliersAsync(Collection<Supplier<T>> suppliers, Executor executor) {
        // supplier封装成CompletableFuture<T>
        List<CompletableFuture<T>> futureList = suppliers.stream()
                .map(supplier -> CompletableFuture.supplyAsync(supplier, executor)
                        .handleAsync((res, ex) -> {
                            if (null != ex) {
                                log.error("", ex);
                            }
                            return res;
                        })
                ).collect(Collectors.toList());

        /* 组合CompletableFuture */
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{}));

        /* 组合执行结果 */
        CompletableFuture<List<T>> resultCompletableFuture = allOfFuture
                .thenApplyAsync(v -> futureList.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()),
                        executor);

        /* 阻塞获取结果 */
        try {
            return resultCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    /**
     * Suppliers async list.
     *
     * @param <T>       the type parameter
     * @param executor  the executor
     * @param suppliers the suppliers
     * @return the list
     */
    public static <T> List<T> suppliersAsync(Executor executor, Supplier<T>... suppliers) {
        // supplier封装成CompletableFuture<T>
        List<CompletableFuture<T>> futureList = Arrays.stream(suppliers)
                .map(supplier -> CompletableFuture.supplyAsync(supplier, executor)
                        .handleAsync((res, ex) -> {
                            if (null != ex) {
                                log.error("", ex);
                            }
                            return res;
                        })
                ).collect(Collectors.toList());

        /* 组合CompletableFuture */
        CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{}));

        /* 组合执行结果 */
        CompletableFuture<List<T>> resultCompletableFuture = allOfFuture.thenApplyAsync(v ->
                futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()), executor);

        /* 阻塞获取结果 */
        try {
            return resultCompletableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("", e);
        }
        return Collections.emptyList();
    }

    /**
     * Run async.
     *
     * @param runnable the runnable
     * @param executor the executor
     */
    public static void runAsync(Runnable runnable, Executor executor) {
        CompletableFuture.runAsync(runnable, executor)
                .exceptionally(ex -> {
                    log.error("", ex);
                    return null;
                });
    }

    /**
     * Run async.
     *
     * @param runnable the runnable
     */
    public static void runAsync(Runnable runnable) {
        CompletableFuture.runAsync(runnable)
                .exceptionally(ex -> {
                    log.error("", ex);
                    return null;
                });
    }

    /**
     * Supply async completable future.
     *
     * @param <U>      the type parameter
     * @param supplier the supplier
     * @param executor the executor
     * @return the completable future
     */
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor)
                .exceptionally(ex -> {
                    log.error("", ex);
                    return null;
                });
    }

    /**
     * Supply async completable future.
     *
     * @param <U>      the type parameter
     * @param supplier the supplier
     * @return the completable future
     */
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier)
                .exceptionally(ex -> {
                    log.error("", ex);
                    return null;
                });
    }
}
