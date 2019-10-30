package com.test;


import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.entity.Person;
import com.entity.User;

public class one_to_one {
	
	public static void selectone(){
      	//得到Session对象
        Session session = HibernateSessionFactory.getSession();
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
	    
	    //得到Session对象
        Session session = HibernateSessionFactory.getSession();
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
	    //得到Session对象
        Session session = HibernateSessionFactory.getSession();
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
	    //得到Session对象
        Session session = HibernateSessionFactory.getSession();
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
	
	public static void delect(){
	    //得到Session对象
        Session session = HibernateSessionFactory.getSession();
        //使用Hibernate操作数据库，都要开启事务,得到事务对象
        Transaction transaction = session.getTransaction();
        try {
        	transaction.begin();
        	 User user =session.get(User.class, 1);
        	 session.delete(user);
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
		//delect();
		
	}
}
