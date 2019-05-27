package cn.pingyougou.manager.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.pingyougou.Interface.ContentService;
import cn.pingyougouUtiles.FastDfsUtiles;
import cn.pinyougou.pojo.TbContent;
import cn.pinyougou.pojo.TbContentCategory;
import entryPingYouGou.ErrorPingYouGou;
import entryPingYouGou.PageToel;

@RestController
@RequestMapping("/content")
public class ContentController {
	
	@Reference
	private ContentService contentService;
	@Value("${FASTDFS}")
	private String filehttp;
	
	//删除
@RequestMapping("/contentDel.do")
public ErrorPingYouGou contentDel(Long[] longs) {
	try {
		contentService.contentDel(longs);
		return new ErrorPingYouGou(true, "成功");
	} catch (Exception e) {
		e.printStackTrace();
		return new ErrorPingYouGou(false, "失败");
	}
}
	//查找所有
	@RequestMapping("/contentList.do")
	public PageToel<TbContent> contentList(int pageNum,int  pageSize){
		System.out.println("d");
		return contentService.contentList(pageNum,pageSize);
	}
	//根据id查询一个
	@RequestMapping("/contentCatOne.do")
	public TbContent contentCatOne(long id) {
		return contentService.contentOne(id);
	}

	//修改和保存
	@RequestMapping("/contentSave.do")
	public ErrorPingYouGou contentSave(@RequestBody TbContent tbContentCategory) {
		try {
			contentService.contentSave(tbContentCategory);
			return new ErrorPingYouGou(true, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(false, "失败");
		}
	}
	
	//图片上传
	@RequestMapping("/contentImage.do")
	public ErrorPingYouGou contentImage(MultipartFile file) {
		try {
			//获取文件扩展名
			String fileHName=file.getOriginalFilename();//获取文件的全名称
			String  orString=fileHName.substring(fileHName.lastIndexOf('.')+1);
			//创建一个FastFDFS客户端
			FastDfsUtiles fastDfsUtiles=new FastDfsUtiles("classpath:config/fdfs_client.conf");
			//执行上传远程服务器处理
				String path=fastDfsUtiles.uploadFile(file.getBytes(),orString);
			//拼接返回全路径
				String url=filehttp+path;
			return new ErrorPingYouGou(true, url);
		} catch (Exception e) {
			e.printStackTrace();
			return new ErrorPingYouGou(false, "失败");
		}
	}
	
	//查询分类
	@RequestMapping("/selectContenCat.do")
	public List<TbContentCategory> selectContenCat(){
		return contentService.selectContenCat();
	}
	

}
