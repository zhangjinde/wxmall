package com.makao.quartz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.makao.entity.City;
import com.makao.entity.OrderOff;
import com.makao.entity.OrderOn;
import com.makao.service.ICityService;
import com.makao.service.IOrderOffService;
import com.makao.service.IOrderOnService;
import com.makao.service.IProductService;
import com.makao.thread.AddInventoryThread;
import com.makao.utils.RedisUtil;

/**
 * @author makao
 *由于用户下单成功后可能在15分钟内未支付或支付失败，需要定期：
 *1.将超过15分钟的未支付的订单里的商品的库存依次加回缓存中
 *2.将缓存中的所有商品的库存定期写入数据库中
 *3.将数据库中超过下单时间15分钟的未支付的订单删除
 *
 *9月27号：
 *用户取消的订单或已退货的订单里的库存也要加回去
 *10月26号：
 *用户已退货的订单里的库存不再加回去
 */
public class InventoryOptJob {
	@Resource
	private IOrderOnService orderOnService;
	@Resource
	private IOrderOffService orderOffService;
	@Resource
	private IProductService productService;
	@Resource
	private ICityService cityService;
	@Autowired
	private RedisUtil redisUtil;
	public void execute(){
		List<City> cities = this.cityService.queryAll();
		for(City c:cities){
			//数据库中查到所有满足条件的订单，同时删除它们，并且返回订单列表
			List<OrderOn> orders = this.orderOnService.unPaidOrders(c.getId());
			//找到已取消的订单列表,注意用户已退货的订单不再加回去，所以inventoryBackCanceledAndReturned只返回已取消的，不返回已退货的了
			List<OrderOff> canceled = this.orderOffService.inventoryBackCanceledAndReturned("Order_"+c.getId()+"_off");
			//遍历返回的订单列表，取出其中的(城市区域商品)-(数量)
			//加入到对应pi_cityId_areaId_id缓存中
			if(orders!=null)
			{	
				for(OrderOn o:orders){
					int areaId = o.getAreaId();
					String[] ids = o.getProductIds().split(",");
					String[] names = o.getProductNames().split(",");
					for(int i=0; i<ids.length; i++){
						String productKey = "pi_"+c.getId()+"_"+areaId+"_"+ids[i];
						String productNum = names[i].split("=")[2];
						AddInventoryThread ait = new AddInventoryThread(productKey, Integer.valueOf(productNum), redisUtil);
						new Thread(ait, "add inventory thread").start();
						//没付钱的订单没有修改销量，所以这里不用将销量减少
					}
				}
			}
			if(canceled!=null&&canceled.size()>0){
				for(OrderOff o:canceled){
					int areaId = o.getAreaId();
					String[] ids = o.getProductIds().split(",");
					String[] names = o.getProductNames().split(",");
					for(int i=0; i<ids.length; i++){
						String productKey = "pi_"+c.getId()+"_"+areaId+"_"+ids[i];
						String productNum = names[i].split("=")[2];
						AddInventoryThread ait = new AddInventoryThread(productKey, Integer.valueOf(productNum), redisUtil);
						new Thread(ait, "add inventory thread").start();
						//已取消或退货后的订单的销量也没有回减
					}
				}
			}
			//遍历当前city的pi_cityid_*为key的所有值，将这些新的库存值写入商品数据库
			//同时将销量写入数据库
			//if(orders!=null||canceled.size()>0){
				List<String> keys = redisUtil.getKeys("pi_"+c.getId()+"_*");
				for(String key : keys){
					String tableName = "Product_"+c.getId()+"_"+key.split("_")[2];
					String productid = key.split("_")[3];
					String inventN = redisUtil.redisQueryObject(key);
					if(inventN!=null&&!"".equals(inventN)){
						//if(orders!=null||canceled.size()>0){//如果本轮发现未支付的订单或取消或退货的订单，才去更新缓存，以免频繁操作缓存
							int res = this.productService.updateInventory(tableName, productid, inventN);
						//}
						//更新销量
						String svKey = "sv"+key.substring(2);
						String sv = redisUtil.redisQueryObject(svKey);
						if(sv!=null&&!"".equals(sv)){
								int res2 = this.productService.updateSalesVolume(tableName, productid, Integer.valueOf(sv));
						}
					}
				}
			//}
		}
	}
}
