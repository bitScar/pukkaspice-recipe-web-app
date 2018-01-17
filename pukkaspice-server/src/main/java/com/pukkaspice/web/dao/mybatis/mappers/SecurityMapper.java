package com.pukkaspice.web.dao.mybatis.mappers;

import org.apache.ibatis.annotations.Param;

public interface SecurityMapper {

    boolean isUserRecord(@Param("tableName") String tableName, @Param("columnName") String columnName, @Param("recordId") int recordId, @Param("userId") int userId);

    boolean isSameUserRecord( @Param("userId") int userId);
    
}
