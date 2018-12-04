package myParser;

import org.apache.poi.hssf.usermodel.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ExcelFile {

    private String pathNameForFile;
    private HSSFSheet sheet;
    private HSSFWorkbook workbook;
    HSSFFont font;
    HSSFCellStyle style;

    public void createExcelFile(String pathNameForFile, String sheetName)  {
        this.pathNameForFile = pathNameForFile;
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(sheetName);
        font = workbook.createFont();
        font.setBold(true);
        style = workbook.createCellStyle();
        style.setFont(font);


        HSSFRow rowhead = sheet.createRow((short)0);

        rowhead.createCell(0).setCellValue("LINKS");
        rowhead.createCell(1).setCellValue("STATUS");
        rowhead.getCell(0).setCellStyle(style);
        rowhead.getCell(1).setCellStyle(style);

    }

    public void writeExcel(int rowNumber, String link, int status){
        HSSFRow row = sheet.createRow((short)rowNumber);
        row.createCell(0).setCellValue(link);
        row.createCell(1).setCellValue(status);
    }

    public void closeExcelFile()throws IOException{

        try {

            FileOutputStream fileOut = new FileOutputStream(pathNameForFile);
            workbook.write(fileOut);
            fileOut.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Your excel file has been generated!");
    }

}
