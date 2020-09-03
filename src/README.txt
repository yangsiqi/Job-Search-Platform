For the user who wants to use this application, the project.sql file should be added to the local database.
If the user's local port is not 3306, or username is not equal to root, or password is not equal to root please change in the db.properties file.
If the user sees the error java.sql.SQLNonTransientConnectionException: No operations allowed after connection closed which is due to the default wait_time setting.
This error can be resolved by re-run or set the wait_timeout longer in my.cnf file.