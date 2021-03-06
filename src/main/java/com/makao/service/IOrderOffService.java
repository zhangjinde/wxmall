package com.makao.service;

import java.util.List;

import com.makao.entity.Comment;
import com.makao.entity.OrderOff;
import com.makao.entity.OrderOn;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月6日
 */
public interface IOrderOffService {

	int insert(OrderOff orderOff);

	int update(OrderOff orderOff);

	List<OrderOff> queryByName(String name);

	List<OrderOff> queryAll(String tableName);

	OrderOff getById(int id);

	int deleteById(int id);
	//已确认收货的订单列表
	List<OrderOff> queryConfirmGetByAreaId(String string, int areaId);

	List<OrderOff> queryCancelByAreaId(String tableName, int areaId);

	/**
	 * @param tableName
	 * @param areaId
	 * @return
	 * 退货申请中和退货中的订单
	 */
	List<OrderOff> queryRefundByAreaId(String tableName, int areaId);

	int refundOrder(int cityId, int orderid);

	int finishRefundOrder(int cityId, int orderid);

	int cancelRefundOrder(int cityId, int orderid, String vcomment);

	List<OrderOff> queryAllCanceledAndReturned(String string);

	OrderOff finishReturnOrder(int cityId, int orderid);

	List<OrderOff> queryByUserId(String tableName, int userid);

	OrderOff queryByOrderId(String tableName, int orderid);
	//发起退货申请
	OrderOff returnOrder(int cityid, int orderid);

	/**
	 * @param cityId
	 * @param areaId
	 * @return
	 * 查询已退货的记录数
	 */
	int getConfirmRecordCount(int cityId, int areaId);

	/**
	 * @param cityId
	 * @param areaId
	 * @return
	 * 查询退货申请中和退货中的订单记录数
	 */
	int getReturnRecordCount(int cityId, int areaId);

	/**
	 * @param cityId
	 * @param areaId
	 * @return
	 * 已取消，已退货，已取消退货的记录数
	 */
	int getCancelRecordCount(int cityId, int areaId);

	/**
	 * @param id
	 * @return
	 * Order_cityid_off表的记录数
	 */
	int getRecordCount(int cityid);

	/**
	 * @param id
	 * @return
	 * 查询所有已取消(卖家取消)的和已退货的订单记录数
	 */
	int getCanceledAndReturnedRecordCount(int cityid);

	/**
	 * @param comment
	 * //添加了评论后需要将评论的id号插入到对应订单中，方便后面读取
			//以"商品id=评论id,"的形式插入
	 */
	int updateComment(Comment comment);

	/**
	 * @param string
	 * @return
	 * 将取消或退货的的订单的inventBack字段设为1
	 * 注意用户已退货的订单不再加回去，所以inventoryBackCanceledAndReturned只返回已取消的，不返回已退货的了
	 */
	List<OrderOff> inventoryBackCanceledAndReturned(String string);

	/**
	 * @param id
	 * @return
	 * 将所有已收货一定时间后的订单状态设为已完成
	 */
	List<OrderOff> terminateOrders(int cityid);

	/**
	 * @param string
	 * @return
	 * 查询待退款的订单的number
	 */
	int queryNeedRefundNumber(String string);

	/**
	 * @param string
	 * @param areaId
	 * @return
	 * 已完成的订单，即状态为13，已收货且已过了退货期的订单
	 */
	List<OrderOff> queryTerminaledByAreaId(String string, int areaId);

	/**
	 * @param cityId
	 * @param areaId
	 * @return
	 * 状态13的订单数
	 */
	int getTermialedRecordCount(int cityId, int areaId);

	OrderOff queryByNumber(String string, String number);

	int vcommentOrderByNumber(int cityId, String number, String vcomment);

	/**
	 * @param tableName
	 * @param areaid
	 * @param fromdate
	 * @param todate
	 * @return
	 * 从orderOff表里查询区域areaid对应的时间区间fromdate到todate的已完成和已取消退货的订单
	 */
	List<OrderOff> queryDealed(String tableName, int areaid, String fromdate,
			String todate);

	/**
	 * @param string
	 * @param areaid
	 * @param fromdate
	 * @param todate
	 * @return
	 * 查找对应区域下在特定时间范围内的已退款的所有订单,注意是已退款不是已退货
	 */
	List<OrderOff> queryReturned(String string, int areaid, String fromdate,
			String todate);

}
