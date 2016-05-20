package com.makao.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.makao.entity.Area;
import com.makao.entity.Catalog;
import com.makao.entity.OrderOn;
import com.makao.entity.Product;
import com.makao.entity.Vendor;
import com.makao.service.IAreaService;
import com.makao.service.IProductService;
import com.makao.service.IVendorService;

/**
 * @description: TODO
 * @author makao
 * @date 2016年5月6日
 */
@Controller
@RequestMapping("/product")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	@Resource
	private IProductService productService;
	@Resource
	private IAreaService areaService;
	@Resource
	private IVendorService vendorService;
	
	/**
	 * @param id
	 * @param cityId
	 * @param areaId
	 * @return
	 * curl -X GET 'http://localhost:8080/wxmall/product/1/1/1'
	 */
	@RequestMapping(value="/{id:\\d+}/{cityId:\\d+}/{areaId:\\d+}",method = RequestMethod.GET)
	public @ResponseBody Product get(@PathVariable("id") int id,@PathVariable("cityId") int cityId,@PathVariable("areaId") int areaId)
	{
		logger.info("获取商品信息id=" + id);
		Product Product = (Product)this.productService.getById(id,cityId,areaId);
		return Product;
	}
	
	@RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public @ResponseBody
    Object delete(@PathVariable("id") Integer id) {
        int res = this.productService.deleteById(id);
        JSONObject jsonObject = new JSONObject();
		if(res==0){
			logger.info("删除商品信息成功id=" + id);
        	jsonObject.put("msg", "删除商品信息成功");
		}
		else{
			logger.info("删除商品信息失败id=" + id);
        	jsonObject.put("msg", "删除商品信息失败");
		}
        return jsonObject;
    }
	
	/**
	 * @param Product
	 * @return
	 * curl l -H "Content-type: application/json" -X POST -d '{"number":"海南千禧小番茄","catalog":"水果","price":"12.00","standard":"一份足2斤","marketPrice":"30.00","inventory":12,"sequence":3,"status":"库存紧张","origin":"海南","salesVolume":7637,"likes":3972,"areaId":1,"cityId":1}' 'http://localhost:8080/wxmall/product/new'
	 */
	@RequestMapping(value = "/vnew/{vendorid:\\d+}", method = RequestMethod.POST)
    public @ResponseBody
    Object add(@PathVariable("vendorid") int vendorid,@RequestBody Product Product) {
		Vendor vendor = this.vendorService.getById(vendorid);
		Product.setAreaId(vendor.getAreaId());
		Product.setCityId(vendor.getCityId());
		int res = this.productService.insert(Product);
		JSONObject jsonObject = new JSONObject();
		if(res==0){
			logger.info("增加商品成功name=" + Product.getProductName());
        	jsonObject.put("msg", "200");
		}
		else{
			logger.info("增加商品成功失败name=" + Product.getProductName());
        	jsonObject.put("msg", "200");
		}
        return jsonObject;
    }

	
	@RequestMapping(value = "/snew/{supervisorid:\\d+}", method = RequestMethod.POST)
    public @ResponseBody
    Object addBySupervisor(@PathVariable("supervisorid") int vendorid,@RequestBody Product product) {
		int res = this.productService.insertToWhole(product);
		JSONObject jsonObject = new JSONObject();
		logger.info("超级管理员添加产品页面完成："+product.getProductName());
		if(res==0){
			jsonObject.put("msg", "200");
		}
		else{
			jsonObject.put("msg", "201");
		}  
	    return jsonObject;
    }
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    Object update(@RequestBody Product Product) {
		int res = this.productService.update(Product);
		JSONObject jsonObject = new JSONObject();
		if(res==0){
			logger.info("修改商品信息成功id=" + Product.getId());
        	jsonObject.put("msg", "修改商品信息成功");
		}
		else{
			logger.info("修改商品信息失败id=" + Product.getId());
        	jsonObject.put("msg", "修改商品信息失败");
		}
        return jsonObject;
    }
	
	@RequestMapping(value = "/query/{name:\\S+}", method = RequestMethod.GET)
    public @ResponseBody
    Object queryByName(@PathVariable("name")String name) {
		List<Product> Products = null;
		//则根据关键字查询
		Products = this.productService.queryByName(name);
		logger.info("根据关键字: '"+name+"' 查询商品信息完成");
        return Products;
    }
	
	/**
	 * @param areaId
	 * @return
	 * 根据cityId和areaId查出所有商品，即到指定的Product_cityId_areaId表里查
	 * curl -X GET 'http://localhost:8080/wxmall/product/query/1/1'
	 */
	@RequestMapping(value = "/query/{cityId:\\d+}/{areaId:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    Object queryByCityAreaId(@PathVariable("cityId")int cityId,@PathVariable("areaId")int areaId) {
		List<Product> Products = null;
		//则根据关键字查询
		Products = this.productService.queryByCityAreaId(cityId,areaId);
		logger.info("根据关键字: '"+areaId+"' 查询商品信息完成");
        return Products;
    }
	
	@RequestMapping(value = "/queryall", method = RequestMethod.GET)
    public @ResponseBody
    Object queryAll() {
		List<Product> Products = null;
		//则查询返回所有
		Products = this.productService.queryAll();
		logger.info("查询所有商品信息完成");
        return Products;
    }
	
	@RequestMapping(value = "/s_queryall", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView query_All() {
		//先查出所有area，从而找到所有cityId_areaId来确定Product_cityId_areaId表
		List<Area> areas = this.areaService.queryAll();
		List<Product> products = new LinkedList<Product>();
		for(Area a : areas){
			List<Product> ps = this.productService.queryByCityAreaId(a.getCityId(),a.getId());
			if(ps!=null)
				products.addAll(ps);
		}
		//这里假设放一些东西进去
		logger.info("查询所有商品信息完成");
		ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("products", products);  
	    modelAndView.setViewName("s_productList");  
	    return modelAndView;
    }
	
	@RequestMapping(value = "/s_catalogs", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView query_Catalogs() {
		//这里假设放一些东西进去
		List<Product> products = new ArrayList<Product>();
		Product oo = new Product();
		oo.setProductName("ddddddddd");
		products.add(oo);
		logger.info("查询所有有效订单信息完成");
		ModelAndView modelAndView = new ModelAndView();  
	    modelAndView.addObject("products", products);  
	    modelAndView.setViewName("s_productCatalog");  
	    return modelAndView;
    }
	
	@RequestMapping(value = "/s_new", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView add() {
		logger.info("跳转到添加产品页面完成");
		ModelAndView modelAndView = new ModelAndView();  
	    //modelAndView.addObject("products", products);  
	    modelAndView.setViewName("s_productAdd");  
	    return modelAndView;
    }
	
	@RequestMapping(value = "/v_new/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView areaAdd(@PathVariable("id") int id, @RequestParam(value="token", required=false) String token) {
	    ModelAndView modelAndView = new ModelAndView();  
		modelAndView.setViewName("v_productAdd");  
		if(token==null){
			return modelAndView;
		}
	    modelAndView.addObject("id", id);  
	    modelAndView.addObject("token", token);   
	    //Vendor vendor = this.vendorService.getById(id);
	    //这里是返回后台商品库的所有商品，即从Product表中获取
		List<Product> products = this.productService.queryRepProducts();
 
	    modelAndView.addObject("products", products);  
		return modelAndView;
    }
	
	@RequestMapping(value = "/v_manage/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView areaManage(@PathVariable("id") int id,
			@RequestParam(value = "token", required = false) String token) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("v_productManage");
		if (token == null) {
			return modelAndView;
		}
		modelAndView.addObject("id", id);
		modelAndView.addObject("token", token);
		Vendor vendor = this.vendorService.getById(id);
		List<Product> products = null;
		if(vendor!=null)
			products = this.productService.queryByCityAreaId(vendor.getCityId(),vendor.getAreaId());
 
	    modelAndView.addObject("products", products);  
	    
		return modelAndView;
	}
	
	@RequestMapping(value = "/v_catalog/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView areaCatalog(@PathVariable("id") int id,
			@RequestParam(value = "token", required = false) String token) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("v_productCatalog");
		if (token == null) {
			return modelAndView;
		}
		modelAndView.addObject("id", id);
		modelAndView.addObject("token", token);
		Vendor vendor = this.vendorService.getById(id);
		List<Catalog> catalogs = new ArrayList<Catalog>();
		if(vendor!=null)
		{
			Area area = this.areaService.getById(vendor.getAreaId());
			if(area!=null){
				String catalogStr = area.getCatalogs();
				if(!"".equals(catalogStr.trim())){
					String[] catalogList = catalogStr.split(",");
					for(String c : catalogList){
						Catalog cc = new Catalog();
						cc.setName(c.split("=")[0]);
						cc.setSequence(c.split("=")[1]);
						catalogs.add(cc);
					}
				}
			}
		}
		modelAndView.addObject("catalogs", catalogs);
		return modelAndView;
    }
	
	@RequestMapping(value = "/v_promotion/{id:\\d+}", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView areaPromotion(@PathVariable("id") int id,
			@RequestParam(value = "token", required = false) String token) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("v_promotionManage");
		if (token == null) {
			return modelAndView;
		}
		modelAndView.addObject("id", id);
		modelAndView.addObject("token", token);
		return modelAndView;
    }
	
}
