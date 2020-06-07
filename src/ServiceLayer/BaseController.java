package ServiceLayer;

import BuisinessLayer.LogicalOperations.BaseFunctionality;
import BuisinessLayer.LogicalOperations.EnumCreator;

import java.util.*;

public class BaseController {

    //Fields


    public BaseController() {
    }

    /***
     * U.C 2.4+2.5 - Search Information.
     *
     * @param userOrGuest
     * @return
     */
    public List<Object> search(BaseFunctionality userOrGuest) {

        //let user choose his option
        SearchOptionsType chosenSearchOption = chooseWhatToSearch();

        //let user choose his filter option
        List<SearchFilterType> chosenFilters = chooseFilterToSearch();

        //get from the user input
        String freeTextFromUser = getTextFromUser();

        List result = userOrGuest.searchDataBase(chosenSearchOption, chosenFilters, freeTextFromUser);

        return result;
    }


    /***
     * Let the user choose which search option he want.
     *
     * @return SearchOption with the result.
     */
    private SearchOptionsType chooseWhatToSearch() {

        //connect scanner
        Scanner sc = new Scanner(System.in);

        //send to user options
        System.out.println(String.format("choose option:\n%s\n%s\n%s\n",
                SearchOptionsType.name,
                SearchOptionsType.category,
                SearchOptionsType.keyWords));

        String input;
        SearchOptionsType searchOptions;

        do {

            input = sc.nextLine();

            searchOptions = EnumCreator.createSearchOption(input);
        }
        while(!(searchOptions instanceof SearchOptionsType));

        //remove scanner from system in
        sc.close();

        return searchOptions;
    }

    /***
     * Let the user choose which search filter he want.
     *
     * @return List<SearchFilterType> of user search filters
     */
    private List<SearchFilterType> chooseFilterToSearch() {

        //Connect Scanner to System.in
        Scanner sc = new Scanner(System.in);

        List<SearchFilterType> filters = new ArrayList<>();

        //send to user options
        System.out.println(String.format("choose option:\n%s\n%s\n%s\n%s\n%s\nEnter blank line to finish.",
                SearchFilterType.Role,
                SearchFilterType.League,
                SearchFilterType.Team,
                SearchFilterType.Season,
                SearchFilterType.Gender));

        //initial variables
        String input;
        SearchFilterType filter;

        input = sc.nextLine();

        //adding filter by user
        while (input.equals("")) {

            filter = EnumCreator.createSearchFilter(input);

            if(filter != null) {
                filters.add(filter);
            }
        }

        Map<String, String> dict = new HashMap<>();
        String answer;

        for(SearchFilterType chosenFilter : filters) {

            System.out.println(String.format("which %s ?", chosenFilter.toString()));

            answer = sc.nextLine();

            while(answer == null || answer.equals("")){
                answer = sc.nextLine();
            }

            dict.put(chosenFilter.toString(), answer);
        }

        //remove scanner from System.in
        sc.close();

        return filters;
    }

    private String getTextFromUser() {

        //initial scanner
        Scanner sc = new Scanner(System.in);

        String searchText = null;

        while(searchText == null || searchText.equals("")) {

            searchText = sc.nextLine();

        }

        //remove scanner
        sc.close();

        return searchText;
    }

}
