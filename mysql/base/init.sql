-- spmple SQL script --
CREATE DATABASE IF NOT EXISTS mydb;
USE mydb;

CREATE TABLE IF NOT EXISTS member
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name`      VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR (255) NOT NULL,
    is_mentor  BOOLEAN   NOT NULL,
    job_title  VARCHAR(100) NOT NULL DEFAULT '멘티' COMMENT '직종',
    work_year INT          NOT NULL DEFAULT 0 COMMENT '경력 (년)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS job
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id        BIGINT       NOT NULL COMMENT '직업을 등록한 사용자(멘토) ID',
    title          VARCHAR(200) NOT NULL COMMENT '직업명',
    job_type       VARCHAR(100) NOT NULL COMMENT '직업 종류',
    skills         JSON         NOT NULL COMMENT '배울 수 있는 기술 (배열)',
    work_hours     VARCHAR(100) NOT NULL COMMENT '근무 시간 (예: 09:00~18:00)',
    physical_level ENUM('LOW', 'MEDIUM', 'HIGH') NOT NULL COMMENT '체력 요구도',
    photo_url      VARCHAR(500) NULL COMMENT '직업 사진 URL',
    photo2_url     VARCHAR(500) NULL COMMENT '직업 사진2 URL',
    main_url       VARCHAR(500) NULL COMMENT '메인 사진 URL',
    media_url      VARCHAR(500) NULL COMMENT '직업 동영상 URL',
    media_url2     VARCHAR(500) NULL COMMENT '직업 동영상2 URL',
    created_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS experience_detail
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT       NOT NULL COMMENT '체험을 등록한 사용자(멘토) ID',
    experience_type  ENUM('HAENYEO', 'DOLDAM', 'GAMGYUL', 'MOKJANG') NOT NULL COMMENT '체험 종류',
    experience_date  DATE         NOT NULL COMMENT '체험 날짜',
    experience_time  TIME         NOT NULL COMMENT '체험 시간',
    location         VARCHAR(300) NOT NULL COMMENT '장소',
    max_participants INT          NOT NULL DEFAULT 10 COMMENT '최대 참가 인원',
    introduction     TEXT         NOT NULL COMMENT '체험 소개',
    schedule         TEXT         NOT NULL COMMENT '체험 일정',
    inclusions       JSON NULL COMMENT '포함 사항',
    requirements     JSON NULL COMMENT '참가 요건',
    main_url VARCHAR(500) NULL COMMENT '메인 사진 URL',
    price BIGINT NOT NULL COMMENT '가격',
    photo_url        VARCHAR(500) NULL COMMENT '체험 사진 URL',
    photo_url2       VARCHAR(500) NULL COMMENT '체험 사진 URL2',
    media_url        VARCHAR(500) NULL COMMENT '체험 동영상 URL',
    media_url2       VARCHAR(500) NULL COMMENT '체험 동영상 URL2',
    skills           JSON      NULL     COMMENT '배울 수 있는 기술 (예: ["해산물 채취", "물질 호흡법"])',
    job_id           BIGINT    NULL     COMMENT '연관 직업 ID',
    created_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS experience_booking (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    experience_id BIGINT    NOT NULL COMMENT '예약할 체험 ID',
    user_id       BIGINT    NOT NULL COMMENT '예약한 사용자 ID',
    start_date    DATE      NOT NULL COMMENT '예약 시작일',
    end_date      DATE      NOT NULL COMMENT '예약 종료일',
    status        ENUM('PENDING', 'CONFIRMED', 'CANCELLED', 'COMPLETED') NOT NULL DEFAULT 'PENDING' COMMENT '예약 상태',
    created_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- =============================================
-- FAVORITE_JOB_LOG 테이블 (관심 직업 로그)
-- =============================================
CREATE TABLE IF NOT EXISTS favorite_job_log
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT    NOT NULL COMMENT '사용자 ID',
    job_id     BIGINT    NOT NULL COMMENT '관심 등록한 직업 ID',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


-- =============================================
-- CATEGORY_INTRO 테이블 (카테고리 소개)
-- =============================================
CREATE TABLE IF NOT EXISTS category_intro
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_type ENUM('EXPERIENCE', 'JOB') NOT NULL COMMENT '카테고리 종류 (체험/직업)',
    category_name VARCHAR(100)              NOT NULL COMMENT '카테고리 이름 (예: HAENYEO, 해녀어업 등)',
    introduction  TEXT                      NOT NULL COMMENT '카테고리 소개글',
    created_at    TIMESTAMP                 NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP                 NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO category_intro (category_type, category_name, introduction) VALUES
('EXPERIENCE', 'HAENYEO', '${experience} 경력의 ${name} 해녀님과 함께하는 물질 체험입니다. 전통 해녀복을 입고 바다에 들어가 직접 해산물을 채취하며 제주 해녀 문화를 체험할 수 있습니다. 초보자도 안전하게 참여할 수 있도록 구명조끼와 안전 장비가 제공되며, 해녀님의 세심한 지도 아래 진행됩니다.'),
('EXPERIENCE', 'DOLDAM', '${experience} 경력의 ${name} 장인님과 함께하는 돌담 쌓기 체험입니다. 제주의 검은 현무암을 직접 손으로 쌓으며 수백 년간 이어온 제주 돌담 문화를 체험할 수 있습니다. 처음 접하시는 분도 편안하게 참여할 수 있도록 장인님의 차근차근한 설명과 함께 진행됩니다.'),
('EXPERIENCE', 'GAMGYUL', '${experience} 경력의 ${name} 농부님과 함께하는 감귤 수확 체험입니다. 제주의 햇살을 가득 받은 감귤을 직접 손으로 따며 제주 감귤 농업의 일상을 느껴볼 수 있습니다. 수확한 감귤은 직접 맛보실 수 있으며, 농부님의 안내 아래 감귤밭 곳곳을 둘러보실 수 있습니다.'),
('EXPERIENCE', 'MOKJANG', '${experience} 경력의 ${name} 목장지기님과 함께하는 목장 체험입니다. 제주의 드넓은 초원에서 말과 소를 직접 돌보며 목장 생활을 경험할 수 있습니다. 동물이 처음이신 분도 안심하고 참여하실 수 있도록 목장지기님이 처음부터 끝까지 함께합니다.'),
('JOB', 'HAENYEO', '${experience} 경력의 ${name} 해녀입니다. 제주 바다와 함께한 오랜 세월 동안 쌓아온 물질 기술과 바다에 대한 깊은 이해를 바탕으로, 해녀로서의 삶을 진솔하게 나눠드립니다. 전통 방식 그대로의 물질 작업과 함께 해녀 공동체의 문화도 함께 전해드립니다.'),
('JOB', 'DOLDAM', '${experience} 경력의 ${name} 돌담 장인입니다. 제주의 바람과 함께 켜켜이 쌓아온 돌담 기술을 바탕으로, 현무암 하나하나를 다루는 방법부터 마을 돌담을 유지·보수하는 노하우까지 성실히 전해드립니다.'),
('JOB', 'GAMGYUL', '${experience} 경력의 ${name} 감귤 농부입니다. 제주의 기후와 토양을 깊이 이해하며 감귤을 길러온 경험을 바탕으로, 농약 관리부터 수확과 출하까지 감귤 농업의 전 과정을 함께합니다.'),
('JOB', 'MOKJANG', '${experience} 경력의 ${name} 목장지기입니다. 제주의 드넓은 초원에서 말과 소를 돌보며 쌓아온 경험을 바탕으로, 동물 관리와 목초지 운영의 실질적인 노하우를 차분히 나눠드립니다.');




-- =============================================
-- SAMPLE DATA
-- =============================================

-- member (멘토 4명 + 멘티 4명)
INSERT INTO member (name, phone, password, is_mentor, job_title, work_year, introduce) VALUES
('김해녀', '010-1111-0001', '$2a$10$dummyhash1aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', TRUE,  '해녀',      20, '제주 바다와 함께한 20년 경력의 해녀입니다. 물질 기술과 해녀 문화를 나누고 싶습니다.'),
('박돌담', '010-1111-0002', '$2a$10$dummyhash2aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', TRUE,  '돌담 장인',  15, '제주 현무암 돌담을 15년째 쌓아온 장인입니다. 전통 방식 그대로의 기술을 전수합니다.'),
('이감귤', '010-1111-0003', '$2a$10$dummyhash3aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', TRUE,  '감귤 농부',  10, '서귀포에서 10년째 감귤 농사를 짓고 있습니다. 수확부터 출하까지 함께합니다.'),
('최목장', '010-1111-0004', '$2a$10$dummyhash4aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', TRUE,  '목장지기',  12, '제주 초원에서 말과 소를 12년째 돌보고 있습니다. 목장 생활의 모든 것을 알려드립니다.'),
('홍길동', '010-2222-0001', '$2a$10$dummyhash5aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', FALSE, '멘티',       0, '제주 전통 문화에 관심이 많은 청년입니다.'),
('이민지', '010-2222-0002', '$2a$10$dummyhash6aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', FALSE, '멘티',       0, '바다와 자연을 좋아해서 해녀 체험에 관심이 생겼습니다.'),
('박준혁', '010-2222-0003', '$2a$10$dummyhash7aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', FALSE, '멘티',       0, '제주로 이주를 꿈꾸며 다양한 직종을 탐색 중입니다.'),
('김수연', '010-2222-0004', '$2a$10$dummyhash8aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', FALSE, '멘티',       0, '농촌 생활에 관심이 많아 귀농을 준비하고 있습니다.');

-- job (멘토별 대표 직업 1개씩)
INSERT INTO job (user_id, title, job_type, skills, work_hours, physical_level, photo_url, main_url) VALUES
(1, '제주 해녀',     'HAENYEO', '["물질 호흡법", "해산물 채취", "테왁 다루기", "바다 지형 파악"]', '05:00~11:00', 'HIGH',   'https://junhuageapi.goorm.training/api/photo/haenyeo_photo.png',  'https://junhuageapi.goorm.training/api/photo/haenyeo_main.png'),
(2, '제주 돌담 장인', 'DOLDAM',  '["현무암 가공", "돌담 설계", "전통 건축 기법", "돌담 보수"]',    '08:00~17:00', 'MEDIUM', 'https://junhuageapi.goorm.training/api/photo/stone_photo.png',   'https://junhuageapi.goorm.training/api/photo/stone_main.png'),
(3, '감귤 농부',     'GAMGYUL', '["감귤 수확", "병해충 관리", "비료 관리", "선별 및 출하"]',       '07:00~16:00', 'LOW',    'https://junhuageapi.goorm.training/api/photo/mandarin_photo.png',  'https://junhuageapi.goorm.training/api/photo/mandarin_main.png'),
(4, '제주 목장지기', 'MOKJANG', '["말 관리", "소 관리", "목초지 운영", "동물 질병 예방"]',         '06:00~18:00', 'MEDIUM', 'https://junhuageapi.goorm.training/api/photo/mokjang_photo.png',  'https://junhuageapi.goorm.training/api/photo/mokjang_main.png');

-- experience_detail (체험 4종)
INSERT INTO experience_detail (user_id, experience_type, experience_date, experience_time, location, max_participants, introduction, schedule, inclusions, requirements, price, skills, job_id, photo_url, main_url) VALUES
(1, 'HAENYEO', '2026-05-10', '09:00:00',
 '제주시 구좌읍 하도리 해녀의 집', 8,
 '20년 경력의 김해녀님과 함께하는 물질 체험입니다. 전통 해녀복을 입고 바다에 들어가 직접 해산물을 채취하며 제주 해녀 문화를 체험할 수 있습니다.',
 '09:00 집합 및 안전 교육 > 09:30 해녀복 착용 > 10:00 바다 입수 및 물질 체험 > 11:30 수확물 시식 > 12:00 해산',
 '["구명조끼", "해녀복", "물안경", "오리발"]',
 '["수영 가능자", "건강한 신체"]',
 50000, '["물질 호흡법", "해산물 채취"]', 1,
 'https://junhuageapi.goorm.training/api/photo/haenyeo_photo.png',
 'https://junhuageapi.goorm.training/api/photo/haenyeo_main.png'),
(2, 'DOLDAM', '2026-05-15', '10:00:00',
 '제주시 애월읍 돌담 문화마을', 10,
 '15년 경력의 박돌담 장인님과 함께하는 돌담 쌓기 체험입니다. 제주의 검은 현무암을 직접 손으로 쌓으며 수백 년간 이어온 제주 돌담 문화를 체험할 수 있습니다.',
 '10:00 집합 및 돌담 역사 설명 > 10:30 돌 고르기 및 기초 쌓기 실습 > 12:00 완성 기념 촬영 > 12:30 해산',
 '["작업 장갑", "앞치마"]',
 '["특별한 제한 없음"]',
 30000, '["돌 고르기", "기초 쌓기 기법"]', 2,
 'https://junhuageapi.goorm.training/api/photo/stone_photo.png',
 'https://junhuageapi.goorm.training/api/photo/stone_main.png'),
(3, 'GAMGYUL', '2026-05-20', '09:00:00',
 '서귀포시 남원읍 감귤 농원', 15,
 '10년 경력의 이감귤 농부님과 함께하는 감귤 수확 체험입니다. 제주의 햇살을 가득 받은 감귤을 직접 손으로 따며 감귤 농업의 일상을 느껴볼 수 있습니다.',
 '09:00 집합 및 농장 안내 > 09:30 감귤 수확 실습 > 11:00 수확한 감귤 시식 및 포장 > 11:30 해산',
 '["수확 가위", "수확 바구니", "감귤 1kg 포장 제공"]',
 '["특별한 제한 없음"]',
 25000, '["감귤 수확 기법", "품질 감별"]', 3,
 'https://junhuageapi.goorm.training/api/photo/mandarin_photo.png',
 'https://junhuageapi.goorm.training/api/photo/mandarin_main.png'),
(4, 'MOKJANG', '2026-06-05', '10:00:00',
 '제주시 한경면 저지리 목장', 12,
 '12년 경력의 최목장 목장지기님과 함께하는 목장 체험입니다. 제주의 드넓은 초원에서 말과 소를 직접 돌보며 목장 생활을 경험할 수 있습니다.',
 '10:00 집합 및 목장 소개 > 10:30 말 먹이주기 및 브러싱 > 11:30 소 돌보기 체험 > 12:30 해산',
 '["작업복", "장갑", "목장 도시락 제공"]',
 '["동물 알레르기 없는 분"]',
 40000, '["말 다루기", "소 먹이주기", "목초지 관리"]', 4,
 'https://junhuageapi.goorm.training/api/photo/mokjang_photo.png',
 'https://junhuageapi.goorm.training/api/photo/mokjang_main.png');

-- experience_booking
INSERT INTO experience_booking (experience_id, user_id, start_date, end_date, status) VALUES
(1, 5, '2026-05-10', '2026-05-10', 'CONFIRMED'),
(1, 6, '2026-05-10', '2026-05-10', 'PENDING'),
(2, 5, '2026-05-15', '2026-05-15', 'CONFIRMED'),
(2, 7, '2026-05-15', '2026-05-15', 'COMPLETED'),
(3, 6, '2026-05-20', '2026-05-20', 'PENDING'),
(3, 8, '2026-05-20', '2026-05-20', 'CONFIRMED'),
(4, 7, '2026-06-05', '2026-06-05', 'CANCELLED'),
(4, 8, '2026-06-05', '2026-06-05', 'CONFIRMED');

-- favorite_job_log
INSERT INTO favorite_job_log (user_id, job_id) VALUES
(5, 1), (5, 3),
(6, 2), (6, 4),
(7, 1), (7, 2),
(8, 3), (8, 4);

CREATE USER 'myuser'@'%' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON mydb.* TO 'myuser'@'%';
FLUSH PRIVILEGES;
