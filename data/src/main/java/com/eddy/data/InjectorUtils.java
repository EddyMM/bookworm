package com.eddy.data;

import android.content.Context;

import com.eddy.data.repository.BooksListRepository;
import com.eddy.data.repository.CategoriesRepository;

public abstract class InjectorUtils {

    public static CategoriesDataSource getCategoriesDataSource(Context context) {
        return CategoriesDataSource.getInstance(context);
    }

    public static CategoriesRepository getCategoriesRepository(Context context) {
        BookwormDatabase bookwormDatabase = BookwormDatabase.getInstance(context);
        return CategoriesRepository.getInstance(
                getCategoriesDataSource(context), bookwormDatabase.categoriesDao());
    }

    public static BooksListRepository getBooksListRepository(Context context) {
//        return BooksListRepository.getInstance(context);
        return null;
    }
}
