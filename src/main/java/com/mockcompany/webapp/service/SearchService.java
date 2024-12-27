package com.mockcompany.webapp.service;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.regex.Pattern;

public class SearchService {
    @Autowired
    public SearchService() {
    }

    public SearchReportResponse report(EntityManager entityManager) {
        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        int count = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();

        List<Number> matchingIds = new ArrayList<>();
        matchingIds.addAll(
                entityManager.createQuery("SELECT item.id from ProductItem item where item.name like '%cool%'").getResultList()
        );
        matchingIds.addAll(
                entityManager.createQuery("SELECT item.id from ProductItem item where item.description like '%cool%'").getResultList()
        );
        matchingIds.addAll(
                entityManager.createQuery("SELECT item.id from ProductItem item where item.name like '%Cool%'").getResultList()
        );
        matchingIds.addAll(
                entityManager.createQuery("SELECT item.id from ProductItem item where item.description like '%cool%'").getResultList()
        );
        List<Number> counted = new ArrayList<>();
        for (Number id: matchingIds) {
            if (!counted.contains(id)) {
                counted.add(id);
            }
        }

        response.getSearchTermHits().put("Cool", counted.size());


        response.setProductCount(count);

        List<ProductItem> allItems = entityManager.createQuery("SELECT item FROM ProductItem item").getResultList();
        int kidCount = 0;
        int perfectCount = 0;
        Pattern kidPattern = Pattern.compile("(.*)[kK][iI][dD][sS](.*)");
        for (ProductItem item : allItems) {
            if (kidPattern.matcher(item.getName()).matches() || kidPattern.matcher(item.getDescription()).matches()) {
                kidCount += 1;
            }
            if (item.getName().toLowerCase().contains("perfect") || item.getDescription().toLowerCase().contains("perfect")) {
                perfectCount += 1;
            }
        }
        response.getSearchTermHits().put("Kids", kidCount);

        response.getSearchTermHits().put("Amazing", entityManager.createQuery("SELECT item FROM ProductItem item where lower(concat(item.name, ' - ', item.description)) like '%amazing%'").getResultList().size());

        hits.put("Perfect", perfectCount);

        return response;
    }

    public Collection<ProductItem> search(String query, ProductItemRepository productItemRepository) {
        /*
         *  For an added challenge, update the ProductItemRepository to do the filtering at the database layer :)
         */

        Iterable<ProductItem> allItems = productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        // This is a loop that the code inside will execute on each of the items from the database.
        for (ProductItem item : allItems) {
            if (query.charAt(0) == '"' && query.charAt(query.length() - 1) == '"') {
                // exact match
                String queryWithoutQuotes = query.substring(1, query.length() - 1);
                if (!(item.getName().equalsIgnoreCase(queryWithoutQuotes)
                        || item.getDescription().equalsIgnoreCase(queryWithoutQuotes)))
                    continue;
            } else {
                // partial match
                if (!(item.getName().toLowerCase().contains(query.toLowerCase())
                        || item.getDescription().toLowerCase().contains(query.toLowerCase())))
                    continue;
            }

            itemList.add(item);
        }
        return itemList;

        // filter from database layer
        //return productItemRepository.findByNameOrDescriptionEqualsOrContainsIgnoreCase(query);
    }
}
