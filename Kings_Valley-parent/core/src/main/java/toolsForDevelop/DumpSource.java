package toolsForDevelop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DumpSource
{

	 public static void main(String[] args) {

	        Path srcDir = Paths.get("src","main","java","engine","gameCharacters");
	        Path output = Paths.get("codigo_concatenado.txt");
	        System.out.println(srcDir.toAbsolutePath());
	        System.out.println(Files.exists(srcDir));
	        try (Stream<Path> paths = Files.walk(srcDir);
	             BufferedWriter writer = Files.newBufferedWriter(output)) {

	            paths
	                .filter(Files::isRegularFile)
	                .filter(p -> p.toString().endsWith(".java"))
	                .sorted()
	                .forEach(p -> escribirArchivo(p, writer));

	            System.out.println("Listo: " + output.toAbsolutePath());

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private static void escribirArchivo(Path file, BufferedWriter writer) {
	        try {
	            writer.write("\n\n==============================\n");
	            writer.write("FILE: " + file.toString() + "\n");
	            writer.write("==============================\n\n");

	            Files.lines(file).forEach(line -> {
	                try {System.out.println(line);
	                    writer.write(line);
	                    writer.newLine();
	                } catch (IOException e) {
	                    throw new RuntimeException(e);
	                }
	            });

	        } catch (IOException e) {
	            System.err.println("Error leyendo: " + file);
	        }
	    }
}
