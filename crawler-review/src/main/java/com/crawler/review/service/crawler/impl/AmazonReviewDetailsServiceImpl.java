package com.crawler.review.service.crawler.impl;

import com.crawler.common.service.impl.BaseServiceImpl;
import com.crawler.review.entity.crawler.review.AmazonReview;
import com.crawler.review.entity.crawler.review.AmazonReviewDetails;
import com.crawler.review.mapper.crawler.AmazonReviewDetailsMapper;
import com.crawler.review.service.crawler.AmazonReviewDetailsService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 15:47
 * @description:
 **/
@Service
public class AmazonReviewDetailsServiceImpl extends BaseServiceImpl<AmazonReviewDetails> implements AmazonReviewDetailsService {

    @Autowired
    private AmazonReviewDetailsMapper amazonReviewDetailsMapper;

    @Override
    public List<AmazonReviewDetails> checkAmazonReviewDetails(List<AmazonReviewDetails> list, AmazonReview amazonReview) {

        if (CollectionUtils.isNotEmpty(list)) {

            List<String> uuidList = list.stream()
                    .map(AmazonReviewDetails::getAmazonUuid)
                    .collect(Collectors.toList());

            Example example = new Example(AmazonReviewDetails.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("amazonName", amazonReview.getAmazonName());
            criteria.andEqualTo("site", amazonReview.getAmazonSite());
            criteria.andEqualTo("asin", amazonReview.getAsin());
            criteria.andIn("amazonUuid", uuidList);

            List<AmazonReviewDetails> resultList = this.amazonReviewDetailsMapper.selectByExample(example);

            if (CollectionUtils.isNotEmpty(resultList)) {
                list = list.stream()
                        .filter(item -> !resultList.contains(item))
                        .collect(Collectors.toList());
            }
        }

        return list;
    }
}
