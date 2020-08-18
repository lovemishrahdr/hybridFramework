package com.framework.TestUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {

    Logger log = LogManager.getLogger(ExcelReader.class.getName());
    PropertyReader pro = new PropertyReader();
    XSSFWorkbook book;
    XSSFSheet sheet;
    XSSFCell cell;
    String[][] inputData;

    // public void readExcelData() {
    public String[][] getData() {
        String excelBook = pro.readProperty().getProperty("book_name");
        String excelSheet = pro.readProperty().getProperty("sheet_name");
        try {
            FileInputStream fileStream = new FileInputStream("./datafiles/" + excelBook);
            book = new XSSFWorkbook(fileStream);
        } catch (Exception e) {
            log.error("Unable to read excel file. Please check the file in the location.");
        }
        try {
            sheet = book.getSheet(excelSheet);
            inputData = new String[sheet.getLastRowNum()][7];
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                for (int j = 0; j < 7; j++) {
                    try {
                        inputData[i - 1][j] = sheet.getRow(i).getCell(j).getStringCellValue();
                    } catch (Exception e) {
                        inputData[i - 1][j] = String.valueOf((int) sheet.getRow(i).getCell(j).getNumericCellValue());
                    }
                }
            }
            return inputData;

        } catch (Exception e) {
            log.error(
                    "Unable to read the sheet. Please verify the correct sheet name or the sheet name in workbook location");
            return null;
        }

    }
}

