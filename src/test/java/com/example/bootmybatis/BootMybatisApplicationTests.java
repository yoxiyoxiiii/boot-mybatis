package com.example.bootmybatis;

import com.example.bootmybatis.dao.FileMapper;
import com.example.bootmybatis.dao.UserMapper;
import com.example.bootmybatis.model.File;
import com.example.bootmybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootMybatisApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FileMapper fileMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void select() {
//        User user = userMapper.queryUaserAndFiles(1);
        User user = userMapper.selectByPrimaryKey(1);
        List<File> byUserId = fileMapper.findByUserId(1);


    }


    @Test
    public void selectOne() throws IOException {
        String config = "config.xml";
        InputStream inputStream = Resources.getResourceAsStream(config);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                .build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.example.bootmybatis.dao.UserMapper.selectByPrimaryKey", 1);
        System.out.println(user);
        sqlSession.close();
    }

}
