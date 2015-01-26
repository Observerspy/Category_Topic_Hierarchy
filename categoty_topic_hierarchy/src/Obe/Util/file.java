package Obe.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;


public class file {

	private String filePath;
	private  String outPath;
	public static String text="";
	
	    public void setfilepath(String filepath){
	    	this.filePath = filepath;
	    	text = "";
	    }
	    
	    public void setoutpath(String filepath){
	    	this.outPath = filepath;
	    	text = "";
	    }
	    
	    public String returnS(){
	    	return this.text;
	    }

	    public void read(){
			try {
				this.loadFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void loadFile() throws IOException{
			File file = new File(filePath);
			
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileInputStream input=new FileInputStream(filePath);
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(input);
			
			int count = 0;
			while(scan.hasNext()){
				text=text.concat("###"+scan.nextLine());
				count++;
				//System.out.println(text);
				}
			
			
			input.close();
		}
		
		public void delete(){
			File ff = new File(outPath);
			if(ff.exists()){
				ff.delete();
			}
		}
		
		public void write(String t) throws IOException{
			
			
			File stuFile = new File(outPath);
			FileOutputStream write=new FileOutputStream(outPath,true);
			if(!stuFile.exists()){
				stuFile.createNewFile();
			}
			PrintStream print = new PrintStream(write);
			print.print(t.toString());
	        write.close();
		}
}
