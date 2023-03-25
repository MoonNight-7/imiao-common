package top.moon.imiao.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.moon.imiao.common.exception.ServiceException;
import top.moon.imiao.common.restful.JsonResult;
import top.moon.imiao.common.restful.ResponseCode;

/**
 * GlobalControllerExceptionHandler:
 *
 * @author xingzhanqi
 * @date 2023/03/12 16:20
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    /**
     * GlobalControllerExceptionHandler: 处理业务异常
     *
     * @param [top.moon.dhc_common.exception.ServiceException]
     * @author xingzhanqi
     * @date 2023年03月15日 13:03:38
     * @ReturnType: top.moon.dhc_common.restful.JsonResult<java.lang.Void>
     */
    @ExceptionHandler(ServiceException.class)
    public JsonResult<Void> handleServiceException(ServiceException e) {
        log.debug("出现业务异常，业务错误码={}，描述文本={}", e.getResponseCode().getValue(), e.getMessage());
        e.printStackTrace();
        JsonResult<Void> result = JsonResult.failed(e);
        log.debug("即将返回：{}", result);
        return result;
    }

    /**
     * GlobalControllerExceptionHandler: 处理绑定异常（通过Validation框架验证请求参数时的异常）
     * @author xingzhanqi
     * @date 2023年03月15日 13:03:23
     * @param [org.springframework.validation.BindException]
     * @ReturnType: top.moon.dhc_common.restful.JsonResult<java.lang.Void>
     */
    @ExceptionHandler(BindException.class)
    public JsonResult<Void> handleBindException(BindException e) {
        log.debug("验证请求数据时出现异常：{}", e.getClass().getName());
        e.printStackTrace();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        JsonResult<Void> result = JsonResult.failed(ResponseCode.BAD_REQUEST, message);
        log.debug("即将返回：{}", result);
        return result;
    }


    /**
     * GlobalControllerExceptionHandler: 处理系统（其它）异常
     * @author xingzhanqi
     * @date 2023年03月15日 13:03:38
     * @param [java.lang.Throwable]
     * @ReturnType: top.moon.dhc_common.restful.JsonResult<java.lang.Void>
     */
    @ExceptionHandler({Throwable.class})
    public JsonResult<Void> handleSystemError(Throwable e) {
        log.debug("出现系统异常，异常类型={}，描述文本={}", e.getClass().getName(), e.getMessage());
        e.printStackTrace();
        JsonResult<Void> result = JsonResult.failed(ResponseCode.INTERNAL_SERVER_ERROR, e);
        log.debug("即将返回：{}", result);
        return result;
    }
}
