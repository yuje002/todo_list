package com.yuje0002.todo.todoList.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yuje0002.todo.member.entity.MemberInfoEntity;
import com.yuje0002.todo.todoList.entity.TodoInfoEntity;
import com.yuje0002.todo.todoList.repository.TodoInfoRepository;

@Service
public class TodoInfoService {
    @Autowired TodoInfoRepository t_repo;
    public Map<String, Object> addTodoInfo(TodoInfoEntity data , HttpSession session){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity loginUser = (MemberInfoEntity)session.getAttribute("loginUser");
        if(loginUser == null){
            map.put("status",false);
            map.put("message","로그인이 필요합니다.");
            map.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            data.setMiSeq(loginUser.getSeq());
            t_repo.save(data);
            map.put("status",true);
            map.put("message","일정이 추가되었습니다.");
            map.put("code", HttpStatus.CREATED);
        }
        return map;
    }
    public Map<String, Object> getTodoList(HttpSession session){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity loginUser = (MemberInfoEntity)session.getAttribute("loginUser");
        if(loginUser == null){
            map.put("status",false);
            map.put("message","로그인이 필요합니다.");
            map.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            map.put("list", t_repo.findAllByMiSeq(loginUser.getSeq()));
            map.put("message","조회하였습니다.");
            map.put("code", HttpStatus.OK);
            map.put("list",t_repo.findAllByMiSeq(loginUser.getSeq()));
        }
        return map;
    }
    public Map<String, Object> updateTodoStatus(Integer status, Long seq){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        TodoInfoEntity todo = t_repo.findBySeq(seq);
        if(todo == null){
            map.put("status",false);
            map.put("message","잘못된 Todo 번호입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            todo.setStatus(status);
            t_repo.save(todo);
            map.put("status",true);
            map.put("message","Todo 상태가 변경되었습니다.");
            map.put("code", HttpStatus.OK);
        }
        return map;
    }
    public Map<String, Object> updateTodoContent(String content, Long seq){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        TodoInfoEntity todo = t_repo.findBySeq(seq);
        if(todo == null){
            map.put("status",false);
            map.put("message","잘못된 Todo 번호입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            todo.setContent(content);
            t_repo.save(todo);
            map.put("status",true);
            map.put("message","Todo 내용이 변경되었습니다.");
            map.put("code", HttpStatus.OK);
        }
        return map;
    }
    @Transactional
    public Map<String, Object> deleteTodo(Long seq, HttpSession session){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity loginUser = (MemberInfoEntity)session.getAttribute("loginUser");
        if(loginUser ==  null){
            map.put("status",false);
            map.put("message","로그인 후 사용 가능합니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        TodoInfoEntity todo = t_repo.findBySeqAndMiSeq(seq, loginUser.getSeq());
        if(todo ==  null){
            map.put("status",false);
            map.put("message","잘못된 Todo 번호입니다.");
            map.put("code", HttpStatus.FORBIDDEN);
        }
        else{
            t_repo.deleteBySeqAndMiSeq(seq, loginUser.getSeq());
            map.put("status",true);
            map.put("message","Todo가 삭제되었습니다");
            map.put("code", HttpStatus.OK);
        }
        return map;
    }

    public Map<String, Object> selectTodoListByTerm(
        HttpSession session, String start, String end
    ){
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        MemberInfoEntity loginUser = (MemberInfoEntity)session.getAttribute("loginUser");
        if(loginUser ==  null){
            map.put("status",false);
            map.put("message","로그인 후 사용 가능합니다.");
            map.put("code", HttpStatus.FORBIDDEN);
            return map;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date startDt = null;
        Date endDt = null;
		try{
        startDt = formatter.parse(start);
		endDt = formatter.parse(end);
        } catch(Exception e){
            map.put("status",false);
            map.put("message","날짜 형식을 확인해 주세요(yyyymmdd ex:20221214");
            map.put("code", HttpStatus.BAD_REQUEST);
            return map;
        }
		map.put("status",true);
        map.put("message","조회 완료");
        map.put("list", t_repo.findByEndDtBetweenAndMiSeq(startDt, endDt, loginUser.getSeq()));
        map.put("code", HttpStatus.OK);
        return map;
		}
    }

