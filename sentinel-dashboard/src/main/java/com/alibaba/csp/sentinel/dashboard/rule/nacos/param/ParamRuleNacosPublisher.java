package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhaoxu
 * @Description nacos流量控制
 * @date 2021/9/4-4:54 下午
 */

@Component("paramRuleNacosPublisher")
public class ParamRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRule>> {

    @Autowired
    private ConfigService configService;

    @Resource
    private Converter<List<ParamFlowRule>, String> paramFlowRuleEntityEncoder;
    @Override
    public void publish(String appName, List<ParamFlowRule> rules) throws Exception {
        AssertUtil.notEmpty(appName, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        configService.publishConfig(appName + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, paramFlowRuleEntityEncoder.convert(rules));
    }
}
