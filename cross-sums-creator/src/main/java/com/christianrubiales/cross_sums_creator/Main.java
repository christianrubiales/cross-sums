package com.christianrubiales.cross_sums_creator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.IO.println;


@SpringBootApplication
public class Main implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		int numBoards = Integer.parseInt(args[0]);
		println("Number of boards to create per size: " + numBoards);

		Map<Integer, List<Board>> boards = new TreeMap<>();

		for (int size = 4; size <= 8; size++) {
			boards.put(size, new ArrayList<>());
			for (int i = 0; i < numBoards; i++) {
				Board board = new Board(size);
				boards.get(size).add(board);
			}
		}

		String boardString = toString(boards);
		println(boardString);
		long timestamp = new Date().getTime();
		Path path = Path.of(timestamp + ".js");
		Files.writeString(path, boardString);
	}

	String toString(Map<Integer, List<Board>> boards) {
		StringBuilder builder = new StringBuilder();

		builder.append("const boards = {\n");

		for (Integer size : boards.keySet()) {
			builder.append("\"" + size +
					"\":  [\n");

			List<Board> boardList = boards.get(size);
			for (Board board : boardList) {
				builder.append(board.toJsonString() + ",\n");
			}

			builder.append("],\n");
		}

		builder.append("}\n");

		return builder.toString();
	}
}
