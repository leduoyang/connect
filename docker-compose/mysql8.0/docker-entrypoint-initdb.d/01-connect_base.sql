USE connect_base;

-- user table
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `userId`            VARCHAR(256) UNIQUE NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `role`              TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - essential, 1 - plus, 2 - premium, 3 - admin',
    `password`          VARCHAR(256) NOT NULL,
    `description`       VARCHAR(256),
    `phone`             VARCHAR(256),
    `email`             VARCHAR(256) UNIQUE NOT NULL,
    `profileImage`      VARCHAR(256),
    `likesCount`        INT NOT NULL DEFAULT 0,
    `viewsCount`        INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='User';
CREATE INDEX idx_userId ON `user` (userId);
CREATE INDEX idx_email ON `user` (email);

DROP TABLE IF EXISTS `email_verification`;
CREATE TABLE `email_verification` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `email`             VARCHAR(256) NOT NULL,
    `code`              VARCHAR(256) NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Email Verification Code';
CREATE INDEX idx_email_code ON `email_verification` (email, code);

-- project table
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `title`             VARCHAR(256) NOT NULL,
    `description`       TEXT,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `boosted`           TINYINT(2) NOT NULL DEFAULT '0' COMMENT '0 - default, 1 - boosted',
    `likesCount`        INT NOT NULL DEFAULT 0,
    `viewsCount`        INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      VARCHAR(256) NOT NULL,
    `updated_user`      VARCHAR(256) DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Project';
CREATE INDEX idx_userid ON `project` (created_user);

DROP TABLE IF EXISTS `project_category`;
CREATE TABLE `project_category` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `title`             VARCHAR(256) NOT NULL,
    `description`       TEXT,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `viewsCount`        INT DEFAULT 0,
    `version`           INT DEFAULT 1,  -- Optimistic Locking: Version column
    `created_user`      VARCHAR(256) NOT NULL,
    `updated_user`      VARCHAR(256) DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='project_category';

-- post table
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `content`           TEXT,
    `referenceId`       INT COMMENT 'null when it is an original post, reference id otherwise',
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `likesCount`        INT NOT NULL DEFAULT 0,
    `viewsCount`        INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      VARCHAR(256) NOT NULL,
    `updated_user`      VARCHAR(256) DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Activity';
CREATE INDEX idx_userid_post ON `post` (created_user);

