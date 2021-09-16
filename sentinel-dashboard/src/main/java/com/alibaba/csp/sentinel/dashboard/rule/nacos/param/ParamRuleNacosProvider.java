package com.alibaba.csp.sentinel.dashboard.rule.nacos.param;

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxu
 * @Description nacos流量控制
 * @date 2021/9/4-4:54 下午
 */

@Component("paramRuleNacosProvider")
public class ParamRuleNacosProvider implements DynamicRuleProvider<List<ParamFlowRule>> {

    @Autowired
    private ConfigService configService;

    @Resource
    private Converter<String, List<ParamFlowRule>> paramRuleEntityDecoder;

    @Override
    public List<ParamFlowRule> getRules(String appName) throws Exception {
        String rules = configService.getConfig(appName + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isEmpty(rules)) {
            return new ArrayList<>();
        }
        return paramRuleEntityDecoder.convert(rules);
    }
}
