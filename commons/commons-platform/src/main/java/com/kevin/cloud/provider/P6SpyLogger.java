package com.kevin.cloud.provider;

import com.p6spy.engine.common.P6Util;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.text.SimpleDateFormat;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider
 * @ClassName: P6SpyLogger
 * @Author: kevin
 * @Description: 自定义P6spy sql监控 日志格式
 * @Date: 2020/2/8 13:20
 * @Version: 1.0
 */
public class P6SpyLogger implements MessageFormattingStrategy {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        StringBuilder sb = new StringBuilder();
        sb
                .append("=====================================================\n")
                .append("连接id：").append(connectionId).append("\n")
                .append("当前时间：").append(now).append("\n")
                .append("类别：").append(category).append("\n")
                .append("花费时间：").append(elapsed).append("\n")
//                .append("url：").append(url).append("\n")
                .append("预编译sql：").append(P6Util.singleLine(prepared)).append("\n")
                .append("最终执行的sql：").append(P6Util.singleLine(sql))
                .append("\n=====================================================\n");
        return sb.toString();
    }

}