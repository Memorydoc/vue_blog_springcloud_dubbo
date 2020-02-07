package com.kevin.cloud.service.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.IpInfo;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.article.dto.SiColumnDto;
import com.kevin.cloud.commons.dto.article.vo.ArticleVo;
import com.kevin.cloud.commons.dto.search.article.ArticleSearchDto;
import com.kevin.cloud.commons.platform.dto.ESParamDto;
import com.kevin.cloud.commons.platform.dto.FallBackResult;
import com.kevin.cloud.commons.platform.dto.PageResult;
import com.kevin.cloud.commons.platform.dto.ResponseResult;
import com.kevin.cloud.commons.platform.utils.BaseServiceUtils;
import com.kevin.cloud.commons.utils.CommonUtils;
import com.kevin.cloud.commons.utils.DateUtils;
import com.kevin.cloud.commons.utils.UserAgentUtils;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.ESService;
import com.kevin.cloud.provider.api.SiColumnService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.service.IdGenerator;
import com.kevin.cloud.service.fallback.ArticleControllerFallBack;
import com.kevin.cloud.service.help.AuthUserHelperImpl;
import com.kevin.cloud.service.limit.ArticleControllerLimit;
import com.kevin.cloud.user.provider.api.UserService;
import com.kevin.cloud.user.provider.domain.UmsAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: vue-blog-backend
 * @description: 文章接口服务
 * @author: kevin
 * @create: 2020-01-15 14:14
 **/

@RequestMapping("article")
@RestController
public class ArticleController {

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Reference(version = "1.0.0")
    private ArticleService articleService;


