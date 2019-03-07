package cn.varfunc.restaurant.util;

import org.hibernate.dialect.MariaDB103Dialect;

/**
 * Modify default database dialect
 */
public class MyMariaDBDialect extends MariaDB103Dialect {
    /**
     * Specify character set to <code>utf8mb4</code> while creating database table
     * so you don't have to change the database configuration.
     */
    @Override
    public String getTableTypeString() {
        String engine = super.getTableTypeString();
        return String.format("%s CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci", engine);
    }
}
