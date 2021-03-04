package com.xzdt.statistics.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xzdt.statistics.JDBCHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
public class DataController {

    String num = "";
    String num1 = "";

    @GetMapping("/statistics/v1/number")
    public String getNumber() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String d = simpleDateFormat.format(date);
        String sql = "select b.dept_unid as id, b.dept_name as deptname,count(1) as number from apas_info a left join ucap.ucap_dept b on a.receive_deptid = b.dept_unid where a.receive_time like '"+d+"%' and a.accept_deptid like '001003036002%' group by accept_deptid";
        num = getResult(sql);
        return num;
    }

    @GetMapping("/statistics/v1/haochaping")
    public String getNumber2() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String d = simpleDateFormat.format(date);
        String sql = "select DEPT_UNID as id, DEPT_NAME as deptname, count(1) as number from pre_evaluate_public_2019 where EVALUATE_TIME like '"+d+"%' AND SATISFACTION_EVALUATE = '5' and EVALUATOR_NAME not like '%测试%'  and DEPT_UNID like '001003036002%' group by DEPT_NAME , DEPT_UNID order by DEPT_UNID";
        Connection connection = DriverManager.getConnection("jdbc:mysql://172.17.13.1:3306/dt_front", "hn_dept81", "hn_dept81_2017");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        num1 = getResult(sql, resultSet);
        return num1;
    }


    @GetMapping("/statistics/v1/baifenbi")
    public String getNumber3() throws SQLException {
        if("".equals(num) || "".equals(num1)){
            num = getNumber();
            num1 = getNumber2();
        }
        JSONObject numJson = JSON.parseObject(num);
        JSONObject num1Json = JSON.parseObject(num1);

        JSONArray numArr = numJson.getJSONArray("data");
        JSONArray num1Arr = num1Json.getJSONArray("data");
        JSONArray res = new JSONArray();
        for(int i = 0; i < numArr.size(); i++){
            JSONObject ob = new JSONObject();
            String id = numArr.getJSONObject(i).getString("id");
            String num2 = numArr.getJSONObject(i).getString("number");
            String deptname = numArr.getJSONObject(i).getString("deptname");
            for(int j = 1; j < num1Arr.size(); j++){
                String id1 = num1Arr.getJSONObject(j).getString("id");
                if(id.equals(id1)){
                    ob.put("deptname", deptname);
                    String num3 = num1Arr.getJSONObject(j).getString("number");
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    numberFormat.setMaximumFractionDigits(2);
                    String result = numberFormat.format((float)Integer.valueOf(num3)/(float)Integer.valueOf(num2)*100);
                    ob.put("number", result + "%");
                    res.add(ob);
                    break;
                }

            }
        }
        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "");
        json.put("count", res.size());
        json.put("data", res);

        return json.toString();
    }


    private String getResult(String sql, ResultSet... res) throws SQLException {
        ResultSet resultSet;
        JDBCHelper instance = null;
        Connection connection = null;
        if(res.length == 0){
            instance = JDBCHelper.getInstance();
            connection = instance.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } else {
            resultSet = res[0];
        }

        JSONObject json = new JSONObject();
        json.put("code", 0);
        json.put("msg", "");
        int size = 0;
        JSONArray array = new JSONArray();
        while(resultSet.next()){
            JSONObject d1 = new JSONObject();
            d1.put("id", resultSet.getString(1));
            d1.put("deptname", resultSet.getString(2));
            d1.put("number", resultSet.getString(3));
            array.add(d1);
            size ++;
        }
        if(connection != null){
            instance.returnConnection(connection);
        }
        json.put("count", size);
        json.put("data", array);
        return json.toString();
    }
}
