package com.jdbc.test.dao.oracle;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface OracleTestMapper {

    @Insert("insert into test(uuid,name,create_time) values(#{uuid},#{name},to_date(#{time},'yyyy-MM-dd hh24:mi:ss'))")
    public void insert(@Param("uuid") String uuid, @Param("name") String name, @Param("time") String time);

    @Insert("insert into test(uuid,name1,create_time) values(#{uuid},#{name},#{time})")
    public void insert1(@Param("uuid") String uuid, @Param("name") String name, @Param("time") String time);


}
