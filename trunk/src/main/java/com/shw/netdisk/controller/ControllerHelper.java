package com.shw.netdisk.controller;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

public interface ControllerHelper {

    static final DateTimeFormatter GENERIC_TIMEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    default List list(Object... objs){
        return Arrays.asList(objs);
    }

    default Map map(Object k, Object v) {
        return ImmutableMap.of(k, v);
    }

    default Map map(Object k1, Object v1, Object k2, Object v2) {
        return ImmutableMap.of(k1, v1, k2, v2);
    }

    default Map map(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3);
    }

    default Map map(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3,
                    Object k4, Object v4) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    default Map map(Object k1, Object v1, Object k2, Object v2, Object k3, Object v3,
                    Object k4, Object v4, Object k5, Object v5) {
        return ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    default <T> CompletableFuture<T> execute(Supplier<T> supplier){
        return CompletableFuture.supplyAsync(supplier);
    }

    default  <T extends  Object> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().map(future -> future.join()).collect(Collectors.<T>toList())
        );
    }

    default boolean empty(Object... objs){
        return Arrays.stream(objs).anyMatch(obj->obj==null);
    }

    default Object getOrDefault(Map map, String key, Object defaultValue) {
        return map == null ? defaultValue : map.getOrDefault(key, defaultValue);
    }
}
