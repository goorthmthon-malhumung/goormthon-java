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
    main_url VARCHAR(500) NULL COMMENT '메인 사진 URL',
    media_url     VARCHAR(500) NULL      COMMENT '직업 동영상 URL',
    media_url2     VARCHAR(500) NULL      COMMENT '직업 동영상 URL',
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
    photo_url2       VARCHAR(500) NULL COMMENT '체험 사진 URL',
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


CREATE USER 'myuser'@'%' IDENTIFIED BY 'mypassword';
GRANT ALL PRIVILEGES ON mydb.* TO 'myuser'@'%';
FLUSH PRIVILEGES;