DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
    `seq` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(30) NOT NULL,
    `id` varchar(50) NOT NULL,
    `password` varchar(65) NOT NULL,
    `phone` varchar(20) NOT NULL,
    PRIMARY KEY (`seq`)
);

DROP TABLE IF EXISTS `reservation`;

CREATE TABLE `reservation` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `member_seq` bigint NOT NULL,
    `restaurant_id` bigint NOT NULL,
    `status` varchar(10) NOT NULL,
    `member_count` bigint NOT NULL,
    `created_at` timestamp NOT NULL,
    `reservation_start_at` timestamp NOT NULL,
    `reservation_end_at` timestamp NOT NULL,
    `cancel_reason` varchar(10) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `restaurant`;

CREATE TABLE `restaurant` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    `address` varchar(100) NOT NULL,
    `latitude` decimal(13,10) NOT NULL,
    `longitude` decimal(13,10) NOT NULL,
    `max_member_count` bigint NOT NULL,
    `max_available_day` bigint NOT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `daily_schedule`;

CREATE TABLE `daily_schedule` (
    `id` bigint NOT NULL,
    `day` varchar(10) NOT NULL,
    `open_time` time NOT NULL,
    `close_time` time NOT NULL,
    `split_time` bigint NOT NULL
);

DROP TABLE IF EXISTS `specific_schedule`;

CREATE TABLE `specific_schedule` (
    `id` bigint NOT NULL,
    `date` date NOT NULL,
    `open_time` time NOT NULL,
    `close_time` time NOT NULL,
    `split_time` bigint NOT NULL
);

INSERT INTO member(name, id, password, phone)
VALUES
("오소영", "syoh", "qwer123", "010-1111-2222"),
("메버릭", "maverick", "asdf123", "010-3333-4444");

INSERT INTO reservation(member_seq, restaurant_id, status, member_count, created_at, reservation_start_at, reservation_end_at)
VALUES
(1, 1, 'SUCCESS', 3, NOW(), '2024-08-13 11:00:00', '2024-08-13 12:00:00'),
(1, 2, 'SUCCESS', 5, NOW(), '2024-08-13 12:00:00', '2024-08-13 12:30:00'),
(2, 1, 'SUCCESS', 2, NOW(), '2024-08-15 10:00:00', '2024-08-15 11:00:00'),
(2, 2, 'SUCCESS', 1, NOW(), '2024-08-15 11:00:00', '2024-08-15 12:00:00');

INSERT INTO restaurant(name, address, latitude, longitude, max_member_count, max_available_day)
VALUES
('R1', '서울 강서구 마곡서로 152', 37.5676859105, 126.8259794500, 10, 5),
('R2', '서울 강서구 마곡중앙로 136', 33.2588494316, 126.4061074950, 4, 3);

INSERT INTO daily_schedule(id, day, open_time, close_time, split_time)
VALUES
(1, 'MONDAY', '10:00:00', '15:00:00', 60),
(1, 'WEDNESDAY', '10:00:00', '15:00:00', 60),
(1, 'FRIDAY', '10:00:00', '15:00:00', 60),
(1, 'SATURDAY', '10:00:00', '13:00:00', 30),
(1, 'SUNDAY', '10:00:00', '13:00:00', 30),
(2, 'FRIDAY', '09:00:00', '19:00:00', 60),
(2, 'SATURDAY', '10:00:00', '19:00:00', 60),
(2, 'SUNDAY', '10:00:00', '20:00:00', 60);

INSERT INTO specific_schedule(id, date, open_time, close_time, split_time)
VALUES
(2, '2024-08-16', '15:00:00', '16:00:00', 60);