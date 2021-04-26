package com.github.shoothzj.demo.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

/**
 * @author hezhangjian
 */
@Slf4j
public class AliService {

    public static final Ali ali = AliConfigReader.getAli();

    public static final DefaultProfile profile = DefaultProfile.getProfile(ali.getRegion(), ali.getAk(), ali.getSk());

    public static final IAcsClient client = new DefaultAcsClient(profile);

}
