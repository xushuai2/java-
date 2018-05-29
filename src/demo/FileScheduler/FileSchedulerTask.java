package demo.FileScheduler;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileSchedulerTask implements Runnable{
	private static final Log logger = LogFactory.getLog(FileSchedulerTask.class);
	   
    private boolean firstRun = true;  
      
    private String directory = "";  
    
    // 初始文件信息  
    private Map<String,Long> currentFiles = new HashMap<String,Long>();
    //初始文件大小
    private Map<String,Long> fileDate = new HashMap<String, Long>();
    // 当前文件信息  
    private Set<String> newFiles = new HashSet<String>();  
      
    /** 
     * 构造函数 
     */  
    public FileSchedulerTask(){  
          
    }  
    public FileSchedulerTask(String directory){
        this.directory = directory;  
    }  
    /** 
     * 在 run() 中执行具体任务 
     */  
    public void run() {  
        File file = new File(directory);  
        if(firstRun){  
            firstRun = false;
            // 初次运行  
            loadFileInfo(file);   
        } else{
            // 检查文件更新状态[add,update]  
            checkFileUpdate(file);  
            // 检查被移除的文件[remove]  
            checkRemovedFiles();  
            //判断有没有需要解析的文件
            Iterator<String> it = currentFiles.keySet().iterator();  
            while(it.hasNext()){  
                String filename = it.next();  
                if(newFiles.contains(filename)){
                	File f = new File(filename);
                	Long length = f.length();
                	if(length.longValue() == currentFiles.get(f.getAbsolutePath()).longValue()){
                		Long dateTime = fileDate.get(filename);
                		Long newTime = new Date().getTime();
                		logger.info(String.format("开始准备解析文件：%s,状态稳定时间(s):%s",f.getAbsolutePath(),(newTime-dateTime)/1000));
                		if(((newTime-dateTime)/1000) >= 60){
                			if(f.renameTo(f.getAbsoluteFile())){
                       			logger.info("-------------------------->>>>状态稳定时间超过60s,文件"+f.getName()+"可以开始解析");
                        		fileDate.put(filename,(new Date().getTime())*2);
                           		try {
            						ReceiptFile.analytical(f.getAbsolutePath(),"E:\\backupXu");
            					} catch (Exception e) {
            						logger.info("------------->解析文件"+f.getAbsolutePath()+"出错"+e);
            						e.printStackTrace();
            					}
                           	}else{
                           		logger.info("文件还在上传中,目前大小："+f.length());
                        		fileDate.put(filename,new Date().getTime());
                           	}
                		}
                	}else{
                		fileDate.put(filename,new Date().getTime());
                	}
                }  
            }
            // 清空临时文件集合  
            newFiles.clear();
        }  
    }  
    /** 
     * 初始化文件信息 
     * @param file 
     */  
    private void loadFileInfo(File file){
        if(file.isFile()){  
            currentFiles.put(file.getAbsolutePath(), file.length());  
            fileDate.put(file.getAbsolutePath(),new Date().getTime());
            return;  
        }  
        File[] files = file.listFiles(); 
        if(files != null ){
	        for(int i=0;i<files.length;i++){  
	            loadFileInfo(files[i]);  
	        }
        }
    }  
    /** 
     * 检查文件更新状态 
     * @param file 
     */  
    private void checkFileUpdate(File file){  
    	
        if(file.isFile()){  
            // 将当前文件加入到 newFiles 集合中  
            newFiles.add(file.getAbsolutePath());  
            // 初始文件信息  取出 这个file
            Long length = currentFiles.get(file.getAbsolutePath());
            if(length == null){
                // 新加入文件  
            	logger.info("有新的文件加入："+file.getAbsolutePath());  
                currentFiles.put(file.getAbsolutePath(), file.length());  
                fileDate.put(file.getAbsolutePath(),new Date().getTime());
                return;  
            }
            Long filesize = file.length(); 
            if(length.longValue() != filesize.longValue()){  
                // 更新文件  
            	logger.info("有文件在更新："+file.getAbsolutePath());  
                currentFiles.put(file.getAbsolutePath(), file.length());
                fileDate.put(file.getAbsolutePath(),new Date().getTime());
                return;  
            }
            return;  
        } else if(file.isDirectory()){  
            File[] files = file.listFiles();  
            if(files == null || files.length == 0){  
                return;  
            }  
            for(int i=0;i<files.length;i++){  
                checkFileUpdate(files[i]);  
            }  
        }  
    }  
    /** 
     * 检查被移除的文件 
     */  
    private void checkRemovedFiles(){  
        // 增加或更新时,newFiles.size() == currentFiles.size()  
        // 删除时,    newFiles.size()  < currentFiles.size()  
        // 不可能出现      newFiles.size()  > currentFiles.size()  
        if(newFiles.size() == currentFiles.size()){  
            // 增加或更新时没有被移除的文件,直接返回  
            return;  
        }  
        Iterator<String> it = currentFiles.keySet().iterator();  
        while(it.hasNext()){  
            String filename = it.next();  
            if(!newFiles.contains(filename)){  
                /*此处不能使用 currentFiles.remove(filename);从 map 中移除元素, 否则会引发同步问题.
                 * 正确的做法是使用 it.remove();来安全地移除元素.*/
                it.remove();  
                logger.info("删除文件:" + filename);  
            }  
        }
        Iterator<String> itd = fileDate.keySet().iterator();  
        while(itd.hasNext()){  
            String filename = itd.next();  
            if(!newFiles.contains(filename)){  
                itd.remove(); 
            }  
        } 
    }  
    /** 
     * 起始目录 
     * @return 
     */  
    public String getDirectory() {  
        return directory;  
    }  
    public void setDirectory(String directory) {  
        this.directory = directory;  
    }  
}
