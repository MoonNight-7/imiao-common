package top.moon.imiao.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.moon.imiao.common.restful.ResponseCode;

/**
 * ServiceException: 业务异常
 *
 * @author xingzhanqi
 * @date 2023/03/12 13:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ServiceException extends RuntimeException {
    private ResponseCode responseCode;

    /**
     * ServiceException:
     *
     * @param responseCode  message
     * @author xingzhanqi
     * @date 2023/3/12 17:31
     * @ReturnType:
     */
    public ServiceException(ResponseCode responseCode, String message) {
        super(message);
        setResponseCode(responseCode);
    }

}
