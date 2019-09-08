package camagru.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConfigRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void setup() {
        jdbcTemplate.update("DROP TABLE IF EXISTS `comments`;");
        jdbcTemplate.update("CREATE TABLE `comments` (   `id` int(11) NOT NULL AUTO_INCREMENT,   `userId` int(11) NOT NULL,   `postId` int(11) DEFAULT NULL,   `content` varchar(300) NOT NULL,   `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,   PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        jdbcTemplate.update("DROP TABLE IF EXISTS `likes`;");
        jdbcTemplate.update("CREATE TABLE `likes` (   `userId` int(11) NOT NULL,   `postId` int(11) NOT NULL,   PRIMARY KEY (`userId`,`postId`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        jdbcTemplate.update("DROP TABLE IF EXISTS `posts`;");
        jdbcTemplate.update("CREATE TABLE `posts` (   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,   `userId` int(11) unsigned DEFAULT NULL,   `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,   PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        jdbcTemplate.update("DROP TABLE IF EXISTS `unique_urls`;");
        jdbcTemplate.update("CREATE TABLE `unique_urls` (   `id` int(11) unsigned NOT NULL AUTO_INCREMENT,   `userId` int(11) DEFAULT NULL,   `url` varchar(35) DEFAULT NULL,   PRIMARY KEY (`id`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");

        jdbcTemplate.update("DROP TABLE IF EXISTS `users`;");
        jdbcTemplate.update("CREATE TABLE `users` (   `id` int(11) NOT NULL AUTO_INCREMENT,   `email` varchar(100) NOT NULL,   `username` varchar(100) NOT NULL,   `password` varchar(10000) NOT NULL DEFAULT '',   `active` tinyint(3) unsigned NOT NULL DEFAULT '0',   `receive_notifications` tinyint(3) unsigned NOT NULL DEFAULT '1',   `salt` varchar(1600) DEFAULT NULL,   PRIMARY KEY (`id`),   UNIQUE KEY `email` (`email`,`username`) ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
    }
}