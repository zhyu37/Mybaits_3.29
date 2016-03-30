package mybaits.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import mybaits_po.User;

public class UserDaoImpl implements UserDao {
	
	private SqlSessionFactory sqlSessionFactory;
	
	//将sqlSessionFactory注入
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		// TODO Auto-generated constructor stub
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(int id) throws Exception {
		// TODO Auto-generated method stub
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		
		User user = sqlSession.selectOne("test.findUserById", id);
		sqlSession.close();
		
		return user;
	}

}
