package com.mrlv.filter;

import com.mrlv.common.constant.ResultStatusCode;
import com.mrlv.common.utils.ResultMsg;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


/**
 * 网关分发过滤器
 * 功能： 校验token，加解密，拆包操作
 */
@Component
public class PreAccessZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(PreAccessZuulFilter.class);

    /**
     * run方法就是过滤器的具体逻辑。
     * return 可以返回任意的对象，当前实现忽略。（spring-cloud-zuul官方解释）
     * 直接返回null即可。
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String method = request.getMethod();

        String remoteIp = request.getHeader("Origin");
        ctx.getResponse().setHeader("Access-Control-Allow-Origin", remoteIp);
        ctx.getResponse().setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        if ("OPTIONS".equalsIgnoreCase(method)) {
            //CORS跨域请求第一次为OPTIONS
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(204);
            return null;
        }
        String uri = request.getRequestURI();
        ResultStatusCode status = ResultStatusCode.OK;

        //可以对请求进行处理。是否通过，赋值于 status

        if (status.getCode() == 200) {
            ctx.setSendZuulResponse(true);
        } else {
            ResultMsg resultMsg = new ResultMsg(status, null);
            logger.info("请求 " + request.getRequestURI() + " 结果：" + status.getMsg());
            //中止转发，直接返回结果
            ctx.setSendZuulResponse(false);
            ctx.setResponseBody(resultMsg.toString());
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setHeader("Content-type", "text/html;charset=UTF-8");
        }
        return null;
    }

    /**
     * 返回boolean类型。代表当前filter是否生效。
     * 默认值为false。
     * 返回true代表开启filter。
     */
    @Override
    public boolean shouldFilter() {
        //可以通过获取请求参数来判断是否启动该过滤器
        //return RequestContext.getCurrentContext().getBoolean(MultiTenantZuulFilter.IS_PREACCESSFILTER);
        return true;
    }

    /**
     * pre - 前置过滤
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 同种类的过滤器的执行顺序。
     * 按照返回值的自然升序执行。
     */
    @Override
    public int filterOrder() {
        return 0;
    }

}
