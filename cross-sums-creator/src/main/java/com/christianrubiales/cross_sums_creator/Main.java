package com.christianrubiales.cross_sums_creator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

import static java.lang.IO.println;


@SpringBootApplication
public class Main implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) {
		int numBoards = Integer.parseInt(args[0]);
		println("Number of boards to create per size: " + numBoards);

		Map<Integer, List<Board>> boards = new TreeMap<>();

		for (int size = 4; size <= 5; size++) {
			boards.put(size, new ArrayList<>());
			for (int i = 0; i < numBoards; i++) {
				Board board = new Board(size);
				println(board.toJsonString());
				boards.get(size).add(board);
			}
		}
	}

	String toString(Map<Integer, List<Board>> boards) {
		return null;
	}
}
