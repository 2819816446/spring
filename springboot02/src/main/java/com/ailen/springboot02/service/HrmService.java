package com.ailen.springboot02.service;


import com.ailen.springboot02.pojo.Hrm;
import com.ailen.springboot02.pojo.HrmCommentVo;

import java.util.List;
import java.util.Map;

/**
*
* 用户注册的service接口
*
* @author yzq
* @param 
* @date 2019年5月24日 下午9:01:01
 */
public interface HrmService {
	/**
	 * 用户注册 
	* @author yzq
	* @param @param account 账号
	* @param @param password 密码
	* @return void 
	* @date 2019年5月24日
	 */
	int registUserByAccountPassword(String account, String password);
	
	/**
	 * 通过账号查找用户
	* @author yzq
	* @param @param account
	* @param @return
	* @return Hrm 
	* @date 2019年5月24日
	 */
	Hrm getHrmByAccount(String account);

	/**
	 * 获取所有用户信息
	* @author yzq
	* @param @return
	* @return List<Hrm> 
	* @date 2019年5月29日
	 */
	List<Hrm> getUsers();

	/**
	 * 根据id获取用户信息
	* @author yzq
	* @param @param id
	* @param @return
	* @return Hrm 
	* @date 2019年6月1日
	 */
	Hrm getHrmById(String id);

	/**
	 * 通哟id删除用户
	* @author yzq
	* @param @param id
	* @param @return
	* @return int 
	* @date 2019年6月1日
	 */
	int deleteUserById(String id);

	/**
	 * 分页获取所有用户信息
	* @author yzq
	* @param @param page
	* @param @param limit
	* @param @return
	* @return List<Hrm> 
	* @date 2019年6月2日
	 */
	List<Hrm> getUsersWithPagination(int page, int limit);
	
	/**
	 * 获取hrm总数
	* @author yzq
	* @param @return
	* @return int 
	* @date 2019年6月2日
	 */
	int getUserCounts();

	/**
	 * 修改用户信息
	* @author yzq
	 * @param string 
	 * @param username 
	 * @param password 
	* @param @param id
	* @param @return
	* @return int 
	* @date 2019年6月2日
	 */
	int updateUserInfo(String id, String username, String string, String password);

	/**
	 * 获取用户的评论列表
	 * @param id 用户ID
	 * @return
	 */
	HrmCommentVo getHrmCommentVo(String id);


	/**
	 * 更新评论的内容。  ps:注解方式
	 * @param content
	 * @param id
	 * @return
	 */
	int UpdateCommentContent(String content, String id);

	/**
	 * 通过注解方式，直接获取评论表的两个字段
	 * ideal 快捷键 Alt+Enter提示导入类
	 * @param id
	 * @return
	 */
	Map<String,String> GetCommentTitleAndContent(String id);


    /**
     * 测试数组返回
     * @param id
     * @return
     */
	List GetCommentTitleAndContentByArr(String id);


	/**
	 * 根据关键字模糊查询用户信息
	 * @param key
	 * @return
	 */
	Hrm getUserByKey(String key);


	/**
	 * 根据多条件获取用户信息。动态sql的使用
	 * @param userName
	 * @param content
	 * @return
	 */
	List<HrmCommentVo> getHrmCommentVoByMultiCondition(
            String userName,
            String content,
            String title,
            List cidList
    );


	/**
	 * 插入一条数据到 hrm
	 * @param hrm
	 * @return
	 */
	int insertOneHrm(Hrm hrm);


	/**
	 * 插入数据到明细表。可能多条
	 * @param list
	 * @return
	 */
	int insertBatchComment(List list);

    /**
     * 删除评论
     * @param id  hrm_id字段值
     * @return
     */
    int deleteComment(int id);


    /**
     * 删除hrm
     * @param id hrm主表的id
     * @return
     */
    int deleteHrm(int id);

    /**
     * 更新hrm
     * @param userName
     * @param account
     * @param password
     * @param id
     * @return
     */
    int updateHrm(String userName, String account, String password, int id);


}
