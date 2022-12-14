package com.yuje0002.todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.yuje0002.todo.todoList.entity.TodoInfoEntity;
import com.yuje0002.todo.todoList.repository.TodoInfoRepository;

@SpringBootTest
class TodoApplicationTests {
	@Autowired TodoInfoRepository tRepo;
	@Test
	void loadTodo() throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		Date start = formatter.parse("2022-12-26");
		Date end = formatter.parse("2022-12-28");
		List<TodoInfoEntity> list =tRepo.findByEndDtBetweenAndMiSeq(start, end, 2L );
		for(TodoInfoEntity t : list){
			System.out.println(t);
		}
	}
}
