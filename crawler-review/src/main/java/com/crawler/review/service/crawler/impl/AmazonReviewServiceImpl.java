package com.crawler.review.service.crawler.impl;

import com.crawler.common.service.impl.BaseServiceImpl;
import com.crawler.review.entity.crawler.review.AmazonReview;
import com.crawler.review.mapper.crawler.AmazonReviewMapper;
import com.crawler.review.service.crawler.AmazonReviewService;
import com.crawler.util.DateUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @program: aukey-customer-service
 * @author: wgf
 * @create: 2019-01-28 15:44
 * @description:
 **/
@Service
public class AmazonReviewServiceImpl extends BaseServiceImpl<AmazonReview> implements AmazonReviewService {

    @Autowired
    private AmazonReviewMapper amazonReviewMapper;

    @Override
    public List<AmazonReview> selectAmazonReviewByStatus(String status) {
        Example example = new Example(AmazonReview.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("reviewStatus", status);

        // 至少获取2个小时前更新过的数据
        Date updateDate = DateUtil.dateOperation(new Date(), -2, Calendar.HOUR);
        criteria.andLessThanOrEqualTo("updateDate", updateDate);
        example.orderBy("updateDate").asc();

        PageHelper.offsetPage(0, 20,false);
        return amazonReviewMapper.selectByExample(example);
    }
}
