package top.moon.imiao.common.config;

/**
 * PrefixConfiguration: 前缀配置文件
 *
 * @author xingzhanqi
 * @date 2023/03/12 17:37
 */
public class PrefixConfiguration {
    /*前缀命名的规则由以下部分组成:
    1.变量名都是常量大写,以_链接,例如 SPU
    2.变量名根据对应保存的业务数据javabean定义变量名称,如Spu 对应SPU_ SpuSimpleVO 对应SPU_SIMPLE_VO
    3.变量名最后全部拼接PREFIX 例如 SPU_PREFIX SPU_SIMPLE_VO_PREFIX
    4.变量名在javabean名字和PREFIX之间写业务逻辑 例如Spu锁,SPU_LOCK_PREFIX
    5.变量值都是key前缀,使用_链接,以_结束,就是变量名称小写,去掉prefix,在业务中使用后面拼接唯一值,例如
    Spu的缓存数据 public static final String SPU_PREFIX="spu_"
    6.在特殊情况下,允许业务层自定义key值*/

    //TODO: 前缀未完成
}
