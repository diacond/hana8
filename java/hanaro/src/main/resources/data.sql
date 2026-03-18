-- 1. 유저 데이터 (사용자 & 관리자)
INSERT INTO users (email, password, nickname, role) VALUES 
('tiger8745@naver.com', '12345678', '신호림', 'USER'),
('admin@hana.com', '12345678', '관리자', 'ADMIN');

-- 2. 계좌 데이터 (회원가입 시 생성된 자유입출금 통장)
INSERT INTO accounts (account_number, balance, user_id) VALUES 
('01029989641', 900000, 1); -- 신호림의 자유입출금 통장 (초기 100만 - 10만 적금납입)

-- 3. 상품 데이터 (하나 든든 적금 & 하나 정기 예금)
INSERT INTO products (name, type, deposit_amount, payment_cycle, onsale, duration_months, maturity_yield, cancellation_yield, image_path) VALUES 
('하나 든든 적금', 'SAVINGS', 100000, 'MONTHLY', 'YES', 36, 3.00, 1.50, '/images/hana_savings.png'),
('하나 정기 예금', 'DEPOSIT', 1000000, 'MONTHLY', 'YES', 12, 2.50, 1.00, '/images/hana_deposit.png');

-- 4. 상품 가입 내역 및 관련 계좌 (신호림이 '하나 든든 적금' 가입)
-- 4-1. 상품 가입용 전용 계좌 생성
INSERT INTO accounts (account_number, balance, user_id) VALUES 
('222-2222-2222', 100000, 1); -- 가입된 적금 계좌 (10만 원 입금됨)

-- 4-2. 가입 히스토리 기록 (한 달 전 가입한 것으로 설정하여 이자 발생 확인)
INSERT INTO history (user_id, product_id, account_id, created_at, status) VALUES 
(1, 1, 2, '2026-02-16 10:00:00', 'ACTIVE');

-- 5. 거래 내역 (Transaction) 기록 (이자 계산의 핵심 소스)
-- 적금 가입 시 10만 원 이체 내역
INSERT INTO transactions (from_account_id, to_account_id, amount, type, description, created_at) VALUES 
(1, 2, 100000, 'TRANSFER', '하나 든든 적금 가입 납입', '2026-02-16 10:00:00');
