package com.mengzhaoxu.eblog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mengzhaoxu.eblog.common.result.CodeMsg;
import com.mengzhaoxu.eblog.common.result.Result;
import com.mengzhaoxu.eblog.entity.Post;
import com.mengzhaoxu.eblog.service.PostService;
import com.mengzhaoxu.eblog.service.SearchService;
import com.mengzhaoxu.eblog.vo.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yixin
 * @date 2020/6/28 2:46 下午
 * @description 管理员操作
 */
@Controller
@RequestMapping("admin")
public class AdminController extends BaseController{


    @Autowired
    private PostService postService;
    @Autowired
    private SearchService searchService;




    @ResponseBody
    @PostMapping("jie-set")
    public Result jieSet(Long id, Integer rank, String field ){
        Post post = postService.getById(id);
        if (post == null){
            return Result.error("该帖子已被删除");
        }
        if("delete".equals(field)) {
            postService.removeById(id);
            return Result.success(CodeMsg.SUCCESS);

        } else if("status".equals(field)) {
            post.setRecommend(rank == 1);

        }  else if("stick".equals(field)) {
            post.setLevel(rank);
        }
        postService.updateById(post);
        return Result.success(CodeMsg.SUCCESS);
    }


    @ResponseBody
    @PostMapping("initEsData")
    public Result initEsData() {

        int size = 10000;
        Page page = new Page();
        page.setSize(size);

        long total = 0;

        for (int i = 1; i < 1000; i ++) {
            page.setCurrent(i);

            IPage<PostVo> paging = postService.paging(page, null, null, null, null, null);

            int num = searchService.initEsData(paging.getRecords());

            total += num;

            // 当一页查不出10000条的时候，说明是最后一页了
            if(paging.getRecords().size() < size) {
                break;
            }
        }
        CodeMsg codeMsg = new CodeMsg();
        codeMsg.setStatus(0);
        codeMsg.setMsg("ES索引初始化成功，共 " + total + " 条记录！");
        return Result.success(codeMsg);
    }


}
