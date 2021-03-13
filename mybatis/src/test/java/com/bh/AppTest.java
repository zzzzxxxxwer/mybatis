package com.bh;
import static org.junit.Assert.assertTrue;
import com.bh.pojo.Person;
import com.bh.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class AppTest {
    //每次执行前首先加载会话工厂
    private SqlSessionFactory sqlSessionFactory;
    // SqlSession:创建sesssion工厂
    @Before //执行@Test前先加载createSqlSessionFactory()方法：创建sesssion工厂
    public void createSqlSessionFactory() throws IOException {
        //获取配置文件
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //创建SqlSessionFactory
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        //SqlSessionFactory是通过 SqlSessionFactoryBuilder进行创建
    }//SqlSession 中封装了对数据库的操作，如：查询selectone selectList/Map 、插入Insert、更新update、删除delete。
    // 通过 SqlSessionFactory 创建 SqlSession，而 SqlSessionFactory 是通过 SqlSessionFactoryBuilder 进行创建
    //根据id查询用户信息
    @Test
    public void testFindUserByIdone() {
        SqlSession sqlSession = null;//创建数据库连接对象
        try {
            sqlSession = sqlSessionFactory.openSession(); //通过SqlsessionFactory.openSession()方法打开一个数据库会话
            //查询单个记录
            User user = sqlSession.selectOne("UserTest.findUserById", 1);
// 动态代理对象调用 sqlSession.selectOne()、sqlSession.selectList()是根据 mapper 接口方法的返回值决定，
// 如果返回 list集合:多行数据则调用 selectList 方法，如果返回单个对象则调用 selectOne 方法。
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null)
//           {
//            }
//            else
            {
                sqlSession.close();//关闭sqlSession连接,释放资源
            }
        }
    }

    @Test
    public void FindByUsername() {     //数据库查询对象
        SqlSession sqlSession = null;
        try {         //对象实例化
            sqlSession = sqlSessionFactory.openSession(); //通过SqlsessionFactory.openSession()方法打开一个数据库会话
            //查询
            List<User> list = sqlSession.selectList("UserTest.findUserByName", "小");//查询selectone selectList/Map
            System.out.println(list.size());//List.size():方法以 int 形式返回列表中元素的个数。
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();//关闭sqlSession连接,释放资源
            }
        }
    }

    @Test
    public void FindAll() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过SqlsessionFactory.openSession()方法打开一个数据库会话
        List<User> list = sqlSession.selectList("UserTest.findAll");//用List集合接收数据库查询的结果
        System.out.println(list);
        sqlSession.close();//关闭sqlSession连接,释放资源
    }

    //添加数据
    @Test
    public void testInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
//添加数据
        try {
            User user = new User(); //实例化User类的对象
            user.setUsername("周星星");
            user.setAddress("北京海淀区");
            user.setBirthday(null);
            user.setSex("男");
            sqlSession.insert("UserTest.insertUser", user);//插入Insert
            sqlSession.commit(); //提交事务
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();//关闭sqlSession连接,释放资源
            }
        }
    }

    //添加数据--uuid 生成主键
    @Test
    public void testInsertUUID() {
        //数据库查询对象
        SqlSession sqlSession = null;
        try {         //对象实例化
            sqlSession = sqlSessionFactory.openSession();
            //添加数据
            Person person = new Person();
            person.setUsername("小尼4");
            person.setAddress("北京海淀区");
            person.setSex("男");
            //使用UserTest.insertPersonUUID
            sqlSession.insert("UserTest.insertPersonUUID", person);
            sqlSession.commit(); //提交事务
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    //删除数据
    @Test
    public void testDelete() {
        //数据库查询对象
        SqlSession sqlSession = null;
        try {         //对象实例化
            sqlSession = sqlSessionFactory.openSession();
            //删除用户
            sqlSession.delete("UserTest.deleteUserById", 30);//删除id=30的数据  删除delete
            //提交事务
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();//关闭sqlSession连接
            }
        }
    }

    //修改数据
    @Test
    public void testUpdate() {
        //数据库查询对象
        SqlSession sqlSession = null;
        try {         //对象实例化
            sqlSession = sqlSessionFactory.openSession();
            //修改数据
            User user = new User();
            user.setId(10);
            user.setUsername("李三");
            user.setAddress("上海");
            user.setSex("男");
            user.setBirthday(null);
            sqlSession.update("UserTest.updateUser", user);//更新update
            sqlSession.commit();//提交事务

        } catch (
                Exception e)

        {
            e.printStackTrace();
        } finally

        {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
