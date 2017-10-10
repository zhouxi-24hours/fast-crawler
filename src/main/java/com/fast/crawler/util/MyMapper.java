package com.fast.crawler.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 * @Description: 公共的mapper接口
 * @author zhouxi
 * @date 2017年8月26日 上午11:09:02
 * @param <T>
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T>{

}
