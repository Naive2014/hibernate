package com.test;


import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.entity.Person;
import com.entity.User;

public class one_to_one {
	
	public static void selectone(){
		//获取加载配置管理类
	    Configuration configuration = new Configuration().configure();
	    //创建Session工厂对象
        SessionFactory sessionFactory = configuration.buildSessionFactory();
      	//得到Session对象
        Session session = sessionFactory.openSession();
        try {
            //查询数据库
            User user =session.get(User.class, 1);
            System.out.println(user.toString());
            System.out.println(user.getPerson().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			 //关闭Session
	        session.close();
		}

       
	}
	public static void add(){
		User user = new User();
	    user.setPassword("123");
	    user.setCellphone("122222");
	    user.setUsername("xxx");
	    Person person = new Person();
	    person.setName("Naive");
	    
	    person.setUser(user);
	    user.setPerson(person);
	    
	    //获取加载配置管理类
	    Configuration configuration = new Configuration().configure();
	    //创建Session工厂对象
        SessionFactory sessionFactory = configuration.buildSessionFactory();
      	//得到Session对象
        Session session = sessionFactory.openSession();
        //使用Hibernate操作数据库，都要开启事务,得到事务对象
        Transaction transaction = session.getTransaction();
        try {
        	 //开启事务
            transaction.begin();
            //把对象添加到数据库中
            session.save(user);
            //session.save(person);
            //提交事务
            transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally {
			 //关闭Session
	        session.close();
		}
	}
	
	public static void query(){
		//获取加载配置管理类
	    Configuration configuration = new Configuration().configure();
	    //创建Session工厂对象
        SessionFactory sessionFactory = configuration.buildSessionFactory();
      	//得到Session对象
        Session session = sessionFactory.openSession();
        try {
			@SuppressWarnings("unchecked")
			ArrayList<User> arrayList = (ArrayList<User>) session.createQuery("from User").list();
        	System.out.println(arrayList.toString());
        	
        	} catch (Exception e) {
			e.printStackTrace();
		}finally{
            session.close();
        }
	}
	
	public static void update(){
		//获取加载配置管理类
	    Configuration configuration = new Configuration().configure();
	    //创建Session工厂对象
        SessionFactory sessionFactory = configuration.buildSessionFactory();
      	//得到Session对象
        Session session = sessionFactory.openSession();
      //使用Hibernate操作数据库，都要开启事务,得到事务对象
        Transaction transaction = session.getTransaction();
        try {
			User user = session.get(User.class, 1);
			user.setUsername("Naive");
			//开启事务
            transaction.begin();
            //把对象添加到数据库中
            session.save(user);
            //提交事务
            transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		}finally {
			session.close();
		}
	}
	
	public static void main(String[] args) {
		//add();
		//query();
		//update();
		selectone();
		
	}
}
