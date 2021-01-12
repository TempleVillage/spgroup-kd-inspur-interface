package com.kingdee.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.kingdee.datasources.annotation.DataSource;
import com.kingdee.service.PurchasePlansService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;

import com.kingdee.model.po.PurchasePlans;

@Service
public class PurchasePlansServiceImpl implements PurchasePlansService {

	@Resource
    private com.kingdee.dao.PurchasePlansMapper mapper_PurchasePlans;



	@DataSource("first")
	@Override
	public void saveLocal(PurchasePlans purchasePlans) throws Exception {

		mapper_PurchasePlans.insert(purchasePlans);
	}

	@DataSource("first")
	@Override
	public void saveLocal(List<PurchasePlans> list_PurchasePlans) throws Exception {
		if(null != list_PurchasePlans) {
			for(PurchasePlans purchasePlans : list_PurchasePlans) {
				mapper_PurchasePlans.insert(purchasePlans);
			}
		}
	}

	@DataSource("first")
	@Override
	public List<PurchasePlans> findLocal(PurchasePlans purchasePlans) throws Exception {

		return mapper_PurchasePlans.select(purchasePlans);
	}

	@DataSource("first")
	@Override
	public void deltLocal(List<PurchasePlans> list_PurchasePlans) throws Exception {

		if(null != list_PurchasePlans && list_PurchasePlans.size() > 0) {
			for(PurchasePlans purchasePlans : list_PurchasePlans) {
				mapper_PurchasePlans.delete(purchasePlans);
			}
		}
	}

	@DataSource("first")
	@Override
	public void clearLocal(PurchasePlans purchasePlans) throws Exception {

		//mapper_PurchasePlans.delete(purchasePlans);
		mapper_PurchasePlans.deleteByPrimaryKey(purchasePlans.getkPurchaseplans());

	}


	@DataSource("wc")
	@Override
	public void saveInspurWC(PurchasePlans purchasePlans) throws Exception {

		mapper_PurchasePlans.insert(purchasePlans);
	}


	@DataSource("wc")
	@Override
	public void saveInspurWC(List<PurchasePlans> list_PurchasePlans) throws Exception {
		if(null != list_PurchasePlans) {
			for(PurchasePlans purchasePlans : list_PurchasePlans) {
				mapper_PurchasePlans.insert(purchasePlans);
			}
		}
	}

	@DataSource("wc")
	@Override
	public List<PurchasePlans> findInspurWC(PurchasePlans purchasePlans) throws Exception {
		return mapper_PurchasePlans.select(purchasePlans);
	}

	@DataSource("wc")
	@Override
	public PurchasePlans findOneInspurWC(PurchasePlans purchasePlans) throws Exception {
		return mapper_PurchasePlans.selectOne(purchasePlans);
	}

	@DataSource("wc")
	@Override
	public void deltInspurWC(List<PurchasePlans> list_PurchasePlans) throws Exception {
		if(null != list_PurchasePlans) {
			for(PurchasePlans purchasePlans : list_PurchasePlans) {
				mapper_PurchasePlans.delete(purchasePlans);
			}
		}
	}

	@DataSource("wc")
	@Override
	public void clearInspurWC(PurchasePlans purchasePlans) throws Exception {
		//mapper_PurchasePlans.delete(purchasePlans);
		mapper_PurchasePlans.deleteByPrimaryKey(purchasePlans.getkPurchaseplans());
	}

		
}
