insert into my_wallets values("my", 1, 1000);
insert into my_wallets values("my", 2, 200000);

insert into users(create_date, modified_date, login_id, identification, name, password, phone_number, user_type, account_member_id, my_wallet_id) values('2023-08-14 17:28:25.306829', '2023-08-14 17:28:25.306829', 'test1', '001004-3234567', '최안식', '$2a$10$a6nTLbQFCipdRD3xqGLLn.EA95hTmdrCtU6kZ0oK99Fr3x13eMV0G', '010-0987-6543', '0', NULL, 1);
insert into users(create_date, modified_date, login_id, identification, name, password, phone_number, user_type, account_member_id, my_wallet_id) values('2023-08-14 17:28:25.306829', '2023-08-14 17:28:25.306829', 'test2', '001004-4234568', '민새미', '$2a$10$a6nTLbQFCipdRD3xqGLLn.EA95hTmdrCtU6kZ0oK99Fr3x13eMV0G', '010-1234-5678', '0', NULL, 2);