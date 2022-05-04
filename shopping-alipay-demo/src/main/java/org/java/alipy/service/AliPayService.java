package org.java.alipy.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import org.java.alipy.config.AliPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
@EnableConfigurationProperties(AliPayConfig.class)
public class AliPayService {



    @Autowired
    private AliPayConfig aliPayConfig;
    /**
     * 调用支付宝的支付接口，进行支付，并检查返回值
     * @param id 订单id
     * @param price 订单价格
     * @param title 订单标题
     */
    public String pay(String id, String price, String title) {

        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getServerUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),"json","UTF-8",aliPayConfig.getPublicKey(),"RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        request.setReturnUrl(aliPayConfig.getReturnUrl());
        JSONObject bizContent = new JSONObject();
        //购物车订单id
        bizContent.put("out_trade_no",id );
        //总金额
        bizContent.put("total_amount",price);
        //标题
        bizContent.put("subject", title);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");


        request.setBizContent(bizContent.toString());
        AlipayTradePagePayResponse response = null;
        //创建一个String对象接受传回来的body
        String form =null;
        try {
            response = alipayClient.pageExecute(request);
            form = response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return  form;
    }

    /**
     * 根据订单ID查询返回用户订单状态的JSON
     * @param out_trade_no 订单ID
     * @return
     */
    public String query(String out_trade_no ){
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getServerUrl(),aliPayConfig.getAppId(),aliPayConfig.getPrivateKey(),"json","UTF-8",aliPayConfig.getPublicKey(),"RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        //bizContent.put("trade_no", "2014112611001004680073956707");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        String body=null;
        try {
            response = alipayClient.execute(request);
            body=response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return  body;
    }


}
