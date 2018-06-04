package com.jd.jr.pmock.agent;

import com.jd.jr.pmock.agent.transform.AppTransformer;

import java.lang.instrument.Instrumentation;

/**
 * User: yangkuan@jd.com
 * Date: 18-5-29
 * Time: 下午7:37
 */
public class AgentMain {
    public static void premain(String agentArgs, Instrumentation inst) {
        AppTransformer appTransformer = new AppTransformer();
        try {
            inst.addTransformer(appTransformer);
        } catch (Throwable e) {
            try {
                inst.removeTransformer(appTransformer);
            } catch (Throwable e1) {
            }
        }

    }

}
