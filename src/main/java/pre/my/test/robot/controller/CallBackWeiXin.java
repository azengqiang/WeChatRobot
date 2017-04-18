package pre.my.test.robot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pre.my.test.robot.handle.EventMessageHandle;
import pre.my.test.robot.handle.LocationMessageHandle;
import pre.my.test.robot.handle.TextMessageHandle;
import pre.my.test.robot.util.CheckUtil;
import pre.my.test.robot.util.Constants;
import pre.my.test.robot.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 微信回调类，接收微信服务器用户数据，进行相应反馈
 * Author:qiang.zeng@hand-china.com on 2017/1/12.
 */
@Controller
@RequestMapping(value = "/robot")
public class CallBackWeiXin {
    private static final Logger logger = LoggerFactory.getLogger(CallBackWeiXin.class);
    @Autowired
    EventMessageHandle eventMessageHandle;
    @Autowired
    TextMessageHandle textMessageHandle;
    @Autowired
    LocationMessageHandle locationMessageHandle;

    //接口认证
    @RequestMapping(method = RequestMethod.GET)
    public void checkSignature(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = response.getWriter();
        if (signature != null) {
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                logger.debug("微信接口验证成功");
                out.print(echostr);
            }
        } else {
            logger.debug("微信接口验证失败");
        }

    }

    //消息处理
    @RequestMapping(method = RequestMethod.POST)
    private void messageHandle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //将请求的xml数据封装为map
        Map<String, String> requestMap = MessageUtil.xmlToMap(request);
        String fromUserName = requestMap.get("FromUserName");
        String toUserName = requestMap.get("ToUserName");
        String msgType = requestMap.get("MsgType");
        String content = requestMap.get("Content");
        String createTime = requestMap.get("CreateTime");

        PrintWriter out = response.getWriter();
        String respMessage = " ";
        //接收消息，进行处理
        switch (msgType) {
            case Constants.MSG_TYPE_TEXT:
                //文本消息处理
                respMessage = textMessageHandle.textMessageHandle(fromUserName, toUserName, content);
                break;
            case Constants.MSG_TYPE_EVENT:
                //事件处理
                respMessage = eventMessageHandle.eventMessageHandle(fromUserName, toUserName, content, createTime, requestMap);
                break;
            case Constants.MSG_TYPE_LOCATION:
                //地理位置处理
                respMessage = locationMessageHandle.locationMessageHandle(fromUserName, toUserName, requestMap);
                break;
            case Constants.MSG_TYPE_IMAGE:
                break;
            case Constants.MSG_TYPE_VOICE:
                break;
            case Constants.MSG_TYPE_VIDEO:
                break;
            case Constants.MSG_TYPE_SHORT_VIDEO:
                break;
            case Constants.MSG_TYPE_LINK:
                break;
            default:
                break;
        }
        //返回数据
        out.print(respMessage);
    }
}