package org.java.alipy.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sun.deploy.net.HttpResponse;
import org.java.alipy.config.AliPayConfig;
import org.java.alipy.service.AliPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@EnableConfigurationProperties(AliPayConfig.class)
public class AlipayController {


    @Autowired
    private AliPayService aliPayService;

    @Autowired
    private AliPayConfig aliPayConfig;



    /**
     * 测试项目能否正常运行
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 创建订单，调用支付宝接口进行支付
     * @return
     */
    @PostMapping("/create")
    public String create(String id , String price , String title, Model model){

        String form =aliPayService.pay(id,price,title);
        model.addAttribute("form",form);

        return "pay";
    }

    @GetMapping("/return")
    public String success(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, AlipayApiException {

        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        //Iterator 迭代器
        //创建一个Iterator迭代器，将其赋值为requestParams.keySet().iterator()
        //判断条件是该Iterator还有下一个值
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            //iter.next取出的是map的key
            String name = (String) iter.next();
            //通过key来查找value
            String[] values = (String[]) requestParams.get(name);
            //创建一个空的字符串接受value
            String valueStr = "";
            //循环遍历
            for (int i = 0; i < values.length; i++) {
                //如果当前下标等于数组长度-1，代表没有元素了，则不需要使用逗号分隔
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            //把遍历后的数值转换为String，存入map
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfig.getPublicKey(), aliPayConfig.getCharSet(), aliPayConfig.getSignType()); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            //out.println("trade_no:"+trade_no+"<br/>out_trade_no:"+out_trade_no+"<br/>total_amount:"+total_amount);
            model.addAttribute("out_trade_no",out_trade_no);//商户订单号
            model.addAttribute("trade_no",trade_no);//支付宝交易账号
            model.addAttribute("total_amount",total_amount);//付款金额
        }else {
            out.println("验签失败");
        }
        return "/query";
    }

}
