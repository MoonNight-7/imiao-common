package top.moon.imiao.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * CommonConfiguration:
 *
 * @author xingzhanqi
 * @date 2023/03/12 17:15
 */
@Configuration
@ComponentScan({
        "top.moon.imiao.common.exception.handler",
        "top.moon.imiao.common.utils"
})
public class CommonConfiguration {
}
