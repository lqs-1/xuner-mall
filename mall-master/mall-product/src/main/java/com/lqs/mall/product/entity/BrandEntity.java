package com.lqs.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lqs.mall.common.valid.AddGroup;
import com.lqs.mall.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 品牌
 * 
 * @author somg
 * @email liqisong2002gmail.com
 * @date 2022-07-30 21:59:36
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@TableId
	@NotNull(message = "修改必须指定id", groups = {UpdateGroup.class})
	@Null(message = "添加不能指定id", groups = {AddGroup.class})
	private Long brandId;
	/**
	 * 品牌名
	 */
	@NotNull(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
	@URL(message = "品牌logo必须是一个合法的url地址", groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "品牌logo不能为空", groups = {AddGroup.class})
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	// 万能的注解,可以写正则
	@Pattern(regexp = "^[a-zA-Z]$", message = "检索字母必须是一个合法的英文字母", groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "品牌检索首字母必须提交", groups = {AddGroup.class})
	private String firstLetter;
	/**
	 * 排序
	 */
	@Min(value = 0, message = "排序值必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
	@NotNull(message = "排序值必须提交", groups = {AddGroup.class})
	private Integer sort;
}
