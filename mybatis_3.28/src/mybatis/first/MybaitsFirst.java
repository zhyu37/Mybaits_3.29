package mybatis.first;

import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.lf5.util.Resource;
import org.junit.Before;
import org.junit.Test;

import mybaits_po.User;

public class MybaitsFirst {
	
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
	
	// 测试根据id查询用户(得到单条记录)
	@Test
	public void testFindUserById(){
		// 通过sqlSessionFactory创建sqlSession

				SqlSession sqlSession = sqlSessionFactory.openSession();

				// 通过sqlSession操作数据库
				// 第一个参数：statement的位置，等于namespace+statement的id
				// 第二个参数：传入的参数
				User user = null;
				try {
					user = sqlSession.selectOne("test.findUserById", 1);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					sqlSession.close();
				}
				System.out.println(user.getSex());
	}
	
	@Test
	public void testFindUserByName(){
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		List<User> user = null;
		try {
			
			user = sqlSession.selectList("test.findUserByName", "王");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		System.out.println(user.get(0).getUsername());
	}
	
	@Test
	public void testInsertUser(){
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = new User();
		user.setUsername("谭月英");
		user.setSex("2");
		user.setAddress("广西");
		user.setBirthday(new Date());
		try {
			
			sqlSession.insert("test.insertUser", user);
			// 需要提交事务
			sqlSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
		System.out.println("用户的id=" + user.getId());
	}
	
	@Test
	public void testDeleteUser(int id){
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		User user = new User();
//		user.setUsername("谭月英");
//		user.setSex("2");
//		user.setAddress("广西");
//		user.setBirthday(new Date());
		try {
			
			sqlSession.delete("test.deleteUser", id);
			// 需要提交事务
			sqlSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
//		System.out.println("用户的id=" + user.getId());
	}
	
	@Test
	public void testUpdateUser(User user){
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
//		User user = new User();
//		user.setUsername("谭月英");
//		user.setSex("2");
//		user.setAddress("广西");
//		user.setBirthday(new Date());
		try {
			
			sqlSession.update("test.updateUser", user);
			// 需要提交事务
			sqlSession.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
	
}
