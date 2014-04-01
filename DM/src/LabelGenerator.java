import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;


public class LabelGenerator {
	public static void main(String[] args) throws IOException {
		String label = "";
		for (int i = 0; i < 26364; i++) {
			label+=" col"+i+" "+" numeric";
		}
		FileUtils.write(new File("resources/labels.txt"), label);
	}
}
