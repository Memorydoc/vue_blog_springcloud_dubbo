package com.kevin.cloud.provider.api;

import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;
import com.kevin.cloud.provider.domain.SiColumn;

import java.util.List;

/**
 * @ProjectName: vue-blog-backend
 * @Package: ${PACKAGE_NAME}
 * @ClassName: ${NAME}
 * @Author: kevin
 * @Description: ${description}
 * @Date: 2020/1/31 13:03queryArticleFromEsByPage
 * @Version: 1.0
 */
public interface SiColumnService {
    List<SiColumnDto> tagListLoad();

    SiColumn selectPersionCategory();
}
