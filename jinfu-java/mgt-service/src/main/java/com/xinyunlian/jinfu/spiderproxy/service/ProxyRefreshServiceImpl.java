package com.xinyunlian.jinfu.spiderproxy.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.ecs.model.v20140526.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Created by bright on 2017/1/8.
 */
@Service
public class ProxyRefreshServiceImpl implements ProxyRefreshService {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProxyRefreshServiceImpl.class);

    @Value("${aliyun.ak}")
    private String ak;

    @Value("${aliyun.aks}")
    private String aks;

    @Value("${aliyun.region}")
    private String region;

    @Value("${aliyun.ecs.id}")
    private String ecsId;

    @Value("${aliyun.eip.payType}")
    private String eipPayType;

    @Value("${aliyun.eip.bandwidth}")
    private String eipBandWidth;

    @Override
    public String getNewProxyIp() throws BizServiceException {
        if(!AppConfigUtil.isProdEnv()){
            return null;
        }
        IClientProfile profile = DefaultProfile.getProfile(region, ak, aks);
        DescribeInstancesRequest describe = new DescribeInstancesRequest();
        describe.setInstanceName(ecsId);
        IAcsClient client = new DefaultAcsClient(profile);
        String result = "";
        try {
            DescribeInstancesResponse response = client.getAcsResponse(describe);
            List<DescribeInstancesResponse.Instance> instances = response.getInstances();
            if (instances.size() > 0) {
                // has proxy server
                DescribeInstancesResponse.Instance instance = instances.get(0);
                String serverId = instance.getInstanceId();
                DescribeInstancesResponse.Instance.EipAddress eipAddresis = instance.getEipAddress();
                if(Objects.nonNull(eipAddresis) && StringUtils.isNotEmpty(eipAddresis.getAllocationId())) {
                    // has eip associated
                    String allocationId = eipAddresis.getAllocationId();
                    UnassociateEipAddressRequest unassociateEipAddressRequest = new UnassociateEipAddressRequest();
                    unassociateEipAddressRequest.setAllocationId(allocationId);
                    unassociateEipAddressRequest.setInstanceId(serverId);
                    UnassociateEipAddressResponse unassociateEipAddressResponse = client.getAcsResponse(unassociateEipAddressRequest);
                    // Query eip status for 5 times
                    for (int i = 0; i < 5; i++) {
                        DescribeEipAddressesRequest describeEipAddressesRequest = new DescribeEipAddressesRequest();
                        describeEipAddressesRequest.setAllocationId(allocationId);
                        DescribeEipAddressesResponse describeEipAddressesResponse = client.getAcsResponse(describeEipAddressesRequest);
                        List<DescribeEipAddressesResponse.EipAddress> eipAddresses = describeEipAddressesResponse.getEipAddresses();
                        if(eipAddresses.size() > 0){
                            DescribeEipAddressesResponse.EipAddress eip = eipAddresses.get(0);
                            if("Available".equals(eip.getStatus())) {
                                // if eip status is "Available", then go release
                                break;
                            }
                            try {
                                LOGGER.info("The EIP{} is still unassociating...", eip.getIpAddress());
                                // waiting 2s for next retry
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                // Ignore
                            }
                        }
                    }
                    ReleaseEipAddressRequest releaseEipAddressRequest = new ReleaseEipAddressRequest();
                    releaseEipAddressRequest.setAllocationId(allocationId);
                    ReleaseEipAddressResponse releaseEipAddressResponse = client.getAcsResponse(releaseEipAddressRequest);
                }
                AllocateEipAddressRequest allocateEipAddressRequest = new AllocateEipAddressRequest();
                allocateEipAddressRequest.setInternetChargeType(eipPayType);
                allocateEipAddressRequest.setBandwidth(eipBandWidth);
                AllocateEipAddressResponse allocateEipAddressResponse = client.getAcsResponse(allocateEipAddressRequest);
                String allocationId = allocateEipAddressResponse.getAllocationId();
                AssociateEipAddressRequest associateEipAddressRequest = new AssociateEipAddressRequest();
                associateEipAddressRequest.setAllocationId(allocationId);
                associateEipAddressRequest.setInstanceId(serverId);
                AssociateEipAddressResponse associateEipAddressResponse = client.getAcsResponse(associateEipAddressRequest);
                result = allocateEipAddressResponse.getEipAddress();
            }
        } catch (Exception e) {
            LOGGER.error("调用Aliyun SDK失败", e);
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "调用Aliyun SDK失败", e);
        }
        return result;
    }
}
