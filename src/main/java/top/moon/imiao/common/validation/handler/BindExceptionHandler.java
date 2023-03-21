package top.moon.imiao.common.validation.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.moon.imiao.common.restful.JsonResult;
import top.moon.imiao.common.restful.ResponseCode;

/**
 * BindExceptionHandler:
 *
 * @author xingzhanqi
 * @date 2023/03/12 17:47
 */
@RestControllerAdvice
@Slf4j
public class BindExceptionHandler {
    /**
     * BindExceptionHandler: 绑定异常处理
     * @author xingzhanqi
     * @date 2023/3/12 17:48 
     * @param 
     * @ReturnType: 
     */
    @ExceptionHandler(BindException.class)
    public JsonResult handleBindException(BindException e) {
        log.debug("验证请求数据时出现异常：{}", e.getClass().getName());
        e.printStackTrace();
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        JsonResult result = JsonResult.failed(ResponseCode.BAD_REQUEST, message);
        log.debug("即将返回：{}", result);
        return result;
    }
}
