package demo.common;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExportExecl {
	private static final Log logger = LogFactory.getLog(ExportExecl.class);
	private static String filename="";
	
	public static HSSFWorkbook Expor(List<Map<String,Object>> list , String[] Column , String[] title , Map<String , Map<Integer, String>> remark , String name) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String datesdf = sdf.format(new Date());
		
		HSSFWorkbook wb = new HSSFWorkbook();    
		HSSFSheet sheet = wb.createSheet(name); 
		
		HSSFRow row = sheet.createRow((int) 0);  
		
		HSSFCellStyle headstyle = wb.createCellStyle();
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        HSSFFont f1  = wb.createFont();      
        f1.setFontHeightInPoints((short) 16);// 字号   
        f1.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// 加粗   
        headstyle.setFont(f1);

		for (int i = 0; i < title.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(title[i]);    
			cell.setCellStyle(headstyle);    
			sheet.autoSizeColumn(i);    
		}    
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFDataFormat format = wb.createDataFormat();   
        headstyle.setDataFormat(format.getFormat("@"));  
        HSSFFont f2  = wb.createFont();      
        f2.setFontHeightInPoints((short) 11);// 字号   
        headstyle.setFont(f2);
        
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			Map<String,Object> map = list.get(i);
			for(int j = 0 ; j < Column.length ; j ++){
				if(mapkey(remark,Column[j])){	//如果这个键不在remark中存在，直接将值存入，如果存在则需要翻译成对应的意思后存入
					HSSFCell cell = row.createCell(j);
					String value = map.get(Column[j])==null?"":map.get(Column[j]).toString();
					cell.setCellValue(value);
					cell.setCellStyle(style);
            	}else{
                	Map<Integer,String> val = remark.get(Column[j]);
					HSSFCell cell = row.createCell(j);
					cell.setCellValue(val.get((Integer)map.get(Column[j])).toString());    
					cell.setCellStyle(style);
            	}
			}
		}
        filename = name+" "+datesdf+".xls";
        logger.info("==============="+filename);
		return wb;   
	}
	//判断一个key是否在一个map中存在
	public static boolean mapkey(Map<String, Map<Integer, String>> remark, String key) {
		if(remark == null)return true;
		Iterator keys = remark.keySet().iterator();
		while(keys.hasNext()){
			String key1 = (String)keys.next();
			if(key.equals(key1)){
				return false;
			}
		}
		return true;
	}
	
	public static void downloadFile(HSSFWorkbook data,HttpServletResponse response) throws IOException{
	    response.setContentType("multipart/from-data");
	    response.setHeader("Content-Disposition","attachment; filename ="+filename);
	    OutputStream out = response.getOutputStream();
	    data.write(out);
	    out.flush();
	    out.close();
	}
}
