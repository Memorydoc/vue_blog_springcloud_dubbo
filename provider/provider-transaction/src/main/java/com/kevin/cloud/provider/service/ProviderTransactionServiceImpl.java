package com.kevin.cloud.provider.service;

import com.alibaba.nacos.client.utils.StringUtils;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.api.ProviderLogService;
import com.kevin.cloud.provider.api.ProviderTransactionService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.domain.SiComment;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;

/**
 * @Classname ProviderTransactionServiceImpl
 * @Description TODO 全局分布式事务服务
 * @Date 2020/2/28 23:56
 * @Created by kevin
 */


@Service(version = "1.0.0")
public class ProviderTransactionServiceImpl implements ProviderTransactionService {


    @Reference(version = "1.0.0", timeout = 60000)
    private BlogService blogService;


    @Reference(version = "1.0.0", timeout = 60000)
    private ProviderLogService providerLogService;


    @Reference(version = "1.0.0", timeout = 60000)
    private ArticleService articleService;

    /**
     * 测试分布式事务
     *
     * @param umsAdminLoginLog
     * @param siComment
     * @param siArticle
     */
    @GlobalTransactional
    @Override
    public void testTransactinon(UmsAdminLoginLog umsAdminLoginLog, SiComment siComment, SiArticle siArticle) {
        System.out.println("******************************** 全局事务 开始执行 *************************************");
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(siArticle, articleDto);

        articleService.testTransaction(articleDto);

        providerLogService.testTransaction(umsAdminLoginLog);

        blogService.testTransaction(siComment);

        if (StringUtils.equals("1", siArticle.getEsId())) {
            throw new RuntimeException("Exception for seata.");
        }
        System.out.println("******************************** 全局事务 结束 *************************************");
    }
}
