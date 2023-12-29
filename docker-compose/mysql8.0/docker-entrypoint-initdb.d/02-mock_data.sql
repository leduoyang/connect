USE connect_base;

-- Insert Mock User Data
INSERT INTO `user` (username, status, password, email, db_create_time, uuid)
VALUES
    ('ROOT', 1, 'thisisrootpassword', 'connect.sideproject@gmail.com', CURRENT_TIMESTAMP, UUID()),
    ('john_doe', ROUND(RAND() * 1), 'john123', 'john.doe@email.com', CURRENT_TIMESTAMP, UUID()),
    ('alice_smith', ROUND(RAND() * 1), 'alice456', 'alice.smith@email.com', CURRENT_TIMESTAMP, UUID()),
    ('bob_jones', ROUND(RAND() * 1), 'bob789', 'bob.jones@email.com', CURRENT_TIMESTAMP, UUID()),
    ('sarah_miller', ROUND(RAND() * 1), 'sarah456', 'sarah.miller@email.com', CURRENT_TIMESTAMP, UUID()),
    ('david_wilson', ROUND(RAND() * 1), 'david789', 'david.wilson@email.com', CURRENT_TIMESTAMP, UUID()),
    ('emily_parker', ROUND(RAND() * 1), 'emily123', 'emily.parker@email.com', CURRENT_TIMESTAMP, UUID()),
    ('michael_taylor', ROUND(RAND() * 1), 'michael789', 'michael.taylor@email.com', CURRENT_TIMESTAMP, UUID()),
    ('olivia_clark', ROUND(RAND() * 1), 'olivia456', 'olivia.clark@email.com', CURRENT_TIMESTAMP, UUID()),
    ('ryan_hall', ROUND(RAND() * 1), 'ryan123', 'ryan.hall@email.com', CURRENT_TIMESTAMP, UUID()),
    ('grace_turner', ROUND(RAND() * 1), 'grace456', 'grace.turner@email.com', CURRENT_TIMESTAMP, UUID()),
    ('alice_doe', ROUND(RAND() * 1), 'password123', 'alice@example.com', CURRENT_TIMESTAMP, UUID()),
    ('bob_smith', ROUND(RAND() * 1), 'bobpass456', 'bob@example.com', CURRENT_TIMESTAMP, UUID()),
    ('carol_jones', ROUND(RAND() * 1), 'carolpass789', 'carol@example.com', CURRENT_TIMESTAMP, UUID()),
    ('dave_miller', ROUND(RAND() * 1), 'davepass321', 'dave@example.com', CURRENT_TIMESTAMP, UUID()),
    ('emma_white', ROUND(RAND() * 1), 'emmapass654', 'emma@example.com', CURRENT_TIMESTAMP, UUID()),
    ('frank_brown', ROUND(RAND() * 1), 'frankpass987', 'frank@example.com', CURRENT_TIMESTAMP, UUID()),
    ('grace_taylor', ROUND(RAND() * 1), 'gracepass123', 'grace@example.com', CURRENT_TIMESTAMP, UUID()),
    ('harry_wilson', ROUND(RAND() * 1), 'harrypass456', 'harry@example.com', CURRENT_TIMESTAMP, UUID()),
    ('isabel_green', ROUND(RAND() * 1), 'isabelpass789', 'isabel@example.com', CURRENT_TIMESTAMP, UUID()),
    ('jack_smith', ROUND(RAND() * 1), 'jackpass321', 'jack@example.com', CURRENT_TIMESTAMP, UUID()),
    ('kate_jones', ROUND(RAND() * 1), 'katepass654', 'kate@example.com', CURRENT_TIMESTAMP, UUID()),
    ('liam_davis', ROUND(RAND() * 1), 'liampass987', 'liam@example.com', CURRENT_TIMESTAMP, UUID()),
    ('maya_anderson', ROUND(RAND() * 1), 'mayapass123', 'maya@example.com', CURRENT_TIMESTAMP, UUID()),
    ('nathan_baker', ROUND(RAND() * 1), 'nathanpass456', 'nathan@example.com', CURRENT_TIMESTAMP, UUID()),
    ('olivia_carter', ROUND(RAND() * 1), 'oliviapass789', 'olivia@example.com', CURRENT_TIMESTAMP, UUID()),
    ('peter_evans', ROUND(RAND() * 1), 'peterpass321', 'peter@example.com', CURRENT_TIMESTAMP, UUID()),
    ('quinn_fisher', ROUND(RAND() * 1), 'quinnpass654', 'quinn@example.com', CURRENT_TIMESTAMP, UUID()),
    ('ryan_yang', ROUND(RAND() * 1), 'ryanpass987', 'ryan@example.com', CURRENT_TIMESTAMP, UUID()),
    ('sophie_irwin', ROUND(RAND() * 1), 'sophiepass123', 'sophie@example.com', CURRENT_TIMESTAMP, UUID()),
    ('tom_king', ROUND(RAND() * 1), 'tompass456', 'tom@example.com', CURRENT_TIMESTAMP, UUID()),
    ('hiroshi', ROUND(RAND() * 1), 'hiroshi123', 'hiroshi@example.com', CURRENT_TIMESTAMP, UUID()),
    ('akiko', ROUND(RAND() * 1), 'akiko456', 'akiko@example.com', CURRENT_TIMESTAMP, UUID()),
    ('takeshi', ROUND(RAND() * 1), 'takeshi789', 'takeshi@example.com', CURRENT_TIMESTAMP, UUID()),
    ('yuki', ROUND(RAND() * 1), 'yuki123', 'yuki@example.com', CURRENT_TIMESTAMP, UUID()),
    ('sakura', ROUND(RAND() * 1), 'sakura456', 'sakura@example.com', CURRENT_TIMESTAMP, UUID()),
    ('taro', ROUND(RAND() * 1), 'taro789', 'taro@example.com', CURRENT_TIMESTAMP, UUID()),
    ('naoko', ROUND(RAND() * 1), 'naoko123', 'naoko@example.com', CURRENT_TIMESTAMP, UUID()),
    ('ryota', ROUND(RAND() * 1), 'ryota456', 'ryota@example.com', CURRENT_TIMESTAMP, UUID()),
    ('ayumi', ROUND(RAND() * 1), 'ayumi789', 'ayumi@example.com', CURRENT_TIMESTAMP, UUID()),
    ('kenta', ROUND(RAND() * 1), 'kenta123', 'kenta@example.com', CURRENT_TIMESTAMP, UUID()),
    ('miho', ROUND(RAND() * 1), 'miho456', 'miho@example.com', CURRENT_TIMESTAMP, UUID()),
    ('sora', ROUND(RAND() * 1), 'sora789', 'sora@example.com', CURRENT_TIMESTAMP, UUID()),
    ('kazuki', ROUND(RAND() * 1), 'kazuki123', 'kazuki@example.com', CURRENT_TIMESTAMP, UUID()),
    ('emiko', ROUND(RAND() * 1), 'emiko456', 'emiko@example.com', CURRENT_TIMESTAMP, UUID()),
    ('takahiro', ROUND(RAND() * 1), 'takahiro789', 'takahiro@example.com', CURRENT_TIMESTAMP, UUID()),
    ('maiko', ROUND(RAND() * 1), 'maiko123', 'maiko@example.com', CURRENT_TIMESTAMP, UUID()),
    ('shinji', ROUND(RAND() * 1), 'shinji456', 'shinji@example.com', CURRENT_TIMESTAMP, UUID()),
    ('liuwei', ROUND(RAND() * 1), 'liuwei123', 'liuwei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('wangying', ROUND(RAND() * 1), 'wangying456', 'wangying@example.com', CURRENT_TIMESTAMP, UUID()),
    ('zhangwei', ROUND(RAND() * 1), 'zhangwei789', 'zhangwei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('chenxin', ROUND(RAND() * 1), 'chenxin123', 'chenxin@example.com', CURRENT_TIMESTAMP, UUID()),
    ('yangming', ROUND(RAND() * 1), 'yangming456', 'yangming@example.com', CURRENT_TIMESTAMP, UUID()),
    ('xuxin', ROUND(RAND() * 1), 'xuxin789', 'xuxin@example.com', CURRENT_TIMESTAMP, UUID()),
    ('lihong', ROUND(RAND() * 1), 'lihong123', 'lihong@example.com', CURRENT_TIMESTAMP, UUID()),
    ('zhaojie', ROUND(RAND() * 1), 'zhaojie456', 'zhaojie@example.com', CURRENT_TIMESTAMP, UUID()),
    ('sunlei', ROUND(RAND() * 1), 'sunlei789', 'sunlei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('wanglei', ROUND(RAND() * 1), 'wanglei123', 'wanglei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('chenxi', ROUND(RAND() * 1), 'chenxi456', 'chenxi@example.com', CURRENT_TIMESTAMP, UUID()),
    ('yangyun', ROUND(RAND() * 1), 'yangyun789', 'yangyun@example.com', CURRENT_TIMESTAMP, UUID()),
    ('linjun', ROUND(RAND() * 1), 'linjun123', 'linjun@example.com', CURRENT_TIMESTAMP, UUID()),
    ('huangwei', ROUND(RAND() * 1), 'huangwei456', 'huangwei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('xumin', ROUND(RAND() * 1), 'xumin789', 'xumin@example.com', CURRENT_TIMESTAMP, UUID()),
    ('zhouwei', ROUND(RAND() * 1), 'zhouwei123', 'zhouwei@example.com', CURRENT_TIMESTAMP, UUID()),
    ('lianghong', ROUND(RAND() * 1), 'lianghong456', 'lianghong@example.com', CURRENT_TIMESTAMP, UUID()),
    ('wangxiao', ROUND(RAND() * 1), 'wangxiao789', 'wangxiao@example.com', CURRENT_TIMESTAMP, UUID()),
    ('fanyu', ROUND(RAND() * 1), 'fanyu123', 'fanyu@example.com', CURRENT_TIMESTAMP, UUID()),
    ('xulian', ROUND(RAND() * 1), 'xulian456', 'xulian@example.com', CURRENT_TIMESTAMP, UUID());

-- Insert Mock Project Data
INSERT INTO `project` (title, description, tags, created_user, updated_user, uuid)
VALUES
    ('電商網站 - 華麗的購物體驗', '打造一個豪華且方便的電子商務平台，提供最新的時尚商品和獨特的購物體驗。', '電商,時尚,購物', 1, 1, UUID()),
    ('社群平台 - 攜手共築網上家園', '建立一個互動豐富的社交平台，讓用戶分享生活點滴、交流想法，打造線上社區。', '社群,互動,分享', 1, 1, UUID()),
    ('新聞網站 - 即時報導最新資訊', '提供最新的新聞報導，涵蓋全球各個領域，讓用戶隨時隨地掌握最新資訊。', '新聞,即時,報導', 1, 1, UUID()),
    ('旅遊導覽App - 探索未知之地', '設計一款能夠提供精準旅遊導覽的應用程序，讓用戶輕鬆探索異國風情。', '旅遊,導覽,探險', 1, 1, UUID()),
    ('健康生活平台 - 追求全面健康', '打造一個線上平台，結合運動、飲食和心靈健康的資訊，協助用戶追求全面的健康。', '健康,生活,運動', 1, 1, UUID()),
    ('音樂分享平台 - 探索聲音的奇蹟', '提供用戶分享和發現音樂的平台，支援各種音樂風格和新興藝術家。', '音樂,分享,探索', 1, 1, UUID()),
    ('線上學習平台 - 知識無界', '建立一個多元化的網上學習平台，提供各種主題的課程，讓學習變得輕鬆有趣。', '學習,網上課程,知識', 2, 2, UUID()),
    ('藝術品展覽網站 - 藝術的饗宴', '打造一個線上藝術品展覽平台，展示各種風格和時期的藝術品。', '藝術,展覽,藝術品', 2, 2, UUID()),
    ('科技創新專案 - 改變世界的一小步', '發起一個支持科技創新的專案，致力於解決社會問題和推動科技進步。', '科技,創新,社會', 2, 2, UUID()),
    ('時尚設計平台 - 創意無限', '建立一個時尚設計師和愛好者的交流平台，分享最新的時尚趨勢和設計靈感。', '時尚,設計,創意', 2, 2, UUID()),
    ('社區農產品市集 - 本地好味道', '打造一個支持本地農產品的線上市集，讓用戶購買新鮮健康的農產品。', '農產品,市集,本地', 2, 2, UUID()),
    ('環保應用 - 小小綠色行動', '開發一個推廣環保生活的應用程序，提供環保資訊和可持續生活建議。', '環保,生活,可持續', 3, 3, UUID()),
    ('教育科技工具 - 創新學習體驗', '設計一個結合教育和科技的應用工具，提升學生的學習效果。', '教育,科技,學習', 3, 3, UUID()),
    ('美食分享社區 - 品味世界美食', '建立一個讓用戶分享和探索全球美食的社區，促進跨文化交流。', '美食,分享,社區', 3, 3, UUID()),
    ('綠意城市計畫 - 創建城市綠洲', '提倡城市綠化，打造一個促進城市居民參與綠化計畫的平台。', '城市綠化,社區,環保', 3, 3, UUID()),
    ('娛樂活動應用 - 探索當地娛樂', '開發一個能夠提供當地娛樂活動信息的應用，讓用戶盡情享受當地文化。', '娛樂,活動,當地', 3, 3, UUID()),
    ('社會公益平台 - 愛心助人', '建立一個支持社會公益事業的平台，匯聚善心人士共同參與公益活動。', '公益,愛心,社會', 3, 3, UUID()),
    ('E-commerce Platform - Seamless Shopping Experience', 'Create a luxurious and convenient e-commerce platform, offering the latest fashion products and a unique shopping experience.', 'e-commerce,fashion,shopping', 4, 4, UUID()),
    ('Social Media Platform - Building an Online Community', 'Build an interactive social platform where users can share life moments, exchange ideas, and create an online community.', 'social media,interaction,sharing', 4, 4, UUID()),
    ('News Website - Real-time Reporting of Latest Information', 'Provide the latest news coverage, covering various global topics, allowing users to stay informed anytime, anywhere.', 'news,real-time,reporting', 4, 4, UUID()),
    ('Travel Guide App - Explore the Unknown', 'Design an app that provides precise travel guides, allowing users to effortlessly explore exotic destinations.', 'travel,guide,adventure', 4, 4, UUID()),
    ('Health Lifestyle Platform - Pursuing Holistic Wellness', 'Create an online platform that combines information on exercise, diet, and mental health to assist users in pursuing holistic health.', 'health,lifestyle,exercise', 4, 4, UUID()),
    ('Music Sharing Platform - Discovering Sonic Wonders', 'Offer a platform for users to share and discover music, supporting various music genres and emerging artists.', 'music,sharing,exploration', 5, 5, UUID()),
    ('Online Learning Platform - Knowledge Without Borders', 'Establish a diverse online learning platform, providing courses on various topics to make learning easy and enjoyable.', 'learning,online courses,knowledge', 5, 5, UUID()),
    ('Art Exhibition Website - Feast of Art', 'Create an online platform for art exhibitions, showcasing artworks of various styles and periods.', 'art,exhibition,artwork', 5, 5, UUID()),
    ('Tech Innovation Project - One Small Step to Change the World', 'Initiate a project supporting technological innovation, dedicated to solving social issues and advancing technology.', 'technology,innovation,social', 5, 5, UUID()),
    ('Fashion Design Platform - Infinite Creativity', 'Build a platform for fashion designers and enthusiasts to exchange ideas, sharing the latest fashion trends and design inspirations.', 'fashion,design,creativity', 5, 5, UUID()),
    ('Community Farmers Market - Local Flavors', 'Create an online market supporting local produce, allowing users to purchase fresh and healthy farm products.', 'farm products,market,local', 6, 6, UUID()),
    ('Environmental App - Small Green Actions', 'Develop an app promoting an eco-friendly lifestyle, providing eco-friendly information and sustainable living suggestions.', 'environmental,lifestyle,sustainable', 6, 6, UUID()),
    ('EdTech Tool - Innovative Learning Experience', 'Design an application tool that combines education and technology to enhance students\' learning effectiveness.', 'education,technology,learning', 6, 6, UUID()),
    ('Food Sharing Community - Taste of World Cuisine', 'Establish a community where users can share and explore global cuisines, promoting cross-cultural exchange.', 'food,sharing,community', 6, 6, UUID()),
    ('Green City Initiative - Creating Urban Oases', 'Advocate for urban greening, create a platform that encourages city residents to participate in greening projects.', 'urban greening,community,environmental', 7, 7, UUID()),
    ('Entertainment Events App - Explore Local Entertainment', 'Develop an app that provides local entertainment event information, allowing users to enjoy local culture to the fullest.', 'entertainment,events,local', 7, 7, UUID()),
    ('Social Welfare Platform - Helping with Love', 'Establish a platform supporting social welfare causes, bringing together kind-hearted individuals to participate in charitable activities.', 'welfare,love,social', 7, 7, UUID()),
    ('Educational Technology Tool - Innovative Learning Solutions', 'Create a tool that combines education and technology to provide innovative solutions for students\' learning challenges.', 'education,technology,learning', 7, 7, UUID()),
    ('Fitness App - Personalized Wellness Journey', 'Develop a fitness app that offers personalized workout plans and wellness guidance, helping users achieve their fitness goals.', 'fitness,wellness,workout', 7, 7, UUID()),
    ('Online Art Marketplace - Connecting Artists and Collectors', 'Build a platform that connects artists with art collectors, facilitating the buying and selling of unique artworks online.', 'art,marketplace,collecting', 7, 7, UUID());

-- Insert Mock post Data
INSERT INTO `post` (content, referenceId, created_user, updated_user)
VALUES
    ('Enjoying a cozy Sunday morning with a cup of coffee and a good book. ☕📖 #SundayMorning #Relaxation', NULL, 1, 1),
    ('Completed a challenging coding task today. Debugging can be a puzzle, but it\'s so satisfying to solve! 💻🧩 #CodingLife #AchievementUnlocked', NULL, 1, 1),
    ('Captured a beautiful sunset during my evening walk. Nature\'s artwork at its finest. 🌅🚶‍♀️ #SunsetMagic #NatureWalk', NULL, 1, 1),
    ('Just finished a thought-provoking book. The power of storytelling never ceases to amaze me. 📚🌟 #Booklover #LiteraryJourney', NULL, 1, 1),
    ('Embarked on a spontaneous road trip with friends. Adventure awaits! 🚗🗺️ #RoadTrip #FriendshipGoals', NULL,  1, 1),
    ('Attended an inspiring webinar on sustainable living. Small changes can make a big impact! 🌱♻️ #Sustainability #Webinar', NULL,  1, 1),
    ('Celebrating my furry friend\'s birthday today. Cake and pawty hats for everyone! 🐾🎂 #DogBirthday #FurryCelebration', NULL,  1, 1),
    ('Successfully wrapped up a client meeting today. Excited about the upcoming project collaboration! 💼🤝 #BusinessMeeting #ProjectCollaboration', NULL,  1, 1),
    ('Explored a new hiking trail with friends. Nature has a way of rejuvenating the soul! 🌳🚶‍♂️ #HikingAdventure #NatureLovers', NULL, 2, 2),
    ('Shared my latest coding project on GitHub. Open to feedback and collaboration! 💻🚀 #CodingProject #GitHub', NULL, 2, 2),
    ('Visited a tech expo and got hands-on experience with the latest innovations. Mind-blowing tech! 🤖🌐 #TechExpo #Innovation', NULL, 2, 2),
    ('Explored a new art technique in my studio. The creative journey never ceases to amaze! 🎨✨ #ArtStudio #CreativeExpression', NULL, 2, 2),
    ('Celebrated a milestone in my freelance career. Grateful for the support of clients and colleagues! 🎉💼 #FreelanceLife #MilestoneAchievement', NULL, 2, 2),
    ('Participated in a cooking competition and had a blast! Culinary adventures are always exciting. 🍽️🏆 #CookingCompetition #CulinaryAdventure', NULL, 2, 2),
    ('Started learning a new instrument. Any tips for beginners? 🎸🎶 #MusicLover #LearningJourney', NULL, 3, 3),
    ('Visited a historical museum and learned so much about the local heritage. 🏛️📜 #HistoryBuff #MuseumVisit', NULL, 3, 3),
    ('Attended a virtual book club meeting. Great discussions and book recommendations! 📖📚 #BookClub #LiteraryCommunity', NULL, 3, 3),
    ('Tried my hand at pottery for the first time. It\'s messier than it looks, but so much fun! 🏺🖌️ #PotteryClass #CreativeExpression', NULL, 3, 3),
    ('Wrapped up a successful day of work. Time to unwind with a good movie! 🎬🍿 #WorkLifeBalance #MovieNight', NULL, 3, 3),
    ('Explored a new recipe for homemade ice cream. It turned out better than expected! 🍦😋 #HomemadeIceCream #FoodieAdventure', NULL, 4, 4),
    ('Attended a mindfulness meditation workshop. Grateful for moments of tranquility amidst a busy week. 🧘‍♂️🌿 #Mindfulness #Meditation', NULL, 4, 4),
    ('Celebrated my child\'s achievements at the school award ceremony. Proud parent moment! 🏆👧 #ParentingJoys #ProudMom', NULL, 4, 4),
    ('Collaborated with colleagues on a creative project. Teamwork makes the dream work! 🤝🎨 #TeamCollaboration #Creativity', NULL, 4, 4),
    ('Explored a new city over the weekend. So many interesting places to discover! 🏙️🗺️ #TravelBug #CityExploration', NULL, 5, 5),
    ('Completed a week-long fitness challenge. Feeling stronger and more energized! 💪🏋️‍♂️ #FitnessChallenge #WellnessJourney', NULL, 5, 5),
    ('Shared a behind-the-scenes look at my latest art project. The creative process is always fascinating! 🎨✨ #ArtisticExpression #ArtProject', NULL, 5, 5),
    ('Took part in a charity run to support a local cause. Grateful for the opportunity to give back! 🏃‍♀️🤝 #CharityRun #CommunitySupport', NULL, 5, 5),
    ('夜晚的城市燈光總是那麼迷人，散步在街頭感受著城市的脈動。🌃🚶‍♂️ #夜晚漫步 #城市生活', NULL, 6, 6),
    ('和家人一起度過了一個愉快的週末野餐。陽光、草地和笑聲，美好的時光！☀️🧺 #家庭時光 #週末野餐', NULL, 6, 6),
    ('參加了一場關於創意思維的工作坊，學到了許多新的啟發。💡🤔 #創意思維 #工作坊', NULL, 6, 6),
    ('分享了最新的攝影作品，捕捉生活中美好的瞬間。📸🌟 #攝影分享 #美好瞬間', NULL, 6, 6),
    ('和朋友們一起參加了一場音樂節，感受著音樂的激情。🎶🎉 #音樂節 #朋友聚會', NULL, 6, 6),
    ('在家裡嘗試了新的烹飪食譜，結果讓人驚艷！🍲😋 #新食譜 #家庭料理', NULL, 6, 6),
    ('參與了一場社區清潔活動，為環境出一份心力。🌍🚮 #社區清潔 #環保行動', NULL, 7, 7),
    ('完成了一個重要專案的階段性任務，團隊合作的力量不可小覷！👥✨ #專案成就 #團隊合作', NULL, 7, 7),
    ('在周末裡參加了一場手作工藝市集，發現了許多獨特的手工藝品。🎨🛍️ #手作市集 #周末活動', NULL, 7, 7),
    ('分享了最新的讀書心得，推薦大家一本好書！📚📖 #讀書分享 #好書推薦', NULL, 7, 7),
    ('參與了一場社區健走活動，促進身心健康。🚶‍♀️🌳 #社區健走 #健康生活', NULL, 7, 7),
    ('和親朋好友一同度過了一個愉快的家庭聚餐。美食和笑聲滿溢！🍽️👨‍👩‍👧‍👦 #家庭聚會 #美食時光', NULL, 8, 8),
    ('參加了一場關於新科技的研討會，對未來的科技發展感到興奮！🤖💡 #科技研討會 #未來科技', NULL, 8, 8),
    ('探索了城市的藝術畫廊，欣賞了眾多令人驚艷的藝術品。🎨🏛️ #藝術探索 #畫廊之旅', NULL, 8, 8),
    ('分享了最新的音樂創作，希望大家喜歡！🎵🎤 #音樂創作 #創作分享', NULL, 8, 8),
    ('在家裡種植了一些小型的室內植物，為空間增添了生氣。🌿🌱 #室內植物 #綠意盎然', NULL, 8, 8),
    ('參與了社區志工活動，幫助需要幫助的人。🤝🌐 #志工活動 #關懷社區', NULL, 8, 8),
    ('成功完成了一次極限運動挑戰，身心都有了不小的突破！🏋️‍♀️💪 #極限挑戰 #運動健身', NULL, 8, 8),
    ('和朋友一同參加了一場瑜珈課程，感受身心靈的平靜。🧘‍♂️🌺 #瑜珈課程 #身心靈平靜', NULL, 9, 9);

-- Insert Mock Comment Data
INSERT INTO `comment` (postId, content, created_user, updated_user)
VALUES
    (1, 'Great work on this project!', 1, 1),
    (2, 'I love the design of your website!', 1, 1),
    (3, 'This news is really interesting.', 1, 1),
    (4, 'Amazing travel app! Can\'t wait to use it.', 1, 1),
    (5, 'Your health tips are very helpful. Thank you!', 1, 1),
    (6, 'The music on your platform is fantastic!', 1, 1),
    (7, 'I learned a lot from your online courses.', 1, 1),
    (8, 'The art exhibition was incredible.', 1, 1),
    (9, 'Your tech innovation project is inspiring.', 1, 1),
    (10, 'Your fashion designs are so creative!', 1, 1),
    (11, 'I enjoy shopping for local produce on your platform.', 2, 2),
    (12, 'Your environmental app motivates me to live sustainably.', 2, 2),
    (13, 'Your EdTech tool makes learning so much fun!', 3, 3),
    (14, 'I tried a recipe shared in your food community. Delicious!', 3, 3),
    (15, 'Your green city initiative is making a positive impact.', 3, 3),
    (16, 'I found great local events using your app.', 4, 4),
    (17, 'Your social welfare platform is doing great things.', 4, 4),
    (18, 'Your educational technology tool helped me a lot.', 4, 4),
    (19, 'Your fitness app keeps me motivated to stay healthy.', 4, 4),
    (20, 'I bought a unique artwork on your art marketplace.', 5, 5),
    (1, 'Interesting insights on your project.', 5, 5),
    (2, 'The community you built around your project is impressive.', 5, 5),
    (3, 'Your news coverage is always reliable.', 5, 5),
    (4, 'I used your travel guide app on my last trip. It was perfect!', 5, 5),
    (5, 'Your health platform helped me adopt a healthier lifestyle.', 5, 5),
    (6, 'Great playlist on your music sharing platform.', 6, 6),
    (7, 'I appreciate the variety of courses on your online learning platform.', 6, 6),
    (8, 'The art pieces at the exhibition were breathtaking.', 6, 6),
    (9, 'Exciting times for your tech innovation project!', 6, 6),
    (10, 'Your fashion design platform is a game-changer.', 6, 6),
    (11, 'I love the community feel of your farmers market.', 7, 7),
    (12, 'Your environmental app is user-friendly and informative.', 7, 7),
    (13, 'Your EdTech tool made my lessons more engaging.', 7, 7),
    (14, 'The recipes shared in your food community are delicious!', 7, 7),
    (15, 'Your green city initiative is transforming urban spaces.', 7, 7),
    (16, 'I discovered fantastic local events through your app.', 7, 7),
    (17, 'Your social welfare platform brings positive change.', 7, 7),
    (18, 'Your educational technology tool is a lifesaver for students.', 7, 7),
    (19, 'Your fitness app helped me achieve my fitness goals.', 7, 7),
    (20, 'The art I bought on your marketplace is now a centerpiece in my home.', 7, 7);

-- Insert Mock Star Data
-- likedType 0: Project (targetId 1-20)
INSERT INTO `star` (userId, targetId, targetType, isActive)
SELECT userId, ROUND(RAND() * 19 + 1), 0, TRUE
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 1: User (targetId 1-20)
INSERT INTO `star` (userId, targetId, targetType, isActive)
SELECT userId, ROUND(RAND() * 19 + 1), 3, TRUE
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 2: Post (targetId 1-40, after the first 20 entries)
INSERT INTO `star` (userId, targetId, targetType, isActive)
SELECT userId, ROUND(RAND() * 39 + 1), 1, TRUE
FROM User
ORDER BY RAND()
LIMIT 50;

-- likedType 3: Comment (targetId 1-20)
INSERT INTO `star` (userId, targetId, targetType, isActive)
SELECT userId, ROUND(RAND() * 19 + 1), 2, TRUE
FROM User
ORDER BY RAND()
LIMIT 50;

-- Follow
INSERT INTO `follow` (followerId, followingId, status)
VALUES
    (2, 1, 1),
    (3, 1, 1),
    (4, 1, 1),
    (5, 1, 1),
    (6, 1, 2),
    (7, 1, 2),
    (8, 1, 2),
    (9, 1, 2),
    (10, 1, 3),
    (11, 1, 3),
    (12, 1, 3),
    (13, 1, 3);



