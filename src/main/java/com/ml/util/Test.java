package com.ml.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		List<String> strs = FileUtils.readLines(new File("C:\\Users\\feiqiu\\Desktop\\sch_summary.txt"));
		Map<String, String> map = new TreeMap<String, String>();
		for(String str: strs) {
			if(str.startsWith("#include")) {
				map.put(str, "");
			}
		}
		for(String key: map.keySet()) {
			System.out.println(key);
		}
		List<String> strs2 = FileUtils.readLines(new File("\\\\10.74.68.10\\public\\sch\\include\\self.h"));
		int struct = 0;
		int function = 0;
		int struct2 = 0;
		int def = 0;
		for(String str: strs2) {
			if(str.startsWith("typedef")) {
				if(str.contains("struct"))
					struct++;
				else
					struct2++;
			}
			if(str.endsWith(");"))
				function++;
			if(str.startsWith("#define"))
				def++;
		}
		System.out.println("struct: " + struct);
		System.out.println("struct2: " + struct2);
		System.out.println("function: " + function);
		System.out.println("def: " + def);
	}

}
