package com.kevin.cloud.provider.service;

import com.google.common.collect.Lists;
import com.kevin.cloud.commons.dto.article.dto.ArticleDto;
import com.kevin.cloud.commons.dto.blog.dto.CommentDto;
import com.kevin.cloud.commons.dto.blog.dto.TypeViewDto;
import com.kevin.cloud.commons.dto.blog.vo.CommentVo;
import com.kevin.cloud.provider.IdProviderGenerator;
import com.kevin.cloud.provider.api.ArticleService;
import com.kevin.cloud.provider.api.BlogService;
import com.kevin.cloud.provider.domain.SiArticle;
import com.kevin.cloud.provider.domain.SiColumn;
import com.kevin.cloud.provider.domain.SiComment;
import com.kevin.cloud.provider.mapper.SiColumnMapper;
import com.kevin.cloud.provider.mapper.SiCommentMapper;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ProjectName: vue-blog-backend
 * @Package: com.kevin.cloud.provider.service
 * @ClassName: BlogServiceImpl
 * @Author: kevin
 * @Description: 博客统一服务接口
 * @Date: 2020/2/3 16:50
 * @Version: 1.0
 */
@Service(version = "1.0.0")
public class BlogServiceImpl implements BlogService {

    @Autowired
    private SiColumnMapper siColumnMapper;


    @Reference(version = "1.0.0")
    private ArticleService articleService;


    @Override
    public List<TypeViewDto> initTypesData() {
        Example exampleType = new Example(SiColumn.class);
        List<TypeViewDto> listTypeViews = Lists.newArrayList();
        List<SiColumn> siColumns = siColumnMapper.selectByExample(exampleType);
        List<SiArticle> siArticles = articleService.selectLists();
        List<ArticleDto> listArticleDtos = Lists.newArrayList();
        siArticles.forEach(x -> {
            ArticleDto articleDto = new ArticleDto();
            BeanUtils.copyProperties(x, articleDto);
            listArticleDtos.add(articleDto);
        });
        Map<Long, List<ArticleDto>> collect = listArticleDtos.stream().collect(Collectors.groupingBy(ArticleDto::getCategory));
        siColumns.forEach(x -> {
            TypeViewDto typeViewDto = new TypeViewDto();
            BeanUtils.copyProperties(x, typeViewDto);
            typeViewDto.setArticleDtoList(collect.get(x.getId()));
            listTypeViews.add(typeViewDto);
        });
        return listTypeViews;
    }

    @Autowired
    private SiCommentMapper siCommentMapper;

    @Override
    public List<CommentDto> initCommentData(Integer isReply) {
        List<CommentDto> listParent = siCommentMapper.initCommentParentData(isReply);

        List<CommentDto> listChildren = siCommentMapper.initCommentChildrenData(isReply);
        // 对评论数据进行加工处理
        listParent.forEach(x -> {
            getChildrenComment(x, listChildren);
        });
        return listParent;
    }

    @Autowired
    private IdProviderGenerator idProviderGenerator;

    @Override
    public boolean commitComment(CommentVo commentVo) {
        SiComment siComment = new SiComment();
        BeanUtils.copyProperties(commentVo, siComment);
        siComment.setId(idProviderGenerator.nextLid());
        String[] split = siComment.getPlnr().split(":");
        if(split.length > 1){
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < split.length; i++) {
                if(i != 0){
                    stringBuffer.append(split[i]);
                }
            }
            siComment.setPlnr(stringBuffer.toString());
        }
        siComment.setPlsj(new Date());
        int insertCount = siCommentMapper.insertSelective(siComment);
        if (insertCount > 0) {
            return true;
        }
        return false;
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int commentLiks(String commentId) {
        String zanCommentSql = "UPDATE si_comment SET liks = liks + 1 WHERE id = ?";
        return  jdbcTemplate.update(zanCommentSql, commentId);
    }

    // 递归评论
    private void getChildrenComment(CommentDto pComment, List<CommentDto> comments) {
        List<CommentDto> replyList = null;
        //子菜单
        for (CommentDto commentDto : comments) {
            if (commentDto.getPid() == null) continue;
            if (commentDto.getPid().equals(pComment.getId())) {
                //commentDto.setReplyName(pComment.getReplyName());
                if (pComment.getReply() != null) {
                    replyList = pComment.getReply();
                    replyList.add(commentDto);
                } else {
                    List<CommentDto> li = new ArrayList<>();
                    li.add(commentDto);
                    pComment.setReply(li);
                }
                getChildrenComment(commentDto, comments);
                continue;
            }
        }
    }


}
