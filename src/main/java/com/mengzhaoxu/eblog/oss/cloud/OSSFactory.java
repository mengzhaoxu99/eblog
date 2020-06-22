/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.mengzhaoxu.eblog.oss.cloud;


/**
 * 文件上传Factory
 *
 * @author Mark sunlightcs@gmail.com
 */
public final class OSSFactory {
//    private static SysConfigService sysConfigService;
//
//    static {
//        io.renren.modules.oss.cloud.OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
//    }

    public static CloudStorageService build(){
        //获取云存储配置信息
//        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

//        if(config.getType() == Constant.CloudService.QINIU.getValue()){
//            return new QiniuCloudStorageService(config);
//        }else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
//            return new AliyunCloudStorageService(config);
//        }else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
//            return new QcloudCloudStorageService(config);
//        }


        /**
         * qiniu:
         *   accessKey: kDBs-jbChhKuqs1ZAAzwOj6EAb7h1KTtL8-zSwOu
         *   secretKey: Ce3i0nQWZV-fdrHWYhpow6NaA3hGNJKeyX7zEJgB
         *   Prefix: upload
         *   # 对象储存
         *   bucket: filemzx # 空间名称
         *   domain: qc7zz5ce9.bkt.clouddn.com # 访问域名
         *
         *
         *    private String qiniuDomain;
         *     //七牛路径前缀
         *     private String qiniuPrefix;
         *     //七牛ACCESS_KEY
         * //    @NotBlank(message="七牛AccessKey不能为空", groups = QiniuGroup.class)
         *     private String qiniuAccessKey;
         *     //七牛SECRET_KEY
         * //    @NotBlank(message="七牛SecretKey不能为空", groups = QiniuGroup.class)
         *     private String qiniuSecretKey;
         *     //七牛存储空间名
         * //    @NotBlank(message="七牛空间名不能为空", groups = QiniuGroup.class)
         *     private String qiniuBucketName;
         */
        CloudStorageConfig config = new CloudStorageConfig();
        config.setQiniuAccessKey("kDBs-jbChhKuqs1ZAAzwOj6EAb7h1KTtL8-zSwOu");
        config.setQiniuSecretKey("Ce3i0nQWZV-fdrHWYhpow6NaA3hGNJKeyX7zEJgB");
        config.setQiniuBucketName("filemzx");
        config.setQiniuPrefix("upload");
        config.setQiniuDomain("qc7zz5ce9.bkt.clouddn.com");
        return new QiniuCloudStorageService(config);
    }

}
