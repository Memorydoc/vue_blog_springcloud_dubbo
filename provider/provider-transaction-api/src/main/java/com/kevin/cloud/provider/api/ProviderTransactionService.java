package com.kevin.cloud.provider.api;

import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.domain.SiComment;
import com.kevin.cloud.provider.domain.UmsAdminLoginLog;

/**
 * @Classname ProviderTransactionService
 * @Description TODO
 * @Date 2020/2/28 23:58
 * @Created by kevin
 */

public interface ProviderTransactionService {

    /**
     *  测试分布式事务
     * @param umsAdminLoginLog
     * @param siComment
     * @param siArticle
     */
    public void testTransactinon(UmsAdminLoginLog umsAdminLoginLog, SiComment siComment, SiArticle siArticle);

}
