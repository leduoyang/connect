USE connect_base;

-- user table
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `userId`            INT PRIMARY KEY AUTO_INCREMENT,
    `uuid`              VARCHAR(256) UNIQUE,
    `username`          VARCHAR(256) UNIQUE NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `role`              TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - essential, 1 - plus, 2 - premium, 3 - admin',
    `password`          VARCHAR(256) NOT NULL,
    `description`       VARCHAR(256),
    `phone`             VARCHAR(256),
    `email`             VARCHAR(256) UNIQUE NOT NULL,
    `profileImage`      VARCHAR(256),
    `views`             INT NOT NULL DEFAULT 0,
    `followers`         INT NOT NULL DEFAULT 0,
    `followings`        INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User';
CREATE INDEX idx_userId ON `user` (userId);
CREATE INDEX idx_username ON `user` (username);

-- social_link table
DROP TABLE IF EXISTS `social_link`;
CREATE TABLE `social_link` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `platform`          VARCHAR(256) NOT NULL,
    `platform_id`       VARCHAR(256),
    `userId`            INT NOT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (userId) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Social Link';
CREATE INDEX idx_userid ON `social_link` (userId);

-- experience table
DROP TABLE IF EXISTS `experience`;
CREATE TABLE `experience` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `company`           VARCHAR(256) NOT NULL,
    `title`             VARCHAR(256) NOT NULL,
    `start`             DATE NOT NUll,
    `until`             DATE NOT NUll,
    `userId`            INT NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (userId) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Experience';
CREATE INDEX idx_userid ON `experience` (userId);

DROP TABLE IF EXISTS `email_verification`;
CREATE TABLE `email_verification` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `email`             VARCHAR(256) NOT NULL,
    `code`              VARCHAR(256) NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - DELETED, 1 - PENDING, 2 - COMPLETED, 3 - EXPIRED',
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Email Verification Code';
CREATE INDEX idx_email_code ON `email_verification` (email, code);

-- project table
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `uuid`              VARCHAR(256) UNIQUE,
    `title`             VARCHAR(256) NOT NULL,
    `description`       TEXT,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `boosted`           TINYINT(2) NOT NULL DEFAULT '0' COMMENT '0 - default, 1 - boosted',
    `stars`             INT NOT NULL DEFAULT 0,
    `views`             INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      INT NOT NULL,
    `updated_user`      INT DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (updated_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Project';
CREATE INDEX idx_userid ON `project` (created_user);

DROP TABLE IF EXISTS `project_category`;
CREATE TABLE `project_category` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `title`             VARCHAR(256) NOT NULL,
    `description`       TEXT,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `stars`             INT NOT NULL DEFAULT 0,
    `views`             INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      INT NOT NULL,
    `updated_user`      INT DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (updated_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='project_category';

-- post table
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `content`           TEXT,
    `referenceId`       INT COMMENT 'null when it is an original post, reference id otherwise',
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `stars`             INT NOT NULL DEFAULT 0,
    `views`             INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      INT NOT NULL,
    `updated_user`      INT DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (updated_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Activity';
CREATE INDEX idx_userid_post ON `post` (created_user);

-- comment Table
CREATE TABLE `comment` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `postId`            INT NOT NULL,
    `content`           TEXT NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `stars`             INT NOT NULL DEFAULT 0,
    `views`             INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      INT NOT NULL,
    `updated_user`      INT DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (updated_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (postId) REFERENCES `post`(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Comment';
CREATE INDEX idx_userid_comment ON `comment` (created_user);
CREATE INDEX idx_postId_comment ON `comment` (postId);

-- star Table
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `userId`            INT NOT NULl,
    `targetId`          INT NOT NULL,
    `targetType`        TINYINT(4) NOT NULL COMMENT '0 - project, 1 - post, 2 - comment, 3 - user',
    `isActive`          BOOLEAN NOT NULL DEFAULT TRUE,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (userId) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Star';
CREATE INDEX idx_type_userId_star ON `star` (targetType, userId);
CREATE INDEX idx_type_targetId_star ON `star` (targetType, targetId);
CREATE INDEX idx_type_targetId_active_star ON `star` (targetType, targetId, isActive);

-- follow table
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `followerId`        INT NOT NULl,
    `followingId`       INT NOT NULl,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - UNFOLLOW, 1 - PENDING, 2 - APPROVED, 3 - REJECTED',
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (`followerId`) REFERENCES `user`(`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`followingId`) REFERENCES `user`(`userId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Follow';
CREATE INDEX idx_followerId_follow ON `follow` (followerId);
CREATE INDEX idx_followerId_followingId_follow ON `follow` (followerId, followingId);
CREATE INDEX idx_followerId_followingId_status_follow ON `follow` (followerId, followingId, status);


