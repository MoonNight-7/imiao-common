package top.moon.imiao.common.restful;

/**
 * ResponseCode:
 *
 * @author xingzhanqi
 * @date 2023/03/12 13:03
 */
public enum ResponseCode {
    /**
     * 成功
     */
    OK(200),
    /**
     * 错误请求
     */
    BAD_REQUEST(400),
    /**
     * 未授权
     */
    UNAUTHORIZED(401),
    /**
     * 禁止
     */
    FORBIDDEN(403),
    /**
     * 未找到
     */
    NOT_FOUND(404),
    /**
     * 不接受
     */
    NOT_ACCEPTABLE(406),
    /**
     * 冲突
     */
    CONFLICT(409),
    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500);

    private Integer value;

    ResponseCode(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
