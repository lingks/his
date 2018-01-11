package net.medcrm.yjb.his.eureka.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.medcrm.yjb.his.eureka.service.TxService;
import net.medcrm.yjb.his.eureka.service.model.TxState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * Created by linsheng on 2017/7/1.
 */
@Controller
public class IndexController {

    @Autowired
    private TxService txService;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        TxState state = txService.getState();
        request.setAttribute("info", state);
        return "index";
    }


    @RequestMapping("/json")
    @ResponseBody
    public String json() {
        //test//
        Set<String> keys =  redisTemplate.keys("tx_manager_notify_*");
        ValueOperations<String,String> value =  redisTemplate.opsForValue();
        JSONArray jsonArray = new JSONArray();
        for(String key:keys){
            String json = value.get(key);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key",key);
            jsonObject.put("value", JSONObject.parse(json));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

}