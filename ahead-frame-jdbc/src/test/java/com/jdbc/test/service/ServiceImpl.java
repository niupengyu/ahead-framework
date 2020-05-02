package com.jdbc.test.service;

import com.github.niupengyu.core.util.DateUtil;
import com.github.niupengyu.core.util.IdGeneratorUtil;
import com.jdbc.test.dao.oracle.OracleTestMapper;
import com.jdbc.test.dao.mysql.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("serviceImpl")
public class ServiceImpl {

    @Autowired
    private TestMapper testMapper;

    @Autowired
    private OracleTestMapper oracleTestMapper;

    @Transactional(rollbackFor = Exception.class)
    public void test(){
        String uuid= IdGeneratorUtil.uuid32();
        String now= DateUtil.dateFormat();
        testMapper.insert(uuid,uuid+"Name",now);
        uuid= IdGeneratorUtil.uuid32();
        now= DateUtil.dateFormat();
        testMapper.insert1(uuid,uuid+"Name",now);

        uuid= IdGeneratorUtil.uuid32();
        now= DateUtil.dateFormat();
        oracleTestMapper.insert(uuid,uuid+"Name",now);

        uuid= IdGeneratorUtil.uuid32();
        now= DateUtil.dateFormat();
        oracleTestMapper.insert1(uuid,uuid+"Name",now);


    }

}