    @PostMapping("list")
    public ResponseResult<ArticleDto> list(@RequestBody ArticleVo articleVo) {
        FallBackResult fallBackResult = articleService.articleList(articleVo);
        if (fallBackResult.isStatus()) {
            PageResult pageResult = (PageResult) fallBackResult.getData();
            return new ResponseResult(ResponseResult.CodeStatus.OK, "文章列表查询成功", pageResult);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "文章列表查询失败", null);
        }
    }

    @Reference(version = "1.0.0")
    private SiColumnService siColumnService;

    /**
     * 加载文章分类
     *
     * @return
     */
    @GetMapping("tagListLoad")
    public ResponseResult tagListLoad() {
        List<SiColumnDto> siColumnDtos = siColumnService.tagListLoad();
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", siColumnDtos);
    }

    @GetMapping("viewArticleById/{id}")
    public ResponseResult viewArticleById(@PathVariable("id") Long id) {

        ArticleDto articleDto = articleService.viewArticleById(id);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "成功", articleDto);
    }

    @Reference(version = "1.0.0")
    private UserService userService;
    @Reference(version = "1.0.0")
    private ESService esService;

    @PostMapping("saveArticle")
    public ResponseResult saveArticle(@RequestBody ArticleDto articleDto) throws Exception {
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleDto, siArticle);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UmsAdmin umsAdmin = userService.get(authentication.getName());
        siArticle.setUpdateBy(umsAdmin.getId());
        //先将文章保存到数据库
        int i = articleService.saveArticle(siArticle);
        esService.updateDataById(CommonUtils.beanToJSONObject(siArticle), "article", "item", siArticle.getId().toString());
        return new ResponseResult(ResponseResult.CodeStatus.OK, "修改成功", null);
    }

    @Autowired
    private IdGenerator idGenerator;

    /**
     * 删除文章
     */
    @PostMapping("deleteArticle")
    public ResponseResult deleteArticle(@RequestBody ArticleDto articleDto) {
        List<String> esList = Lists.newArrayList();
        int i = articleService.deleteIdArr(articleDto.getDeleteIdArr());
        // 刪除es 中的文章
        articleDto.getDeleteIdArr().forEach(x -> {
            esList.add(x.toString());
        });
        esService.deleteDataByIdMany("article", "item", esList);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "删除成功", null);
    }

    @Autowired
    private AuthUserHelperImpl authUserHelper;

    /**
     * 添加文章
     */
    @PostMapping("addArticle")
    public ResponseResult addArticle(@RequestBody ArticleVo articleVo) throws Exception {
        ArticleSearchDto articleSearchDto = new ArticleSearchDto();
        BeanUtils.copyProperties(articleVo, articleSearchDto);
        articleSearchDto.setCreateDate(new Date());
        // 将文章保存到es中 方便全文搜索
        String esId = esService.addDataId(CommonUtils.beanToJSONObject(articleSearchDto), "article", "item", idGenerator.nextSid());
        logger.info("文章插入成功,esId =>", esId);
        SiArticle siArticle = new SiArticle();
        BeanUtils.copyProperties(articleVo, siArticle);
        siArticle.setId(idGenerator.nextLid());
        siArticle.setEsId(esId);
        siArticle.setCreateBy(authUserHelper.getCurrentUser().getId());
        siArticle.setCreateDate(new Date());
        int i = articleService.insert(siArticle);
        if (i > 0) {
            System.out.println("插入数据库成功");
        } else {
            System.out.println("插入数据库失败");
        }

        return new ResponseResult(ResponseResult.CodeStatus.OK, "文章添加成功", esId);
    }

    //分页查询文章, 从ES 中查询数据
    @PostMapping("front/queryArticleFromEsByPage")
    public ResponseResult queryArticleFromEsByPage(@RequestBody ArticleVo articleVo) {
        Map resultMap = new ConcurrentHashMap();
        ESParamDto esParamDto = new ESParamDto();
        esParamDto.setHightLight(true);
        esParamDto.setPage(true);
        esParamDto.setPageNum(articleVo.getPageNum());
        esParamDto.setPageSize(articleVo.getPageSize());
        esParamDto.setKeyword(articleVo.getKeywords());
        esParamDto.setMatchField("mc,content");
        esParamDto.setIndex("article");
        esParamDto.setType("item");
        esParamDto.setHightLightField("mc,content");
        esParamDto.setSortField("createDate"); // 按照创建时间排序
        PageResult result = (PageResult) esService.search(esParamDto);
        List<Map> recordList = result.getRecordList();
        recordList.forEach(x -> {
            x.put("createDate", DateUtils.formatDate(new Date((Long) x.get("createDate")), "yyyy-MM-dd HH:mm:ss"));
        });
        result.setRecordList(recordList);
        //查询文章点击状元
        ArticleDto clickTop = articleService.selectClickTop();
        resultMap.put("data", result);
        resultMap.put("clickTop", clickTop);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "ES文章分页查询成功", resultMap);
    }


    /*//分页查询文章, 从ES 中查询数据
    @PostMapping("front/quanwenSearch")
    public ResponseResult quanwenSearch(@RequestBody ArticleVo articleVo) {
        ESParamDto esParamDto = new ESParamDto();
        esParamDto.setHightLight(true);
        esParamDto.setPageNum(articleVo.getPageNum());
        esParamDto.setPageSize(articleVo.getPageSize());
        esParamDto.setPage(true);
        esParamDto.setMatchField("mc,content");
        esParamDto.setIndex("article");
        esParamDto.setType("item");
        esParamDto.setHightLightField("mc,content");
        esParamDto.setKeyword(articleVo.getKeywords());
        List<Map<String, Object>> result = (List<Map<String, Object>>) esService.search(esParamDto);
        PageInfo pageInfo = new PageInfo(result);
        PageResult pageResult = BaseServiceUtils.buildPageResult(pageInfo);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "全文搜索成功", pageResult);
    }*/

    @PostMapping("front/searchArticleByIdFromEs")
    public ResponseResult searchArticleByIdFromEs(@RequestBody ArticleVo articleVo) {
        boolean esUpdate = false;
        ESParamDto esParamDto = new ESParamDto();
        esParamDto.setMatchField("mc,content,createBy,id,category,pl,categoryName,content,createDate,wgrs,titlepic,liks");
        esParamDto.setIndex("article");
        esParamDto.setEsId(articleVo.getEsId());
        esParamDto.setType("item");
        Map<String, Object> result = (Map<String, Object>) esService.searchById(esParamDto);
        result.put("createDate", DateUtils.formatDate(new Date((Long) result.get("createDate")), "yyyy-MM-dd HH:mm:ss"));
        //设置围观人数
        ArticleDto articleDto = articleService.updateWgCount(articleVo);
        //修改es中数据
        if (articleDto != null) {
            esUpdate = esService.doLikeByEsId(articleDto, "article", "item");
        }
        if (!esUpdate) {
            logger.error("围观人数修改失败");
        }

        return new ResponseResult(ResponseResult.CodeStatus.OK, "ES文章分页查询成功", result);
    }

    @GetMapping("front/initTuijian")
    public ResponseResult initTuijian() {
        Map resultMap = new HashMap();
        List<ArticleDto> list = articleService.initTuijian(); //查询特别推荐文章
        resultMap.put("tuijianLists", list);
        // 查询特别推荐所属的文章分类列表
        List<SiColumnDto> siColumnDtos = articleService.tuijianTags();
        resultMap.put("tuijianTags", siColumnDtos);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", resultMap);
    }

    /**
     * @param esId esId
     *             加载上一篇 下一篇文章数据
     */
    @GetMapping("front/loadBeforeAndBack")
    public ResponseResult loadBeforeAndBack(String esId) {
        Map resultMap = new HashMap();
        List<ArticleDto> before = articleService.loadBefore(esId);
        List<ArticleDto> after = articleService.loadAfter(esId);
        if (before.size() > 0) {
            resultMap.put("before", before.get(0));
        }
        if (after.size() > 0) {
            resultMap.put("after", after.get(0));
        }

        return new ResponseResult(ResponseResult.CodeStatus.OK, "", resultMap);
    }

    /**
     * 查询时间轴数据
     */
    @ApiOperation(value = "查询时间轴数据", notes = "查询时间轴数据")
    @PostMapping("front/initTimesData")
    public ResponseResult initTimesData(@RequestBody ArticleVo articleVo) {

        PageResult pageResult = articleService.initTimesData(articleVo);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "", pageResult);
    }

    /**
     * 文章点赞  这里使用sentinel 自动注解方式限流， 下面的是手动方式限流
     */
    @ApiOperation(value = "文章点赞", notes = "文章点赞  这里使用sentinel 自动注解方式限流， 下面的是手动方式限流")
    @GetMapping("front/doLike")
    @SentinelResource(value = "doLike", fallback = "doLikeFallBack",
            fallbackClass = ArticleControllerFallBack.class,
            blockHandler = "doLikeLimit",
            blockHandlerClass = {ArticleControllerLimit.class})
    public ResponseResult doLike(@RequestParam("esId") String esId, HttpServletRequest request) {
        // 点赞的时候，先判断IP是否已经点过赞
        String ipAddr = UserAgentUtils.getIpAddr(request);
        //IpInfo info = UserAgentUtils.getIpInfo(ipAddr);
        boolean esUpdate = false;
        // 修改mysql数据库文章点赞数
        ArticleDto articleDto = articleService.doLikeByEsId(esId);
        //修改es中数据
        if (articleDto != null) {
            esUpdate = esService.doLikeByEsId(articleDto, "article", "item");
        }
        if (esUpdate) {
            return new ResponseResult(ResponseResult.CodeStatus.OK, "点赞成功", null);
        } else {
            return new ResponseResult(ResponseResult.CodeStatus.FAIL, "点赞失败", null);
        }
    }



