package com.makao.utils;
/**
 * @description: TODO
 * @author makao
 * @date 2016年6月17日
 */
public class MakaoConstants {
	public static final int PAGE_SIZE = 10;//商品列表，券列表，用户列表等分页每页的记录数
	public static final long TOKEN_EXPIRE = 10;//系统自定义token在缓存中保留的时间(暂设为秒)
	public static final String SERVER_DOMAIN = "http://m1.shequvip.com";//最后部署上线的服务器的域名，用于设置微信回调页面
	
	public static final long ORDER_EXPIRE = 15;//下单等待支付的时间限制，单位为分
	
	public static final String DEFAULT_CITY_NAME="上海";
	public static final String DEFAULT_AREA_NAME="张江";
	public static final int DEFAULT_CITY_ID=1;
	public static final int DEFAULT_AREA_ID=1;
	public static final int PRETIME = 30;//从数据库中找出需要将状态从排队中改为待处理的订单，将其状态设为待处理
											//当配送时间起点-PRETIME(min)<=当前时间时的订单满足条件
	public static final int COMFIRMTIME = -3;//完成配送的时间与当前时间的分钟差
												//如果时间差(是负数)小于预先定义的COMFIRMTIME，系统帮助确认收货，单位为分
	public static final int REMOVETIME = -18;//数据库中查到所有15分钟内未支付或支付失败的订单，同时删除它们，并且返回订单列表
											//下单时间与当前时间的分钟差，小于该时间则开始删除，单位为分，为负值，注意其绝对值必须大于ORDER_EXPIRE
	public static final int RETURN_EXPIRE_TIME = -2;//从确认收货到不能申请退货的时间差，单位为分
	public static final int WEIXIN_TOKEN_EXPIRE_TIME = 110;//微信的access_token和jsapi_ticket的在缓存中的有效时间，单位为分
	public static final int INITIAL_POINT = 40;//用户第一次进来时的默认积分
	public static final float POINT_PROPORTION = 0.1f;//订单金额化成积分的比例
	public static final int COUPON_EXPIRE_DAY = 7;//代金券有效期天数
}
