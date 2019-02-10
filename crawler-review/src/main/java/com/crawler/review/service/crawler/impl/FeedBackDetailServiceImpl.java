package com.crawler.review.service.crawler.impl;

import com.crawler.common.service.impl.BaseServiceImpl;
import com.crawler.review.entity.crawler.feedback.FeedBackDetail;
import com.crawler.review.mapper.crawler.FeedBackDetailMapper;
import com.crawler.review.service.crawler.FeedBackDetailService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-22 15:15
 * @description:
 **/
@Service
public class FeedBackDetailServiceImpl extends BaseServiceImpl<FeedBackDetail> implements FeedBackDetailService {
    @Autowired
    private FeedBackDetailMapper feedBackDetailMapper;

    @Override
    public List<FeedBackDetail> checkFeedBackDetail(List<FeedBackDetail> feedBackDetailList) {

        List<FeedBackDetail> filterList = null;
        if (CollectionUtils.isNotEmpty(feedBackDetailList)) {

            List<String> contentList = feedBackDetailList.stream()
                    .map(FeedBackDetail::getContent)
                    .collect(Collectors.toList());

            Example example = new Example(FeedBackDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("content", contentList);
            List<FeedBackDetail> resultList = feedBackDetailMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(resultList)) {
                filterList = feedBackDetailList.stream()
                        .filter(item -> !resultList.contains(item))
                        .collect(Collectors.toList());
            }
        }

        return filterList;
    }

    @Override
    public void deleteFeedBackByContent(List<String> contentList) {

        if (CollectionUtils.isNotEmpty(contentList)) {
            Example example = new Example(FeedBackDetail.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("content", contentList);

            this.feedBackDetailMapper.deleteByExample(example);
        }
    }
}
