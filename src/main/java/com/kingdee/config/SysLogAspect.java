package com.kingdee.config;

/**
 * @author 
 * @title: SysLogAspect
 * @projectName door
 * @description: TODO
 * @date 2020/11/39:00 上午
 */
import com.alibaba.fastjson.JSON;
import com.kingdee.model.vo.Result;
import com.kingdee.model.vo.Visitlog;
import com.kingdee.service.LogService;
import com.kingdee.util.InputMDC;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志：切面处理类
 */
//@Configuration
//@Lazy(false)
@Aspect
@Component
public class SysLogAspect {

    @Resource
    private LogService logService;

    private Sid sid;

    /**
     * 日志前置通知，为了可以打印出用户信息
     */
    @Before("execution(* com.kingdee.util..LogUtils*.*(..))")
    public void qzLog()  {
        InputMDC.putMDC();
    }


    //定义切点 @Pointcut
    //在注解的位置切入代码                com.kingdee.config.MyLog
    @Pointcut("@annotation(com.kingdee.config.MyLog)")
    public void logPoinCut() {
    }

    //切面 配置通知
    @Around(value = "logPoinCut()")
    public Object saveSysLog(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();


        System.out.println("切面。。。。。");
        //保存日志
        Visitlog sysLog = new Visitlog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);


        // 获取开始时间
        Long startTime = System.currentTimeMillis();
        // 获取返回结果集
        Object obj = joinPoint.proceed(joinPoint.getArgs());
        // 获取方法执行时间
        Long endTime= System.currentTimeMillis() - startTime;

        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JSON.toJSONString(args);
        if(obj instanceof String){
            sysLog.setType(0);
            sysLog.setMessage((String) obj);
            sysLog.setParams(params  + obj);

        }else if (obj instanceof Result){
            Result result = (Result) obj;
            sysLog.setType(result.getType());
            sysLog.setMessage(result.getResult());
            sysLog.setRequest(result.getRequest());
            sysLog.setParams(params  + result.getResult());
        }
        //sysLog.setParams(params  + result.getResult());

        sysLog.setId(sid.nextShort());
        sysLog.setTime(new Date());
        sysLog.setRuntime(endTime.toString()+"ms");
        //获取用户ip地址
        sysLog.setIp(getRemoteHost(request));

        //调用service保存SysLog实体类到数据库
        logService.save(sysLog);
        return obj;
    }



    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 入参数据
     * @param joinPoint
     * @param request
     * @return
     */
    private String preHandle(ProceedingJoinPoint joinPoint, HttpServletRequest request) {

        String reqParam = "";
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations = targetMethod.getAnnotations();
        for (Annotation annotation : annotations) {
            //此处可以改成自定义的注解
            if (annotation.annotationType().equals(RequestMapping.class)) {
                reqParam = JSON.toJSONString(request.getParameterMap());
                break;
            }
        }
        return reqParam;
    }
}
