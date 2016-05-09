package com.makao.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.makao.dao.IProductDao;
import com.makao.entity.Product;
import com.makao.entity.User;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月7日
 */
@Repository
@Transactional
public class ProductDaoImpl implements IProductDao {
	private static final Logger logger = Logger.getLogger(ProductDaoImpl.class);
	@Resource
	private SessionFactory sessionFactory;
	@Override
	public int insert(Product product) {
		String tableName = "Product_"+product.getCityId()+"_"+product.getAreaId();
		String sql = "INSERT INTO `"
				+ tableName
				+ "` (`productName`,`catalog`,`price`,`standard`,`marketPrice`,`inventory`,`sequence`,`status`,"
				+ "`origin`,`salesVolume`,`likes`,`areaId`,`cityId`)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Session session = null;
		Transaction tx = null;
		int res = 0;// 返回0表示成功，1表示失败
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			session.doWork(
			// 定义一个匿名类，实现了Work接口
			new Work() {
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = null;
					try {
						ps = connection.prepareStatement(sql);
						ps.setString(1, product.getProductName());
						ps.setString(2, product.getCatalog());
						ps.setString(3, product.getPrice());
						ps.setString(4, product.getStandard());
						ps.setString(5, product.getMarketPrice());
						ps.setInt(6, product.getInventory());
						ps.setInt(7, product.getSequence());
						ps.setString(8, product.getStatus());
						ps.setString(9, product.getOrigin());
						ps.setInt(10, product.getSalesVolume());
						ps.setInt(11, product.getLikes());
						ps.setInt(12, product.getAreaId());
						ps.setInt(13, product.getCityId());
						ps.executeUpdate();
					} finally {
						doClose(ps);
					}
				}
			});
			tx.commit(); // 使用 Hibernate事务处理边界
		} catch (HibernateException e) {
			if (null != tx)
				tx.rollback();// 回滚
			logger.error(e.getMessage(), e);
			res = 1;
		} finally {
			if (null != session)
				session.close();// 关闭回话
		}
		return res;
	}

	@Override
	public Product getById(int id, int cityId, int areaId) {
		String tableName = "Product_"+cityId+"_"+areaId;
		String sql = "SELECT * FROM `"
				+ tableName
				+ "` WHERE id = ?";
		Session session = null;
		Transaction tx = null;
		List<Product> products = new ArrayList<Product>();
		try {
			session = sessionFactory.openSession();// 获取和数据库的回话
			tx = session.beginTransaction();// 事务开始
			session.doWork(new Work(){
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = null;
					try {
						ps = connection.prepareStatement(sql);
						ps.setInt(1, id);
						ResultSet rs = ps.executeQuery();
						//int col = rs.getMetaData().getColumnCount();
						while(rs.next()){
							Product p = new Product();
							p.setId(rs.getInt("id"));
							p.setCatalog(rs.getString("catalog"));
							p.setShowWay(rs.getString("showWay"));
							p.setPrice(rs.getString("price"));
							p.setStandard(rs.getString("standard"));
							p.setMarketPrice(rs.getString("marketPrice"));
							p.setLabel(rs.getString("label"));
							p.setCoverSUrl(rs.getString("coverSUrl"));
							p.setCoverBUrl(rs.getString("coverBUrl"));
							p.setInventory(rs.getInt("inventory"));
							p.setSequence(rs.getInt("sequence"));
							p.setStatus(rs.getString("status"));
							p.setDescription(rs.getString("description"));
							p.setOrigin(rs.getString("origin"));
							p.setSalesVolume(rs.getInt("salesVolume"));
							p.setLikes(rs.getInt("likes"));
							p.setDetailUrl(rs.getString("detailUrl"));
							p.setIsShow(rs.getString("isShow"));
							p.setAreaId(rs.getInt("areaId"));
							p.setCityId(rs.getInt("cityId"));
							products.add(p);
						}
					}finally{
						doClose(ps);
					}	
				}
			});
			tx.commit();// 提交事务
		} catch (HibernateException e) {
			if (null != tx)
				tx.rollback();// 回滚
			logger.error(e.getMessage(), e);
		} finally {
			if (null != session)
				session.close();// 关闭回话
		}
		return products.size()>0 ? products.get(0) : null;
	}

	@Override
	public int update(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Product> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> queryByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void testor() {
		// TODO Auto-generated method stub

	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public List<Product> queryByCityAreaId(String cityId, String areaId) {
		String tableName = "Product_"+cityId+"_"+areaId;
		String sql = "SELECT * FROM "+tableName;
		Session session = null;
		Transaction tx = null;
		List<Product> res = new LinkedList<Product>();
		try {
			session = sessionFactory.openSession();// 获取和数据库的回话
			tx = session.beginTransaction();// 事务开始
			//res = session.createQuery("from User").list();
			session.doWork(new Work(){
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement ps = null;
					try {
						ps = connection.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						//int col = rs.getMetaData().getColumnCount();
						while(rs.next()){
							Product p = new Product();
							p.setId(rs.getInt("id"));
							p.setCatalog(rs.getString("catalog"));
							p.setShowWay(rs.getString("showWay"));
							p.setPrice(rs.getString("price"));
							p.setStandard(rs.getString("standard"));
							p.setMarketPrice(rs.getString("marketPrice"));
							p.setLabel(rs.getString("label"));
							p.setCoverSUrl(rs.getString("coverSUrl"));
							p.setCoverBUrl(rs.getString("coverBUrl"));
							p.setInventory(rs.getInt("inventory"));
							p.setSequence(rs.getInt("sequence"));
							p.setStatus(rs.getString("status"));
							p.setDescription(rs.getString("description"));
							p.setOrigin(rs.getString("origin"));
							p.setSalesVolume(rs.getInt("salesVolume"));
							p.setLikes(rs.getInt("likes"));
							p.setDetailUrl(rs.getString("detailUrl"));
							p.setIsShow(rs.getString("isShow"));
							p.setAreaId(rs.getInt("areaId"));
							p.setCityId(rs.getInt("cityId"));
							res.add(p);
						}
					}finally{
						doClose(ps);
					}
					
				}
				
			});
			tx.commit();// 提交事务
		} catch (HibernateException e) {
			if (null != tx)
				tx.rollback();// 回滚
			logger.error(e.getMessage(), e);
		} finally {
			if (null != session)
				session.close();// 关闭回话
		}
		return (res.size()>0 ? res : null);
	}
	
	protected void doClose(PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception ex) {
				rs = null;
				logger.error(ex.getMessage(), ex);
				ex.printStackTrace();
			}
		}
		// Statement对象关闭时,会自动释放其管理的一个ResultSet对象
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception ex) {
				stmt = null;
				logger.error(ex.getMessage(), ex);
			}
		}
		// 当Hibernate的事务由Spring接管时,session的关闭由Spring管理.不用手动关闭
		// if(session != null){
		// session.close();
		// }
	}

	protected void doClose(PreparedStatement stmt) {
		// Statement对象关闭时,会自动释放其管理的一个ResultSet对象
		if (stmt != null) {
			try {
				stmt.close();
				stmt = null;
			} catch (Exception ex) {
				stmt = null;
				logger.error(ex.getMessage(), ex);
			}
		}
	}

}