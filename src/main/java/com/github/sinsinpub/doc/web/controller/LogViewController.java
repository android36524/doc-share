package com.github.sinsinpub.doc.web.controller;

import java.util.List;

import com.github.sinsinpub.doc.web.RoutesDefines;
import com.github.sinsinpub.doc.web.controller.base.JsonAwareController;
import com.github.sinsinpub.doc.web.model.AuditLog;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.NoUrlPara;
import com.jfinal.plugin.activerecord.Page;

/**
 * 映射到`/api/log`处理审计日志查询请求的控制器.
 * 
 * @see RoutesDefines
 * @author sin_sin
 * @version $Date: Oct 8, 2015 $
 */
public class LogViewController extends JsonAwareController {

    @Before(NoUrlPara.class)
    public void index() {
        renderJson("status", "ok");
    }

    // 要限制只能用HTTP GET方法时：
    // Jetty默认OPTIONS显示接受GET,HEAD,POST,OPTIONS，其实PUT、DELETE这些方法可以用。
    // 想用它们来做RESTful API可参考Restful拦截器，或者直接写它们对应的方法限制拦截器即可。
    @Before(GET.class)
    public void access() {
        if (getPara() == null) {
            List<AuditLog> list = AuditLog.REPO.fetchAll();
            renderJson(list);
        } else {
            int pageNumber = getParaToInt(0);
            int pageSize = getParaToInt(1, 10);
            Page<AuditLog> page = AuditLog.REPO.fetchPage(pageNumber, pageSize, null,
                    (Object[]) null);
            renderJson(page);
        }
    }

}
