package com.kingdee.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.service.PurchaseOrdersService;
import org.springframework.stereotype.Service;

import com.kingdee.model.po.PurchaseOrders;
import com.kingdee.model.po.PurorderItem;

@Service
public class PurchaseOrdersServiceImpl implements PurchaseOrdersService {

    @Resource
    private com.kingdee.dao.PurchaseOrdersMapper mapper_PurchaseOrders;

    @Resource
    private com.kingdee.dao.PurorderItemMapper mapper_Purorderitem;


	/**
	 * 1。1 本地库单数据新增
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public void saveLocal(PurchaseOrders purchaseOrders) throws Exception {

		mapper_PurchaseOrders.insert(purchaseOrders);
	}

	/**
	 * 1.2 本地库批量新增
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, readOnly = false)	
	@DataSource("first")
	@Override
	public void saveLocal(List<PurchaseOrders> list_PurchaseOrders) throws Exception {
		if(null != list_PurchaseOrders) {
			for(PurchaseOrders purchaseOrders : list_PurchaseOrders) {
				mapper_PurchaseOrders.insert(purchaseOrders);
			}
		}
	}

	/**
	 * 1.3 本地库批量查找
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public List<PurchaseOrders> findLocal(PurchaseOrders purchaseOrders) throws Exception {

		return mapper_PurchaseOrders.select(purchaseOrders);
	}

	/**
	 * 1.4 本地库单数据查找
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public PurchaseOrders findOneLocal(PurchaseOrders purchaseOrders) throws Exception {
		// TODO Auto-generated method stub
		return mapper_PurchaseOrders.selectOne(purchaseOrders);
	}

	/**
	 * 1.5 批量删除订单数据
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public void deltLocal(List<PurchaseOrders> list_PurchaseOrders) throws Exception {

		if(null != list_PurchaseOrders && list_PurchaseOrders.size() > 0) {
			for(PurchaseOrders purchaseOrders : list_PurchaseOrders) {
				mapper_PurchaseOrders.delete(purchaseOrders);
			}
		}
	}

	/**
	 * 1.6 订单单条数据清理
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public void clearLocal(PurchaseOrders purchaseOrders) throws Exception {
		mapper_PurchaseOrders.delete(purchaseOrders);
	}

	/**
	 * 1.7 订单获取分录
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("first")
	@Override
	public List<PurorderItem> findItemLocal(PurchaseOrders purchaseOrders) throws Exception {
		//采购订单标识符
		String purOrderId = purchaseOrders.getPurorderid();

		PurorderItem purorderItem = new PurorderItem();

		purorderItem.setPurorderid(purOrderId);

		return mapper_Purorderitem.select(purorderItem);
	}

	/**
	 * 1.8 保存订单分录数据
	 */
	@DataSource("first")
	@Override
	public void saveItemLocal(PurorderItem purorderItem) throws Exception {

		mapper_Purorderitem.insert(purorderItem);

	}

	/**
	 * 1.9 更新订单分录数据
	 */
	@DataSource("first")
	@Override
	public void updateItemLocal(PurorderItem purorderItem) throws Exception {

		mapper_Purorderitem.updateByPrimaryKey(purorderItem);

	}

	/**
	 * 1.10 更新订单分录数据
	 */
	@DataSource("first")
	@Override
	public void updateOrderLocal(PurchaseOrders purchaseOrders) throws Exception {

		mapper_PurchaseOrders.updateByPrimaryKey(purchaseOrders);

	}


