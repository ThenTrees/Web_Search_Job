create table if not exists address
(
    add_id  bigint auto_increment
    primary key,
    city    varchar(50)  null,
    country smallint     null,
    number  varchar(20)  null,
    street  varchar(150) null,
    zipcode varchar(7)   null
    );

create table if not exists role
(
    id        bigint auto_increment
    primary key,
    role_type enum ('ADMIN', 'CANDIDATE', 'COMPANY') null,
    constraint UK8nhufvk7ufr23s4xoqglqtbdx
    unique (role_type)
    );

create table if not exists skill
(
    skill_id   bigint auto_increment
    primary key,
    skill_desc varchar(300)                                         null,
    skill_name varchar(150)                                         null,
    skill_type enum ('SOFT_SKILL', 'TECHNICAL_SKILL', 'UNSPECIFIC') null
    );

create table if not exists user
(
    id        bigint auto_increment
    primary key,
    is_active bit          null,
    email     varchar(255) null,
    password  varchar(255) not null,
    phone     varchar(255) not null,
    address   bigint       null,
    role_id   bigint       null,
    constraint UK589idila9li6a4arw1t8ht1gx
    unique (phone),
    constraint UKg4x48cvlxs8po7lbyxrs4s8fk
    unique (address),
    constraint UKob8kqyqqgmefl0aco34akdtpe
    unique (email),
    constraint FKi371i1vdmyh0slejk03x1ihe7
    foreign key (address) references address (add_id),
    constraint FKn82ha3ccdebhokx3a8fgdqeyy
    foreign key (role_id) references role (id)
    );

create table if not exists cadidate
(
    dob       date         null,
    full_name varchar(255) null,
    id        bigint       not null
    primary key,
    constraint FKj4c7d0lyn3na209nabsb7f7s
    foreign key (id) references user (id)
    );

create table if not exists candidate_skill
(
    more_infos  varchar(1000)                                                          null,
    skill_level enum ('ADVANCED', 'BEGINER', 'IMTERMEDIATE', 'MASTER', 'PROFESSIONAL') null,
    can_id      bigint                                                                 not null,
    skill_id    bigint                                                                 not null,
    primary key (can_id, skill_id),
    constraint FK31oksux67e4hfofeqa2t4hdct
    foreign key (can_id) references cadidate (id),
    constraint FKb7cxhiqhcah7c20a2cdlvr1f8
    foreign key (skill_id) references skill (skill_id)
    );

create table if not exists company
(
    about     varchar(2000) null,
    full_name varchar(255)  null,
    web_url   varchar(2000) null,
    id        bigint        not null
    primary key,
    constraint FK6f1tjnnj5tyq4c0f56gotwvti
    foreign key (id) references user (id)
    );

create table if not exists experience
(
    exp_id    bigint auto_increment
    primary key,
    company   varchar(120) null,
    from_date date         null,
    role      varchar(100) null,
    to_date   date         null,
    work_desc varchar(400) null,
    can_id    bigint       null,
    constraint FKkpp22oks6ql1r2n4llf8l8enk
    foreign key (can_id) references cadidate (id)
    );

create table if not exists job
(
    job_id     bigint auto_increment
    primary key,
    created_at datetime(6)                                                            null,
    job_desc   varchar(2000)                                                          null,
    level      enum ('ADVANCED', 'BEGINER', 'IMTERMEDIATE', 'MASTER', 'PROFESSIONAL') null,
    job_name   varchar(255)                                                           null,
    salary     double                                                                 null,
    updated_at datetime(6)                                                            null,
    company    bigint                                                                 null,
    constraint FKbaqlvluu78phmo9ld89um7wnm
    foreign key (company) references company (id)
    );

create table if not exists candidate_job
(
    can_id bigint not null,
    job_id bigint not null,
    primary key (can_id, job_id),
    constraint FK7536y5p3ocewrf0rjrnhpxd54
    foreign key (can_id) references cadidate (id),
    constraint FKajpx3r3j6ff7r12f5qd1ind8t
    foreign key (job_id) references job (job_id)
    );

