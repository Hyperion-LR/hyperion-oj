package com.hyperionoj.page.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hyperionoj.page.dao.mapper.article.ArticleCommentMapper;
import com.hyperionoj.page.dao.mapper.problem.ProblemCommentMapper;
import com.hyperionoj.page.dao.pojo.article.ArticleComment;
import com.hyperionoj.page.dao.pojo.problem.ProblemComment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Hyperion
 * @date 2021/12/13
 */
@Component
public class ScheduledTask {

    @Resource
    private ProblemCommentMapper problemCommentMapper;

    @Resource
    private ArticleCommentMapper articleCommentMapper;

    /**
     * 定时将评论中`is_delete`字段为1的删除
     */
    @Scheduled(fixedDelay = 48 * 60 * 60 * 1000)
    public void deleteComment() {
        LambdaQueryWrapper<ProblemComment> problemCommentQueryWrapper = new LambdaQueryWrapper<>();
        problemCommentQueryWrapper.eq(ProblemComment::getIsDelete, 1);
        problemCommentMapper.delete(problemCommentQueryWrapper);
        LambdaQueryWrapper<ArticleComment> articleCommentQueryWrapper = new LambdaQueryWrapper<>();
        articleCommentQueryWrapper.eq(ArticleComment::getIsDelete, 1);
        articleCommentMapper.delete(articleCommentQueryWrapper);
    }

}
