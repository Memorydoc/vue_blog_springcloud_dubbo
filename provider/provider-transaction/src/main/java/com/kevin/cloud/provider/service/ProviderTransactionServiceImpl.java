package com.kevin.cloud.provider.service;

import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.provider.IdProviderGenerator;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname ProviderTransactionServiceImpl
 * @Description TODO 全局分布式事务服务
 * @Date 2020/2/28 23:56
 * @Created by kevin
 */


@Service(version = "1.0.0")
public class ProviderTransactionServiceImpl implements ProviderTransactionService {


    @Autowired
    private IdProviderGenerator idProviderGenerator;


    @Reference(version = "1.0.0", timeout = 60000)
    private ArticleService articleService;

    @Reference(version = "1.0.0", timeout = 60000)
    private BlogService blogService;

    @Reference(version = "1.0.0", timeout = 60000)
    private ProviderLogService providerLogService;

    /**
     * 测试分布式事务
     */
    @GlobalTransactional
    @Override
    public void testTransactinon(String esId) {
        UmsAdminLoginLog umsAdminLoginLog = new UmsAdminLoginLog();
        umsAdminLoginLog.setId(idProviderGenerator.nextLid());
        umsAdminLoginLog.setAddress("测试分布式事务日志");
        SiComment siComment = new SiComment();
        siComment.setId(idProviderGenerator.nextLid());
        siComment.setPlnr("测试分布式定时任务的评论");
        SiArticle siArticle = new SiArticle();
        siArticle.setId(idProviderGenerator.nextLid());
        siArticle.setMc("测试评论内容的文章");
        siArticle.setEsId(esId);
        System.out.println("******************************** 全局事务 开始执行 *************************************");
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(siArticle, articleDto);
        articleService.testTransaction(articleDto);
        providerLogService.testTransaction(umsAdminLoginLog);
        blogService.testTransaction(siComment, esId);

     /*   if (StringUtils.equals("1", esId)) {
            System.out.println(1 / 0);
        }*/


        System.out.println("******************************** 全局事务 结束 *************************************");
    }
}
