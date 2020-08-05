package com.ailen.springboot02.mapper;
import com.ailen.springboot02.pojo.Hrm;
import com.ailen.springboot02.pojo.HrmCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper

public interface HrmMapper {
	/**
	 * 
	*
	* 用户注册mapper接口
	*
	* @Package com.ailen.mapper 
	* @author yzq
	* @param 
	* @return void 
	* @date 2019年5月23日
	 */
	int insertUserByAccountPassword(@Param("account") String account, @Param("password") String password);

	/**
	 * 根据account号或者用户（Hrm）信息
	* @author yzq
	* @param @param account 账号
	* @param @return
	* @return Hrm 
	* @date 2019年5月24日
	 */
	Hrm getHrmByAccount(@Param("account") String account);

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
	 * 通过id删除用户
	* @author yzq
	* @param @param id
	* @param @return
	* @return int 
	* @date 2019年6月1日
	 */
	int deleteUserById(String id);

	/**
	 * 分页获取所有用户信息
	 * 多个参数，需要在mapper(dao)接口处使用@Param定义参数 ，paramType = "String" 只能接受一个参数
	* @author yzq
	* @param @param start
	* @param @param end
	* @param @param pageSize
	* @param @return
	* @return List<Hrm> 
	* @date 2019年6月2日
	 */
	List<Hrm> getUsersWithPagination(@Param("start") int start, @Param("end") int end, @Param("pageSize") int pageSize);

	/**
	 * 获取总数 
	* @author yzq
	* @param @return
	* @return int 
	* @date 2019年6月2日
	 */
	int getUserCounts();
	

	/**
	 * 修改用户信息
	* @author yzq
	* @param @param id
	* @param @param username
	* @param @param account
	* @param @param password
	* @param @return
	* @return int 
	* @date 2019年6月2日
	 */
	int updateUserInfo(@Param("id") String id, @Param("username") String username, @Param("account") String account, @Param("password") String password);

	/**
	 * 查找用户、评论一对多组合VO对象
	 * @param id
	 * @return
	 */
	HrmCommentVo getHrmCommentVo(@Param("id") String id);


	/**
	 * 通过注解的方式直接更新字段
	 * @return
	 */
	@Update( " update comment set content = #{content} where id = #{id} " )
	int UpdateCommentContent(@Param("content") String content, @Param("id") String id);


    /**
     * 返回Map数据（单条）---map方式返回的，直接使用@Select，而不是在mapper.xml中配置
     * @param id
     * @return
     */
	@Select( " select content,title from comment where id = #{id}  " )
    Map<String,String>  GetCommentTitleAndContent(@Param("id") String id);

    /**
     * 返回Map数据多条（JSONArray 测试）
     * @param id
     * @return
     */
    @Select( " select content,title from comment where hrm_id = #{id}  " )
    List GetCommentTitleAndContentByArr(@Param("id") String id);


	/**
	 * 模糊搜索 hrm
	 * @param key
	 * @return
	 */
	Hrm getUserByKey(@Param("key") String key);


    /**
     * 搜索hrmVo  动态sql的应用
     * @param userName
     * @param content
     * @param title
     * @return
     */
	List<HrmCommentVo> getHrmCommentVoByMultiCondition(
            @Param("userName") String userName,
            @Param("content") String content,
            @Param("title") String title,
            @Param("cidList") List cidList

    );

	/**
	 * 插入一条到hrm数据库
	 *
	 * 自定义对象也用@param注解.
	 * 在mapper.xml中使用的时候，#{对象别名.属性名}，如#{user.id}
	 * 注意，使用了@pram注解的话在mapper.xml不加parameterType。
	 * @param hrm
	 * @return
	 * 注意：
	 * 要返回自增主键：不能加 @Param("hrm")  参考：https://www.cnblogs.com/waterystone/p/5654300.html
	 *
	 */
	int insertOneHrm(Hrm hrm);

	/**
	 * 插入到评论明细表
	 * @param list
	 * @return
	 */
	int insertBatchComment(@Param("list") List list);


    /**
     * 删除评论
     * @param id  hrm_id字段值
     * @return
     */
	int deleteComment(@Param("id") int id);


    /**
     * 删除hrm
     * @param id hrm主表的id
     * @return
     */
    int deleteHrm(@Param("id") int id);

    /**
     *
     * update 更新hrm数据
     * @param userName
     * @param account
     * @param password
     * @param id
     * @return
     */
    int updateHrm(
            @Param("userName") String userName,
            @Param("account") String account,
            @Param("password") String password,
            @Param("id") int id


    );

}
