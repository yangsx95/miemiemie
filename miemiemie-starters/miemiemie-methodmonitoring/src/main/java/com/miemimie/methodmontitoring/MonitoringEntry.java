package com.miemimie.methodmontitoring;

/**
 * 监控数据条目
 *
 * @param id            id
 * @param traceId       全局链路id
 * @param desc          描述
 * @param success       是成功
 * @param level         条目等级
 * @param startTime     开始时间
 * @param endTime       结束时间
 * @param paramMessage  入参
 * @param returnMessage 返回
 * @param errorMessage  错误信息
 * @param hostInfo      当前主机信息
 * @param typeInfo      具体类型的额外信息
 * @author yangshunxiang
 * @since 2024/1/23
 */
public record MonitoringEntry<T extends Type>(
        String id,
        String traceId,
        String desc,
        boolean success,
        Integer level,
        long startTime,
        long endTime,
        String paramMessage,
        String returnMessage,
        String errorMessage,
        HostInfo hostInfo,
        T typeInfo
) {
}
