package by.a1qa.poznyak.support;

import by.a1qa.poznyak.dataProcessors.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheckingTheSorting {
    public static boolean isListSorted(List<Post> listOfPosts) {
        List <Integer> idList = new ArrayList<>();
        for (Post post: listOfPosts) {
            idList.add(post.getId());
        }

        List<Integer> listForSort = new ArrayList<Integer>(idList);
        Collections.sort(listForSort);
        return listForSort.equals(idList);
    }
}
