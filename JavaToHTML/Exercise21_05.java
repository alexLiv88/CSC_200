/*
 * 		Description: The file the user wishes to copy must be within the src folder of the project, the same folder where this .java file resides.
 */

import java.io.*;
import java.util.*;

public class Exercise21_05 {
	
	static Scanner input = new Scanner(System.in);
	static Scanner inputFileScan;
	static PrintWriter output;
	static Set<String> fileIn = new LinkedHashSet<>();
	static int inComment;
	static int inStrLiteral;
	static int inCharLiteral;
	
	public static void main(String[] args){
			
		// Prompt the user for the file to be converted and what it should be named.
		System.out.print("Enter source file name and output file name separated by a space.. ");
		File inputFile = new File("src/" + input.next());
		File outputFile = new File(input.next());
		
		// check for correct file extensions on each file
		if(!inputFile.toString().contains("java") || !outputFile.toString().contains("html")){
			System.out.println("ERROR: INVALID FILE TYPES");
			System.exit(0);
		}
		
		// Try to create a scanner for the file, make sure that the file is not empty,
		// and make sure that the file can be read. If any of these cases are not met
		// the program ends.
		try {
			inputFileScan = new Scanner(inputFile);
			if(!inputFile.canRead()){
				System.out.println("ERROR: INPUT FILE CANNOT BE READ");
				System.exit(0);
			}
			if(!inputFileScan.hasNext()){
				System.out.println("ERROR: INPUT FILE IS EMPTY");
				System.exit(0);
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: INPUT FILE NOT FOUND");
			System.exit(0);
		}
		
		// Make sure the output file does not already exist.
		if(outputFile.exists()){
			System.out.println("ERROR: OUTPUT FILE ALREADY EXISTS");
			System.exit(0);
		}
		
		// Try to create a PrintWriter for the output file
		try {
			outputFile.createNewFile();
			output = new java.io.PrintWriter(outputFile);
		} catch (Exception e) {
			System.out.println("Somehow you broke it.");
			System.exit(0);
		}
		
		// Create a LinkedHashSet to store lines of the input file.
		// Set<String> fileIn = new LinkedHashSet<>();
		
		while(inputFileScan.hasNext()){
			
			fileIn.add(inputFileScan.nextLine());
			
		}
		inComment = 0;
		inStrLiteral = 0;
		inCharLiteral=0;
		
		System.out.print(fileIn);
		
		output.println("<html><head></head><body>");
		for(String el: fileIn){
				System.out.println("ITERATING STRING IS : " + el);
				System.out.println("inchar VALUE IS " + inCharLiteral);
			//System.out.println(el);
			//System.out.println("<br/>");
			// instead let's run this loop for the entirety of working through the string
			while(el.length()>0){
				// NEED TO INSERT A CHECK FOR A TAB SPACE AND IF THERE IS THEN OUTPUT THE VALUE BELOW
				// first check the first character, see if this is a tab.. 
				if(el.length()>1) {
					el=isTab(el, checkFirstChar(el));
				}
				// second check the first character, see if this is a comment
				if(el.length() == 0) {break;}
				if(notInside()<1) {
					el=isComment(el, checkFirstChar(el));
				}
				else if(inComment == 1){
					el=endComment(el, checkFirstChar(el));
				}
				else if(inCharLiteral == 1) {
					System.out.println("inovking endCharLiteral ");
					el=endCharLiteral(el, checkFirstChar(el));
				}
				if(notInside()<1 && el.length()>1) {
					el=isStrLiteral(el, checkFirstChar(el));
				}
				else if(inStrLiteral == 1) {
					el=endStrLiteral(el, checkFirstChar(el));
				}
				if(notInside()<1 && el.length()>1) {
					el=isCharLiteral(el,checkFirstChar(el));
				}
				// third, print the next character and remove it from the string
				if(el.length()>0) {
					System.out.println("printing to the console " + el.charAt(0));
					output.print(el.charAt(0));
					el=el.substring(1);
				}
			}
			output.print("<br/>");

			
	}

		output.println("</body></html>");
		System.out.println("Writing file " + inputFile + " to file " + outputFile);
		
		output.close();
		
		System.out.println((int)'\t');
	}


	private static int notInside() {
		int sum = inStrLiteral + inCharLiteral + inComment;
		System.out.println("NotInside value is " + sum);
		return sum;
	}

	private static String endComment(String el, char checkFirstChar) {
		if(el.contains("*/")) {
			while(el.contains("*/")) {
				output.print(el.charAt(0));
				el=el.substring(1);
			}
			output.print("/</font>");
			inComment = 0;
			return el.substring(1);
		}
		else {
			output.print(el+"<br/>");
			return "";
		}
	}

	private static String endCharLiteral(String el, char checkFirstChar) {
		if(el.contains("'")) {
			while(el.charAt(0) != '\''){
				output.print(el.charAt(0));
				el=el.substring(1);
			}
			output.print("'</font>");
			inCharLiteral = 0;
			return el.substring(1);
		}
		else {
			output.print(el+"<br/>");
			return "";
		}
	}


	private static String endStrLiteral(String el, char checkFirstChar) {
		if(el.contains("\"")) {
			while(el.charAt(0)!='"') {
				output.print(el.charAt(0));
				el=el.substring(1);
			}
			output.print("\"</font>");
			inStrLiteral = 0;
			return el.substring(1);
		}
		else {
			output.print(el+"<br/>");
			return "";
		}
	}

	private static String isComment(String el, char first) {
		if(first!='/') {
			return el;
		}
		else {
			if(checkFirstChar(el.substring(1))=='/') {
				output.print("<font color=\"#228B22\">/" + el + "</font>");
				return "";
			}
			else if(checkFirstChar(el.substring(1))=='*') {
				output.print("<font color=\"#228B22\">/");
				inComment = 1;
				return el.substring(1);
			}
			else {
				return el;
			}
		}
	}
	
	private static String isStrLiteral(String el, char first) {
		if ((int)first != 34) {
			return el;
		}
		else {
			output.print("<font color=\"blue\">\"");
			inStrLiteral = 1;
			return el.substring(1);
		}
	}

	
	private static String isCharLiteral(String el, char first) {
		if((int)first!=39){
			return el;
		}
		else {
			output.print("<font color=\"blue\">\'");
			inCharLiteral = 1;
			return el.substring(1);
		}
	}
	
	private static char checkFirstChar(String el) {
		return el.charAt(0);
	}
	
	// check if char first is a tab, if it is we print the tab to the html file and then
	// check the next value, until first is no longer a tab.
	private static String isTab(String el, char first) {
		if((int)first!=9) {
			// do nothing, end
			return el;
		}
		else {
			// print the tab and then check again
			output.print("&emsp;");
			if(el.length()>1) {
				return isTab(el.substring(1), el.charAt(1));
			}
			else {
				return "";
			}
		}
	}

}