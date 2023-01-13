package com.lqs.mall.member.app;

import com.lqs.mall.common.constant.REnum;
import com.lqs.mall.common.utils.R;
import com.lqs.mall.common.utils.pagination.PageUtils;
import com.lqs.mall.member.entity.MemberLevelEntity;
import com.lqs.mall.member.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员等级
 *
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:58:01
 */
@RestController
@RequestMapping("member/memberlevel")
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        try{
            // 程序代码
            PageUtils memberLevelListByPage = memberLevelService.queryPage(params);

            return R.ok(REnum.REQUEST_MEMBER_LEVEL_LIST_BY_PAGE_SUCCESS.getStatusCode(),
                    REnum.REQUEST_MEMBER_LEVEL_LIST_BY_PAGE_SUCCESS.getStatusMsg())
                    .put("memberLevelList", memberLevelListByPage);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_MEMBER_LEVEL_LIST_BY_PAGE_FAIL.getStatusCode(),
                    REnum.REQUEST_MEMBER_LEVEL_LIST_BY_PAGE_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){

        try{
            // 程序代码
            MemberLevelEntity memberLevel = memberLevelService.getById(id);

            return R.ok(REnum.REQUEST_MEMBER_LEVEL_SUCCESS.getStatusCode(),
                            REnum.REQUEST_MEMBER_LEVEL_SUCCESS.getStatusMsg())
                    .put("memberLevel", memberLevel);
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.REQUEST_MEMBER_LEVEL_FAIL.getStatusCode(),
                    REnum.REQUEST_MEMBER_LEVEL_FAIL.getStatusMsg());
        }finally{
           // 程序代码
        }

    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody MemberLevelEntity memberLevel){

        try{
            // 程序代码
            memberLevelService.save(memberLevel);

            return R.ok(REnum.APPEND_MEMBER_LEVEL_SUCCESS.getStatusCode(),
                            REnum.APPEND_MEMBER_LEVEL_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.APPEND_MEMBER_LEVEL_FAIL.getStatusCode(),
                    REnum.APPEND_MEMBER_LEVEL_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public R update(@RequestBody MemberLevelEntity memberLevel){

        try{
            // 程序代码
            memberLevelService.updateById(memberLevel);

            return R.ok(REnum.ALERT_MEMBER_LEVEL_SUCCESS.getStatusCode(),
                    REnum.ALERT_MEMBER_LEVEL_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.ALERT_MEMBER_LEVEL_FAIL.getStatusCode(),
                    REnum.ALERT_MEMBER_LEVEL_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){

        try{
            // 程序代码
            memberLevelService.removeByIds(Arrays.asList(ids));

            return R.ok(REnum.DELETE_MEMBER_LEVEL_SUCCESS.getStatusCode(),
                    REnum.DELETE_MEMBER_LEVEL_SUCCESS.getStatusMsg());
        }catch(Exception e){
            //Catch 块
            e.printStackTrace();

            return R.error(REnum.DELETE_MEMBER_LEVEL_FAIL.getStatusCode(),
                    REnum.DELETE_MEMBER_LEVEL_FAIL.getStatusMsg());
        }finally{
            // 程序代码
        }

    }

}
