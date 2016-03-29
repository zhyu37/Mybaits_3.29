# Mybaits_3.29
mybaits练习
=====
##Mybaits 3.29
>自学Java框架——SSM。MyBatis是一个一流的持久性框架,支持自定义SQL存储过程和先进的映射。MyBatis消除几乎所有的JDBC代码和手动设置的参数和检索结果。MyBatis可以使用简单的XML或注释进行配置和映射原语,映射接口和Java pojo(传统的普通Java对象)数据库记录。

<p>1.导入jar包
<pre>
mybaits-3.3.1.jar
依赖jar包
asm-3.3.1.jar
cglib-2.2.2.jar
commmons-logging-1.1.1.jar
javassist-3.17.1.GA.jar
log4j-1.2.17.jar
log4j-api-2.0-rc1.jar
log4j-core-2.0-rc1.jar
slf4j-api-1.7.5.jar
slf4j-log4j12-1.7.5.jar
`可以导入最新版本的jar包`
</pre>

<p>2.工程架构
<pre>
src
	mybaits.first(存放class)
	mybaits.po(存放pojo)
config
	sqlmap
		User.xml(这个model所用到的sql查询语句)
	SqlMapConfig.xml(sql配置)
	log4j.properties(控制日志信息输送)
</pre>

<p>3.log4j.properties(公用文件)
<pre>
解释log4j.properties：
Log4j是Apache的一个开放源代码项目，通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件、甚至是套接口服务器、NT的事件记录器、UNIXSyslog守护进程等；我们也可以控制每一条日志的输出格式；通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程。最令人感兴趣的就是，这些可以通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。
详情:http://zhidao.baidu.com/link?url=WhqfJvhZIk2m9MhQ2mC_O38vP4S0wbiEsJfI2S3QzeYYHB8TQOuXbSnvNTU1muAz5TiecGg4TO4QKpn6qPgFa2wlBpTKkcDyTcr-KnKKBQC
<code>
//# Global logging configuration
log4j.rootLogger=DEBUG, stdout
//# Console output...
log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%5p [%t] - %m%n
</code>
</pre>
<p>4. SqlMapConfig.xml(公用文件)
<pre>
`通过SqlMapConfig.xml加载mybaits运行环境`
<code>
< !DOCTYPE configuration
PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
< configuration>
	// 和spring整合后 environments配置将废除
	 <environments default="development">
		< environment id="development">
		//使用jdbc事务管理
			< transactionManager type="JDBC" />
		// 数据库连接池
			< dataSource type="POOLED">
				< property name="driver" value="com.mysql.jdbc.Driver" />
				< property name="url" value="jdbc:mysql://localhost:3306/mine?characterEncoding=utf-8" />
				< property name="username" value="root" />
				< property name="password" value="xkws211jvS?>" />
			< /dataSource>
		< /environment>
	< /environments>
	
// 加载mapper.xml
< mappers>
	< mapper resource="sqlMap/User.xml" />
< /mappers>
	
< /configuration>
</code>
</pre>
<p>5. 根据id查询用户
<pre>
	1. pojo(User.java)
	<code>
	public class User {
private int id;
private String username;// 用户姓名
private String sex;// 性别
private Date birthday;// 生日
private String address;// 地址
	</code>
	2. User.xml
	建议命名规则：表名+mapper.xml
	早期ibatis命名规则：表名.xml
	<pre><code>
< ?xml version="1.0" encoding="UTF-8" ?>
< !DOCTYPE mapper
PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
< !-- namespace命名空间，为了对sql语句进行隔离，方便管理 ，mapper开发dao方式，使用namespace有特殊作用 -->
< mapper namespace="test">
< !-- 根据id查询用户信息 -->
< !-- 
		id：唯一标识 一个statement
		#{}：表示 一个占位符，如果#{}中传入简单类型的参数，#{}中的名称随意
		parameterType：输入 参数的类型，通过#{}接收parameterType输入 的参数
		resultType：输出结果 类型，不管返回是多条还是单条，指定单条记录映射的pojo类型
	 -->
	< select id="findUserById" parameterType="int" resultType="mybaits_po.User">
		select * from user where id = #{id}
	< /select>	
< !-- 根据用户名称查询用户信息，可能返回多条
	${}：表示sql的拼接，通过${}接收参数，将参数的内容不加任何修饰拼接在sql中。
-->
	 < !-- warning 为什么不能叫name  啊啊啊啊啊  坑死我算了 -->
	 < !-- statementType为“STATEMENT” -->
	< select id="findUserByName" parameterType="java.lang.String" resultType="mybaits_po.User" statementType="STATEMENT">
		select * from user where username like '%${value}%'
	< /select>
< /mapper> 
	</code></pre>
	3. 编码
<pre><code>
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
	
}

</code></pre>
</pre>

##See You
