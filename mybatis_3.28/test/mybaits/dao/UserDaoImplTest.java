package mybaits.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import mybaits_po.User;

public class UserDaoImplTest {

	// 会话工厂
			private SqlSessionFactory sqlSessionFactory;
		
		//创建工厂
		@Before
		public void init() throws IOException{
			//配置文件(SqlMapConfig.xml)
			String resource = "SqlMapConfig.xml"; 
			
			//加载配置文件到输入流
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			// 创建会话工厂
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}

	@Test
	public void testFindUserById() throws Exception {
		UserDao userDao = new UserDaoImpl(sqlSessionFactory);
		
		User user = userDao.findUserById(34);
		System.out.print(user);
	}

}