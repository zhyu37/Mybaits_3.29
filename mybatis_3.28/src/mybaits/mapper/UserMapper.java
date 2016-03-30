package mybaits.mapper;

import mybaits_po.User;

public interface UserMapper {
	
	public User findUserById(int id) throws Exception;
	
}