	/**
	 * 2.1 下文为王朝对应的CUID操作 单条订单数据新增
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public void saveInspurWC(PurchaseOrders purchaseOrders) throws Exception {

		mapper_PurchaseOrders.insert(purchaseOrders);
	}

	/**
	 * 2.2 订单数据批量新增 暂不用，因此没有具体实现
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public void saveInspurWC(List<PurchaseOrders> list_PurchaseOrders) throws Exception {

		//mapper_PurchaseOrders.insertList(list_PurchaseOrders);
	}

	/**
	 * 2.3 查找单条订单数据
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public PurchaseOrders findOneInspurWC(PurchaseOrders purchaseOrders) throws Exception {
		// TODO Auto-generated method stub
		return mapper_PurchaseOrders.selectOne(purchaseOrders);
	}

	/**
	 * 2.4 批量查找订单
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public List<PurchaseOrders> findInspurWC(PurchaseOrders purchaseOrders) throws Exception {

		return mapper_PurchaseOrders.select(purchaseOrders);
	}

	/**
	 * 2.5 单条订单数据删除
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public void clearInspurWC(PurchaseOrders purchaseOrders) throws Exception {
		mapper_PurchaseOrders.delete(purchaseOrders);

	}

	/**
	 * 2.6 多订单数据删除
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	@DataSource("wc")
	@Override
	public void deltInspurWC(List<PurchaseOrders> list_PurchaseOrders) throws Exception {

		if(null != list_PurchaseOrders && list_PurchaseOrders.size() > 0) {
			for(PurchaseOrders purchaseOrders : list_PurchaseOrders) {
				mapper_PurchaseOrders.delete(purchaseOrders);
			}
		}
	}

	/**
	 * 2.7 更新订单分录数据
	 */
	@DataSource("wc")
	@Override
	public void updateItemInspurWC(PurorderItem purorderItem) throws Exception {

		mapper_Purorderitem.updateByPrimaryKey(purorderItem);

	}

	/**
	 * 2.8 更新订单分录数据
	 */
	@DataSource("wc")
	@Override
	public void updateOrderInspurWC(PurchaseOrders purchaseOrders) throws Exception {

		//mapper_PurchaseOrders.updateByPrimaryKey(purchaseOrders);
		mapper_PurchaseOrders.updateByPrimaryKeySelective(purchaseOrders);

	}

	/**
	 * 2.9 按照订单id从远处浪潮中间表查找分录
	 */
	@DataSource("wc")
	@Override
	public List<PurorderItem> findItemByOrderIdInspurWC(PurchaseOrders purchaseOrders) throws Exception {
		//采购订单标识符
		String purOrderId = purchaseOrders.getPurorderid();

		PurorderItem purorderItem = new PurorderItem();

		purorderItem.setPurorderid(purOrderId);

		return mapper_Purorderitem.select(purorderItem);
	}

	/**
	 * 2.10 按照订单id从远处浪潮中间表查找分录 特别说明：需要按组织进行隔离
	 */
	@DataSource("wc")
	@Override
	public Map<String, List<PurorderItem>> findSplitOrgItemByOrderIdInspurWC(PurchaseOrders purchaseOrders)
			throws Exception {
		//采购订单标识符
		String purOrderId = purchaseOrders.getPurorderid();

		PurorderItem purorderItem = new PurorderItem();

		purorderItem.setPurorderid(purOrderId);

		List<PurorderItem> list_PurorderItem = mapper_Purorderitem.select(purorderItem);

		Map<String, List<PurorderItem>> map_PurorderItem = splitOrgItemMap(list_PurorderItem);

		return map_PurorderItem;
	}


	/**
	 * 3.1 下文为  利民  对应的CUID操作 单条订单数据新增
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public void saveInspurLM(PurchaseOrders purchaseOrders) throws Exception {

		mapper_PurchaseOrders.insert(purchaseOrders);
	}

	/**
	 * 3.2 订单数据批量新增 暂不用，因此没有具体实现
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public void saveInspurLM(List<PurchaseOrders> list_PurchaseOrders) throws Exception {

		//mapper_PurchaseOrders.insertList(list_PurchaseOrders);
	}

	/**
	 * 3.3 查找单条订单数据
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public PurchaseOrders findOneInspurLM(PurchaseOrders purchaseOrders) throws Exception {
		// TODO Auto-generated method stub
		return mapper_PurchaseOrders.selectOne(purchaseOrders);
	}

	/**
	 * 3.4 批量查找订单
	 * @param purchaseOrders
	 * @return
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public List<PurchaseOrders> findInspurLM(PurchaseOrders purchaseOrders) throws Exception {

		return mapper_PurchaseOrders.select(purchaseOrders);
	}

	/**
	 * 3.5 单条订单数据删除
	 * @param purchaseOrders
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public void clearInspurLM(PurchaseOrders purchaseOrders) throws Exception {
		mapper_PurchaseOrders.delete(purchaseOrders);

	}

	/**
	 * 3.6 多订单数据删除
	 * @param list_PurchaseOrders
	 * @throws Exception
	 */
	@DataSource("lm")
	@Override
	public void deltInspurLM(List<PurchaseOrders> list_PurchaseOrders) throws Exception {

		if(null != list_PurchaseOrders && list_PurchaseOrders.size() > 0) {
			for(PurchaseOrders purchaseOrders : list_PurchaseOrders) {
				mapper_PurchaseOrders.delete(purchaseOrders);
			}
		}
	}

