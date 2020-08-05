package com.ailen.springboot02.service.impl;


import com.ailen.springboot02.mapper.HrmMapper;
import com.ailen.springboot02.pojo.Hrm;
import com.ailen.springboot02.pojo.HrmCommentVo;
import com.ailen.springboot02.service.HrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
*
* 用户注册的具体实现类
 *
* alt+shift+p 快速实现方法
* @author yzq
* @param 
* @date 2019年5月24日 下午9:01:57
 */

// 缺少注释不能生成bean，controller注入会报错
@Service
public class HrmServiceImpl implements HrmService {
	@Autowired(required = false)
	private HrmMapper hrmMapper;

	@Override
	public int registUserByAccountPassword(String account, String password) {
		int i = hrmMapper.insertUserByAccountPassword(account, password);
		return i;
	}

	@Override
	public Hrm getHrmByAccount(String account) {
		Hrm hrm  = hrmMapper.getHrmByAccount(account);
		return hrm;
	}

	@Override
	public List<Hrm> getUsers() {
		// TODO Auto-generated method stub
		List<Hrm> lists = hrmMapper.getUsers();
		return lists;
	}

	@Override
	public Hrm getHrmById(String id) {
		Hrm hrm = hrmMapper.getHrmById(id);
		// TODO Auto-generated method stub
		return hrm;
	}

	@Override
	public  int  deleteUserById(String id) {
		return hrmMapper.deleteUserById(id);
	}

	@Override
	public List<Hrm> getUsersWithPagination(int page, int limit) {
		page = page >= 1?page:1; //默认取第一页，如果小于1的话
		int start = (page-1)*limit;
		int end =  page*limit;
		int pageSize = limit;
		List<Hrm> lists = hrmMapper.getUsersWithPagination(start,end,pageSize);
		return lists;
	}

	@Override
	public int getUserCounts() {
		return hrmMapper.getUserCounts();
	}



	@Override
	public int updateUserInfo(String id, String username, String account, String password) {
		return hrmMapper.updateUserInfo(id,username,account,password);
	}

	@Override
	public HrmCommentVo getHrmCommentVo(String id) {
		/*调用应该是hrmMapper.xxx方法，而我错写为HrmMapper.xxx方法，这样就是静态方法的调用方式：类直接调用，所以才报错的。*/
		return hrmMapper.getHrmCommentVo(id);
	}

	/**
	 * 注解方式更新数据
	 * 通过右键->generate->Override methods..
	 * @param content
	 * @param id
	 * @return
	 */
	@Override
	public int UpdateCommentContent(String content, String id) {
		return hrmMapper.UpdateCommentContent(content,id);
	}

    /**
     * map方式返回的，(单值，多值会报错)
     * @param id
     * @return
     */
    @Override
    public Map<String,String> GetCommentTitleAndContent(String id) {
        Map<String,String> map = hrmMapper.GetCommentTitleAndContent(id);
        return map;
    }

    /**
     * 测试数组返回
     * @param id
     * @return
     */
    @Override
    public List GetCommentTitleAndContentByArr(String id) {
        List list = hrmMapper.GetCommentTitleAndContentByArr(id);
        return list;
    }

	/**
	 * 模糊搜索hrm
	 * @param key
	 * @return
	 */
	@Override
	public Hrm getUserByKey(String key) {
		Hrm hrm = hrmMapper.getUserByKey(key);
		return hrm;
	}

	/**
	 * 多条件获取用户信息。动态sql
	 * @param userName
	 * @param content
	 * @return
	 */
	@Override
	public List<HrmCommentVo> getHrmCommentVoByMultiCondition(String userName,String content , String title, List cidList) {
		//未添加redis前的原有逻辑
	    List<HrmCommentVo> lists = hrmMapper.getHrmCommentVoByMultiCondition(userName,content,title,cidList);
	    return lists;

		//添加redis后的逻辑
		/*List<HrmCommentVo> lists = null;
		Object redisHrmCommentVoLists = redisUtil.get("RedisHrmCommentVoLists");
		if (redisHrmCommentVoLists != null){
			System.out.println("缓存中存在，直接返回");
			lists = (List<HrmCommentVo>)redisUtil.get("RedisHrmCommentVoLists");

		}else{
			System.out.println("缓存中无数据，查询数据库");
			lists = hrmMapper.getHrmCommentVoByMultiCondition(userName,content,title,cidList);
			redisUtil.set("RedisHrmCommentVoLists",lists);
		}
		return lists;*/

	}

	/**
	 * 插入数据到hrm，单条
	 * @param hrm
	 * @return
	 */
	@Override
	public int insertOneHrm(Hrm hrm) {
		return hrmMapper.insertOneHrm(hrm);
	}

	/**
	 * 插入数据到Comment,多条
	 * @param list
	 * @return
	 */
	@Override
	public int insertBatchComment(List list) {
		return hrmMapper.insertBatchComment(list);
	}

    @Override
    public int deleteComment(int id) {
        return hrmMapper.deleteComment(id);
    }

    @Override
    public int deleteHrm(int id) {
        return hrmMapper.deleteHrm(id);
    }

    @Override
    public int updateHrm(String userName, String account, String password, int id) {
        return hrmMapper.updateHrm(userName,account,password,id);
    }


}
