package com.kevin.cloud.provider.service;

import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.article.dto.SiFinkDto;
import com.kevin.cloud.provider.api.SiFinkService;
import com.kevin.cloud.provider.domain.SiFlink;
import com.kevin.cloud.provider.mapper.SiFlinkMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.service
 * @ClassName: SiFlinkServiceImpl
 * @Author: kevin
 * @Description:
 * @Date: 2020/1/31 23:10
 * @Version: 1.0
 */
@Service(version = "1.0.0")
public class SiFlinkServiceImpl implements SiFinkService {


    @Autowired
    private SiFlinkMapper siFlinkMapper;
    @Override
    public List<SiFinkDto> initLinks() {
        List<SiFinkDto> list = Lists.newArrayList();
        Example example = new Example(SiFlink.class);
        List<SiFlink> siFlinks = siFlinkMapper.selectByExample(example);
        siFlinks.forEach(x->{
            SiFinkDto siFinkDto = new SiFinkDto();
            BeanUtils.copyProperties(x, siFinkDto);
            list.add(siFinkDto);
        });

        return list;
    }
}
