package com.crawler.annotation;

import com.crawler.config.RedisConf;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by acer on 2019/2/6.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisConf.class})
public @interface EnableRedis {
}