/**
 * 文章点赞  这里使用sentinel 手动方式降级，会有代码侵入性 使用上面的方法是自动注解限流
 */
    /*@GetMapping("front/doLike")
    public ResponseResult doLike(@RequestParam("esId") String esId, HttpServletRequest request) {
        // 进行手动限流操作
        Entry entry = null;
        // 流控代码
        try {
            entry = SphU.entry("doLike");
            // 点赞的时候，先判断IP是否已经点过赞
            String ipAddr = UserAgentUtils.getIpAddr(request);
            //IpInfo info = UserAgentUtils.getIpInfo(ipAddr);
            boolean esUpdate = false;
            // 修改mysql数据库文章点赞数
            ArticleDto articleDto = articleService.doLikeByEsId(esId);
            //修改es中数据
            if (articleDto != null) {
                esUpdate = esService.doLikeByEsId(articleDto, "article", "item");
            }
            if (esUpdate) {
                return new ResponseResult(ResponseResult.CodeStatus.OK, "点赞成功", null);
            } else {
                return new ResponseResult(ResponseResult.CodeStatus.FAIL, "点赞失败", null);
            }
        } catch (BlockException e) {
            e.printStackTrace();
        }finally {
            if(entry!=null){
                entry.exit();
            }
        }
        return new ResponseResult(ResponseResult.CodeStatus.FAIL, "操作过于频繁", null);
    }*/

    /**
     *  查询本栏推荐
     * @param esId  文章对应的esId
     * @return
     */
    @ApiOperation(value = "查询本栏推荐文章", notes = "当前文章类别，点击量最多的5条作为本栏推荐文章")
    @GetMapping("front/loadCurrentTuijianData")
    public ResponseResult loadCurrentTuijianData(@ApiParam(value = "esId")String esId){
       List<SiArticle> articleDtos =  articleService.loadCurrentTuijianData(esId);
       if(articleDtos != null){
           return  new ResponseResult(ResponseResult.CodeStatus.OK, "", articleDtos);
       }else{
           return  new ResponseResult(ResponseResult.CodeStatus.FAIL, "查询", null);
       }
    }

    @ApiOperation(value = "查询文章页点击排行数据")
    @GetMapping("front/loadClickTops")
    public ResponseResult loadClickTops(){
        List<SiArticle> articles = articleService.loadClickTops();
        return new ResponseResult(ResponseResult.CodeStatus.OK, "查询文章排行数据成功", articles);
    }

    @ApiOperation(value = "加载相关文章数据")
    @GetMapping("front/loadRelativeArticles")
    public ResponseResult loadRelativeArticles(@ApiParam("esId")String esId){
        List<SiArticle> articles = articleService.loadRelativeArticles(esId);
        return new ResponseResult(ResponseResult.CodeStatus.OK, "查询相关文章数据成功", articles);
    }



}