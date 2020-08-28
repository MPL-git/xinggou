package com.xinggou.admin.rc.converter;

import com.xinggou.admin.rc.vo.request.PaginateOssFileRequest;
import com.xinggou.admin.rc.vo.response.OssVo;
import com.xinggou.common.vo.PageWrap;
import com.xinggou.rc.bean.params.PaginateOssFileParams;
import com.xinggou.rc.entity.Oss;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author luoyb
 * Created on 2020/8/20
 */
@Mapper
public interface RcConverter {
    RcConverter INSTANCE = Mappers.getMapper(RcConverter.class);

    PaginateOssFileParams toPaginateOssFileParams(PaginateOssFileRequest request);

    PageWrap<OssVo> toOssVoPageWrap(PageWrap<Oss> pageWrap);

}
