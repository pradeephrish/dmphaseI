import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;


public class Verify {
	public static void main(String[] args) throws IOException {
		List<String> aniketData = FileUtils.readLines(new File("resources/Training_Set.csv"));
		List<String> data = FileUtils.readLines(new File("resources/trainingdata.db"));
		
		String aline = aniketData.get(100+1);
		String line = data.get(100);
		
		String[] acols = aline.split(",");
		String[] cols = line.split(" ");
		
		if(acols.length==cols.length)
			System.out.println("rows same");
		
		
		Integer sameRows = 0;
		
		for (int i = 0; i < cols.length; i++) {
			System.out.println(acols[i]);
			System.out.println(cols[i]);
			if(acols[i]==cols[i])
				++sameRows;
		}
		System.out.println(sameRows);
	}
}
