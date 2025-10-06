package com.christianrubiales.cross_sums_creator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static int MAX_SIZE = 8;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Override
	public void run(String... args) throws IOException {
		int numBoards = Integer.parseInt(args[0]);
		println("Number of boards to create per size: " + numBoards);

		Map<Integer, List<Board>> boards = new TreeMap<>();

		for (int size = 4; size <= MAX_SIZE; size++) {
			boards.put(size, new ArrayList<>());
			for (int i = 0; i < numBoards; i++) {
				Board board = new Board(size);
				boards.get(size).add(board);
			}
		}

		String boardString = toString(boards);
//		println(boardString);
		long timestamp = new Date().getTime();
		Files.writeString(Path.of(timestamp + ".js"), "const boards = " + boardString);

        String minified = minify(boardString);
//        println(minified);
        Files.writeString(Path.of(timestamp + ".min.js"), "const boards = " + minified);
	}

	String toString(Map<Integer, List<Board>> boards) {
		StringBuilder builder = new StringBuilder();

		builder.append("{\n");

		for (Integer size : boards.keySet()) {
			builder.append("\"" + size +
					"\":  [\n");

			List<Board> boardList = boards.get(size);
            int i = 1;
			for (Board board : boardList) {
				builder.append(board.toJsonString());
                if (i < boardList.size()) builder.append(",");
                builder.append("\n");
                i++;
			}

			builder.append("]");
            if (size < MAX_SIZE) builder.append(",");
            builder.append("\n");
		}

		builder.append("}\n");

		return builder.toString();
	}

    String minify(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Object object = mapper.readValue(json, Object.class);
        return mapper.writeValueAsString(object);
    }
}
