package com.kevin.cloud.provider.service;

import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;
import com.kevin.cloud.provider.domain.SiColumn;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import javax.annotation.Resource;
import com.kevin.cloud.provider.mapper.SiColumnMapper;
import com.kevin.cloud.provider.api.SiColumnService;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ProjectName:    vue-blog-backend 
 * @Package:        ${PACKAGE_NAME}
 * @ClassName:      ${NAME}
 * @Author:     kevin
 * @Description:  ${description}  
 * @Date:    2020/1/31 13:03
 * @Version:    1.0
 */
@Service(version = "1.0.0")
public class SiColumnServiceImpl implements SiColumnService{

    @Resource
    private SiColumnMapper siColumnMapper;

    @Override
    public List<SiColumnDto> tagListLoad() {
        Example example = new Example(SiColumn.class);
        List<SiColumn> siColumns = siColumnMapper.selectByExample(example);
        List<SiColumnDto> resultDto = Lists.newArrayList();
        siColumns.forEach(x->{
            SiColumnDto siColumnDto = new SiColumnDto();
            BeanUtils.copyProperties(x, siColumnDto);
            resultDto.add(siColumnDto);
        });
        return resultDto;
    }
}
