package com.kingdee.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.DSFactory;
import com.cloud.webapi.K3CloudApiClient;
import com.kingdee.constant.InspurConstant;
import com.kingdee.model.po.Area;
import com.kingdee.service.AreaService;
import com.kingdee.util.K3CloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jambin
 * @description 同步地区
 * @date 2020/11/12 11:41
 */
@RestController
@RequestMapping
public class AreaController {
    @Autowired
    private AreaService areaService;

    /**
     * @param
     * @return void
     * @description: 同步地区
     * @date: 2020/11/12 13:33
     * @author: Jambin
     */
    @RequestMapping("/synArea")
    public void synArea() {
        System.out.println(new Date() + " 地区同步后台事务开始");
        synAreaFromCloud();
        synAreaFromWise();
    }

    /**
     * @return void
     * @description: 星空同步地区
     * @date: 2020/11/20 9:49
     * @author: Jambin
     */
    private void synAreaFromCloud() {
        List<List<Object>> response = new ArrayList<List<Object>>();
        String request = "{\"FormId\":\"BOS_ASSISTANTDATA_DETAIL\",\"FieldKeys\":\"FEntryID,fnumber,fdatavalue,FCreateDate,FModifyDate\",\"FilterString\":\"fid='2465ece5-86b0-4b5b-bf39-133c8b34d1c5'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try {
            K3CloudApiClient client = K3CloudUtil.getK3CloudApiClient();
            System.out.println(client);
            response = client.executeBillQuery(request);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < response.size(); i++) {
            Area area = new Area();
            try {
                List<Object> oneInfo = response.get(i);
                area.setVgsdm(InspurConstant.LMGSDM);
                area.setVfid("Cloud_" + oneInfo.get(0).toString());
                area.setVdm(oneInfo.get(1).toString());
                area.setVmc(oneInfo.get(2).toString());
                area.setDcjrq(DateUtil.parse(oneInfo.get(3).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                area.setDxgrq(DateUtil.parse(oneInfo.get(4).toString().replaceAll("T", " "), "yyyy-MM-dd hh:mm:ss"));
                areaService.saveLocal(area);
                areaService.saveInspur(area);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * @return void
     * @description: wise同步地区
     * @date: 2020/11/20 9:49
     * @author: Jambin
     */
    private void synAreaFromWise() {
        //广大纸业数据库源
        DataSource dataSource = DSFactory.get("group_gdzy");
        //查询辅助资料 类型为26 区域的数据
        List<Entity> result = null;
        try {
            result = Db.use(dataSource).query(" SELECT UUID, FInterID,FNAME FROM t_SubMessage   WHERE FTYPEID='26' ");
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        for (int i = 0; i < result.size(); i++) {
            try {
                Area area = new Area();
                area.setVgsdm(InspurConstant.GDZYGSDM);
                area.setVfid("Wise_" + result.get(i).get("UUID"));
                area.setVdm(result.get(i).get("FInterID").toString());
                area.setVmc(result.get(i).get("FNAME").toString());
                area.setDcjrq(new Date());
                area.setDxgrq(new Date());

                areaService.saveLocal(area);
                areaService.saveInspur(area);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