-- comment Table
CREATE TABLE `comment` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `postId`            INT NOT NULL,
    `content`           TEXT NOT NULL,
    `status`            TINYINT(4) NOT NULL DEFAULT '0' COMMENT '0 - public, 1 - semi, 2 - private, 3 - deleted',
    `tags`              VARCHAR(256) DEFAULT NULL,
    `likesCount`        INT NOT NULL DEFAULT 0,
    `viewsCount`        INT NOT NULL DEFAULT 0,
    `version`           INT NOT NULL DEFAULT 1,
    `created_user`      VARCHAR(256) NOT NULL,
    `updated_user`      VARCHAR(256) DEFAULT NULL,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    `db_modify_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3) ON UPDATE CURRENT_TIMESTAMP (3),
    FOREIGN KEY (created_user) REFERENCES `user`(userId) ON DELETE CASCADE,
    FOREIGN KEY (postId) REFERENCES `post`(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Comment';
CREATE INDEX idx_userid_comment ON `comment` (created_user);
CREATE INDEX idx_postId_comment ON `comment` (postId);

-- star Table
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `userId`            VARCHAR(256) NOT NULl,
    `targetId`          INT NOT NULL,
    `targetType`        TINYINT(4) NOT NULL COMMENT '0 - project, 1 - post, 2 - comment, 3 - user',
    `active`            BOOLEAN NOT NULL DEFAULT TRUE,
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    FOREIGN KEY (userId) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Star';
CREATE INDEX idx_userId_star ON `star` (userId);
CREATE INDEX idx_type_targetId_star ON `star` (type, targetId);

-- subscribe table
DROP TABLE IF EXISTS `subscribe`;
CREATE TABLE `subscribe` (
    `id`                INT PRIMARY KEY AUTO_INCREMENT,
    `userId`            VARCHAR(256) NOT NULl,
    `targetId`          INT NOT NULL,
    `type`              TINYINT(4) NOT NULL COMMENT '0 - project, 1 - user, 2 - post, 3 - comment',
    `db_create_time`    DATETIME(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
    FOREIGN KEY (userId) REFERENCES `user`(userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Subscribe';
CREATE INDEX idx_userId_subscribe ON `subscribe` (userId);
CREATE INDEX idx_type_targetId_subscribe ON `subscribe` (type, targetId);

-- Insert Mock User Data
INSERT INTO `user` (userId, status, password, email, db_create_time)
VALUES
    ('ROOT', 0, 'thisisrootpassword', 'connect.sideproject@gmail.com', CURRENT_TIMESTAMP),
    ('john_doe', 0, 'john123', 'john.doe@email.com', CURRENT_TIMESTAMP),
    ('alice_smith', 0, 'alice456', 'alice.smith@email.com', CURRENT_TIMESTAMP),
    ('bob_jones', 0, 'bob789', 'bob.jones@email.com', CURRENT_TIMESTAMP),
    ('sarah_miller', 0, 'sarah456', 'sarah.miller@email.com', CURRENT_TIMESTAMP),
    ('david_wilson', 0, 'david789', 'david.wilson@email.com', CURRENT_TIMESTAMP),
    ('emily_parker', 0, 'emily123', 'emily.parker@email.com', CURRENT_TIMESTAMP),
    ('michael_taylor', 0, 'michael789', 'michael.taylor@email.com', CURRENT_TIMESTAMP),
    ('olivia_clark', 0, 'olivia456', 'olivia.clark@email.com', CURRENT_TIMESTAMP),
    ('ryan_hall', 0, 'ryan123', 'ryan.hall@email.com', CURRENT_TIMESTAMP),
    ('grace_turner', 0, 'grace456', 'grace.turner@email.com', CURRENT_TIMESTAMP),
    ('alice_doe', 0, 'password123', 'alice@example.com', CURRENT_TIMESTAMP),
    ('bob_smith', 0, 'bobpass456', 'bob@example.com', CURRENT_TIMESTAMP),
    ('carol_jones', 0, 'carolpass789', 'carol@example.com', CURRENT_TIMESTAMP),
    ('dave_miller', 0, 'davepass321', 'dave@example.com', CURRENT_TIMESTAMP),
    ('emma_white', 0, 'emmapass654', 'emma@example.com', CURRENT_TIMESTAMP),
    ('frank_brown', 0, 'frankpass987', 'frank@example.com', CURRENT_TIMESTAMP),
    ('grace_taylor', 0, 'gracepass123', 'grace@example.com', CURRENT_TIMESTAMP),
    ('harry_wilson', 0, 'harrypass456', 'harry@example.com', CURRENT_TIMESTAMP),
    ('isabel_green', 0, 'isabelpass789', 'isabel@example.com', CURRENT_TIMESTAMP),
    ('jack_smith', 0, 'jackpass321', 'jack@example.com', CURRENT_TIMESTAMP),
    ('kate_jones', 0, 'katepass654', 'kate@example.com', CURRENT_TIMESTAMP),
    ('liam_davis', 0, 'liampass987', 'liam@example.com', CURRENT_TIMESTAMP),
    ('maya_anderson', 0, 'mayapass123', 'maya@example.com', CURRENT_TIMESTAMP),
    ('nathan_baker', 0, 'nathanpass456', 'nathan@example.com', CURRENT_TIMESTAMP),
    ('olivia_carter', 0, 'oliviapass789', 'olivia@example.com', CURRENT_TIMESTAMP),
    ('peter_evans', 0, 'peterpass321', 'peter@example.com', CURRENT_TIMESTAMP),
    ('quinn_fisher', 0, 'quinnpass654', 'quinn@example.com', CURRENT_TIMESTAMP),
    ('ryan_yang', 0, 'ryanpass987', 'ryan@example.com', CURRENT_TIMESTAMP),
    ('sophie_irwin', 0, 'sophiepass123', 'sophie@example.com', CURRENT_TIMESTAMP),
    ('tom_king', 0, 'tompass456', 'tom@example.com', CURRENT_TIMESTAMP),
    ('hiroshi', 0, 'hiroshi123', 'hiroshi@example.com', CURRENT_TIMESTAMP),
    ('akiko', 0, 'akiko456', 'akiko@example.com', CURRENT_TIMESTAMP),
    ('takeshi', 0, 'takeshi789', 'takeshi@example.com', CURRENT_TIMESTAMP),
    ('yuki', 0, 'yuki123', 'yuki@example.com', CURRENT_TIMESTAMP),
    ('sakura', 0, 'sakura456', 'sakura@example.com', CURRENT_TIMESTAMP),
    ('taro', 0, 'taro789', 'taro@example.com', CURRENT_TIMESTAMP),
    ('naoko', 0, 'naoko123', 'naoko@example.com', CURRENT_TIMESTAMP),
    ('ryota', 0, 'ryota456', 'ryota@example.com', CURRENT_TIMESTAMP),
    ('ayumi', 0, 'ayumi789', 'ayumi@example.com', CURRENT_TIMESTAMP),
    ('kenta', 0, 'kenta123', 'kenta@example.com', CURRENT_TIMESTAMP),
    ('miho', 0, 'miho456', 'miho@example.com', CURRENT_TIMESTAMP),
    ('sora', 0, 'sora789', 'sora@example.com', CURRENT_TIMESTAMP),
    ('kazuki', 0, 'kazuki123', 'kazuki@example.com', CURRENT_TIMESTAMP),
    ('emiko', 0, 'emiko456', 'emiko@example.com', CURRENT_TIMESTAMP),
    ('takahiro', 0, 'takahiro789', 'takahiro@example.com', CURRENT_TIMESTAMP),
    ('maiko', 0, 'maiko123', 'maiko@example.com', CURRENT_TIMESTAMP),
    ('shinji', 0, 'shinji456', 'shinji@example.com', CURRENT_TIMESTAMP),
    ('liuwei', 0, 'liuwei123', 'liuwei@example.com', CURRENT_TIMESTAMP),
    ('wangying', 0, 'wangying456', 'wangying@example.com', CURRENT_TIMESTAMP),
    ('zhangwei', 0, 'zhangwei789', 'zhangwei@example.com', CURRENT_TIMESTAMP),
    ('chenxin', 0, 'chenxin123', 'chenxin@example.com', CURRENT_TIMESTAMP),
    ('yangming', 0, 'yangming456', 'yangming@example.com', CURRENT_TIMESTAMP),
    ('xuxin', 0, 'xuxin789', 'xuxin@example.com', CURRENT_TIMESTAMP),
    ('lihong', 0, 'lihong123', 'lihong@example.com', CURRENT_TIMESTAMP),
    ('zhaojie', 0, 'zhaojie456', 'zhaojie@example.com', CURRENT_TIMESTAMP),
    ('sunlei', 0, 'sunlei789', 'sunlei@example.com', CURRENT_TIMESTAMP),
    ('wanglei', 0, 'wanglei123', 'wanglei@example.com', CURRENT_TIMESTAMP),
    ('chenxi', 0, 'chenxi456', 'chenxi@example.com', CURRENT_TIMESTAMP),
    ('yangyun', 0, 'yangyun789', 'yangyun@example.com', CURRENT_TIMESTAMP),
    ('linjun', 0, 'linjun123', 'linjun@example.com', CURRENT_TIMESTAMP),
    ('huangwei', 0, 'huangwei456', 'huangwei@example.com', CURRENT_TIMESTAMP),
    ('xumin', 0, 'xumin789', 'xumin@example.com', CURRENT_TIMESTAMP),
    ('zhouwei', 0, 'zhouwei123', 'zhouwei@example.com', CURRENT_TIMESTAMP),
    ('lianghong', 0, 'lianghong456', 'lianghong@example.com', CURRENT_TIMESTAMP),
    ('wangxiao', 0, 'wangxiao789', 'wangxiao@example.com', CURRENT_TIMESTAMP),
    ('fanyu', 0, 'fanyu123', 'fanyu@example.com', CURRENT_TIMESTAMP),
    ('xulian', 0, 'xulian456', 'xulian@example.com', CURRENT_TIMESTAMP);

-- Insert Mock Project Data
INSERT INTO `project` (title, description, tags, created_user, updated_user)
VALUES
    ('電商網站 - 華麗的購物體驗', '打造一個豪華且方便的電子商務平台，提供最新的時尚商品和獨特的購物體驗。', '電商,時尚,購物', 'lihong', 'lihong'),
    ('社群平台 - 攜手共築網上家園', '建立一個互動豐富的社交平台，讓用戶分享生活點滴、交流想法，打造線上社區。', '社群,互動,分享', 'wangying', 'wangying'),
    ('新聞網站 - 即時報導最新資訊', '提供最新的新聞報導，涵蓋全球各個領域，讓用戶隨時隨地掌握最新資訊。', '新聞,即時,報導', 'zhangwei', 'zhangwei'),
    ('旅遊導覽App - 探索未知之地', '設計一款能夠提供精準旅遊導覽的應用程序，讓用戶輕鬆探索異國風情。', '旅遊,導覽,探險', 'chenxin', 'chenxin'),
    ('健康生活平台 - 追求全面健康', '打造一個線上平台，結合運動、飲食和心靈健康的資訊，協助用戶追求全面的健康。', '健康,生活,運動', 'yangming', 'yangming'),
    ('音樂分享平台 - 探索聲音的奇蹟', '提供用戶分享和發現音樂的平台，支援各種音樂風格和新興藝術家。', '音樂,分享,探索', 'xuxin', 'xuxin'),
    ('線上學習平台 - 知識無界', '建立一個多元化的網上學習平台，提供各種主題的課程，讓學習變得輕鬆有趣。', '學習,網上課程,知識', 'liuwei', 'liuwei'),
    ('藝術品展覽網站 - 藝術的饗宴', '打造一個線上藝術品展覽平台，展示各種風格和時期的藝術品。', '藝術,展覽,藝術品', 'ryan_hall', 'ryan_hall'),
    ('科技創新專案 - 改變世界的一小步', '發起一個支持科技創新的專案，致力於解決社會問題和推動科技進步。', '科技,創新,社會', 'sophie_irwin', 'sophie_irwin'),
    ('時尚設計平台 - 創意無限', '建立一個時尚設計師和愛好者的交流平台，分享最新的時尚趨勢和設計靈感。', '時尚,設計,創意', 'tom_king', 'tom_king'),
    ('社區農產品市集 - 本地好味道', '打造一個支持本地農產品的線上市集，讓用戶購買新鮮健康的農產品。', '農產品,市集,本地', 'lihong', 'lihong'),
    ('環保應用 - 小小綠色行動', '開發一個推廣環保生活的應用程序，提供環保資訊和可持續生活建議。', '環保,生活,可持續', 'wangying', 'wangying'),
    ('教育科技工具 - 創新學習體驗', '設計一個結合教育和科技的應用工具，提升學生的學習效果。', '教育,科技,學習', 'zhangwei', 'zhangwei'),
    ('美食分享社區 - 品味世界美食', '建立一個讓用戶分享和探索全球美食的社區，促進跨文化交流。', '美食,分享,社區', 'chenxin', 'chenxin'),
    ('綠意城市計畫 - 創建城市綠洲', '提倡城市綠化，打造一個促進城市居民參與綠化計畫的平台。', '城市綠化,社區,環保', 'yangming', 'yangming'),
    ('娛樂活動應用 - 探索當地娛樂', '開發一個能夠提供當地娛樂活動信息的應用，讓用戶盡情享受當地文化。', '娛樂,活動,當地', 'xuxin', 'xuxin'),
    ('社會公益平台 - 愛心助人', '建立一個支持社會公益事業的平台，匯聚善心人士共同參與公益活動。', '公益,愛心,社會', 'liuwei', 'liuwei'),
    ('E-commerce Platform - Seamless Shopping Experience', 'Create a luxurious and convenient e-commerce platform, offering the latest fashion products and a unique shopping experience.', 'e-commerce,fashion,shopping', 'ryan_hall', 'ryan_hall'),
    ('Social Media Platform - Building an Online Community', 'Build an interactive social platform where users can share life moments, exchange ideas, and create an online community.', 'social media,interaction,sharing', 'sophie_irwin', 'sophie_irwin'),
    ('News Website - Real-time Reporting of Latest Information', 'Provide the latest news coverage, covering various global topics, allowing users to stay informed anytime, anywhere.', 'news,real-time,reporting', 'tom_king', 'tom_king'),
    ('Travel Guide App - Explore the Unknown', 'Design an app that provides precise travel guides, allowing users to effortlessly explore exotic destinations.', 'travel,guide,adventure', 'lihong', 'lihong'),
    ('Health Lifestyle Platform - Pursuing Holistic Wellness', 'Create an online platform that combines information on exercise, diet, and mental health to assist users in pursuing holistic health.', 'health,lifestyle,exercise', 'wangying', 'wangying'),
    ('Music Sharing Platform - Discovering Sonic Wonders', 'Offer a platform for users to share and discover music, supporting various music genres and emerging artists.', 'music,sharing,exploration', 'zhangwei', 'zhangwei'),
    ('Online Learning Platform - Knowledge Without Borders', 'Establish a diverse online learning platform, providing courses on various topics to make learning easy and enjoyable.', 'learning,online courses,knowledge', 'chenxin', 'chenxin'),
    ('Art Exhibition Website - Feast of Art', 'Create an online platform for art exhibitions, showcasing artworks of various styles and periods.', 'art,exhibition,artwork', 'yangming', 'yangming'),
    ('Tech Innovation Project - One Small Step to Change the World', 'Initiate a project supporting technological innovation, dedicated to solving social issues and advancing technology.', 'technology,innovation,social', 'liuwei', 'liuwei'),
    ('Fashion Design Platform - Infinite Creativity', 'Build a platform for fashion designers and enthusiasts to exchange ideas, sharing the latest fashion trends and design inspirations.', 'fashion,design,creativity', 'ryan_hall', 'ryan_hall'),
    ('Community Farmers Market - Local Flavors', 'Create an online market supporting local produce, allowing users to purchase fresh and healthy farm products.', 'farm products,market,local', 'sophie_irwin', 'sophie_irwin'),
    ('Environmental App - Small Green Actions', 'Develop an app promoting an eco-friendly lifestyle, providing eco-friendly information and sustainable living suggestions.', 'environmental,lifestyle,sustainable', 'tom_king', 'tom_king'),
    ('EdTech Tool - Innovative Learning Experience', 'Design an application tool that combines education and technology to enhance students\' learning effectiveness.', 'education,technology,learning', 'lihong', 'lihong'),
    ('Food Sharing Community - Taste of World Cuisine', 'Establish a community where users can share and explore global cuisines, promoting cross-cultural exchange.', 'food,sharing,community', 'wangying', 'wangying'),
    ('Green City Initiative - Creating Urban Oases', 'Advocate for urban greening, create a platform that encourages city residents to participate in greening projects.', 'urban greening,community,environmental', 'zhangwei', 'zhangwei'),
    ('Entertainment Events App - Explore Local Entertainment', 'Develop an app that provides local entertainment event information, allowing users to enjoy local culture to the fullest.', 'entertainment,events,local', 'chenxin', 'chenxin'),
    ('Social Welfare Platform - Helping with Love', 'Establish a platform supporting social welfare causes, bringing together kind-hearted individuals to participate in charitable activities.', 'welfare,love,social', 'yangming', 'yangming'),
    ('Educational Technology Tool - Innovative Learning Solutions', 'Create a tool that combines education and technology to provide innovative solutions for students\' learning challenges.', 'education,technology,learning', 'xuxin', 'xuxin'),
    ('Fitness App - Personalized Wellness Journey', 'Develop a fitness app that offers personalized workout plans and wellness guidance, helping users achieve their fitness goals.', 'fitness,wellness,workout', 'liuwei', 'liuwei'),
    ('Online Art Marketplace - Connecting Artists and Collectors', 'Build a platform that connects artists with art collectors, facilitating the buying and selling of unique artworks online.', 'art,marketplace,collecting', 'sophie_irwin', 'sophie_irwin');

-- Insert Mock post Data
INSERT INTO `post` (content, referenceId, created_user, updated_user)
VALUES
    ('Enjoying a cozy Sunday morning with a cup of coffee and a good book. ☕📖 #SundayMorning #Relaxation', NULL, 'alice_doe', 'alice_doe'),
    ('Completed a challenging coding task today. Debugging can be a puzzle, but it\'s so satisfying to solve! 💻🧩 #CodingLife #AchievementUnlocked', NULL, 'bob_smith', 'bob_smith'),
    ('Captured a beautiful sunset during my evening walk. Nature\'s artwork at its finest. 🌅🚶‍♀️ #SunsetMagic #NatureWalk', NULL, 'carol_jones', 'carol_jones'),
    ('Just finished a thought-provoking book. The power of storytelling never ceases to amaze me. 📚🌟 #Booklover #LiteraryJourney', NULL, 'dave_miller', 'dave_miller'),
    ('Embarked on a spontaneous road trip with friends. Adventure awaits! 🚗🗺️ #RoadTrip #FriendshipGoals', NULL, 'emma_white', 'emma_white'),
    ('Attended an inspiring webinar on sustainable living. Small changes can make a big impact! 🌱♻️ #Sustainability #Webinar', NULL, 'frank_brown', 'frank_brown'),
    ('Celebrating my furry friend\'s birthday today. Cake and pawty hats for everyone! 🐾🎂 #DogBirthday #FurryCelebration', NULL, 'grace_taylor', 'grace_taylor'),
    ('Successfully wrapped up a client meeting today. Excited about the upcoming project collaboration! 💼🤝 #BusinessMeeting #ProjectCollaboration', NULL, 'harry_wilson', 'harry_wilson'),
    ('Explored a new hiking trail with friends. Nature has a way of rejuvenating the soul! 🌳🚶‍♂️ #HikingAdventure #NatureLovers', NULL, 'isabel_green', 'isabel_green'),
    ('Shared my latest coding project on GitHub. Open to feedback and collaboration! 💻🚀 #CodingProject #GitHub', NULL, 'jack_smith', 'jack_smith'),
    ('Visited a tech expo and got hands-on experience with the latest innovations. Mind-blowing tech! 🤖🌐 #TechExpo #Innovation', NULL, 'kate_jones', 'kate_jones'),
    ('Explored a new art technique in my studio. The creative journey never ceases to amaze! 🎨✨ #ArtStudio #CreativeExpression', NULL, 'liam_davis', 'liam_davis'),
    ('Celebrated a milestone in my freelance career. Grateful for the support of clients and colleagues! 🎉💼 #FreelanceLife #MilestoneAchievement', NULL, 'maya_anderson', 'maya_anderson'),
    ('Participated in a cooking competition and had a blast! Culinary adventures are always exciting. 🍽️🏆 #CookingCompetition #CulinaryAdventure', NULL, 'nathan_baker', 'nathan_baker'),
    ('Started learning a new instrument. Any tips for beginners? 🎸🎶 #MusicLover #LearningJourney', NULL, 'olivia_carter', 'olivia_carter'),
    ('Visited a historical museum and learned so much about the local heritage. 🏛️📜 #HistoryBuff #MuseumVisit', NULL, 'peter_evans', 'peter_evans'),
    ('Attended a virtual book club meeting. Great discussions and book recommendations! 📖📚 #BookClub #LiteraryCommunity', NULL, 'quinn_fisher', 'quinn_fisher'),
    ('Tried my hand at pottery for the first time. It\'s messier than it looks, but so much fun! 🏺🖌️ #PotteryClass #CreativeExpression', NULL, 'ryan_hall', 'ryan_hall'),
    ('Wrapped up a successful day of work. Time to unwind with a good movie! 🎬🍿 #WorkLifeBalance #MovieNight', NULL, 'sophie_irwin', 'sophie_irwin'),
    ('Explored a new recipe for homemade ice cream. It turned out better than expected! 🍦😋 #HomemadeIceCream #FoodieAdventure', NULL, 'tom_king', 'tom_king'),
    ('Attended a mindfulness meditation workshop. Grateful for moments of tranquility amidst a busy week. 🧘‍♂️🌿 #Mindfulness #Meditation', NULL, 'liuwei', 'liuwei'),
    ('Celebrated my child\'s achievements at the school award ceremony. Proud parent moment! 🏆👧 #ParentingJoys #ProudMom', NULL, 'wangying', 'wangying'),
    ('Collaborated with colleagues on a creative project. Teamwork makes the dream work! 🤝🎨 #TeamCollaboration #Creativity', NULL, 'zhangwei', 'zhangwei'),
    ('Explored a new city over the weekend. So many interesting places to discover! 🏙️🗺️ #TravelBug #CityExploration', NULL, 'chenxin', 'chenxin'),
    ('Completed a week-long fitness challenge. Feeling stronger and more energized! 💪🏋️‍♂️ #FitnessChallenge #WellnessJourney', NULL, 'yangming', 'yangming'),
    ('Shared a behind-the-scenes look at my latest art project. The creative process is always fascinating! 🎨✨ #ArtisticExpression #ArtProject', NULL, 'xuxin', 'xuxin'),
    ('Took part in a charity run to support a local cause. Grateful for the opportunity to give back! 🏃‍♀️🤝 #CharityRun #CommunitySupport', NULL, 'lihong', 'lihong'),
    ('夜晚的城市燈光總是那麼迷人，散步在街頭感受著城市的脈動。🌃🚶‍♂️ #夜晚漫步 #城市生活', NULL, 'lihong', 'lihong'),
    ('和家人一起度過了一個愉快的週末野餐。陽光、草地和笑聲，美好的時光！☀️🧺 #家庭時光 #週末野餐', NULL, 'wangying', 'wangying'),
    ('參加了一場關於創意思維的工作坊，學到了許多新的啟發。💡🤔 #創意思維 #工作坊', NULL, 'zhangwei', 'zhangwei'),
    ('分享了最新的攝影作品，捕捉生活中美好的瞬間。📸🌟 #攝影分享 #美好瞬間', NULL, 'chenxin', 'chenxin'),
    ('和朋友們一起參加了一場音樂節，感受著音樂的激情。🎶🎉 #音樂節 #朋友聚會', NULL, 'yangming', 'yangming'),
    ('在家裡嘗試了新的烹飪食譜，結果讓人驚艷！🍲😋 #新食譜 #家庭料理', NULL, 'xuxin', 'xuxin'),
    ('參與了一場社區清潔活動，為環境出一份心力。🌍🚮 #社區清潔 #環保行動', NULL, 'liuwei', 'liuwei'),
    ('完成了一個重要專案的階段性任務，團隊合作的力量不可小覷！👥✨ #專案成就 #團隊合作', NULL, 'ryan_hall', 'ryan_hall'),
    ('在周末裡參加了一場手作工藝市集，發現了許多獨特的手工藝品。🎨🛍️ #手作市集 #周末活動', NULL, 'sophie_irwin', 'sophie_irwin'),
    ('分享了最新的讀書心得，推薦大家一本好書！📚📖 #讀書分享 #好書推薦', NULL, 'tom_king', 'tom_king'),
    ('參與了一場社區健走活動，促進身心健康。🚶‍♀️🌳 #社區健走 #健康生活', NULL, 'lihong', 'lihong'),
    ('和親朋好友一同度過了一個愉快的家庭聚餐。美食和笑聲滿溢！🍽️👨‍👩‍👧‍👦 #家庭聚會 #美食時光', NULL, 'wangying', 'wangying'),
    ('參加了一場關於新科技的研討會，對未來的科技發展感到興奮！🤖💡 #科技研討會 #未來科技', NULL, 'zhangwei', 'zhangwei'),
    ('探索了城市的藝術畫廊，欣賞了眾多令人驚艷的藝術品。🎨🏛️ #藝術探索 #畫廊之旅', NULL, 'chenxin', 'chenxin'),
    ('分享了最新的音樂創作，希望大家喜歡！🎵🎤 #音樂創作 #創作分享', NULL, 'yangming', 'yangming'),
    ('在家裡種植了一些小型的室內植物，為空間增添了生氣。🌿🌱 #室內植物 #綠意盎然', NULL, 'xuxin', 'xuxin'),
    ('參與了社區志工活動，幫助需要幫助的人。🤝🌐 #志工活動 #關懷社區', NULL, 'liuwei', 'liuwei'),
    ('成功完成了一次極限運動挑戰，身心都有了不小的突破！🏋️‍♀️💪 #極限挑戰 #運動健身', NULL, 'ryan_hall', 'ryan_hall'),
    ('和朋友一同參加了一場瑜珈課程，感受身心靈的平靜。🧘‍♂️🌺 #瑜珈課程 #身心靈平靜', NULL, 'sophie_irwin', 'sophie_irwin');

-- Insert Mock Comment Data
INSERT INTO `comment` (postId, content, created_user, updated_user)
VALUES
    (1, 'Great work on this project!', 'ryan_hall', 'ryan_hall'),
    (2, 'I love the design of your website!', 'sophie_irwin', 'sophie_irwin'),
    (3, 'This news is really interesting.', 'tom_king', 'tom_king'),
    (4, 'Amazing travel app! Can\'t wait to use it.', 'lihong', 'lihong'),
    (5, 'Your health tips are very helpful. Thank you!', 'wangying', 'wangying'),
    (6, 'The music on your platform is fantastic!', 'zhangwei', 'zhangwei'),
    (7, 'I learned a lot from your online courses.', 'chenxin', 'chenxin'),
    (8, 'The art exhibition was incredible.', 'yangming', 'yangming'),
    (9, 'Your tech innovation project is inspiring.', 'liuwei', 'liuwei'),
    (10, 'Your fashion designs are so creative!', 'ryan_hall', 'ryan_hall'),
    (11, 'I enjoy shopping for local produce on your platform.', 'sophie_irwin', 'sophie_irwin'),
    (12, 'Your environmental app motivates me to live sustainably.', 'tom_king', 'tom_king'),
    (13, 'Your EdTech tool makes learning so much fun!', 'lihong', 'lihong'),
    (14, 'I tried a recipe shared in your food community. Delicious!', 'wangying', 'wangying'),
    (15, 'Your green city initiative is making a positive impact.', 'zhangwei', 'zhangwei'),
    (16, 'I found great local events using your app.', 'chenxin', 'chenxin'),
    (17, 'Your social welfare platform is doing great things.', 'yangming', 'yangming'),
    (18, 'Your educational technology tool helped me a lot.', 'xuxin', 'xuxin'),
    (19, 'Your fitness app keeps me motivated to stay healthy.', 'liuwei', 'liuwei'),
    (20, 'I bought a unique artwork on your art marketplace.', 'sophie_irwin', 'sophie_irwin'),
    (1, 'Interesting insights on your project.', 'chenxin', 'chenxin'),
    (2, 'The community you built around your project is impressive.', 'lihong', 'lihong'),
    (3, 'Your news coverage is always reliable.', 'yangming', 'yangming'),
    (4, 'I used your travel guide app on my last trip. It was perfect!', 'zhangwei', 'zhangwei'),
    (5, 'Your health platform helped me adopt a healthier lifestyle.', 'liuwei', 'liuwei'),
    (6, 'Great playlist on your music sharing platform.', 'sophie_irwin', 'sophie_irwin'),
    (7, 'I appreciate the variety of courses on your online learning platform.', 'chenxin', 'chenxin'),
    (8, 'The art pieces at the exhibition were breathtaking.', 'ryan_hall', 'ryan_hall'),
    (9, 'Exciting times for your tech innovation project!', 'tom_king', 'tom_king'),
    (10, 'Your fashion design platform is a game-changer.', 'sophie_irwin', 'sophie_irwin'),
    (11, 'I love the community feel of your farmers market.', 'wangying', 'wangying'),
    (12, 'Your environmental app is user-friendly and informative.', 'lihong', 'lihong'),
    (13, 'Your EdTech tool made my lessons more engaging.', 'chenxin', 'chenxin'),
    (14, 'The recipes shared in your food community are delicious!', 'wangying', 'wangying'),
    (15, 'Your green city initiative is transforming urban spaces.', 'zhangwei', 'zhangwei'),
    (16, 'I discovered fantastic local events through your app.', 'chenxin', 'chenxin'),
    (17, 'Your social welfare platform brings positive change.', 'yangming', 'yangming'),
    (18, 'Your educational technology tool is a lifesaver for students.', 'xuxin', 'xuxin'),
    (19, 'Your fitness app helped me achieve my fitness goals.', 'liuwei', 'liuwei'),
    (20, 'The art I bought on your marketplace is now a centerpiece in my home.', 'ryan_hall', 'ryan_hall');

-- Insert Mock Star Data
-- likedType 0: Project (targetId 1-20)
INSERT INTO `star` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 19 + 1), 0
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 1: User (targetId 1-20)
INSERT INTO `star` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 19 + 1), 0
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 2: Post (targetId 1-40, after the first 20 entries)
INSERT INTO `star` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 39 + 1), 2
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 3: Comment (targetId 1-20)
INSERT INTO `star` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 19 + 1), 3
FROM User
ORDER BY RAND()
LIMIT 50;

-- Insert Mock Subscribe Data
-- likedType 0: Project (targetId 1-20)
INSERT INTO `subscribe` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 19 + 1), 0
FROM User
ORDER BY RAND()
LIMIT 20;

-- likedType 1: User (targetId 1-20)
INSERT INTO `subscribe` (userId, targetId, type)
SELECT userId, ROUND(RAND() * 19 + 1), 1
FROM User
ORDER BY RAND()
LIMIT 20;


