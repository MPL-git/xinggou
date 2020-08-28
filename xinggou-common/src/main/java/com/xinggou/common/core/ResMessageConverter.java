package com.xinggou.common.core;

import com.xinggou.common.utils.JsonKit;
import com.xinggou.common.utils.R;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;

//@Component
public class ResMessageConverter extends AbstractHttpMessageConverter<R> {

    public ResMessageConverter() {
        super(MediaType.ALL);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // 表明只处理R类型的参数。
        return R.class.isAssignableFrom(clazz);
    }

    @Override
    protected boolean canRead(MediaType mediaType) {
        return false;
    }

    @Override
    protected R readInternal(Class<? extends R> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    /**
     * 重写writeInternal ，处理如何输出数据到response。
     */
    @Override
    protected void writeInternal(R r,
                                 HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        Map map = JsonKit.beanToMap(r);
        if (r.getMsg() == null || r.getMsg().isEmpty()) {
            map.remove("msg");
        }
        if (r.getData() == null) {
            map.remove("data");
        }

        String out = JsonKit.toJson(map);
        outputMessage.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        outputMessage.getBody().write(out.getBytes());
    }


}