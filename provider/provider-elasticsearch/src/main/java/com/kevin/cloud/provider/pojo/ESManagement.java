package com.kevin.cloud.provider.pojo;

import com.kevin.cloud.provider.api.ESService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: vue-blog-backend
 * @description:
 * @author: kevin
 * @create: 2020-01-22 21:55
 **/
@Component
public class ESManagement {
    private Map<String, BaseESDto> processorMap = new HashMap<>();


    public Map<String, BaseESDto> getProcessorMap() {
        return processorMap;
    }

    public void setProcessorMap(Map<String, BaseESDto> processorMap) {
        this.processorMap = processorMap;
    }
}
