package com.mrlv.filter;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.mrlv.common.constant.ResultStatusCode;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class PostAccessZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(PostAccessZuulFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 对返回的数据进行加密
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
//        RequestContext ctx = RequestContext.getCurrentContext();
//        String aesKey = "";
//        String statsApp = "";
//        Map<String, List<String>> reqMap = ctx.getRequestQueryParams();
//        List<String> aesListStr = null;
//        List<String> statsAppListStr = null;
//        try {
//            aesListStr = reqMap.get("aesKey"); // 获取到encKey
//            statsAppListStr = reqMap.get("statsApp");
//        } catch (NullPointerException e1) { // 没有加密信息，直接返回
//            return null;
//        }
//
//
//        if (aesListStr != null && !aesListStr.isEmpty()) {
//            aesKey = aesListStr.get(0);
//        }
//        if (statsAppListStr != null && !statsAppListStr.isEmpty()) {
//            statsApp = statsAppListStr.get(0);
//
//            HttpServletRequest request = ctx.getRequest();
//
//            //获取客户端真实IP
//            String clientIp = getIpAddr(request);
//
//            StatsInfoVo vo = JSONObject.parseObject(statsApp, StatsInfoVo.class);
//            vo.setContentLength(ctx.getOriginContentLength());
//            vo.setSource(clientIp);
//            String factoryNumber = (String) ThreadLocalMap.get(TenantConstants.TENANT_KEY);
//            TenantMqMessageDto<String> messageDto = new TenantMqMessageDto<>();
//            messageDto.setTenantId(factoryNumber);
//            messageDto.setTopic("stats-app");
//            messageDto.setData(JSONObject.toJSONString(vo));
////			rabbitMqSender.send("stats-app", JSONObject.toJSONString(vo));
//            rabbitMqSender.send(messageDto);
//        }
//        if (StringUtils.isNotBlank(aesKey)) { // 判断是否需要加密
//            try {
//                // 获取到数据
//                InputStream stream = ctx.getResponseDataStream();
//                String dataJson = StreamUtils.copyToString(stream,
//                        Charset.forName("UTF-8"));
//
//                // 加密dataJson数据 返回
//                dataJson = DecryptUtil.getEncryptData(dataJson, aesKey);
//                ctx.setResponseBody(dataJson);
//
//                return ResultStatusCode.OK;
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("返回数据加密异常！！错误信息： {}", ExceptionUtil.getMessage(e));
//                return ResultStatusCode.SYSTEM_ERR;
//            }
//        }
        return null;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     * @return ip
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
