package denguestatisticssystem;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.javatuples.Pair;
import org.jpl7.Query;
import org.jpl7.Term;

public class PrologConnection {
  // prolog file location
  private static final String PROLOG_FILE = "InsertionSort.pl";

  // get descendingly sorted dengue cases
  public static List<Pair<String, Integer>> getDescSortedList(List<Pair<String, Integer>> totalCasesList, BidirectionalMap<Integer, String> districtMap) {
    // start connection
    Query connectionQuery = new Query("consult('" + PROLOG_FILE + "')");

    // check if connection successful
    if (connectionQuery.hasSolution()) {
      // create query string
      String queryString = "desc_insertion_sort([";

      // send dengue cases data into prolog
      for (int i = 0; i < totalCasesList.size() - 1; i++) {
        queryString += "('" + districtMap.getValue(i) + "'," + totalCasesList.get(i).getValue1() + "),";
      }
      queryString += "('" + districtMap.getValue(totalCasesList.size() - 1) + "'," + totalCasesList.get(totalCasesList.size() - 1).getValue1() + ")],Sorted).";

      // sort using prolog
      Query query = new Query(queryString);

      // convert returned data into list
      return convertTermToList(new ArrayList<>(), query.oneSolution().get("Sorted"));
    } else {
      // display error message box
      JOptionPane.showMessageDialog(new JFrame(), "Connection Failed", "Error", JOptionPane.ERROR_MESSAGE);
    }

    // close query connection
    connectionQuery.close();
    return null;
  }

  // recursively convert term to list of pair
  private static List<Pair<String, Integer>> convertTermToList(List<Pair<String, Integer>> termList, Term term) {
    // check if term is empty
    if (term.arity() != 0) {
      // read cases details
      String districtString = term.arg(1).arg(1).toString();
      String district = districtString.substring(1, districtString.length() - 1);
      int cases = term.arg(1).arg(2).intValue();

      // add dengue cases to list
      termList.add(new Pair<>(district, cases));

      // recursively call to split term
      return convertTermToList(termList, term.arg(2));
    }
    return termList;
  }
}
