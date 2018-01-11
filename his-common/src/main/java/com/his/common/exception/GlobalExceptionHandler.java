package com.his.common.exception;



import javax.servlet.http.HttpServletRequest;

import com.his.common.model.BaseResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.his.common.model.ResultStatus;

/**
 * 全局异常处理
 */
@ControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 系统异常处理，比如：404,500
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseResp<?> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("", e);
        BaseResp<Object> baseResp = new BaseResp<Object>();
        baseResp.setMessage(e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
            baseResp.setCode(ResultStatus.http_status_not_found.getErrorCode());
        } else {
            baseResp.setCode(ResultStatus.http_status_internal_server_error.getErrorCode());
        }
        baseResp.setData("");
        return baseResp;
    }
}
