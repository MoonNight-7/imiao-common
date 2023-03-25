package top.moon.imiao.common.utils;

import cn.hutool.core.lang.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IdWorkerConfiguration: 雪花算法配置
 *
 * @author xingzhanqi
 * @date 2023/03/25 14:51
 */

@Configuration
public class IdWorkerConfiguration {

    @Bean
    public Snowflake snowflake(){
        return new Snowflake();
    }
}

