package denguestatisticssystem;

import java.io.FileInputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxReader {
  // data file locations
  private static final String TARGET_FILE_1 = "statistik-kes-denggi-di-negeri-pahang-bagi-tempoh-2014-2017.xlsx";
  private static final String TARGET_FILE_2 = "statistik-kes-denggi-di-negeri-pahang-bagi-tempoh-2018-2019.xlsx";

  // properties
  private List<DengueCase> dengueCaseList = new ArrayList<>();
  private BidirectionalMap<Integer, String> districtMap = new BidirectionalMap<>();
  private BidirectionalMap<Integer, Year> yearMap = new BidirectionalMap<>();

  // constructor
  XlsxReader() {
    try {
      // read excel files
      XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(TARGET_FILE_1));
      XSSFWorkbook workbook2 = new XSSFWorkbook(new FileInputStream(TARGET_FILE_2));

      // get excel sheets
      XSSFSheet sheet1 = workbook1.getSheetAt(0);
      XSSFSheet sheet2 = workbook2.getSheetAt(0);

      // initialise keys
      int districtKey = 0;
      int yearKey = 0;

      // read first file
      for (Row row : sheet1) {
        // skip rows
        if (row.getRowNum() < 4) {
          continue;
        }

        // read cells
        for (Cell cell : row) {
          // skip columns
          if (cell.getColumnIndex() < 2) {
            continue;
          }

          // read cell details
          String district = row.getCell(1).getStringCellValue().trim();
          String yearString = sheet1.getRow(2).getCell(cell.getColumnIndex()).getStringCellValue();
          Year year = Year.parse(yearString.substring(yearString.length() - 4));
          int cases = (int) cell.getNumericCellValue();

          // add cell to list
          dengueCaseList.add(new DengueCase(district, year, cases));

          // add unique district to bidirectional list
          if (districtMap.getKey(district) == null) {
            districtMap.put(districtKey++, district);
          }

          // add unique year to bidirectional list
          if (yearMap.getKey(year) == null) {
            yearMap.put(yearKey++, year);
          }

          // end at column
          if (cell.getColumnIndex() > 3) {
            break;
          }
        }

        // end at row
        if (row.getRowNum() > 13) {
          break;
        }
      }

      // read second file
      for (Row row : sheet2) {
        // skip rows
        if (row.getRowNum() < 5) {
          continue;
        }

        // read cell
        for (Cell cell : row) {
          // skip columns
          if (cell.getColumnIndex() < 2) {
            continue;
          }

          // read cell details
          String district = row.getCell(1).getStringCellValue().trim();
          String yearString = Double.toString(sheet2.getRow(4).getCell(cell.getColumnIndex()).getNumericCellValue());
          Year year = Year.parse(yearString.substring(0, 4));
          int cases = (int) cell.getNumericCellValue();

          // add cell to list
          dengueCaseList.add(new DengueCase(district, year, cases));

          // add unique district to bidirectional list
          if (districtMap.getKey(district) == null) {
            districtMap.put(districtKey++, district);
          }

          // add unique year to bidirectional list
          if (yearMap.getKey(year) == null) {
            yearMap.put(yearKey++, year);
          }
        }

        // end at row
        if (row.getRowNum() > 14) {
          break;
        }
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  // getters
  public List<DengueCase> getDengueCaseList() {
    return dengueCaseList;
  }

  public BidirectionalMap<Integer, String> getDistrictMap() {
    return districtMap;
  }

  public BidirectionalMap<Integer, Year> getYearMap() {
    return yearMap;
  }
}
