package com.example.bootmybatis;

import com.example.bootmybatis.dao.FileMapper;
import com.example.bootmybatis.dao.UserMapper;
import com.example.bootmybatis.model.File;
import com.example.bootmybatis.model.User;
import com.example.bootmybatis.model.UserVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void pageList() {
        PageHelper.startPage(1,10);
        List<User> userList = userMapper.selectAll();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        int pages = userPageInfo.getPages();
        long total = userPageInfo.getTotal();
        System.out.println("pages = "+ pages);//总页数
        System.out.println("total = "+ total);// 总的记录数
    }

    @Test
    public void pageListUserVo() {
        PageHelper.startPage(1, 2);
        List<User> userList = userMapper.selectAll();
        List<UserVo> userVoList = userList.stream().map(item -> UserVo.builder().username(item.getUsername()).id(item.getId()).build()).collect(Collectors.toList());
        PageInfo<UserVo> userPageInfo = new PageInfo<>(userVoList);
        int pages = userPageInfo.getPages();
        long total = userPageInfo.getTotal();
        System.out.println("pages = "+ pages);//总页数
        System.out.println("total = "+ total);// 总的记录数
    }

    @Test
    public void pageListUserVo2() {
        PageHelper.startPage(1, 2);
        List<User> userList = userMapper.selectAll();
        PageInfo<User> userPageInfo = new PageInfo<>(userList);// 先保存数据

        List<UserVo> userVoList = userList.stream().map(item -> UserVo.builder().id(item.getId()).username(item.getUsername()).build())
                .collect(Collectors.toList());
        PageInfo<UserVo> userVoPageInfoFinal = new PageInfo<>(userVoList);

        // 这里就是分页数据的 Copy 如总的页数，总的记录数
        BeanUtils.copyProperties(userPageInfo, userVoPageInfoFinal);

        int pages = userVoPageInfoFinal.getPages();
        long total = userVoPageInfoFinal.getTotal();
        System.out.println("pages = "+ pages);//总页数
        System.out.println("total = "+ total);// 总的记录数
    }

}
