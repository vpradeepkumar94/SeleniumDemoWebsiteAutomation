/**
 * 
 */
package com.excels.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excel.java.TestData;

public class ReadExcelFile {

	// create a HashMap to store all the test Data
    protected static Map<String, TestData> maps = new  LinkedHashMap<String, TestData>();

	public static Object getCellType(Cell cell) {
		// Returns the cell Type present in the excel file
		switch(cell.getCellTypeEnum()) {
		  case BOOLEAN:  return cell.getBooleanCellValue();
		  case NUMERIC:  return cell.getNumericCellValue();
		  case STRING:   return cell.getStringCellValue();
		  default:		  return null;
		}
   }

	public static Map<String, TestData> readExcelFile(String filePath) throws IOException {
	
		try (FileInputStream  fis = new  FileInputStream(new File(filePath));
			 XSSFWorkbook workbook = new XSSFWorkbook(fis);) {
			
			// specify the sheet from which the data has to be loaded
			Sheet currentSheet = workbook.getSheetAt(0);
			
			// use iterator to fetch all the rows present in the sheet
			Iterator<Row> rows = currentSheet.iterator();
		
			// while if there is another row exists
			while(rows.hasNext()) {
				// instantiate an object of the POJO Class
				TestData td = new TestData();
				//get the current row
				Row currentRow = rows.next();
				// split the rows into different cells using iterator
				Iterator<Cell> cells = currentRow.cellIterator();
				String key = "";
				// get the row number of the current row
				int rowNumber =currentRow.getRowNum();	
				// while if cell exists in the current row 
				while( cells.hasNext()) {
	  			  // get the current cell
				   Cell currentCell = cells.next();
				   // get the column index
				   int columnIndex = currentCell.getColumnIndex();
					// skip the first row,as the first row is of type Header	
					if(rowNumber >0) {
						// Cell 0 will always acts as 'Key' as it is being Unique
						if(currentCell.getColumnIndex() == 0)
					  	   key = currentCell.getStringCellValue();
						 // based  upon the cell type assign values to the POJO Class
							switch(columnIndex) {
								case 1:  
						   	          td.setFirstName((String)getCellType(currentCell));
									  break;
								case 2:
									 td.setAge((Double)getCellType(currentCell));
									  break;
								case 3:
								      td.setGender((String)getCellType(currentCell));
									  break;
								case 4:
								      td.setMajor((boolean) getCellType(currentCell));
									 break;
							} // end of switch block
						} // end of  updating Pojo Class for each row (if block 0
					} // end of cell iterator  ( while block )
				// update the HashMap with the values obtained.
				 if(rowNumber >0) {
					maps.put(key, td);
				 }
			} // end of row Iterator while block
		 } catch( Exception e) {
			System.out.println(e.getMessage());
		 }
	 return maps;
	}
	public static TestData getPOJOData(String key) {
		 System.out.println(maps.get("TC001"));
		 if(maps.containsKey(key))
		   return maps.get(key); 
		 else {
			 return  null;
		 }
		
	}
	
	public static void main(String[] args) throws IOException {
		// specify the file Name here
		 Map<String, TestData> ds = readExcelFile("C:\\Users\\Vamsi\\Documents\\Data.xlsx");
		 
		 String key  = "TC002";
		 TestData data = getPOJOData(key);
		 System.out.println("First Name is " +data.getFirstName());
		 System.out.println("Age is " + data.getAge());
		 System.out.println("Gender is " + data.getGender());
		 System.out.println("Is he  Major " + data.isMajor());
		 
		 // iterate over all the available key value pairs
		 ds.forEach((k,v) -> {
			if(!k.equalsIgnoreCase("TC0010")) {
				System.out.println("Name: " + v.getFirstName() +  " Gender is  "+ v.getGender() + 
						"Age is " + v.getAge() +  "Is he  Major ? " +v.isMajor());
			}
		 });
	}

}
