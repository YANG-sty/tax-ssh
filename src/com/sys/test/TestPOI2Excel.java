package com.sys.test;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestPOI2Excel {
    /**
     * 测试 单元格的创建
     * Create by y_zzu on 2019/9/25 on 10:02
     */
    @Test
    public void testWrite03Excel() throws Exception{
        //1.创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建工作表
        HSSFSheet sheet = workbook.createSheet("hello");
        //3.创建行, 第三行
        HSSFRow row = sheet.createRow(2);
        //4.创建单元格，第三行第三列
        HSSFCell cell = row.createCell(2);
        cell.setCellValue("Hello World!!!!!!!!!!!!!!");

        //输出到硬盘
        FileOutputStream outputStream = new FileOutputStream("F:\\log\\test.xls");
        //把 Excel 输出到 具体的地址
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    /**
     * .xlsx 07 版本的Excel表格
     * Create by y_zzu on 2019/9/25 on 10:01
     */
    @Test
    public void testWrite07Excel() throws Exception {
        //1.创建工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        //2.创建工作表
        XSSFSheet sheet = workbook.createSheet("hello");
        //3.创建行, 第三行
        XSSFRow row = sheet.createRow(2);
        //4.创建单元格，第三行第三列
        XSSFCell cell = row.createCell(2);
        cell.setCellValue("Xello World!!!!!!!!!!!!!!");

        //输出到硬盘
        FileOutputStream outputStream = new FileOutputStream("F:\\log\\test07.xlsx");
        //把 Excel 输出到 具体的地址
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    /**
     * 读取 03 工作表的内容
     * Create by y_zzu on 2019/9/25 on 10:08
     */
    @Test
    public void testRead03Excel() throws Exception{
        FileInputStream inputStream = new FileInputStream("F:\\log\\test.xls");
        //1.读取工作薄
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        //2.读取第一个工作表
        HSSFSheet sheet = workbook.getSheetAt(0);
        //3.读取行；读取第3行
        HSSFRow row = sheet.getRow(2);
        //4.读取单元格，读取第三行第三列
        HSSFCell cell = row.getCell(2);
        System.out.println("第三行，第三列，单元格的内容为："+cell.getStringCellValue());

        workbook.close();
        inputStream.close();
    }
    /**
     * 读取 07 工作表的内容
     * Create by y_zzu on 2019/9/25 on 10:08
     */
    @Test
    public void testRead07Excel() throws Exception{
        FileInputStream inputStream = new FileInputStream("F:\\log\\test07.xlsx");
        //1.读取工作薄
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //2.读取第一个工作表
        XSSFSheet sheet = workbook.getSheetAt(0);
        //3.读取行；读取第3行
        XSSFRow row = sheet.getRow(2);
        //4.读取单元格，读取第三行第三列
        XSSFCell cell = row.getCell(2);
        System.out.println("第三行，第三列，单元格的内容为："+cell.getStringCellValue());

        workbook.close();
        inputStream.close();
    }

    /**
     * 将 03 和 07 的读取 合成一个
     * Create by y_zzu on 2019/9/25 on 10:18
     */
    @Test
    public void testRead03And07Excel() throws IOException {
//        String fileName = "F:\\log\\test.xls";
        String fileName = "F:\\log\\test07.xlsx";
        if(fileName.matches("^.+\\.(?i)((xls)|(xlsx))$")){
            //判断文档是否是 Excel 文档
            boolean is03Excel = fileName.matches("^.+\\.(?i)(xls)$");
            FileInputStream inputStream = new FileInputStream(fileName);
            //1.读取工作薄
            Workbook workbook = is03Excel ?new HSSFWorkbook(inputStream):new XSSFWorkbook(inputStream);
            //2.读取一个工作表, Excel文档（工作薄） 的第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            //3.读取行
            Row row = sheet.getRow(2);
            //4.读取列
            Cell cell = row.getCell(2);
            System.out.println("第三行，第三列，单元格的内容为："+cell.getStringCellValue());

            workbook.close();
            inputStream.close();
        }
    }

    /**
     * 设置单元格的 样式
     * Create by y_zzu on 2019/9/25 on 10:28
     */
    @Test
    public void testExcelStyle() throws IOException {
        //1.创建工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
//        System.out.println(workbook);
        //1.1 创建合并单元格对象；合并第3行的  第3列到第5列
        CellRangeAddress cellRangeAddress =
                new CellRangeAddress(2,2,2,4);//起始行号，结束行号，起始列号，结束列号
        //1.2 设置单元格样式
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
        //1.3 创建字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//字体加粗
        font.setFontHeightInPoints((short) 16);//设置字体大小
        //加载字体
        style.setFont(font);
        //1.4 单元格背景颜色
        //设置背景填充模式
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //设置填充背景颜色
        style.setFillBackgroundColor(HSSFColor.YELLOW.index);
        //设置填充前景色
        style.setFillForegroundColor(HSSFColor.RED.index);

        //2、创建工作表
        HSSFSheet sheet = workbook.createSheet("Hello World");//指定工作表名
        //2.1、加载合并单元格对象
        sheet.addMergedRegion(cellRangeAddress);

        //3、创建行；创建第3行
        HSSFRow row = sheet.createRow(2);
        //4、创建单元格；创建第3行第3列
        HSSFCell cell = row.createCell(2);
        //加载样式
        cell.setCellStyle(style);
        cell.setCellValue("Hello World!");

        //输出到硬盘
        FileOutputStream outputStream = new FileOutputStream("F:\\log\\logstyle.xls");
        //把excel输出到具体的地址
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }


}