create table if not exists job_skill
(
    more_infos  varchar(1000)                                                          null,
    skill_level enum ('ADVANCED', 'BEGINER', 'IMTERMEDIATE', 'MASTER', 'PROFESSIONAL') null,
    job_id      bigint                                                                 not null,
    skill_id    bigint                                                                 not null,
    primary key (job_id, skill_id),
    constraint FK9ix4wg520ii2gu2felxdhdup6
    foreign key (job_id) references job (job_id),
    constraint FKj33qbbf3vk1lvhqpcosnh54u1
    foreign key (skill_id) references skill (skill_id)
    );

insert into lab05.role (id, role_type)
values  (2, 'ADMIN'),
        (1, 'CANDIDATE'),
        (3, 'COMPANY');

insert into lab05.skill (skill_id, skill_desc, skill_name, skill_type)
values  (1, null, 'Java', 'TECHNICAL_SKILL'),
        (2, null, 'React', 'TECHNICAL_SKILL'),
        (3, null, 'NodeJs', 'TECHNICAL_SKILL'),
        (4, null, 'ExpressJs', 'TECHNICAL_SKILL'),
        (5, null, 'C#', 'TECHNICAL_SKILL'),
        (6, null, '.Net', 'TECHNICAL_SKILL'),
        (7, null, 'python', 'TECHNICAL_SKILL'),
        (8, null, 'Linux', 'TECHNICAL_SKILL'),
        (9, null, 'Design', 'TECHNICAL_SKILL'),
        (10, null, 'NoSQL', 'TECHNICAL_SKILL'),
        (11, null, 'SQL', 'TECHNICAL_SKILL');

insert into lab05.address (add_id, city, country, number, street, zipcode)
values  (1, 'Hồ Chí Minh', 704, '212', 'Nguyễn Oanh', '71400'),
        (2, 'Hồ Chí Minh', 704, '111', 'Lê Đức Thọ', '71400'),
        (3, 'Hà Nội', 704, '205/12', 'đường số 20', '71400'),
        (4, 'Hồ Chí Minh', 704, '412', 'Bạch Đằng ', '71400'),
        (5, 'Hà Nội', 704, '132', 'Yên Lãng', '71400'),
        (6, 'Đà Nẵng', 704, 'hẻm 350', 'Lê Văn Thọ', '71400'),
        (7, 'Hồ Chí Minh', 704, '3/2', 'Mai Chí Thọ', '71400');

insert into lab05.cadidate (dob, full_name, id)
values  ('2024-12-15', 'admin', 1),
        ('2003-09-28', 'Trần Lê Thiên Trí', 2),
        ('2024-12-10', 'Lê Mạnh Quỳnh', 3),
        ('2024-12-03', 'Nguyễn Văn A', 4);

