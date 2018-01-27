package com.xinyunlian.jinfu.factory;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.domain.CommandRequest;
import com.xinyunlian.jinfu.executer.CommandExecuter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ExecuterFactory {

//    private static CommandExecuter executer;

    private static ConcurrentMap<Class, CommandExecuter> cachedBeans = new ConcurrentHashMap<>();

    public static CommandExecuter getExecuter(CommandRequest<?> request) {
//        if (null == executer) {
//            if (AppConfigUtil.isDevEnv()) {
//                executer = new MockExecuter();
//            } else {
        CommandExecuter executer = cachedBeans.get(request.getClass());
        if(executer == null) {
            Map<String, CommandExecuter> beans = ApplicationContextUtil.getBeans(CommandExecuter.class);
            for (CommandExecuter commandExecuter : beans.values()) {
                if (commandExecuter.isSupportedRequestType(request.getClass())) {
                    executer = commandExecuter;
                    break;
                }
            }
            if(executer != null) {
                cachedBeans.putIfAbsent(request.getClass(), executer);
            }
        }
        if (null == executer){
            throw new NoSuchBeanDefinitionException(CommandExecuter.class);
        }
//            }
//        }

        return executer;
    }

}
