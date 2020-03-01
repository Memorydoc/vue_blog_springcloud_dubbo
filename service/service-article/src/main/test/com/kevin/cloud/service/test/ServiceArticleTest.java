package com.kevin.cloud.service.test;

import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.provider.api.ProviderTransactionService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.domain.SiComment;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;
import com.kevin.cloud.service.IdGenerator;
import com.kevin.cloud.service.controller.ArticleController;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Classname ServiceArticleTest
 * @Description TODO
 * @Date 2020/2/25 19:10
 * @Created by kevin
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceArticleTest {


    @Autowired
    private ArticleController articleController;

    @Autowired
    private IdGenerator idGenerator;



    @Reference(version = "1.0.0", timeout =  60000)
    private ProviderTransactionService providerTransactionService;

    @Test
    public void testTranction() {
    }
}
