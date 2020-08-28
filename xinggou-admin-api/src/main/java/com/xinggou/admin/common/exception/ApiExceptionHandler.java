package com.xinggou.admin.common.exception;

import com.xinggou.common.exception.BizException;
import com.xinggou.common.utils.R;
import com.xinggou.common.utils.StrKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 *
 */
@RestControllerAdvice
public class ApiExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(BizException.class)
	public R handleRRException(BizException e){
		return R.error(e.getCode(), e.getMessage());
	}

	@ExceptionHandler(BindException.class)
	public R handleBindException(BindException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();
		String msg = fieldError == null ? "请求参数异常" : fieldError.getDefaultMessage();
		return R.error(msg);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		FieldError fieldError = e.getBindingResult().getFieldError();
		String msg = fieldError == null ? "请求参数异常" : fieldError.getDefaultMessage();
		return R.error(msg);
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}


	@ExceptionHandler(HttpMessageNotReadableException.class)
	public R handleHttpMessageNotReadableException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error(StrKit.buildMsg("请求参数格式异常:{}", e.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error("未知错误，请联系客服处理");
	}
}