	/**
	 * 3.7 更新订单分录数据
	 */
	@DataSource("lm")
	@Override
	public void updateItemInspurLM(PurorderItem purorderItem) throws Exception {

		mapper_Purorderitem.updateByPrimaryKey(purorderItem);

	}

	/**
	 * 3.8 更新订单分录数据
	 */
	@DataSource("lm")
	@Override
	public void updateOrderInspurLM(PurchaseOrders purchaseOrders) throws Exception {

		//mapper_PurchaseOrders.updateByPrimaryKey(purchaseOrders);
		mapper_PurchaseOrders.updateByPrimaryKeySelective(purchaseOrders);

	}

	/**
	 * 3.9 按照订单id从远处浪潮中间表查找分录
	 */
	@DataSource("lm")
	@Override
	public List<PurorderItem> findItemByOrderIdInspurLM(PurchaseOrders purchaseOrders) throws Exception {
		//采购订单标识符
		String purOrderId = purchaseOrders.getPurorderid();

		PurorderItem purorderItem = new PurorderItem();

		purorderItem.setPurorderid(purOrderId);

		return mapper_Purorderitem.select(purorderItem);
	}

	/**
	 * 2.10 按照订单id从远处浪潮中间表查找分录 特别说明：需要按组织进行隔离
	 */
	@DataSource("lm")
	@Override
	public Map<String, List<PurorderItem>> findSplitOrgItemByOrderIdInspurLM(PurchaseOrders purchaseOrders)
			throws Exception {
		//采购订单标识符
		String purOrderId = purchaseOrders.getPurorderid();

		PurorderItem purorderItem = new PurorderItem();

		purorderItem.setPurorderid(purOrderId);

		List<PurorderItem> list_PurorderItem = mapper_Purorderitem.select(purorderItem);

		Map<String, List<PurorderItem>> map_PurorderItem = splitOrgItemMap(list_PurorderItem);

		return map_PurorderItem;
	}

	/**
	 * 9 需要按组织进行隔离
	 * @param list_PurorderItem
	 * @return
	 */
	private Map<String, List<PurorderItem>> splitOrgItemMap(List<PurorderItem> list_PurorderItem) {
		
		Map<String, List<PurorderItem>> map_PurorderItem = new HashMap<String, List<PurorderItem>>();
		if(null != list_PurorderItem && list_PurorderItem.size() > 0) {
			for(PurorderItem purorderItem : list_PurorderItem) {
				String company = purorderItem.getCompanyid();
				if(null != company && !"".equals(company)) {
					//如果已经存在该组织的分组，则将新的purorderItem放入该分组集合List
					if(map_PurorderItem.containsKey(company)) {
						map_PurorderItem.get(company).add(purorderItem);
					//否则，新增该组织分组，并将新的purorderItem放入新增分组集合List
					} else {
						List<PurorderItem> list_Temp_PurorderItem = new ArrayList<PurorderItem>();
						list_Temp_PurorderItem.add(purorderItem);
						map_PurorderItem.put(company, list_Temp_PurorderItem);
					}
				}
			}
		}
		return map_PurorderItem;
	}
		
}
