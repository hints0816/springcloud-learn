package com.hints.serverthree.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    public OutputStream queryUser(String id, HttpServletResponse response)throws Exception{
        OutputStream out = null;
        try {
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            //2.创建工作簿
            HSSFSheet sheet = hssfWorkbook.createSheet();
            //3.创建标题行
            HSSFRow titlerRow = sheet.createRow(0);
            titlerRow.createCell(0).setCellValue("省");
            titlerRow.createCell(1).setCellValue("市");
            titlerRow.createCell(2).setCellValue("区");
            titlerRow.createCell(3).setCellValue("邮编");
            titlerRow.createCell(4).setCellValue("简码");
            titlerRow.createCell(5).setCellValue("城市编码");

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("省", "1");
            map.put("市", "1");
            map.put("区", "1");
            map.put("邮编", "1");
            map.put("简码", "1");
            map.put("城市简码", "1");
            list.add(map);
            //4.遍历数据,创建数据行
            for (Map<String, Object> mapList : list) {
                //获取最后一行的行号
                int lastRowNum = sheet.getLastRowNum();
                HSSFRow dataRow = sheet.createRow(lastRowNum + 1);
                dataRow.createCell(0).setCellValue(mapList.get("省").toString());
                dataRow.createCell(1).setCellValue(mapList.get("市").toString());
                dataRow.createCell(2).setCellValue(mapList.get("区").toString());
                dataRow.createCell(3).setCellValue(mapList.get("邮编").toString());
                dataRow.createCell(4).setCellValue(mapList.get("简码").toString());
                dataRow.createCell(5).setCellValue(mapList.get("城市简码").toString());
            }
            out = response.getOutputStream();
            hssfWorkbook.write(out);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return out;
    }
}