insert into lab05.company (about, full_name, web_url, id)
values  ('NFQ is part of the global NFQ Group. We help ambitious fast-moving international startups
As a part of the global NFQ Group with 20+ years of experience scaling companies and a team of 800++ developers globally, NFQ Vietnam focuses on building high-performance dedicated teams for ambitious tech-driven businesses in Europe and Asia.
Each team here works directly with the client from end to end in an Agile spirit. This unique set up gives our employees the experience of working for a real product company, with a real sense of commitment and ownership to the product itself.
NFQ Vietnam has more than 250+ engineers located in Hochiminh, Danang, Hanoi & Cantho offices. We are recognized as top-notch place to work in Vietnam. JOIN US!', 'NFQ Asia', 'https://www.nfq.com/', 5),
        ('Vietnam''s premium e-commerce solutions provider
Established in 2006, SmartOSC is a premium, full-service e-commerce agency. We offer simple yet effective solutions, from consulting, website development, UX/UI design to managed services. With a variety of large customers in North America, Asia, ANZ and Europe, SmartOSC’s technical and business expertise is built around selected platforms including Adobe Commerce (Magento), Sitecore, BigCommerce, Shopify Plus, Kentico, Optimizely and Salesforce Commerce Cloud.

Over the years, SmartOSC has attracted both tech and non-tech specialists, increasing the number of employees to over 1,000 across Vietnam, Singapore, Japan, USA, Australia, UK, Thailand, Korea and Indonesia. With a “people-centric” strategy, we always strive to enable employees to grow sustainably and pursue their passion with a transparent career path.', 'SmartOSC', 'https://careers.smartosc.com/', 6),
        ('Making Financial Inclusion a Reality using Machine Learning and AI
We are an AI Fintech company specialized in assessing credit profiles of consumers in emerging markets combining pioneering AI with large alternative data sources. In 2020 we reached our ambitious milestone of credit profiling 1bn consumers spanning 4 countries - Vietnam, Indonesia, India & the Philippines - and building a platform for the wider industry and the financial services industry, in particular, to provide the ''un & under'' served access to credit. At the core of this initiative has been our strict and unwavering adherence to the norms of consumer data privacy and consumer data rights.

But we''re not satisfied as we embark on the next leg of our journey to deliver 100 million credit lines to consumers in the markets where we operate. Although this goal is ambitious, we truly believe that by harnessing the power of AI & Big Data we can deliver financial access at an unprecedented scale.

As a firm, we''re audacious problem-solvers motivated by our impact on society. We deeply espouse the values of ownership - of our actions and initiatives, integrity in all we do and agility in execution.', ' Trusting Social', 'https://trustingsocial.com/', 7),
        ('TymeX is a part of Tyme Group - one of the world’s fastest-growing digital banking groups.
Tyme Group is one of the world’s fastest-growing digital banking groups, building high-tech and high-touch banks in fast-growing, emerging markets. Headquartered in Singapore with a Technology and product Development Hub in Vietnam, Tyme designs, builds, and commercializes digital banks for emerging markets, with particular expertise in serving under-served and under-banked populations.
Established in 2016, TymeX has been Tyme Group''s Product & Technology Development Hub, bringing together engineering and product people who share a global mission to become serial bank builders, shaping the future of banking through technology. We build products with the principle of finding the right balance between the digital and physical worlds.
We have proudly provided the banking platform as a service for:
TymeBank, based in South Africa, is one of the world’s fastest-growing digital banks, with over 7 million customers since launching in February 2019.
GoTyme Bank, based in the Philippines, is a joint venture between the Gokongwei Group and Tyme Group with the official launch in October 2022 and onboarded more than 1 million customers in less than nine months.', 'TymeX', 'https://www.vietnam.tyme.com/', 8);


insert into lab05.user (id, is_active, email, password, phone, address, role_id)
values  (1, true, 'thientri.trank17@gmail.com', '$2a$10$MrGsrbLDHfnPsGlsvJS2ZOkZJycSWDjYuWr8SmnneWoC1gsVFl3iS', '0385788328', null, 2),
        (2, true, 'thienni198@gmail.com', '$2a$10$rXuuYrNA.2OoZa8iIi9im.iN89frAGsY2DCCd8XWm2ADEODv4fRH2', '0938749250', 1, 1),
        (3, true, 'example.email@gmail.com', '$2a$10$F4F.ypD05Jtv9zQWhHfRIuV5Vr7ebC68cR7wly4LND0FMVOdi.5kS', '0909123123', 2, 1),
        (4, true, 'example2.email@gmail.com', '$2a$10$lixf4yiGmkeE.N/CbRJfOuLMuPmkTohPBaoxIIwWOwvsbioUo2eee', '0321321321', 3, 1),
        (5, true, 'nfq@gmail.com', '$2a$10$wlijedfOgIFpu8WVoPlIYOIP1GNSD17aqBPM54GShO9YPdTdJuJN2', '0322678678', 4, 3),
        (6, true, 'smart@gmail.com', '$2a$10$1C9EC/vX0zuUI2Y/auLw3uZ9XM0swwi1pGLYoqMLSD7vb0Sc5Ij8C', '0567891212', 5, 3),
        (7, true, 'trus@tr.com.vn', '$2a$10$9JzkA3A/KTHSH/WpEE8MBupz0T.G9pwTuGErwbzoey/YChMm8t/ZG', '0321456456', 6, 3),
        (8, true, 'tymeX@tyme.com', '$2a$10$u9Fvg9l2RQ4UsItPbYXB.usVD60d6QL.C8tv8Q/Ff9FWB5xakqfCu', '0123123456', 7, 3);



insert into lab05.job (job_id, created_at, job_desc, level, job_name, salary, updated_at, company)
values  (1, '2024-12-15 22:59:18.621696', 'Responsible for managing and communicating with relevant departments regarding the project.
Responsible for planning, monitoring project progress, managing costs, and ensuring project quality.
Perform other tasks as assigned by superiors. ', 'PROFESSIONAL', 'Java Team Leader ~60M Gross (Onboard After Tet)', 5000000, '2024-12-15 22:59:18.741488', 6),
        (2, '2024-12-15 22:59:54.409998', 'Java Backend - Phỏng vấn trước tết/Đi làm sau tết', 'BEGINER', 'Java Backend - Phỏng vấn trước tết/Đi làm sau tết', 15000000, '2024-12-15 22:59:54.529002', 6),
        (3, '2024-12-15 23:01:46.684648', 'We are looking for a talented Salesforce developer to design world-class Salesforce applications for our evolving CRM requirements. As a Salesforce developer, you will be responsible for developing customized solutions within the Salesforce platform. You will also analyze project objectives, create customer workflows, and troubleshoot errors.

To ensure success as a Salesforce developer, you should have extensive experience working with Salesforce CRM platforms, application development skills, and the ability to solve complex software problems.

Salesforce Developer Responsibilities:

Meeting with project managers to determine CRM needs.
Developing customized solutions within the Salesforce platform.
Designing, coding, and implementing Salesforce applications.
Creating timelines and development goals.
Testing the stability and functionality of the application.
Troubleshooting and fixing bugs.
Writing documents and providing technical training for Salesforce staff.', 'PROFESSIONAL', 'Salesforce Developer (Junior & Expert)', 55000000, '2024-12-15 23:01:46.713600', 7),
        (4, '2024-12-15 23:03:37.021616', 'Design and implement System Framework to verify device driver
Creating control features to ensure systems effectively meet the organization’s quality standards
Design and implement window application for automation test system (error detection, error analysis, log collection), which external controls multiple devices and graphic driver (PDM/HDPC, MSPG, EIDEN) / NVidia card.
Log analysis to define & resolve market issue, ensure product reliability.
Ensures completion of all documentation required for production introduction.
Use GitHub for source code repository and Jira for task tracking.', 'MASTER', 'System Software (Embedded, Linux)', 15000000, '2024-12-15 23:03:37.053526', 7),
        (5, '2024-12-15 23:04:49.378682', 'Develop scalable, highly secure, and high-performance applications
Design and build a complex architecture of new products and features
Quickly fix system failures or abnormalities by monitoring or notifications
Introduce tools, systems, and cultures for efficient development
Develop fundamental skills of team members
Resolve any technical issues, risks, and challenges, escalating as necessary and providing solutions', 'IMTERMEDIATE', 'HCM - Principal Java Spring Boot/ Kotlin Engineer', 12000000, '2024-12-15 23:04:49.409753', 8);

insert into lab05.job_skill (more_infos, skill_level, job_id, skill_id)
values  (null, null, 1, 1),
        (null, null, 1, 8),
        (null, null, 1, 10),
        (null, null, 1, 11),
        (null, null, 2, 1),
        (null, null, 2, 2),
        (null, null, 2, 4),
        (null, null, 2, 9),
        (null, null, 2, 11),
        (null, null, 3, 1),
        (null, null, 3, 5),
        (null, null, 3, 8),
        (null, null, 3, 10),
        (null, null, 3, 11),
        (null, null, 4, 5),
        (null, null, 4, 6),
        (null, null, 4, 11),
        (null, null, 5, 1),
        (null, null, 5, 4),
        (null, null, 5, 7);

insert into lab05.candidate_skill (more_infos, skill_level, can_id, skill_id)
values  (null, null, 2, 1),
        (null, null, 2, 3),
        (null, null, 2, 7),
        (null, null, 2, 10),
        (null, null, 3, 2),
        (null, null, 3, 3),
        (null, null, 3, 8),
        (null, null, 4, 3),
        (null, null, 4, 5),
        (null, null, 4, 8);