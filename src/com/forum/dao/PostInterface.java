package com.forum.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.forum.vo.PostVO;

public interface PostInterface {

	@Insert("insert into tab_post(col_subject,col_content,col_content_text,col_type,col_high_light,col_top,col_parent_id,col_parent_content_summary,col_attach,col_submit_time,col_module_id,col_user_id,col_praise)"
			+ " values(#{subject},#{content},#{contentText},#{type},#{highLight},#{top},#{parentId},#{parentContentSummary},#{attach},#{submitTime},#{moduleId},#{userId},#{praise})")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer addPost(PostVO postVO);

	@Select("select * from tab_post where col_parent_id=0 and col_module_id=#{moduleId} and col_type=2 order by col_submit_time desc limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostLimit(@Param("start") long start,
			@Param("end") long end, @Param("moduleId") long moduleId);

	@Select("select * from tab_post where col_parent_id=0 and col_module_id=#{moduleId} and col_type=2")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostCount(long moduleId);

	@Select("select * from tab_post where col_parent_id=0 and col_type=2 order by col_submit_time desc limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostTopFive4Admin(@Param("start") long start,
			@Param("end") long end);

	@Select("select * from tab_post where col_parent_id=0 and col_type=2 and col_module_id in (select col_id from tab_module where col_visible = #{visible}) order by col_submit_time desc limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "visible", column = "col_visible"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostTopFive(@Param("start") long start,
			@Param("end") long end, @Param("visible") boolean visible);

	@Select("select * from tab_post where col_parent_id=0 and col_type=2 order by col_submit_time desc")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPost();

	@Select("select * from tab_post where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public PostVO getPostById(long id);

	@Select("select * from tab_post where col_type>1 and col_parent_id=0 and col_user_id=#{userId} limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getPostByUserIdLimit(@Param("userId") long userId,
			@Param("start") long start, @Param("end") long end);

	@Select("select * from tab_post where col_type>1 and col_parent_id=0 and col_user_id=#{userId}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getPostByUserId(long userId);

	@Select("select * from tab_post where col_parent_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getCommentByPostId(long id);

	@Select("select * from tab_post where col_parent_id!=0 and col_user_id=#{userId} limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllCommentByUserIdLimit(
			@Param("userId") long userId, @Param("start") long start,
			@Param("end") long end);

	@Select("select * from tab_post where col_parent_id!=0 and col_user_id=#{userId}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllCommentByUserId(long userId);

	@Select("select * from tab_post where col_type>2 and col_parent_id=0")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostByHold();

	@Update("update tab_post set col_type=#{type} where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer passPostById(@Param("type") String type, @Param("id") long id);

	@Update("update tab_post set col_high_light=#{highLight} where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer setHighLight(@Param("highLight") String highLight,
			@Param("id") long id);

	@Update("update tab_post set col_top=#{top} where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer setTop(@Param("top") String top, @Param("id") long id);

	@Delete("delete from tab_post where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer del(@Param("id") long id);

	@Select("select * from tab_post where col_type=2 and col_parent_id=0 and col_subject like CONCAT('%',#{subject},'%')")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostByParamToAdmin(String subject);

	@Select("select * from tab_post where col_type=2 and col_parent_id=0 and col_module_id in (select col_id from tab_module where col_visible = false) and col_subject like CONCAT('%',#{subject},'%')")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostByParam(String subject);

	@Select("select * from tab_post where col_type=2 and col_parent_id=0 and col_subject like CONCAT('%',#{subject},'%') limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostByParam4LimitToAdmin(
			@Param("subject") String subject, @Param("start") long start,
			@Param("end") long end);

	@Select("select * from tab_post where col_type=2 and col_parent_id=0 and col_module_id in (select col_id from tab_module where col_visible = false) and col_subject like CONCAT('%',#{subject},'%') limit #{start},#{end}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public List<PostVO> getAllPostByParam4Limit(
			@Param("subject") String subject, @Param("start") long start,
			@Param("end") long end);
	
	@Update("update tab_post set col_praise = ifnull(col_praise,0)+#{praise} where col_id=#{id}")
	@Results(value = {
			@Result(property = "id", column = "col_id"),
			@Result(property = "subject", column = "col_subject"),
			@Result(property = "content", column = "col_content"),
			@Result(property = "contentText", column = "col_content_text"),
			@Result(property = "type", column = "col_type"),
			@Result(property = "highLight", column = "col_high_light"),
			@Result(property = "top", column = "col_top"),
			@Result(property = "parentId", column = "col_parent_id"),
			@Result(property = "parentContentSummary", column = "col_parent_content_summary"),
			@Result(property = "attach", column = "col_attach"),
			@Result(property = "submitTime", column = "col_submit_time"),
			@Result(property = "moduleId", column = "col_module_id"),
			@Result(property = "userId", column = "col_user_id"),
			@Result(property = "praise", column = "col_praise") })
	public Integer addPraise(@Param("praise") long praise, @Param("id") long id);
	
}
