package demo.web.controller;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.common.RandomValidateCode;
import demo.common.ResponseUtil;


@Controller
@RequestMapping(value="/user")
public class ImageGenController {

    //登录获取验证码
    @RequestMapping(value = "/code", method = RequestMethod.GET)
    @ResponseBody
    public String getSysManageLoginCode(HttpServletResponse response,
            HttpServletRequest request) {
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Set-Cookie", "name=value; HttpOnly");//设置HttpOnly属性,防止Xss攻击
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        try {
            randomValidateCode.getRandcode(request, response,"imagecode");// 输出图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //验证码验证
    @RequestMapping(value = "/checkimagecode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkTcode(HttpServletRequest request,HttpServletResponse response) {
        String validateCode = request.getParameter("validateCode");
        String code = null;
        //1:获取cookie里面的验证码信息
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("imagecode".equals(cookie.getName())) {
                code = cookie.getValue();
                break;
            }
        }
        //1:获取session验证码的信息
        //String code1 = (String) request.getSession().getAttribute("");
        //2:判断验证码是否正确
        if(!StringUtils.isEmpty(validateCode) && validateCode.equals(code)){
        	return ResponseUtil.sendResult(true,"验证码验证成功");   
        }
        return ResponseUtil.sendResult(false,"验证码验证失败！");
        //这里我没有进行字母大小模糊的验证处理，感兴趣的你可以去试一下！
    }

}