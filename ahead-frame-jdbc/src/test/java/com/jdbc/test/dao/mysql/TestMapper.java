package com.jdbc.test.dao.mysql;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TestMapper {

    @Insert("insert into test(uuid,name,create_time) values(#{uuid},#{name},#{time})")
    public void insert(@Param("uuid") String uuid,@Param("name") String name,@Param("time") String time);

    @Insert("insert into test(uuid,name,create_time) values(#{uuid},#{name},#{time})")
    public void insert1(@Param("uuid") String uuid,@Param("name") String name,@Param("time")String time);



}
