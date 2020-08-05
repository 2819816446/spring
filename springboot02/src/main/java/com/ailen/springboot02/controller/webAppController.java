package com.ailen.springboot02.controller;

import com.ailen.springboot02.pojo.Comment;
import com.ailen.springboot02.pojo.Hrm;
import com.ailen.springboot02.pojo.HrmCommentVo;
import com.ailen.springboot02.service.HrmService;
import com.ailen.springboot02.util.RedisTool;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/AppController")
public class webAppController {

    @GetMapping("/hello")
    public static String hello(){
        return "hello springBoot !!!";
    }

    @Autowired
    private HrmService hrmService;

    @Autowired
    private RedisTool redisTool;

    @RequestMapping(value="/regist", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject regist(String account,String password) {
        System.out.println("regist。。。");
        JSONObject jsonObject = new JSONObject();
        try {
            Hrm hrm = hrmService.getHrmByAccount(account);
            if (hrm != null) { //用户已经存在
                jsonObject.put("status", false);
                jsonObject.put("msg", "用户已存在");
            }else{ //用户不存在，则注册
                int insertCount =  hrmService.registUserByAccountPassword(account, password);
                if (insertCount >=1) {
                    Hrm hrm1 = hrmService.getHrmByAccount(account);
                    JSONObject obj = new JSONObject();
                    obj.put("id", hrm1.getId());
                    obj.put("account", hrm1.getAccount());
                    obj.put("userName", hrm1.getUserName());
                    obj.put("password", hrm1.getPassword());
                    jsonObject.put("user", obj);
                    jsonObject.put("status", true);
                    jsonObject.put("msg", "注册成功");
                }else{
                    jsonObject.put("status", false);
                    jsonObject.put("msg", "注册失败，服务器注册异常!");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("status", false);
            jsonObject.put("msg", "服务器出错");
        }

        return jsonObject;


    }

    @RequestMapping(value="/getUsers", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUsers() {
        List<Hrm> lists = hrmService.getUsers();
        int allCount = hrmService.getUserCounts(); //获取总数
        JSONObject jsonObject = new JSONObject();
        JSONArray dataArr = new JSONArray();

        if (lists != null) {
            for (Hrm hrm : lists) {
                JSONObject o = new JSONObject();
                o.put("id", hrm.getId());
                o.put("username", hrm.getUserName());
                o.put("account", hrm.getAccount());
                o.put("password", hrm.getPassword());
                dataArr.add(o);
            }
            jsonObject.put("code", 0);
            jsonObject.put("msg", "获取成功!");
            jsonObject.put("count", allCount);
            jsonObject.put("data", dataArr);
        }else{
            jsonObject.put("lists", "noDate");
        }
        return jsonObject;

    }

    /**
     *
     * @author yzq
     * @param @param page  	 当前页
     * @param @param limit	每页显示条数
     * @param @return
     * @return JSONObject
     * @date 2019年6月2日
     */
    @RequestMapping(value="/getUsersWithPagination", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUsersWithPagination(int page,int limit) {
        JSONArray dataArr = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        try {
            List<Hrm> lists = hrmService.getUsersWithPagination(page,limit);
            int allCount = hrmService.getUserCounts(); //获取总数
            if (lists != null) {
                for (Hrm hrm : lists) {
                    JSONObject o = new JSONObject();
                    o.put("id", hrm.getId());
                    o.put("username", hrm.getUserName());
                    o.put("account", hrm.getAccount());
                    o.put("password", hrm.getPassword());
                    dataArr.add(o);
                }
                jsonObject.put("code", 0);
                jsonObject.put("msg", "获取成功!");
                jsonObject.put("count", allCount);
                jsonObject.put("data", dataArr);
            }else{
                jsonObject.put("lists", "noDate");
                jsonObject.put("msg", "获取失败!");
            }
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("msg", "服务器报错!");

        }
        return jsonObject;

    }
    @RequestMapping(value="/getHrmById",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject getHrmById(String id){
        JSONObject jsonObject = new JSONObject();
        Hrm hrm = hrmService.getHrmById(id);
        if (hrm != null) { //用户信息存在
            jsonObject.put("status", true);
            jsonObject.put("msg", "获取用户信息成功!");
            jsonObject.put("id", hrm.getId());
            jsonObject.put("username", hrm.getUserName());
            jsonObject.put("account", hrm.getAccount());
            jsonObject.put("password", hrm.getPassword());
        }else{
            jsonObject.put("status", false);
            jsonObject.put("msg", "用户不存存在");


        }
        return jsonObject;

    }

    @RequestMapping(value="/deleteUserById",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteUserById(String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            int count = hrmService.deleteUserById(id);
            if (count >= 1) { //删除成功
                jsonObject.put("status", true);
                jsonObject.put("msg", "用户删除成功!");
            }else{
                jsonObject.put("status", false);
                jsonObject.put("msg", "用户删除失败");
            }
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("msg", "服务器报错!");
        }
        return jsonObject;

    }

    @RequestMapping(value="/updateUserInfo",method=RequestMethod.POST)
    @ResponseBody
    public JSONObject updateUserInfoById(String id,String username,String account,String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            int count = hrmService.updateUserInfo(id,username,account,password);
            if (count >= 1) { //删除成功
                jsonObject.put("status", true);
                jsonObject.put("msg", "修改成功!");
            }else{
                jsonObject.put("status", false);
                jsonObject.put("msg", "修改失败");
            }
        } catch (Exception e) {
            jsonObject.put("status", false);
            jsonObject.put("msg", "服务器报错!");
        }
        return jsonObject;

    }


    @RequestMapping(value = "/getUserByKey", method = RequestMethod.POST )
    public Hrm getUserByKey(String key){
        System.out.println("getUserByKey start。。。");
        Hrm hrm =  hrmService.getUserByKey(key);
        System.out.println(hrm);
        System.out.println("getUserByKey end。。。");
        return  hrm;
    }
    /**
     * 获取一个用户，及评论列表：一对多
     * @return
     */
    @RequestMapping(value = "/hrmCommentVo", method = RequestMethod.POST )
    public HrmCommentVo getHrmCommentVo(String id){
        System.out.println("hrmCommentVo start。。。");
        HrmCommentVo hrmCommentVo =  hrmService.getHrmCommentVo(id);
        System.out.println(hrmCommentVo);
        return  hrmCommentVo;
    }

    /**
     * 通过注解的方式去更新数据
     * @param content
     * @param id
     * @return
     */
    @RequestMapping(value = "/commentContentUpdate", method = {RequestMethod.POST,RequestMethod.GET})
    public  int UpdateCommentContent(String content,String id){
        int count = hrmService.UpdateCommentContent(content,id);
        System.out.println("注解方式更新的返回值："+count);
        return  count;

    }

    /**
     * 测试返回map数据。同json数据一样，需要添加ResponseBody注解
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCommentTitleAndContent", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> GetCommentTitleAndContent(String id){
        Map<String,String> map   = new HashMap<String,String>();
        map = hrmService.GetCommentTitleAndContent(id);
        System.out.println("返回的map数据如下：===》");
        System.out.println(map);
        return  map;

    }

    /**
     * 测试返回数组返回JSONArray数据。同json数据一样，需要添加ResponseBody注解
     * 备注：该方法报错。 resultType 绑定异常
     * @param id
     * @return
     */
    @RequestMapping(value = "/GetCommentTitleAndContentByArr", method = RequestMethod.POST)
    @ResponseBody
    public List GetCommentTitleAndContentByArr(String id){
        JSONArray arr   = new JSONArray();
        List list = new ArrayList();
        list = hrmService.GetCommentTitleAndContentByArr(id);
        System.out.println("返回的list数据如下：===》");
        System.out.println(list);
        return  list;

    }

    @RequestMapping(value = "/getHrmCommentVoByMultiCondition", method = RequestMethod.POST)
    @ResponseBody
    public List<HrmCommentVo> getHrmCommentVoByMultiCondition(String userName, String content,String title, String cids){
        System.out.println("controller getHrmCommentVoByMultiCondition start。。。");
        int idSize = cids.split(",").length;
        String[] idsArr =  new String[idSize];
        System.out.println("idsArr-size:"+idsArr.length + "idsArr:"+idsArr);
        if ( !cids.equals("") ){ //非空字符串再转数字组
            idsArr = cids.split(",");
        }
        List cidList  = new ArrayList();
        if (idsArr==null||idsArr.length==0 || ( idsArr.length == 1 &&   ("".equals(idsArr[0]) || null ==idsArr[0]  ) )  ){
            //空数组
        }else{
            for (int i = 0; i < idsArr.length; i++) {
                String idTem = idsArr[i];
                cidList.add(idTem);
            }
        }

        //添加redis前
		/*System.out.println("cidList-size:"+cidList.size() + "cidList:"+cidList);
		List<HrmCommentVo> lists =  hrmService.getHrmCommentVoByMultiCondition(userName,content,title,cidList);
		System.out.println(lists);
		System.out.println("controller getHrmCommentVoByMultiCondition end。。。");
		return  lists;*/

        //添加redis后
        System.out.println("cidList-size:"+cidList.size() + "cidList:"+cidList);
        List<HrmCommentVo> lists = null;
        Object redisHrmCommentVoLists = redisTool.get("RedisHrmCommentVoLists");
        if (redisHrmCommentVoLists != null){
            System.out.println("缓存中存在，直接返回");
            lists = (List<HrmCommentVo>)redisTool.get("RedisHrmCommentVoLists");

        }else{
            System.out.println("缓存中无数据，查询数据库");
            lists = hrmService.getHrmCommentVoByMultiCondition(userName,content,title,cidList);
            redisTool.set("RedisHrmCommentVoLists",lists);
        }
        System.out.println("controller getHrmCommentVoByMultiCondition end。。。");
        return  lists;


    }


    /**
     * 插入一条数据到Hrm，同时插入多条到明细Comment, 参数通过自动映射，
     * 即：传的参数需要为Hrm对象的属性参数：userName，account，password
     * @param userName
     * @param account
     * @param password
     * @param commentListStr  明细数组 comment  aliba
     * @return
     */
    @RequestMapping(value = "/insertHrmWithComments", method =RequestMethod.POST )
    @ResponseBody
    /*method = {RequestMethod.POST,RequestMethod.GET}*/
    public JSONObject insertHrmWithComments(String userName,String account , String password ,String commentListStr){
        int insertCount = 0;
        JSONObject resultObj  = new JSONObject();
        try {
            if (account == null || password == null){
                insertCount = -1;
                resultObj.put("status","-1");
                resultObj.put("msg","账号密码不能为空！");
            }else{
                System.out.println("insertHrmWithComments===========start");

                Hrm hrm = new Hrm();
                hrm.setUserName(userName);
                hrm.setAccount(account);
                hrm.setPassword(password);
                insertCount =  hrmService.insertOneHrm(hrm);
                System.out.println("主表插入个数insertCount："+insertCount);
                System.out.println("主表插入后返回的主表id为："+hrm.getId());

                //注意在HrmMapper.xml映射文件中的insertOneHrm语句中加入两属性userGeneratedKeys和keyProperty，目的是让在添加对象后，
                // 自动将新加对象的Id给设置到上面的c（comment）对象中，方便在添加下面的Student对象的时候设置hrm_id属性的值

                JSONArray paramArr = JSON.parseArray(commentListStr);


                List list = new ArrayList();
                for (int i = 0; i < paramArr.size(); i++) {
                    JSONObject obj = ((JSONArray) paramArr).getJSONObject(i);
                    Comment comment = new Comment();
                    comment.setHrmId(hrm.getId()); //获取前面插入的id
                    comment.setContent(obj.getString("content"));
                    comment.setTitle(obj.getString("title"));
                    list.add(comment);
                    //Arrays.asList(s1, s2)
                }

                int dtCount = 0;
                System.out.println("明细list:"+ list.toString());//打印数组：Arrays.toString(list)
                if ( CollectionUtils.isEmpty(list) ){
                    System.out.println("明细为空。。。。。不执行insert");
                }else{
                    dtCount = hrmService.insertBatchComment(list);
                }
                System.out.println("明细表插入个数dtCount："+dtCount);


                resultObj.put("status","1");
                resultObj.put("msg","插入成功！");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultObj;
    }


    /**
     * 删除hrm和comment关联表数据
     * @param id
     * @return
     */
    @RequestMapping( value = "/deleteHrmWithComment" , method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSONObject deleteHrmWithComment( int id ){
        JSONObject resultObj = new JSONObject();
        Hrm hrm = hrmService.getHrmById( String.valueOf(id) );
        if (hrm == null){
            resultObj.put("status","-1");
            resultObj.put("msg","不存在该数据，或已删除，请确认！");
        }else{
            int deletCount = hrmService.deleteComment(id);
            if (deletCount>0 ){
                int deleteHrmCount =  hrmService.deleteHrm(id);
                if (deleteHrmCount>0){
                    resultObj.put("status","1");
                    resultObj.put("msg","删除成功！");
                }else{
                    resultObj.put("status","-1");
                    resultObj.put("msg","删除失败！");

                }
            }
        }
        return resultObj;
    }

    @RequestMapping( value = "/updateHrmById" , method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public JSONObject updateHrmById(String userName, String account, String password, int id){
        JSONObject resultObj = new JSONObject();
        Hrm hrm = hrmService.getHrmById( String.valueOf(id) );
        if (hrm == null){
            resultObj.put("status","-1");
            resultObj.put("msg","不存在该数据，或已删除，请确认！");
        }else{
            int upCount = hrmService.updateHrm(userName,account,password,id);
            if (upCount > 0 ){
                resultObj.put("status","1");
                resultObj.put("msg","update 更新成功！");
            }else{
                resultObj.put("status","-1");
                resultObj.put("msg","更新失败！");
            }
        }

        return  resultObj;
    }

}
