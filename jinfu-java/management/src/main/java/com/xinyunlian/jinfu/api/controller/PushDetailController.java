package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.push.dto.PushMessageDto;
import com.xinyunlian.jinfu.push.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by apple on 2017/1/11.
 */
@Controller
@RequestMapping(value = "open-api/push")
public class PushDetailController {

    @Autowired
    private PushService pushService;

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public Object detail(HttpServletResponse res,@RequestParam Long messageId) {
        PushMessageDto dto = pushService.detail(messageId);

        PrintWriter out = null;
        res.setContentType("text/html");
        try {
            out = res.getWriter();
            out.append(JsonUtil.toJson(dto));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                out.close();
        }
        return null;
    }
}
