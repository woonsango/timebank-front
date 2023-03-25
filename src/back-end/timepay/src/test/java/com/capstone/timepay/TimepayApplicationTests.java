package com.capstone.timepay;

import com.capstone.timepay.domain.freeBoard.FreeBoard;
import com.capstone.timepay.domain.freeBoard.FreeBoardRepository;
import com.capstone.timepay.service.board.dto.FreeBoardDTO;
import com.capstone.timepay.service.board.service.FreeBoardService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TimepayApplicationTests {

	@Autowired
	FreeBoardService freeBoardService;

	@Autowired
	FreeBoardRepository freeBoardRepository;

	@BeforeEach
	void beforeEach()
	{

	}

	@AfterEach
	void afterEach()
	{
	}

	@Test
	void test()
	{
		FreeBoard freeBoard = new FreeBoard();
		freeBoard.setTitle("Test 제목입니다1");
		freeBoard.setContent("Test 내용입니다 111111");
		freeBoard.setCategory("Test Category");
		freeBoardService.write(FreeBoardDTO.toFreeBoardDTO(freeBoard));
	}

}
