package denguestatisticssystem;

import java.time.Year;

public class DengueCase {
  // properties
  private String district;
  private Year year;
  private int cases;

  // constructor
  DengueCase(String district, Year year, int cases) {
    this.district = district;
    this.year = year;
    this.cases = cases;
  }

  // getters
  public String getDistrict() {
    return district;
  }

  public Year getYear() {
    return year;
  }

  public int getCases() {
    return cases;
  }
}
