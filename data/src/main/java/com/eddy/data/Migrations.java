package com.eddy.data;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public abstract class Migrations {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Copy book table
            database.execSQL("CREATE TABLE IF NOT EXISTS `book_copy` (" +
                    "`title` TEXT NOT NULL, " +
                    "`author` TEXT NOT NULL, " +
                    "`description` TEXT, " +
                    "`book_image_url` TEXT, " +
                    "`book_image_width` INTEGER, " +
                    "`book_image_height` INTEGER, " +
                    "`review_url` TEXT, " +
                    "`publisher` TEXT, " +
                    "`rank_this_week` INTEGER, " +
                    "`rank_last_week` INTEGER, " +
                    "`weeks_on_list` INTEGER, " +
                    "PRIMARY KEY(`title`, `author`))");
             // Transfer rows from book to book_copy
            database.execSQL("INSERT INTO book_copy (" +
                    "`title`, `author`, `description`, `book_image_url`," +
                    "`book_image_width`, `book_image_height`, `review_url`, `publisher`," +
                    "`rank_this_week`, `rank_last_week`, `weeks_on_list`)" +
                    "SELECT `title`, `author`, `description`, `book_image_url`, " +
                    "`book_image_width`, `book_image_height`, `review_url`, `publisher`, " +
                    "`rank_this_week`, `rank_last_week`, `weeks_on_list` " +
                    "FROM book WHERE `is_bookmarked`=1 " +
                    "ORDER BY id");

            // Copy buy_link table
            database.execSQL("CREATE TABLE IF NOT EXISTS `buy_link_copy` (" +
                    "`name` TEXT, " +
                    "`url` TEXT NOT NULL, " +
                    "`book_title` TEXT, " +
                    "`book_author` TEXT, " +
                    "PRIMARY KEY(`url`), " +
                    "FOREIGN KEY(`book_title`, `book_author`) " +
                    "REFERENCES `book`(`title`, `author`) " +
                    "ON UPDATE NO ACTION " +
                    "ON DELETE CASCADE )");
            database.execSQL("CREATE INDEX `index_buy_link_book_title_book_author` " +
                    "ON buy_link_copy(`book_title`, `book_author`)");

            // Transfer rows from buy_link to buy_link_copy
            database.execSQL("INSERT INTO buy_link_copy (" +
                    "`name`, `url`, `book_title`, `book_author`) " +
                    "SELECT `name`, `url`, book.title, book.author " +
                    "FROM buy_link JOIN book WHERE book.id=buy_link.book_id " +
                    "ORDER BY buy_link.id");

            // Drop tables
            database.execSQL("DROP TABLE IF EXISTS category");
            database.execSQL("DROP TABLE IF EXISTS book_category");
            database.execSQL("DROP TABLE IF EXISTS book");
            database.execSQL("DROP TABLE IF EXISTS buy_link");

            // Rename copies to original names
            database.execSQL("ALTER TABLE `book_copy` RENAME TO `book`");
            database.execSQL("ALTER TABLE `buy_link_copy` RENAME TO `buy_link`");
        }
    };

}
