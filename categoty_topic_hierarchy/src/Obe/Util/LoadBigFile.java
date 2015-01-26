package Obe.Util;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
public class LoadBigFile {
	private String text = "";
	private String path = "";
	
    public  String Load() throws Exception {
	   final int BUFFER_SIZE =  0x1200000;// 12M
	   
       File f = new File(path);

       MappedByteBuffer inputBuffer = new RandomAccessFile(f, "r")
       .getChannel().map(FileChannel.MapMode.READ_ONLY,
                         0, f.length());

       byte[] dst = new byte[BUFFER_SIZE];// read 3M each time

       long start = System.currentTimeMillis();

       for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {

           if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {

               for (int i = 0; i < BUFFER_SIZE; i++)

                   dst[i] = inputBuffer.get(offset + i);

           } else {

               for (int i = 0; i < inputBuffer.capacity() - offset; i++)

                   dst[i] = inputBuffer.get(offset + i);

           }

           int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE
                        : inputBuffer.capacity() % BUFFER_SIZE;

           text += new String(dst, 0, length);
           
       }

       long end = System.currentTimeMillis();

       System.out.println("time used:" + (end - start) + "ms");
	return text;
   }

	public void setPath(String path) {
		this.path = path;
	}
}