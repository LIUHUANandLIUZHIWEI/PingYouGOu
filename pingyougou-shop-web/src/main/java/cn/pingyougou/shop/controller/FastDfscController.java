package cn.pingyougou.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.pingyougouUtiles.FastDfsUtiles;
import entryPingYouGou.ErrorPingYouGou;
@RestController
public class FastDfscController {
	@Value("${FASTDFS}")
	private String fileValue;
	
	@RequestMapping("/imageOn.do")
	public ErrorPingYouGou iamgeOn(MultipartFile file) {
		//获取文件扩展名
		String or=file.getOriginalFilename();
		String orString=or.substring(or.lastIndexOf(".")+1);
		
	//创建一个FastFDFS的客户端
		try {
			FastDfsUtiles dastDfsutile=new FastDfsUtiles("classpath:config/fdfs_client.conf");
			//执行上传处理
			String path=dastDfsutile.uploadFile(file.getBytes(),orString);
			
		//拼接并返回的url和IP地址拼装成完整的url
			String url = fileValue+path;
			return new ErrorPingYouGou(true, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  new ErrorPingYouGou(true, "上传失败");
	}
}
